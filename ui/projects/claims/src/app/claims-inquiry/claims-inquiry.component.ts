import { Component, OnInit, ViewChild } from '@angular/core';
import { ClaimsService } from '../claims.service';
import { AuthService, AccountSelectorDialogService } from 'common';
import { UtilService, FormService, SubAccount, yyyymmdd, EmailDialogData, EmailDialogService, FeedbackTypes, SnackbarService, dateValidator } from 'common';
import { Claim } from '../models/claim.model';
import {animate, state, style, transition, trigger} from '@angular/animations';


import {
  FormBuilder,
  FormGroup,
  FormGroupDirective,
  Validators
} from '@angular/forms';
import { MatTableDataSource, MatPaginator, MatSort } from '@angular/material';

import { ClaimsRequest } from '../models/claims-request.model';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-claims-inquiry',
  templateUrl: './claims-inquiry.component.html',
  styleUrls: ['./claims-inquiry.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed, void', style({ height: '0px', minHeight: '0', display: 'none', border: 'none' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
      transition('expanded <=> void', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)'))
    ])
  ]
})

export class ClaimsInquiryComponent implements OnInit {
  @ViewChild(FormGroupDirective) myForm;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  claimRequest: ClaimsRequest;
  claimResults: MatTableDataSource<Claim>;
  displayedColumns: string[];

  formGroup: FormGroup;

  errorMessages: [FeedbackTypes, string[]];
  loading: boolean;
  loadingClaims: boolean;

  showAccountDropdown: boolean;

  searchByOptions: string[];
  statusOptions: string[];

  today: Date;
  minDate: Date;


  constructor(
    private claimService: ClaimsService,
     private utilService: UtilService,
     private fb: FormBuilder,
     public formService: FormService,
     public authService: AuthService,
     public accountSelectorDialogService: AccountSelectorDialogService,
     public emailDialogService: EmailDialogService,
     private snackbarService: SnackbarService
    ) {
      this.displayedColumns = ['claimNumber', 'proNumber', 'status', 'dateFiled', 'refNumber', 'claimAmount', 'remitTo', 'expand'];
      this.claimResults = new MatTableDataSource();
      this.claimResults.sort = this.sort;
     }

  ngOnInit() {
    this.loading = true;
    this.formGroup = this.fb.group({
      accountNumber: [''],
      searchBy: ['Date Range'],
      startDate: ['', dateValidator()],
      endDate: ['', dateValidator()],
      numbers: [''],
      status: ['All'],
      filedDateRange: ['30']
    });

    this.searchByOptions = [
      "Date Range",
      "PRO Number",
      "Your Reference Number",
      "Estes Claim Number"
    ];

    this.statusOptions = [
      "All",
      "Open",
      "Paid",
      "Declined"
    ];

    this.today = new Date();
    this.minDate = new Date();
    this.minDate.setDate(this.today.getDate() - 365);

    //Subscribe to ClaimService regulated data
    this.claimService.currentClaim.subscribe(claim => this.claimRequest = claim);

    this.showAccountDropdown = this.authService.isGroupAccount
                            || this.authService.isNationalAccount
                            || this.authService.is91Account;
    if (this.showAccountDropdown) {
      this.accountNumber.setValidators(Validators.required);
    }

    this.loading = false;

    this.formGroup.get('searchBy').valueChanges.subscribe(val => {

    });
  }

  get accountNumber() {
    return this.formGroup.controls['accountNumber'];
  }

  get searchBy() {
    return this.formGroup.controls['searchBy'];
  }

  get status() {
    return this.formGroup.controls['status'];
  }

  get startDate() {
    return this.formGroup.controls['startDate'];
  }

  get endDate() {
    return this.formGroup.controls['endDate'];
  }

  get numbers() {
    return this.formGroup.controls['numbers'];
  }

  get filedDateRange() {
    return this.formGroup.controls['filedDateRange'];
  }

  searchClaimsDateRange(range: number, status: string) {
    this.resetFormControls();
    this.searchBy.setValue('Date Range');
    this.filedDateRange.setValue(range.toString());
    this.setDatesByRange(range);
    this.status.setValue(status);
    this.onSubmit();
  }

  formatClaimRequest(request: any) {
    if (this.accountNumber.value != "") request.accountNumber = this.accountNumber.value;
    if (this.numbers.value) {
      const nums = this.numbers.value.replace('-', '');
      request.numbers = nums.split(/\r?\n/);
    } 
    request.endDate = yyyymmdd(this.endDate.value);
    request.searchBy = this.searchBy.value;
    request.startDate = yyyymmdd(this.startDate.value);
    request.status = this.status.value;
    return request;
  }

  setDatesByRange(range: number) {
    this.endDate.setValue(this.today);
    let newStartDate = new Date();
    newStartDate.setDate(this.today.getDate() - range);
    this.startDate.setValue(newStartDate);
  }

  onSubmit() {
    if (this.searchBy.value == 'Date Range' && this.filedDateRange.value !== 'Custom') {
      this.setDatesByRange(Number(this.filedDateRange.value));
    }
    if (this.formGroup.valid) {
      let ctrl = this;
      this.loadingClaims = true;

      let request = new ClaimsRequest();
      request = this.formatClaimRequest(request);

      this.claimService.getClaims(request)
        .subscribe(data => {
          this.claimResults = new MatTableDataSource(data);
          this.claimResults.paginator = ctrl.paginator;
          this.claimResults.sort = ctrl.sort;
          this.errorMessages = null;
          this.loadingClaims = false;
          this.snackbarService.success(`See result in the table below.`);

        },
        err => {
          this.errorMessages = ['error', err];
          this.claimResults = new MatTableDataSource();
          this.loadingClaims = false;
        });
    } else {
      this.snackbarService.validationError();
    }
  }

  openAccountSearchDialog() {
    this.accountSelectorDialogService.openAccountDialog().subscribe(next => {
      if (next) {
        this.formGroup.controls['accountNumber'].setValue(next);
      }
    });
  }

  openEmailDialog() {
    let request: any;
    request = {};
    request = this.formatClaimRequest(request);
    request.emailAddresses = '';
    let data = <EmailDialogData> {
      title: `Email Claims Request`,
      // tslint:disable-next-line:max-line-length
      message: `To receive a list of claims based on your search criteria, enter one or more email addresses in the form below and select a format.`,
      url: `${environment.serviceApiUrl}/myestes/Claims/v1.0/emailClaims`,
      emailKey: 'emailAddresses',
      formatKey: 'format',
      requestPayload: request,
      successMessage: 'Your email has been sent.'
    };

    this.emailDialogService.openEmailDialog(data).subscribe(next => {
    });
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.claimResults.filter = filterValue;
  }

  resetFormControls() {
    this.numbers.reset();
    this.startDate.reset();
    this.endDate.reset();
    this.status.reset();
  }

  updateFormValidity() {
    if (this.searchBy.value == 'Date Range') {
      this.numbers.setValidators(null);
      this.numbers.updateValueAndValidity();
    } else {
      [this.startDate, this.endDate, this.status].forEach(field => {
        field.setValidators(null);
        field.updateValueAndValidity();
      });
    }
  }

}
