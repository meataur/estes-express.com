import { Component, OnInit, OnDestroy, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { DatePipe } from '@angular/common';

import { SnackbarService, FormService, FeedbackTypes, patternValidator, MessageConstants, RegEx, AccountSelectorDialogService, MyEstesValidators, validateFormFields, dateValidator } from 'common';
import {
  FormBuilder,
  FormGroup,
  FormGroupDirective,
  Validators
} from '@angular/forms';
import { OnlineReportingService } from './online-reporting.service';
import { ReportType } from './report-type.model';
import { Report } from './report.model';
import { AuthService, UtilService, SubAccount, DialogService } from 'common';
import { startWith } from 'rxjs/operators';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-create-report',
  templateUrl: './create-report.component.html',
  styleUrls: ['./create-report.component.scss']
})
export class CreateReportComponent implements OnInit, OnDestroy {
  @Input() report: Report;
  @Output() created = new EventEmitter<string>();
  @ViewChild(FormGroupDirective) myForm;

  reportTypes: ReportType[];
  formGroup: FormGroup;
  editMode: boolean;
  errorMessages: [FeedbackTypes, string];
  today: Date;
  week: string[];
  loading: boolean;
  datePipe: DatePipe;
  reportTypeDetails: string[];
  createReportHelpText: string;
  showAccountDropdown: boolean;
  subAccounts: SubAccount[];
  reportDataDetails: any[];
  reportSub: Subscription;
  dialogSub: Subscription;
  reportTypeSub: Subscription;
  dateSub: Subscription;
  submitSub: Subscription;
  accountSub: Subscription;


  constructor(
    private fb: FormBuilder,
    public formService: FormService,
    private snackbarService: SnackbarService,
    private authService: AuthService,
    public accountSelectorDialogService: AccountSelectorDialogService,
    public utilService: UtilService,
    public dialogService: DialogService,
    private onlineReportingService: OnlineReportingService) { }

  ngOnInit() {
    this.reportTypes = [];
    this.errorMessages = null;
    this.today = new Date();
    this.week = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'];
    this.datePipe = new DatePipe('en-US');
    this.reportTypeDetails = [`Account: Alpha Numeric\nRole: Alpha Numeric\nSEL Status: Alpha Numeric`, `Account: Alpha Numeric\nRole: Alpha Numeric\nSEL Status: Alpha Numeric`, `Account: Alpha Numeric\nRole: Alpha Numeric\nSEL Status: Alpha Numeric`];
    this.createReportHelpText = 'You can schedule a report to run daily, weekly, monthly, or a single time.';
    this.reportDataDetails =
    [`<p>See all shipments picked up or delivered within a 31-day range, and find detailed tracking results for each.</p><p>This report includes:</p><table><tbody><tr><td valign="top"><b>Data Type</b> <br>Account <br>Role <br>SEL Status <br>From Date <br>To Date <br>Date Type <br>Pro Number <br>Bill of Lading <br>Purchase Order Number <br>Pickup Date <br>Delivery Date <br>Piece Count <br>Weight Count <br>Freight Charges <br>Status <br>Received By <br>First Delivery Date <br>First Delivery Time <br>Appointment Date <br>Appointment Time <br>Shipper Number <br>Shipper Name <br>Shipper Address1 <br>Shipper Address 2 <br>Shipper City <br>Shipper State <br>Shipper Zip <br>Consignee Number <br>Consignee Name <br>Consignee Address1 <br>Consignee Address2 <br>Consignee City <br>Consignee State <br>Consignee Zip <br>Destination Terminal <br>Phone Number <br>Fax Number</td><td valign="top"><b>Format</b> <br>Alpha Numeric <br>Alpha Numeric <br>Alpha Numeric <br>MM/DD/CCYY <br>MM/DD/CCYY <br>Alpha Numeric <br>Alpha Numeric <br>Alpha Numeric <br>Alpha Numeric <br>MM/DD/YY <br>MM/DD/YY <br>99999- <br>9999999- <br>99,999.99- <br>Alpha Numeric <br>Alpha Numeric <br>MM/DD/CCYY <br>HH:MM <br>MM/DD/CCYY <br>HH:MM <br>Alpha Numeric <br>Alpha Numeric <br>Alpha Numeric <br>Alpha Numeric <br>Alpha Numeric <br>Alpha Numeric <br>Alpha Numeric <br>Alpha Numeric <br>Alpha Numeric <br>Alpha Numeric <br>Alpha Numeric <br>Alpha Numeric <br>Alpha Numeric <br>Alpha Numeric <br>Alpha Numeric <br>(999) 999-9999 <br>(999) 999-9999</td></tr></tbody></table>`,
     `<p>Find the status of your freight, including when it was picked up and delivered.</p><p>This report includes:</p><table><tbody><tr><td><b>Data Type</b> <br>Account <br>Role <br>SEL Status <br>From Date <br>To Date <br>Date Type <br>Pro Number <br>Bill of Lading <br>Pickup Date <br>Delivery Date <br>Consignee City <br>Consignee State <br></td><td>     <b>Format</b> <br>Alpha Numeric <br>Alpha Numeric <br>Alpha Numeric <br>MM/DD/CCYY <br>MM/DD/CCYY <br>Alpha Numeric <br>Alpha Numeric <br>Alpha Numeric <br>MM/DD/YY <br>MM/DD/YY <br>Alpha Numeric <br>Alpha Numeric <br></td></tr></tbody></table>`,
     `<p>Get reports for open, paid, or declined claims with Estes.</p><p>This report includes:</p><table class="output-table" width="300px"><tbody><tr><td><b>Data Type</b> <br>Account <br>Role <br>SEL Status <br>From Date <br>To Date <br>Date Type <br>Pro Number <br>Bill of Lading <br>Pickup Date <br>Delivery Date <br>Consignee City <br>Consignee State <br></td><td><b>Format</b> <br>Alpha Numeric <br>Alpha Numeric <br>Alpha Numeric <br>MM/DD/CCYY <br>MM/DD/CCYY <br>Alpha Numeric <br>Alpha Numeric <br>Alpha Numeric <br>MM/DD/YY <br>MM/DD/YY <br>Alpha Numeric <br>Alpha Numeric <br></td></tr></tbody></table>`];

     this.setupReportForm();

    //check if report was input, which indicates edit mode
    if (this.report && this.report.scheduleId) {
      this.reportSub = this.onlineReportingService.getReportById(this.report.scheduleId)
      .subscribe(data => {
        this.report = data;
        this.editMode = true;
        this.setupReportForm();
      },
      err => {
        this.errorMessages = ['error', err];
      });
    } else {
      this.oneTime.setValue(true);
    }
    //retrieve all report types from services
    this.reportTypeSub = this.onlineReportingService.getReportTypes().subscribe(
      data => {
        data.forEach(r => {
          r.reportId = Number(r.reportId);
        });
        this.reportTypes = data;
      },
      err => {
        this.errorMessages = ['error', err];
      });
      //check if we need to show the account dropdown and selector
      this.showAccountDropdown = this.authService.isGroupAccount
      || this.authService.isNationalAccount
      || this.authService.is91Account;

      if (this.showAccountDropdown) {
        this.accountNumber.setValidators([Validators.maxLength(7), Validators.required]);
      }

      this.setExpirationDatePickerState();
  }

