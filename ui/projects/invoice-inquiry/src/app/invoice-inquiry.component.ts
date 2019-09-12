import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatSort, MatPaginator, MatDialog } from '@angular/material';
import { SelectionModel } from '@angular/cdk/collections';
import { ActivatedRoute, Router } from '@angular/router';
import { merge, Subscription } from 'rxjs';
import { SnackbarService, FormService, FeedbackTypes, AccountSelectorDialogService, RegEx, textAreaValidator } from 'common';
import { AuthService, UtilService, DialogService, PromoService, LeftNavigationService } from 'common';
import { InvoiceInquiryService } from './invoice-inquiry.service';
import { InvoiceSearchRequest } from './models/invoice-search-request.model';
import { AgingDetail } from './models/aging-detail.model';
import { ElementRef } from '@angular/core';

import {
  FormBuilder,
  FormGroup,
  FormArray,
  Validators
} from '@angular/forms';
import { AgingDetailRequest } from './models/aging-detail-request.model';
import { EmailDialogComponent } from './email-dialog/email-dialog.component';

@Component({
  selector: 'app-invoice-inquiry',
  templateUrl: './invoice-inquiry.component.html',
  styleUrls: ['./invoice-inquiry.component.scss']
})
export class InvoiceInquiryComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  formGroup: FormGroup;
  errorMessages: [FeedbackTypes, string[]];
  loadingBuckets: boolean;
  loadingInvoiceDetails: boolean;
  searchingInvoiceDetails: boolean;

  agingSummaryBuckets: number[];
  agingSummaryBucketLabels: string[];
  agingRefreshDate: string;

  invoiceDetails: MatTableDataSource<AgingDetail>;
  invoiceDetailsSelections: SelectionModel<AgingDetail>;
  agingBucketIndex: number;
  agingSummaryTotal: number;
  invoiceDetailsTotalRows: number;
  displayedColumns: string[];
  tableSub: Subscription;
  searchTableTitle: string;
  paramSub: Subscription;
  noAgingSummaryResultsMessage: [FeedbackTypes, string];

  constructor(
    private fb: FormBuilder,
    public formService: FormService,
    private router: Router,
    private route: ActivatedRoute,
    private snackbarService: SnackbarService,
    private authService: AuthService,
    public accountSelectorDialogService: AccountSelectorDialogService,
    public utilService: UtilService,
    public dialogService: DialogService,
    private invoiceInquiryService: InvoiceInquiryService,
    public dialog: MatDialog,
    public promoService: PromoService,
	public leftNavigationService: LeftNavigationService,
    private myElement: ElementRef) { }

  ngOnInit() {
    this.formGroup = this.fb.group({
      searchType: ['F', Validators.required],
      imageTypes: this.fb.array([false, false]),
      criteria: ['', [Validators.required, textAreaValidator(RegEx.anythingButWhitespace, `Please enter a valid value per line.`)]]
    });
    this.errorMessages = ['error', []];

    this.loadingBuckets = true;

    this.leftNavigationService.setNavigation(`Manage`, `/manage`);
    this.promoService.setAppId('invoice-inquiry');
    this.promoService.setAppState('authenticated');
    
    this.invoiceInquiryService.getAgingSummary().subscribe(data => {
      if (!data || !data.aging || data.aging.length === 0) {
        this.noAgingSummaryResultsMessage = ['error', 'There are no open invoices for this account at this time.'];
      } else {
        this.agingSummaryBuckets = data.aging;
        this.agingSummaryTotal = this.agingSummaryBuckets.pop();
        this.agingRefreshDate = data.refreshDate;
      }
      this.loadingBuckets = false;
    }, err => {
      this.noAgingSummaryResultsMessage = ['error', 'There are no open invoices for this account at this time.'];
      this.loadingBuckets = false;
    });

    this.agingSummaryBucketLabels = [`0–15 days`, '16–30 days', '31–45 days', '46–60 days', '61–75 days', '76–90 days', '91–120 days', '121+ days', 'View All Open'];
    this.displayedColumns = ['select', 'pro', 'pickupDate', 'deliveryDate', 'invoiceDate', 'bol', 'poNum', 'statementNum', 'openAmount', 'pendingPayAmount', 'imageLink'];
    this.invoiceDetails = new MatTableDataSource();
    this.invoiceDetailsSelections = new SelectionModel<AgingDetail>(true, []);
    this.invoiceDetails.paginator = this.paginator;
    this.invoiceDetails.sort = this.sort;
    this.invoiceDetailsTotalRows = 0;

    this.invoiceInquiryService.setSelectedAgingDetails([]);

    this.tableSub = merge(this.sort.sortChange, this.paginator.page)
		.subscribe(data => {
      if (this.agingBucketIndex || this.agingBucketIndex === 0) {
        this.searchByAgingBucket(this.agingBucketIndex, (this.paginator.pageIndex + 1), {suppressSuccessMessage: true});
      } else {
        this.invoiceDetails.data.sort((a, b) => {
          const isAsc = this.sort.direction === 'asc';
          switch (this.sort.active) {
            case '0': return this.compare(a.pro, b.pro, isAsc);
            case '1': return this.compare(a.pickupDate, b.pickupDate, isAsc);
            case '2': return this.compare(a.deliveryDate, b.deliveryDate, isAsc);
            case '3': return this.compare(a.invoiceDate, b.invoiceDate, isAsc);
            case '4': return this.compare(a.bol, b.bol, isAsc);
            case '5': return this.compare(a.poNum, b.poNum, isAsc);
            case '6': return this.compare(a.statementNum, b.statementNum, isAsc);
            case '7': return this.compare(a.openAmount, b.openAmount, isAsc);
            case '8': return this.compare(a.pendingPayAmount, b.pendingPayAmount, isAsc);
            default: return 0;
          }
        });
      }
    });

    this.paramSub = this.route.queryParams.subscribe(params => {
      let id = params['id'];
      let action = params['action'];
      if (id && action) {
        this.invoiceInquiryService.finalizePayment(id, action).subscribe(data => {
          // console.log('finalized: ', data);
        }, err => {
          // console.log('error finalizing', err);
        });
      }
   });

    this.invoiceDetailsTotalRows = 0;

    this.authService.authStateSet.subscribe((authState: string) => {
      if ('unauthenticated' === authState) {
        this.router.navigate(['login']);
      }
    });
  }

  get searchType() {
    return this.formGroup.controls['searchType'];
  }
  get criteria() {
    return this.formGroup.controls['criteria'];
  }
  get imageTypes() {
    return this.formGroup.controls['imageTypes'] as FormArray;
  }

  compare(a: number | string, b: number | string, isAsc: boolean) {
    return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
  }

  searchByAgingBucket(bucketIndex: number, pageNum: number, options: any) {
    if (this.agingBucketIndex !== bucketIndex) this.invoiceDetailsSelections = new SelectionModel<AgingDetail>(true, []);
    this.agingBucketIndex = bucketIndex;
    this.searchTableTitle = 'Invoice Aging Detail';
    this.loadingInvoiceDetails = true;

    let request = new AgingDetailRequest();

    if (options && options.reset) {
      this.resetPaginatorAndSort();
    }

    request.bucket = bucketIndex;
    request.maxRows = this.paginator.pageSize;
    request.page = pageNum;
    request.sort = this.sort.active;
    request.direction = (this.sort.direction == 'asc') ? '0' : '1';

    this.invoiceInquiryService.getInvoicesByAgeBucket(request).subscribe(data => {
      this.invoiceDetails = new MatTableDataSource(data.details ? data.details : []);
      this.invoiceDetailsTotalRows = data.totalRows;
      this.loadingInvoiceDetails = false;
      if (!options || !options.suppressSuccessMessage) {
        this.snackbarService.success(`See the aging detail result in the table below.`);
      }
      //TODO: remove this if we decide not to implement auto scroll in applications
      // this.scrollToResultsTable();
    }, err => {
      this.invoiceDetailsTotalRows = 0;
      this.loadingInvoiceDetails = false;
      this.snackbarService.error(err);
    });
  }

  onSubmitSearch() {
    if (this.formGroup.valid) {
      this.loadingInvoiceDetails = true;
      this.errorMessages[1] = [];
      this.searchingInvoiceDetails = true;
      this.searchTableTitle = 'Statement Search Results';
      let request = new InvoiceSearchRequest();
      request.imageTypes = ['dr', 'bol'];
      // if (this.imageTypes.at(0).value) request.imageTypes.push('dr');
      // if (this.imageTypes.at(1).value) request.imageTypes.push('bol');
      request.criteria = this.criteria.value.split('\n');
      request.criteria = request.criteria.map(c => {return c.trim()});
      request.searchType = this.searchType.value;

      this.invoiceInquiryService.searchInvoices(request).subscribe(data => {
        let results: AgingDetail[] = [];
        data.forEach(item => {
          if (item.error && item.error.errorCode) {
            this.errorMessages[1].push(item.error.message);
          } else {
            results.push(item.result);
          }
        });
        this.invoiceDetails = new MatTableDataSource(results || []);
        this.invoiceDetails.paginator = this.paginator;
        this.invoiceDetails.sort = this.sort;
        this.invoiceDetailsTotalRows = results.length;
        this.loadingInvoiceDetails = false;
        this.searchingInvoiceDetails = false;
        this.agingBucketIndex = null;
        this.invoiceDetailsSelections = new SelectionModel<AgingDetail>(true, []);
        this.snackbarService.success(`See the search results in the table below.`);
        //TODO: remove this if we decide not to implement auto scroll in applications
        // this.scrollToResultsTable();

      }, err => {
        this.errorMessages[1] = err;
        this.invoiceDetailsTotalRows = 0;
        this.loadingInvoiceDetails = false;
        this.searchingInvoiceDetails = false;
      });
    }
  }

  // hidePendingPay() {
  //   this.displayedColumns = ['select', 'pro', 'pickupDate', 'deliveryDate', 'invoiceDate', 'bol', 'poNum', 'statementNum', 'openAmount', 'imageLink'];
  // }

  // showPendingPay() {
  //   this.displayedColumns = ['select', 'pro', 'pickupDate', 'deliveryDate', 'invoiceDate', 'bol', 'poNum', 'statementNum', 'openAmount', 'pendingPayAmount', 'imageLink'];
  // }

  hasSpecialInstructions() {
    this.invoiceInquiryService.hasSpecialInstructions().subscribe(data => {
      if (!data) this.router.navigate(['/special-instructions']);
    });
  }

  resetPaginatorAndSort() {
    this.paginator.pageIndex = 0;
    this.paginator.pageSize = 25;
    this.sort.active = '0';
    this.sort.direction = 'asc';
  }

  clearSearch() {
    this.searchType.setValue('F');
    this.criteria.reset();
    this.invoiceDetails.data = [];
    this.invoiceDetailsTotalRows = 0;
  }

  navigateToStatementDetails(statementNum: string) {
    this.router.navigate(['/statement-details', statementNum]);
  }

  navigateToPayments() {
    let cont = false;
    this.invoiceDetailsSelections.selected.forEach(selection => {
      if (selection.openAmount > selection.pendingPayAmount) {
        cont = true;
      }
    });
    if (cont) {
      this.invoiceInquiryService.setSelectedAgingDetails(this.invoiceDetailsSelections.selected);
      this.router.navigate(['/payments']);
    } else {
      this.snackbarService.info('Please select at least one invoice with an open amount.');
    }

  }

  openEmailModal() {
    const data = {
      bucket: this.agingBucketIndex ? this.agingBucketIndex.toString() : '',
      selectedPros: this.invoiceDetailsSelections.selected
    };

    const emailDialogRef = this.dialog.open(EmailDialogComponent, {
      width: '50rem',
      panelClass: ['estes-modal'],
      data: data
    });
    emailDialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.snackbarService.success(result.message);
      }
    });
  }

  scrollToResultsTable() {
    let ctrl = this;
    setTimeout(function() {
      let el = ctrl.myElement.nativeElement.querySelector('#resultsTableAnchor');
      el.scrollIntoView({behavior: 'smooth'});
    }, 0);
  }

  //The below four methods, isAllSelected, isOneSelected, masterToggle, and isSelected all pertain to the checkbox selection functionality of the invoice results table

  isAllSelected() {
    let allSelected = true;
    this.invoiceDetails.data.forEach(row =>{
      if (!this.isSelected(row)) allSelected = false;
    });
    return allSelected;
  }

  isOneSelected() {
    this.invoiceDetails.data.forEach(row =>{
      if (!this.isSelected(row)) return true;
    });
    return false;
  }

  masterToggle() {
    this.isAllSelected() ?
    this.invoiceDetailsSelections.clear() :
    this.invoiceDetails.data.forEach(row => this.invoiceDetailsSelections.select(row));
  }

  isSelected(row) {
    for (let s of this.invoiceDetailsSelections.selected) {
        if (s['pro'] == row.pro) {
            return true;
        }
    }
  }
}
