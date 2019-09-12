import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { BolService } from '../../services/bol.service';
import { Draft } from '../../models';
import { MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { map, startWith, switchMap, catchError } from 'rxjs/operators';
import { Subscription, merge, of, Subject } from 'rxjs';
import { DraftFilterEnum } from '../draft-advanced-search/draft-advanced-search.component';
import { DialogService, SnackbarService } from 'common';
import { DraftsModalComponent } from '../drafts-modal/drafts-modal.component';

@Component({
  selector: 'app-drafts-bol',
  templateUrl: './drafts-bol.component.html',
  styleUrls: ['./drafts-bol.component.scss']
})
export class DraftsBolComponent implements OnInit, OnDestroy {
  private refreshData = new Subject<boolean>();
  activeAdvancedSearch = false;
  loading = false;
  displayedColumns = ['bolNumber', 'bolDate', 'proNumber', 'shipper', 'consignee', 'actions'];
  pageSize = 25;
  results: Array<Draft>;
  dataSource = new MatTableDataSource<Draft>();
  noData = this.dataSource.connect().pipe(map(data => data.length === 0));
  tableSub: Subscription;
  resultsLength: number;
  expandSearch = false;
  filterOptions: {
    bolEndDate: string;
    bolNumber: string;
    bolStartDate: string;
    consignee: string;
    filterBy: string;
    proNumber: string;
    shipper: string;
  } = {
    bolEndDate: '',
    bolNumber: '',
    bolStartDate: '',
    consignee: '',
    filterBy: '',
    proNumber: '',
    shipper: ''
  };

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    private bolService: BolService,
    private dialog: DialogService,
    private snackbar: SnackbarService
  ) {}

  openHelpModal() {
    this.dialog.prompt(DraftsModalComponent, null);
  }

  ngOnInit() {
    this.paginator.pageSize = this.pageSize;
    this.sort.active = 'bolNumber';
    this.sort.direction = 'asc';

    // this.dataSource.sortingDataAccessor = (item, property) => {
    //   console.log(item, property);
    //   switch (property) {
    //     case 'bolDate':
    //       console.log(`sorting bolDate`);
    //       return new Date(item.bolDate);
    //     default:
    //       return item[property];
    //   }
    // };

    this.tableSub = merge(this.sort.sortChange, this.paginator.page, this.refreshData)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.loading = true;
          return this.bolService.getDrafts(
            {
              page: this.paginator.pageIndex + 1,
              size: this.paginator.pageSize,
              sort: this.sort.active || 'bolnumber',
              direction: this.sort.direction || 'asc'
            },
            this.filterOptions
          );
        }),
        map(data => {
          this.resultsLength = data.totalElements;

          return data.content;
        }),
        catchError(() => {
          return of([]);
        })
      )
      .subscribe(data => {
        this.loading = false;
        this.dataSource.data = data;
      });
  }

  onFilterChange(e: [DraftFilterEnum, string | string[] | null]) {
    const filter: DraftFilterEnum = e[0];

    this.filterOptions.filterBy = filter;

    switch (DraftFilterEnum[filter]) {
      case DraftFilterEnum.SHOW_ALL:
        this.activeAdvancedSearch = false;
        // No contains filter needed.
        break;
      case DraftFilterEnum.BOL_DATE_RANGE:
        this.filterOptions.bolStartDate = e[1][0];
        this.filterOptions.bolEndDate = e[1][1];
        this.activeAdvancedSearch = true;
        break;
      case DraftFilterEnum.BOL_NUMBER:
        this.filterOptions.bolNumber = e[1] as string;
        this.activeAdvancedSearch = true;
        break;
      case DraftFilterEnum.CONSIGNEE:
        this.filterOptions.consignee = e[1] as string;
        this.activeAdvancedSearch = true;
        break;
      case DraftFilterEnum.SHIPPER:
        this.filterOptions.shipper = e[1] as string;
        this.activeAdvancedSearch = true;
        break;
      case DraftFilterEnum.PRO_NUMBER:
        this.filterOptions.proNumber = e[1] as string;
        this.activeAdvancedSearch = true;
        break;
    }

    this.paginator.firstPage();
    this.refreshData.next(true);
  }

  delete(d: Draft) {
    this.dialog
      .confirm(
        `Confirm Delete`,
        `Are you sure you want to delete the draft for Reference Number: ${d.bolNumber}?`
      )
      .subscribe(next => {
        if (next) {
          this.bolService.deleteDraft(d.bolNumber).subscribe(
            () => {
              this.snackbar.success(`Draft deleted successfully.`);
              this.refreshData.next(true);
            },
            err => {
              this.snackbar.error(
                `An unexpected error occurred while deleting the draft.  Please try again.`
              );
            }
          );
        }
      });
  }

  ngOnDestroy() {
    if (this.tableSub) {
      this.tableSub.unsubscribe();
    }
  }
}
