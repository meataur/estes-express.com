import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { InvoiceInquiryService } from '../invoice-inquiry.service';
import { AgingDetail } from '../models/aging-detail.model';
import { MatTableDataSource, MatDialog, MatPaginator } from '@angular/material';
import { Subscription } from 'rxjs';
import { FeedbackTypes } from 'common';
import { Image } from '../models/image.model';
import { StatementDetail } from '../models/statement-detail.model';
import { InvoiceSearchRequest } from '../models/invoice-search-request.model';
import { SnackbarService } from 'common';
import { SelectionModel } from '@angular/cdk/collections';
import { EmailDialogComponent } from '../email-dialog/email-dialog.component';

@Component({
  selector: 'app-statement-details',
  templateUrl: './statement-details.component.html',
  styleUrls: ['./statement-details.component.scss']
})
export class StatementDetailsComponent implements OnInit, OnDestroy {

  @ViewChild(MatPaginator) paginator: MatPaginator;

  invoiceDetails: MatTableDataSource<AgingDetail>;
  invoiceDetailsSelections: SelectionModel<AgingDetail>;
  displayedColumns: string[];
  statusSubs: Subscription[];
  images: Image[];
  paramSub: Subscription;
  statementSub: Subscription;
  invoiceSub: Subscription;

  statementNumber: string;
  loading: boolean;
  errorMessages: [FeedbackTypes, string[]];
  totalOpenAmount: number;
  totalInvoices: number;
  pageSize: number = 25;
  statementDetailsList: StatementDetail[];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private invoiceInquiryService: InvoiceInquiryService,
    public dialog: MatDialog,
    private snackbarService: SnackbarService) { }

  ngOnInit() {
    window.scrollTo({ top: 0, behavior: 'smooth' });
    this.invoiceDetails = new MatTableDataSource([]);
    this.invoiceDetails.paginator = this.paginator;
    this.invoiceDetailsSelections = new SelectionModel<AgingDetail>(true, []);

    this.displayedColumns = ['select', 'pro', 'pickupDate', 'deliveryDate', 'invoiceDate', 'bol', 'poNum', 'statementNum', 'openAmount', 'pendingPayAmount', 'imageLink'];
    this.errorMessages = ['error', []];

    this.statusSubs = [];
    this.images = [];

    this.paramSub = this.route.params.subscribe(params => {
      this.statementNumber = params['statement'];
      this.loadStatements(this.statementNumber);
   });
  }

  ngOnDestroy() {
    if (this.statementSub) {
      this.statementSub.unsubscribe();
    }
    if (this.invoiceSub) {
      this.invoiceSub.unsubscribe();
    }
    if (this.paramSub) {
      this.paramSub.unsubscribe();
    }
  }

  loadStatements(statementNum: string) {
    this.loading = true;
    //First we load the statement details, and use the pro numbers to load the invoice info for those statement details.
    this.statementSub = this.invoiceInquiryService.getStatement(statementNum).subscribe(res => {

      this.statementDetailsList = res;
      this.totalInvoices = res.length;
      this.pageInvoices();
    }, err => {
      this.errorMessages[1] = err;
      this.loading = false;
    });
  }
  // pageInvoices uses a subset of the statement details results to load invoices based on the current state of the paginator.
  pageInvoices() {
    this.loading = true;
    //paginator logic
    const pageSize = this.paginator.pageSize;
    const pageIndex = this.paginator.pageIndex;
    const startIndex = pageSize * pageIndex;
    const endIndex = startIndex + pageSize;

    let statementsToSearch = this.statementDetailsList.slice(startIndex, endIndex);

    //search invoices logic
    let request = new InvoiceSearchRequest();
    request.imageTypes = ['dr', 'bol'];
    request.searchType = 'F'
    request.criteria = [];

    statementsToSearch.forEach(statement => {
      if (statement.pro) request.criteria.push(statement.pro)
    });
    this.invoiceSub = this.invoiceInquiryService.searchInvoices(request).subscribe(res => {
      let results: AgingDetail[] = [];
      this.totalOpenAmount = 0;
      res.forEach(item => {
        if (item.error && item.error.errorCode) {
          this.errorMessages[1].push(item.error.message);
        } else {
          results.push(item.result);
          this.totalOpenAmount += item.result.openAmount;
        }
      });
      this.invoiceDetails = new MatTableDataSource(results);
      this.loading = false;
    }, err => {
      this.errorMessages[1] = err;
      this.loading = false;
    });
  }

  navigateToPayments() {
    this.invoiceInquiryService.setSelectedAgingDetails(this.invoiceDetailsSelections.selected);
    this.router.navigate(['/payments']);
  }

  openEmailModal() {
    const data = {
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
