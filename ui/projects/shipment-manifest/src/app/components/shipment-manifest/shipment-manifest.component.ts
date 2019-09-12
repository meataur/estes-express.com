import { Component, OnInit, ViewChild } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  FormGroupDirective
} from '@angular/forms';
import { AuthService, FeedbackTypes, MyEstesValidators } from 'common';
import { validateFormFields, FormService, SnackbarService, LeftNavigationService, PromoService, UtilService } from 'common';
import { DatePipe } from '@angular/common';
import { Subscription, forkJoin, Observable, merge } from 'rxjs';
import {
  ShipmentManifestResponse,
  PaginatedRequestPayload,
  SubAccount,
  EmailRequestPayload
} from '../../models';
import { ShipmentManifestService } from '../../services/shipment-manifest.service';
import {
  trigger,
  state,
  style,
  transition,
  animate
} from '@angular/animations';
import { Router } from '@angular/router';
import { MatPaginator } from '@angular/material';
import { switchMap, tap } from 'rxjs/operators';
import { textAreaValidator, RegEx, MessageConstants, dateValidator } from 'common';
import { EmailDialogData, EmailDialogService } from 'common';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-shipment-manifest',
  templateUrl: './shipment-manifest.component.html',
  styleUrls: ['./shipment-manifest.component.scss'],
  animations: [
    trigger('detailExpand', [
      state(
        'collapsed',
        style({ height: '0px', minHeight: '0', display: 'none' })
      ),
      state('expanded', style({ height: '*' })),
      transition(
        'expanded <=> collapsed',
        animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')
      )
    ])
  ]
})
export class ShipmentManifestComponent implements OnInit {
  @ViewChild('outboundPaginator') outboundPaginator: MatPaginator;
  @ViewChild('inboundPaginator') inboundPaginator: MatPaginator;
  @ViewChild('thirdPartyPaginator') thirdPartyPaginator: MatPaginator;
  @ViewChild(FormGroupDirective) myForm;
  loading = false;
  selectedTab = null;
  isLocalAccount = false;
  formGroup: FormGroup;
  displayedColumns: string[] = [
    'proNumber',
    'pickupDate',
    'deliveryDate',
    'bol',
    'purchaseOrder',
    'status',
    'toggle'
  ];
  outboundPaginationSub: Subscription;
  inboundPaginationSub: Subscription;
  thirdPartyPaginationSub: Subscription;
  inboundPageSize = 25;
  outboundPageSize = 25;
  thirdPartyPageSize = 25;

