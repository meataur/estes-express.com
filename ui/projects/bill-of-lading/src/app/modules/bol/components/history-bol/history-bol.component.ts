import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { BolService } from '../../services/bol.service';
import { Draft } from '../../models';
import { MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { map, startWith, switchMap, catchError } from 'rxjs/operators';
import { Subscription, merge, of, Subject } from 'rxjs';
import { DraftFilterEnum } from '../draft-advanced-search/draft-advanced-search.component';
import { DialogService, SnackbarService } from 'common';
import { Bol } from '../../models/bol.interface';
import { environment } from '../../../../../environments/environment';
import { HistoryModalComponent } from '../history-modal/history-modal.component';
import { CreateShippingLabelModalComponent } from '../create-shipping-label-modal/create-shipping-label-modal.component';

@Component({
  selector: 'app-history-bol',
  templateUrl: './history-bol.component.html',
  styleUrls: ['./history-bol.component.scss']
})
export class HistoryBolComponent implements OnInit, OnDestroy {
  private refreshData = new Subject<boolean>();
  activeAdvancedSearch = false;
  loading = false;
  displayedColumns = [
    'bolNumber',
    'bolDate',
    'proNumber',
    'shipper',
    'consignee',
    'hasShippingLabel',
    'actions'
  ];
  pageSize = 25;
  // results: Array<Draft>;
  dataSource = new MatTableDataSource<Bol>();
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
  loadingShippingLabel: boolean;

  constructor(
    private bolService: BolService,
    private dialog: DialogService,
    private snackbar: SnackbarService
  ) {}

  openHelpModal() {
    this.dialog.prompt(HistoryModalComponent, null);
  }

  private openShippingLabelModal(bol: Bol) {
    this.dialog.prompt(CreateShippingLabelModalComponent, bol).subscribe(next => {
      if (next === true) {
        this.refreshData.next(true);
      }
    });
  }

  ngOnInit() {
    this.paginator.pageSize = this.pageSize;
    this.sort.active = 'bolDate';
    this.sort.direction = 'desc';

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
          return this.bolService.getHistory(
            {
              page: this.paginator.pageIndex + 1,
              size: this.paginator.pageSize,
              sort: this.sort.active,
              direction: this.sort.direction
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

  private open(url: string) {
    if (url) {
      const win = window.open(url, '_blank');
      win.focus();
    }
  }

  viewShippingLabels(bolId: string) {
    this.loadingShippingLabel = true;
    this.bolService.getShippingLabel(bolId).subscribe(
      next => {
        // this.loadingShippingLabel = false;
        // console.log(next);
        this.open(`${environment.serviceBaseUrl}${next.documentUrl}`);
        // const win = window.open(url, '_blank');
        // win.focus();
      },
      err => {
        this.loadingShippingLabel = false;
        this.snackbar.error(
          `An error occurred while retrieving the Shipping Labels.  Please try again.`
        );
      },
      () => (this.loadingShippingLabel = false)
    );
  }

  createShippingLabels(bol: Bol) {
    this.openShippingLabelModal(bol);
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

  view(b: Bol) {
    this.openBol(b);
  }

  private openBol(b: Bol) {
    // this.loadingPdf = true;
    this.bolService.viewBolPdf(b.bolId.toString()).subscribe(
      next => {
        // this.loadingPdf = false;
        // console.log(next);
        this.open(`${environment.serviceBaseUrl}${next.documentUrl}`);
        // const win = window.open(url, '_blank');
        // win.focus();
      },
      err => {
        // this.loadingPdf = false;
        this.snackbar.error(
          `An error occurred while retrieving the Bill of Lading.  Please try again.`
        );
      }
    );
  }

  cancel(b: Bol) {
    this.dialog
      .confirm(
        `Confirm Cancel`,
        `Are you sure you want to cancel the Bill of Lading for Reference Number: ${b.bolNumber}?`
      )
      .subscribe(next => {
        if (next) {
          this.bolService.cancelBillOfLading(b.bolId.toString()).subscribe(
            () => {
              this.snackbar.success(`Bill of Lading canceled successfully.`);
              this.refreshData.next(true);
            },
            err => {
              const errorMessage = err || `An unexpected error occurred while canceling the Bill of Lading.  Please try again.`;
              this.snackbar.error(
                errorMessage
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
