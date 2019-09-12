import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { RateQuoteService } from '../../services/rate-quote.service';
import { RateSearch, RateSummary } from '../../models';
import { Observable, Subscription, Subject, merge, of } from 'rxjs';
import { MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { map, startWith, switchMap, catchError, takeUntil, tap } from 'rxjs/operators';
import { extractDateFromString, SnackbarService } from 'common';
import { trigger, state, style, transition, animate } from '@angular/animations';

@Component({
  selector: 'app-quote-history',
  templateUrl: './quote-history.component.html',
  styleUrls: ['./quote-history.component.scss'],
  animations: [
    trigger('detailExpand', [
      state(
        'collapsed',
        style({ height: '0px', minHeight: '0', display: 'none', overflow: 'hidden' })
      ),
      state('expanded', style({ height: '*', overflow: 'hidden' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)'))
    ])
  ]
})
export class QuoteHistoryComponent implements OnInit, OnDestroy {
  private stop$ = new Subject<boolean>();
  private refreshData = new Subject<boolean>();
  private rateSearch: RateSearch;
  expandedElement: any;
  loading = false;
  pageSize = 25;
  displayedColumns = [
    'quoteID',
    'quoteDate',
    'serviceLevel',
    'originZip',
    'originState',
    'destZip',
    'destState',
    'estCharges',
    'actions'
  ];
  resultsLength: number;
  tableSub: Subscription;
  dataSource = new MatTableDataSource<RateSummary>();
  noData = this.dataSource.connect().pipe(map(data => data.length === 0));
  data$: Observable<Array<RateSummary>>;
  expandSearch = false;
  activeAdvancedSearch = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private rateQuoteService: RateQuoteService, private snackbar: SnackbarService) {}

  ngOnInit() {
    this.initTable();
  }

  ngOnDestroy() {
    this.stop$.next(true);
    this.stop$.unsubscribe();

    if (this.tableSub) {
      this.tableSub.unsubscribe();
    }
  }

  onQuoteSelected(element: RateSummary) {
    if (this.expandedElement === element) {
      this.expandedElement = null;
    } else {
      if (element.rateQuote) {
        this.expandedElement = element;
      } else {
        element.isSelecting = true;
        this.rateQuoteService.getQuoteById(element.quoteID).subscribe(
          next => {
            element.rateQuote = next;
            this.expandedElement = element;
          },
          err => {
            element.isSelecting = false;
            let msg = `An unexpected error occurred while retrieving quote details.  Please try again.`;
            if (err.error.message) {
              msg = err.error.message;
            }
            this.snackbar.error(msg);
          },
          () => (element.isSelecting = false)
        );
      }
    }
  }

  private initTable() {
    this.paginator.pageSize = this.pageSize;
    this.sort.active = 'quoteDate';
    this.sort.direction = 'desc';

    this.tableSub = merge(this.sort.sortChange, this.paginator.page, this.refreshData)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.stop$.next(true);
          this.loading = true;
          return this.rateQuoteService
            .getQuoteHistory(this.getRateSearchObject(), this.getFilterOptions())
            .pipe(
              takeUntil(this.stop$)
            );
        }),
        map(data => {
          this.resultsLength = data.totalElements;

          return data.content;
        }),
        catchError(err => {
          return of([]);
        })
      )
      .subscribe(data => {
        this.loading = false;
        this.dataSource.data = data;
      });
  }

  onFilterChange(event: RateSearch | null) {
    if (!event) {
      this.activeAdvancedSearch = false;
    } else {
      this.activeAdvancedSearch = true;
    }

    this.rateSearch = event || new RateSearch();
    this.paginator.firstPage();
    this.refreshData.next(true);
  }

  private getRateSearchObject(): RateSearch | any {
    return this.rateSearch || {};
  }

  private getFilterOptions(): {
    order: 'asc' | 'desc';
    sort: 'QUOTENUM' | 'TIMST' | 'SVCLVL' | 'ORGZIP' | 'DESZIP' | 'NETCHG';
    page: number;
    size: number;
  } {
    const payload: any = {};
    let sort: string;

    switch (this.sort.active) {
      case 'quoteID':
        sort = 'QUOTENUM';
        break;
      case 'quoteDate':
        sort = 'TIMST';
        break;
      case 'serviceLevel':
        sort = 'SVCLVL';
        break;
      case 'originZip':
        sort = 'ORGZIP';
        break;
      case 'destZip':
        sort = 'DESZIP';
        break;
      case 'estCharges':
        sort = 'NETCHG';
        break;
    }

    payload.sort = sort;
    payload.order = this.sort.direction;
    payload.page = this.paginator.pageIndex + 1;
    payload.size = this.paginator.pageSize;

    return payload;
  }
}