  onFrequencyChange() {
    const val = this.frequency.value;
    this.runOnPrevious.setValue((val === 'D') ? '1' : '0');
    this.requestedMonths.setValue((val === 'M') ? '1' : '0');
    this.requestedWeeks.setValue((val === 'W') ? '1' : '0');
    this.updateDurationSelection('oneTime');
  }

  ngOnDestroy() {
    if (this.reportSub) {
      this.reportSub.unsubscribe();
    }
    if (this.reportTypeSub) {
      this.reportTypeSub.unsubscribe();
    }
    if (this.submitSub) {
      this.submitSub.unsubscribe();
    }
    if (this.accountSub) {
      this.accountSub.unsubscribe();
    }
    if (this.dialogSub) {
      this.dialogSub.unsubscribe();
    }
    if (this.dateSub) {
      this.dateSub.unsubscribe();
    }
  }

  setupReportForm() {
    if (!this.report) {
      this.report = new Report();
    }
    this.formGroup = this.fb.group({
      reportName: [this.report.reportName || '', [MyEstesValidators.required, Validators.maxLength(50)]],
      userRole: [this.report.userRole || 'S', MyEstesValidators.required],
      accountNumber: [this.report.accountNumber || '', [Validators.maxLength(7)]],
      dateType: [this.report.dateType || 'D', MyEstesValidators.required],
      reportStatus: [this.report.reportStatus || 'A', MyEstesValidators.required],
      requestStatus: [this.report.requestStatus || ''],
      frequency: [this.report.frequency || 'D', MyEstesValidators.required],
      reportId: [this.report.reportId || 1, MyEstesValidators.required],
      runOnPrevious: [this.report.runOnPrevious || '1'],
      dayToRun: [this.report.dayToRun || '1'],
      dayOfMonth: [this.report.dayOfMonth || '1'],
      oneTime: [this.report.oneTime || false],
      manually: [this.report.manually || false],
      numberOfDays: [this.report.numberOfDays || ''],
      numberOfWeeks: [this.report.numberOfWeeks || false],
      numberOfMonths: [this.report.numberOfMonths || false],
      untilDate: [this.report.untilDate || false],
      expirationDate: [this.report.expirationDate ? new Date(this.report.expirationDate) : '', [dateValidator()]],
      requestedWeeks: [this.report.requestedWeeks || '0'],
      requestedMonths: [this.report.requestedMonths || '0'],
      email: [this.report.email || '', [MyEstesValidators.required, patternValidator(RegEx.email, MessageConstants.invalidEmail)]],
      format: [this.report.format || 'XLS', MyEstesValidators.required],
      shipmentType: [this.report.shipmentType || 'A'],
      timeToRun: [this.report.timeToRun || '0'],
      startDate: ['', dateValidator()],
      scheduleId: [this.report.scheduleId || null]
    });

    const newDate = new Date();
    this.startDate.setValue(this.datePipe.transform(newDate.setDate(new Date().getDate() + 1), 'yyyy-MM-dd'));
  }