  outboundResults: Array<any>;
  outboundResultsLength = 0;
  inboundResults: Array<any>;
  inboundResultsLength = 0;
  thirdPartyResults: Array<any>;
  thirdPartyResultsLength = 0;
  expandedOutboundElement: any | null;
  expandedInboundElement: any | null;
  expandedThirdPartyElement: any | null;
  outboundRequestPayload: PaginatedRequestPayload;
  inboundRequestPayload: PaginatedRequestPayload;
  thirdPartyRequestPayload: PaginatedRequestPayload;
  emailRequestPayload: EmailRequestPayload;
  outboundSub: Subscription;
  inboundSub: Subscription;
  thirdPartySub: Subscription;
  shipmentManifestSub: Subscription;
  showOutboundManifest = false;
  showInboundManifest = false;
  showThirdPartyManifest = false;
  errorMessage: [FeedbackTypes, string];

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    public formService: FormService,
    private shipmentManifestService: ShipmentManifestService,
    private emailDialogService: EmailDialogService,
    private snackbarService: SnackbarService,
    public leftNavigationService: LeftNavigationService,
    public promoService: PromoService,
    public utilService: UtilService,
	private router: Router
  ) {}

  ngOnInit() {
    this.formGroup = this.fb.group({
      account: ['', MyEstesValidators.required],
      shipmentType: ['ALL', MyEstesValidators.required],
      searchBy: ['PICKUPDATE', MyEstesValidators.required],
      sortByDate: ['ASCENDING', MyEstesValidators.required],
      dateSelection: ['TODAYYESTERDAY', MyEstesValidators.required],
      fromDate: ['', dateValidator()],
      toDate: ['', dateValidator()],
      viewOrEmail: ['VIEW', MyEstesValidators.required],
      fileFormat: ['XLS'],
      email: ['']
    });

    this.inboundPaginator.pageSize = this.inboundPageSize;
    this.outboundPaginator.pageSize = this.outboundPageSize;
    this.thirdPartyPaginator.pageSize = this.thirdPartyPageSize;

    // this.formGroup.patchValue({
    //   dateSelection: 'CUSTOMDATE',
    //   fromDate: new Date(2018, 9, 1),
    //   toDate: new Date(2018, 9, 31)
    // });

    this.initOutboundPaginationSub();
    this.initInboundPaginationSub();
    this.initThirdPartyPaginationSub();

    if (this.authService.isLocalAccount) {
      const token = this.authService.getAuthToken();
      this.isLocalAccount = true;
      this.account.setValue(token.accountCode);
    }
    this.viewOrEmail.valueChanges.subscribe(next => {
      if (next.toUpperCase() === 'EMAIL') {
        this.fileFormat.setValidators(MyEstesValidators.required);
        this.email.setValidators([
          MyEstesValidators.required,
          textAreaValidator(
            RegEx.email,
            MessageConstants.invalidEmailTextArea,
            5
          )
        ]);
        this.email.updateValueAndValidity();
      } else {
        // View
        this.fileFormat.reset('xls');
        this.fileFormat.clearValidators();
        this.email.reset('');
        this.email.clearValidators();
        this.email.updateValueAndValidity();
      }
    });

    this.dateSelection.valueChanges.subscribe(next => {
      switch ((next as string).toUpperCase()) {
        case 'CUSTOMDATE':
          this.fromDate.setValidators([MyEstesValidators.required]);
          this.toDate.setValidators([MyEstesValidators.required]);
          break;
        default:
          this.fromDate.reset('');
          this.fromDate.clearValidators();
          this.fromDate.updateValueAndValidity();
          this.toDate.reset('');
          this.toDate.clearValidators();
          this.toDate.updateValueAndValidity();
      }
    });

    this.leftNavigationService.setNavigation(`Manage`, `/manage`);
    this.promoService.setAppId('shipment-manifest');
    this.promoService.setAppState('authenticated');
    
 	this.authService.authStateSet.subscribe((authState: string) => {
      if ('unauthenticated' === authState) {
        this.router.navigate(['login']);
	  }
    });
  }

  get account() {
    return this.formGroup.controls['account'];
  }
  get shipmentType() {
    return this.formGroup.controls['shipmentType'];
  }
  get searchBy() {
    return this.formGroup.controls['searchBy'];
  }
  get sortByDate() {
    return this.formGroup.controls['sortByDate'];
  }
  get dateSelection() {
    return this.formGroup.controls['dateSelection'];
  }
  get fromDate() {
    return this.formGroup.controls['fromDate'];
  }
  get toDate() {
    return this.formGroup.controls['toDate'];
  }
  get viewOrEmail() {
    return this.formGroup.controls['viewOrEmail'];
  }
  get email() {
    return this.formGroup.controls['email'];
  }
  get fileFormat() {
    return this.formGroup.controls['fileFormat'];
  }
  get today() {
    return new Date();
  }
  get thirtyDayWindow() {
    if (
      this.fromDate.valid &&
      this.fromDate.value instanceof Date &&
      !isNaN(this.fromDate.value.valueOf())
    ) {
      const today = this.today;
      const thirtyDaysFromToday = new Date(today);
      thirtyDaysFromToday.setDate(thirtyDaysFromToday.getDate() - 30);

      if (
        (this.fromDate.value as Date).getTime() >= thirtyDaysFromToday.getTime()
      ) {
        return today;
      } else {
        const thirtyDaysAfterFromDate = new Date(this.fromDate.value);
        thirtyDaysAfterFromDate.setDate(thirtyDaysAfterFromDate.getDate() + 30);
        return thirtyDaysAfterFromDate;
      }
    } else {
      return null;
    }
  }

  onSubmit() {
    this.errorMessage = null;
    if (this.formGroup.valid) {
      this.myForm.resetForm(this.formGroup.value);
      this.generateRequestPayloads();

      if (this.viewOrEmail.value.toUpperCase() === 'VIEW') {
        const observableBatch = new Array<
          Observable<ShipmentManifestResponse>
        >();

        switch ((this.shipmentType.value as string).toUpperCase()) {
          case 'ALL':
            observableBatch.push(
              this.getInboundManifest(),
              this.getOutboundManifest(),
              this.getThirdPartyManifest()
            );
            this.showOutboundManifest = true;
            this.showInboundManifest = true;
            this.showThirdPartyManifest = true;
            this.selectedTab = 1;
            break;
          case 'INBOUND':
            observableBatch.push(this.getInboundManifest());
            this.showInboundManifest = true;
            this.showOutboundManifest = false;
            this.showThirdPartyManifest = false;
            this.outboundResultsLength = 0;
            this.thirdPartyResultsLength = 0;
            this.selectedTab = 2;
            break;
          case 'OUTBOUND':
            observableBatch.push(this.getOutboundManifest());
            this.showOutboundManifest = true;
            this.showInboundManifest = false;
            this.showThirdPartyManifest = false;
            this.inboundResultsLength = 0;
            this.thirdPartyResultsLength = 0;
            this.selectedTab = 1;
            break;
          case 'THIRDPARTY':
            observableBatch.push(this.getThirdPartyManifest());
            this.showThirdPartyManifest = true;
            this.showOutboundManifest = false;
            this.showInboundManifest = false;
            this.outboundResultsLength = 0;
            this.inboundResultsLength = 0;
            this.selectedTab = 3;
            break;
        }

        this.loading = true;
        this.shipmentManifestSub = forkJoin(observableBatch).subscribe(
          next => {
            if (this.outboundResultsLength > 0) {
              this.selectedTab = 1;
            } else if (this.inboundResultsLength > 0) {
              this.selectedTab = 2;
            } else if (this.thirdPartyResultsLength > 0) {
              this.selectedTab = 3;
            }
          },
          err => {
            this.loading = false;
            this.errorMessage = ['error', err];
          },
          () => (this.loading = false)
        );
      } else {
        this.loading = true;
        this.shipmentManifestService
          .emailShipmentManifest(this.emailRequestPayload)
          .subscribe(
            next => {
              if (next.errorCode && next.errorCode === 'ERROR') {
                this.errorMessage = ['error', next.message];
              } else {
                this.snackbarService.success(`Your email has been sent.`);
              }
              /**
               * {"errorCode":"ERROR","message":"Unable to send Emails."}
               */

              /**
               * {errorCode: "ERROR", message: "No results found."}
               */
            },
            err => {
              this.loading = false;
              this.errorMessage = ['error', err];
            },
            () => (this.loading = false)
          );
      }
    } else {
      validateFormFields(this.formGroup);
      this.snackbarService.validationError();
    }
  }

  openEmailDialog(shipmentType: 'THIRDPARTY' | 'OUTBOUND' | 'INBOUND') {
    let payload: PaginatedRequestPayload = null;
    switch (shipmentType.toUpperCase()) {
      case 'INBOUND':
        payload = this.inboundRequestPayload;
        break;
      case 'OUTBOUND':
        payload = this.outboundRequestPayload;
        break;
      case 'THIRDPARTY':
        payload = this.thirdPartyRequestPayload;
        break;
    }

    const {
      pageSize,
      pageNum,
      ...payloadCopy
    }: PaginatedRequestPayload = payload;

    const emailPayload: EmailRequestPayload = {
      ...payloadCopy,
      format: '',
      email: ''
    };

    let data: EmailDialogData;

    data = {
      title: `Email Manifest Request`,
      // tslint:disable-next-line:max-line-length
      message: `Receive shipment history via email. Select a preferred file format and enter all email addresses that should receive the manifest.`,
      url: `${environment.serviceApiUrl}/myestes/ShipmentManifest/v1.0/email`,
      formatKey: 'format',
      requestPayload: emailPayload
    };

    this.emailDialogService.openEmailDialog(data).subscribe(next => {
    });
  }

  private getInboundManifest(): Observable<ShipmentManifestResponse | any> {
    return this.getShipmentManifest(this.inboundRequestPayload).pipe(
      tap(next => {
        if (next) {
          this.inboundResults = next.content;
          this.inboundResultsLength = next.totalElements;
        } else {
          this.inboundResults = [];
          this.inboundResultsLength = 0;
        }
      })
    );
  }
  private getOutboundManifest(): Observable<ShipmentManifestResponse | any> {
    return this.getShipmentManifest(this.outboundRequestPayload).pipe(
      tap(next => {
        if (next) {
          this.outboundResults = next.content;
          this.outboundResultsLength = next.totalElements;
        } else {
          this.outboundResults = [];
          this.outboundResultsLength = 0;
        }
      })
    );
  }
  private getThirdPartyManifest(): Observable<ShipmentManifestResponse | any> {
    return this.getShipmentManifest(this.thirdPartyRequestPayload).pipe(
      tap(next => {
        if (next) {
          this.thirdPartyResults = next.content;
          this.thirdPartyResultsLength = next.totalElements;
        } else {
          this.thirdPartyResults = [];
          this.thirdPartyResultsLength = 0;
        }
      })
    );
  }

  private initOutboundPaginationSub() {
    this.outboundPaginationSub = merge(this.outboundPaginator.page, this.outboundPaginator.pageSize)
      .pipe(
        switchMap(() => {
          this.outboundRequestPayload.pageNum =
            this.outboundPaginator.pageIndex + 1;
            this.outboundRequestPayload.pageSize = this.outboundPaginator.pageSize;
            this.loading = true;
          return this.shipmentManifestService.getShipmentManifest(
            this.outboundRequestPayload
          );
        })
      )
      .subscribe(resp => {
        this.outboundResults = resp.content;
        this.outboundResultsLength = resp.totalElements;
        this.loading = false;
      }, err => {
        this.loading = false;
        this.errorMessage = ['error', err];
      });
  }

  private initInboundPaginationSub() {
    this.inboundPaginationSub = merge(this.inboundPaginator.page, this.inboundPaginator.pageSize)
      .pipe(
        switchMap(() => {
          this.inboundRequestPayload.pageNum =
            this.inboundPaginator.pageIndex + 1;
            this.inboundRequestPayload.pageSize = this.inboundPaginator.pageSize;
            this.loading = true;
          return this.shipmentManifestService.getShipmentManifest(
            this.inboundRequestPayload
          );
        })
      )
      .subscribe(resp => {
        this.inboundResults = resp.content;
        this.inboundResultsLength = resp.totalElements;
        this.loading = false;
      }, err => {
        this.loading = false;
        this.errorMessage = ['error', err];
      });
  }

  private initThirdPartyPaginationSub() {
    this.thirdPartyPaginationSub = merge(this.thirdPartyPaginator.page, this.thirdPartyPaginator.pageSize)
      .pipe(
        switchMap(() => {
          this.thirdPartyRequestPayload.pageNum =
            this.thirdPartyPaginator.pageIndex + 1;
            this.thirdPartyRequestPayload.pageSize = this.thirdPartyPaginator.pageSize;
            this.loading = true;

          return this.shipmentManifestService.getShipmentManifest(
            this.thirdPartyRequestPayload
          );
        })
      )
      .subscribe(resp => {
        this.thirdPartyResults = resp.content;
        this.thirdPartyResultsLength = resp.totalElements;
        this.loading = false;
      }, err => {
        this.loading = false;
        this.errorMessage = ['error', err];
      });
  }

  private generateRequestPayloads() {
    const toFromDates = this.getStartEndDates();

    this.outboundRequestPayload = new PaginatedRequestPayload();
    this.outboundRequestPayload.pageNum = 1;
    this.outboundRequestPayload.pageSize = this.outboundPageSize;
    this.outboundRequestPayload.account = this.account.value;
    this.outboundRequestPayload.startDate = toFromDates[0];
    this.outboundRequestPayload.endDate = toFromDates[1];
    this.outboundRequestPayload.shipmentTypes = 'OUTBOUND';
    this.outboundRequestPayload.sortBy = this.sortByDate.value;
    this.outboundRequestPayload.viewCharges = true;
    this.outboundRequestPayload.searchBy = this.searchBy.value;

    this.inboundRequestPayload = { ...this.outboundRequestPayload };
    this.inboundRequestPayload.shipmentTypes = 'INBOUND';

    this.thirdPartyRequestPayload = { ...this.outboundRequestPayload };
    this.thirdPartyRequestPayload.shipmentTypes = 'THIRDPARTY';

    const {
      pageSize,
      pageNum,
      ...outboundPayloadCopy
    }: any = this.outboundRequestPayload;

    this.emailRequestPayload = {
      ...outboundPayloadCopy,
      shipmentTypes: this.shipmentType.value,
      format: this.fileFormat.value || '',
      email: this.email.value ? this.email.value : ''
    };
  }

  private getShipmentManifest(
    payload: PaginatedRequestPayload
  ): Observable<ShipmentManifestResponse> {
    return this.shipmentManifestService.getShipmentManifest(payload);
  }

  private getStartEndDates(): [string, string] {
    const datePipe = new DatePipe('en-US');
    let startDateString = '';
    let endDateString = '';
    const today = new Date();
    let startDate = new Date(today);
    let endDate = new Date(today);

    switch ((this.dateSelection.value as string).toUpperCase()) {
      case 'TODAYYESTERDAY':
        startDate.setDate(today.getDate() - 1);
        break;
      case 'LAST30DAYS':
        startDate.setDate(today.getDate() - 30);
        break;
      case 'LAST15DAYS':
        startDate.setDate(today.getDate() - 15);
        break;
      case 'LAST7DAYS':
        startDate.setDate(today.getDate() - 7);
        break;
      case 'CUSTOMDATE':
        startDate = new Date(this.fromDate.value);
        endDate = new Date(this.toDate.value);
        break;
      default:
        throw new Error(
          `No matching date selection found for ${this.dateSelection.value}`
        );
    }

    startDateString = datePipe.transform(startDate, 'yyyyMMdd');
    endDateString = datePipe.transform(endDate, 'yyyyMMdd');

    return [startDateString, endDateString];
  }
}