  setExpirationDatePickerState() {
    if (this.untilDate.value === true) {
      this.expirationDate.enable();
      this.expirationDate.setValidators([Validators.required, dateValidator()]);
    } else {
      this.expirationDate.disable();
      this.expirationDate.clearValidators();
      this.expirationDate.setValue('');
    }

  }

  get reportName() {
    return this.formGroup.controls['reportName'];
  }
  get userRole() {
    return this.formGroup.controls['userRole'];
  }
  get dateType() {
    return this.formGroup.controls['dateType'];
  }
  get reportStatus() {
    return this.formGroup.controls['reportStatus'];
  }
  get frequency() {
    return this.formGroup.controls['frequency'];
  }
  get reportId() {
    return this.formGroup.controls['reportId'];
  }
  get runOnPrevious() {
    return this.formGroup.controls['runOnPrevious'];
  }
  get dayToRun() {
    return this.formGroup.controls['dayToRun'];
  }
  get dayOfMonth() {
    return this.formGroup.controls['dayOfMonth'];
  }
  get oneTime() {
    return this.formGroup.controls['oneTime'];
  }
  get manually() {
    return this.formGroup.controls['manually'];
  }
  get numberOfWeeks() {
    return this.formGroup.controls['numberOfWeeks'];
  }
  get numberOfMonths() {
    return this.formGroup.controls['numberOfMonths'];
  }
  get expirationDate() {
    return this.formGroup.controls['expirationDate'];
  }
  get requestedWeeks() {
    return this.formGroup.controls['requestedWeeks'];
  }
  get requestedMonths() {
    return this.formGroup.controls['requestedMonths'];
  }
  get untilDate() {
    return this.formGroup.controls['untilDate'];
  }
  get email() {
    return this.formGroup.controls['email'];
  }
  get format() {
    return this.formGroup.controls['format'];
  }
  get numberOfDays() {
    return this.formGroup.controls['numberOfDays'];
  }
  get requestStatus() {
    return this.formGroup.controls['requestStatus'];
  }
  get shipmentType() {
    return this.formGroup.controls['shipmentType'];
  }
  get startDate() {
    return this.formGroup.controls['startDate'];
  }
  get accountNumber() {
    return this.formGroup.controls['accountNumber'];
  }

  updateDurationSelection(selection: string) {
    this.formGroup.controls['oneTime'].setValue(false);
    this.formGroup.controls['manually'].setValue(false);
    this.formGroup.controls['numberOfWeeks'].setValue(false);
    this.formGroup.controls['numberOfMonths'].setValue(false);
    this.formGroup.controls['untilDate'].setValue(false);
    if (selection) {
      this.formGroup.controls[selection].setValue(true);
      this.requestedMonths.setValue((selection === 'numberOfMonths') ? '1' : '0');
      this.requestedWeeks.setValue((selection === 'numberOfWeeks')  ? '1' : '0');
    }
    this.setExpirationDatePickerState();
  }

  checkDurationValidity() {
    if (this.untilDate.value && !this.expirationDate.value)
      return false;
    else
      return true;
  }

  onSubmit() {
    if (this.formGroup.valid && this.checkDurationValidity()) {
      this.errorMessages = null;
      if (this.expirationDate.value) {
        this.expirationDate.setValue(this.datePipe.transform(this.expirationDate.value, 'yyyy-MM-dd') || '');
      }
      this.loading = true;
      let serviceCall = this.editMode ? this.onlineReportingService.updateReport(this.formGroup.getRawValue()): this.onlineReportingService.createReport(this.formGroup.getRawValue());

      this.submitSub = serviceCall.subscribe(resp => {
          this.snackbarService.success(resp.message);
          this.myForm.resetForm();
          this.ngOnInit();
          this.loading = false;
          this.created.emit('completed');
        },
        err => {
          this.errorMessages = ['error', err];
          this.loading = false;
        });
    } else {
      validateFormFields(this.formGroup);
      this.snackbarService.validationError();
    }

  }
  reset() {
    if (this.formGroup.touched) {
      this.dialogSub = this.dialogService.confirm('', 'Any changes to the form will be lost. Are you sure you want to cancel?').subscribe(confirm => {
        if (confirm) {
          this.myForm.resetForm();
          this.ngOnInit();
          this.created.emit('completed');
        }
      });
    } else {
      this.myForm.resetForm();
      this.ngOnInit();
      this.created.emit('completed');
    }
  }

  openDataDetailsModal(reportType: ReportType) {
    this.dialogService.info(reportType.reportName, this.reportDataDetails[reportType.reportId - 1]);
  }

  openAccountSelectorDialog() {
    this.accountSub = this.accountSelectorDialogService.openAccountDialog().subscribe(next => {
      if (next) {
        this.accountNumber.setValue(next);
      }
    });
  }

}
