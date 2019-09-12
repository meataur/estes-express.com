import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { ShipmentTrackingRequest } from './models/shipment-tracking-request.model';
import { ShipmentTrackingResponse } from './models/shipment-tracking-response.model';
import { ShipmentTrackingService } from './shipment-tracking.service';
import { LeftNavigationService, PromoService, SnackbarService } from 'common';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService, DialogService } from 'common';
import {
  FormGroup,
  FormBuilder,
  FormGroupDirective,
  ValidationErrors
} from '@angular/forms';
import {
  FormService,
  MyEstesValidators,
  RegEx,
  textAreaValidator,
  UtilService
} from 'common';
import { Alert } from 'common';
import { SelectionModel } from '@angular/cdk/collections';
import { EmailDialogComponent } from './components/email-dialog/email-dialog.component';
import {
  MatDialog,
  MatPaginator,
  MatSort,
  MatTableDataSource
} from '@angular/material';
import {
  animate,
  state,
  style,
  transition,
  trigger
} from '@angular/animations';
import { pipe, range, timer, zip } from 'rxjs';
import { expand, delay, tap, takeWhile, takeUntil } from 'rxjs/operators';
import { environment } from '../environments/environment';
import { merge, Subscription } from 'rxjs';
import { ShipmentImageRequest } from './models/shipment-image-request.model';
import { ShipmentImageResponse } from './models/shipment-image-response.model';

@Component({
  selector: 'app-root',
  templateUrl: './shipment-tracking.component.html',
  styleUrls: ['./shipment-tracking.component.scss'],
  animations: [
    trigger('detailExpand', [
      state(
        'collapsed',
        style({
          height: '0px',
          minHeight: '0px',
          display: 'none',
          border: 'none',
          overflow: 'hidden'
        })
      ),
      state(
        'expanded',
        style({
          height: '*',
          overflow: 'hidden'
        })
      ),
      transition(
        'expanded <=> collapsed',
        animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')
      )
    ])
  ]
})
export class ShipmentTrackingComponent implements OnInit, AfterViewInit {
  title = 'Shipment Tracking';
  isAuthenticated: boolean;
  accountCode: string;
  loading: boolean;
  hasOnlyDeliveredShipments: boolean;
  hasPartyShipments: boolean;
  public routeData: any;
  public hintText: string;
  messages: Alert[];
  public shipmentTrackingReq: ShipmentTrackingRequest;
  public shipmentTrackingRes: ShipmentTrackingResponse[];
  public searchByOptions: any;
  expandedElement: any;
  pageSizeOptions: number[];
  numSearchValues: number;
  hasExpandedItems: boolean;
  servicesBaseURL: string;
  formSubmitted: boolean;
  formGroup: FormGroup;
  tableSub: Subscription;

  displayedColumns = [
    'select',
    'pro',
    'pickupDate',
    'bol',
    'poNum',
    'status',
    'toggle'
  ];
  dataSource: MatTableDataSource<ShipmentTrackingResponse>;
  selection = new SelectionModel(true, []);

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  // @ViewChild('shipmentTrackingForm') shipmentTrackingForm: NgForm;
  @ViewChild(FormGroupDirective) shipmentTrackingForm;

  constructor(
    public activatedRoute: ActivatedRoute,
    public shipmentTrackingService: ShipmentTrackingService,
    public leftNavigationService: LeftNavigationService,
    public promoService: PromoService,
    private utilService: UtilService,
    public dialog: MatDialog,
    public dialogService: DialogService,
    public route: Router,
    public authService: AuthService,
    private snackbar: SnackbarService,
    public formService: FormService,
    private fb: FormBuilder
  ) {
    this.searchByOptions = [
      {
        value: 'PRO',
        viewValue: 'PRO'
      },
      {
        value: 'BOL',
        viewValue: 'Bill of Lading'
      },
      {
        value: 'PO',
        viewValue: 'Purchase Order'
      },
      {
        value: 'INTERLINEPRO',
        viewValue: 'Interline PRO'
      },
      {
        value: 'LOADNUMBER',
        viewValue: 'Load Order'
      },
      {
        value: 'EXLAID',
        viewValue: 'Optional EXLA-ID'
      }
    ];
    this.shipmentTrackingRes = [];
    this.messages = [];
    this.hintText = this.searchByOptions[0].value;
    this.isAuthenticated = false;
    this.loading = false;
    this.hasOnlyDeliveredShipments = true;
    this.hasPartyShipments = false;
    this.pageSizeOptions = [25, 50, 75, 100];
    this.numSearchValues = 0;
    this.hasExpandedItems = false;
    this.servicesBaseURL = environment.serviceBaseUrl;
    this.formSubmitted = false;
    // this.proRegex = RegEx.numbersWithDashes;
  }

  get searchBy() {
    return this.formGroup.controls['searchBy'];
  }

  get criteria() {
    return this.formGroup.controls['criteria'];
  }

  expandShipmentRow(element) {
    if (!element.error) {
      if (!element.expanded) {
        this.getShipmentImages(element);
        element.expanded = true;
        this.hasExpandedItems = true;
      } else {
        element.expanded = false;
        let hasExpandedRows = false;
        this.dataSource.data.forEach(row => {
          if (row.expanded === true) {
            hasExpandedRows = true;
          }
        });
        this.hasExpandedItems = hasExpandedRows;
      }
    }
  }

  expandAll() {
    const self = this;
    this.dataSource.data.forEach(row => {
      if (!row.expanded) {
        row.expanded = true;
        self.hasExpandedItems = true;
        this.getShipmentImages(row);
      }
    });
  }

  collapseAll() {
    this.dataSource.data.forEach(row => {
      if (row.expanded === true) {
        row.expanded = false;
      }
    });
    this.hasExpandedItems = false;
  }

  openShipmentTrackingHelpDialog() {
    // tslint:disable-next-line:max-line-length
    this.dialogService.info(
      'Shipment Tracking Help',
      // tslint:disable-next-line: max-line-length
      '<strong>Tracking a Shipment</strong><br><ul><ol><li>Select the type of search criteria from the drop-down menu. You can search with a valid PRO number, Bill of Lading number, Purchase Order number, Interline PRO number or Load Order number.</li><li>To search for multiple shipments at once, enter each shipment number into the field (one number per line).</li><li>Remove your search criteria, and begin a new search by using the Clear button.</li></ol>'
    );
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(EmailDialogComponent, {
      width: '550px',
      panelClass: ['estes-app', 'estes-modal'],
      data: {
        dialogTitle: 'Email Shipment Status Updates',
        sharedData: this.selection
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        if (result.length > 0) {
          for (const item of result) {
            if (item.errorCode) {
              this.messages.push({
                type: 'danger',
                message: item.message
              });
            } else {
              this.messages.push({
                type: 'success',
                message: item.message
              });
            }
          }
        }
      }
    });
  }

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    let numRows = 0;
    this.dataSource.data.forEach(row => {
      if (!this.validDate(row.deliveryDate) && row.isParty === 'Y') {
        numRows += 1;
      }
    });
    return numSelected === numRows;
  }

  masterToggle() {
    this.isAllSelected()
      ? this.selection.clear()
      : this.dataSource.data.forEach(row => {
          if (!this.validDate(row.deliveryDate) && row.isParty === 'Y') {
            this.selection.select(row);
          }
        });
  }

  getShipmentImages(shipment: ShipmentTrackingResponse) {
    shipment.bolImages = new Array<ShipmentImageResponse>();
    shipment.wrCertificateImages = new Array<ShipmentImageResponse>();
    shipment.deliveryReceiptImages = new Array<ShipmentImageResponse>();
    if (this.isAuthenticated && shipment) {
      if (!shipment.bolImageLoaded && shipment.bol !== '' && (shipment.isParty === 'Y')) {
        this.postShipmentImagesRequest(shipment, 'bol');
      }

      if (!shipment.drImageLoaded && shipment.isParty === 'Y') {
        this.postShipmentImagesRequest(shipment, 'dr');
      }
      if (!shipment.wrImageLoaded && shipment.isPayor === 'Y' && shipment.isParty === 'Y') {
        this.postShipmentImagesRequest(shipment, 'wr');
      }
    }
  }

  postShipmentImagesRequest(shipment: ShipmentTrackingResponse, type: string) {
    const imageReq = new ShipmentImageRequest();
      imageReq.proNumber = shipment.pro;

      const timer$ = timer(20000);

      timer$.subscribe(val => {
        shipment.bolImageLoaded = true;
        shipment.drImageLoaded = true;
        shipment.wrImageLoaded = true;
      });

      this.shipmentTrackingService
        .postShipmentImagesRequest(imageReq, type)
        .pipe(
          expand(val => {
            if ( this.retryShipmentImageRequest(val, shipment, type) ) {
              return this.shipmentTrackingService
              .postShipmentImagesRequest(imageReq, type)
              .pipe(delay(5000));
            }
          }),
          tap(val => {
            imageReq.requestNumber = val[0].requestNumber;
            this.processShipmentImageResponse(val, shipment, type);
          }),
          takeWhile(val => this.retryShipmentImageRequest(val, shipment, type)),
          takeUntil(timer$)
        )
        .subscribe(val => {}, err => {
          switch (type) {
            case 'wr':
                shipment.wrImageLoaded = true;
              break;
            case 'dr':
                shipment.drImageLoaded = true;
              break;

            case 'bol':
                shipment.bolImageLoaded = true;
              break;
            default:
          }
        });
  }

  processShipmentImageResponse(
    data: ShipmentImageResponse[],
    shipment: ShipmentTrackingResponse,
    type: string
  ) {
    if (data && data.length > 0) {
      data.forEach(image => {
        if (image.found && image.imagePath) {
          switch (type) {
            case 'wr':
              shipment.wrCertificateImages.push(image);
              break;
            case 'dr':
              shipment.deliveryReceiptImages.push(image);
              break;

            case 'bol':
              shipment.bolImages.push(image);
              break;
            default:
          }
        }
      });
    }
    if (shipment.wrCertificateImages && shipment.wrCertificateImages.length > 0) {
      shipment.wrImageLoaded = true;
    }
    if (shipment.deliveryReceiptImages && shipment.deliveryReceiptImages.length > 0) {
      shipment.drImageLoaded = true;
    }
    if (shipment.bolImages && shipment.bolImages.length > 0) {
      shipment.bolImageLoaded = true;
    }
  }


  retryShipmentImageRequest(
    imageArray: ShipmentImageResponse[],
    shipment: ShipmentTrackingResponse,
    type: string
  ) {
    let retry = false;
    imageArray.forEach(image => {
      retry = retry || image.retry;
    });
    if (!retry) {
      switch (type) {
        case 'bol':
          shipment.bolImageLoaded = true;
          break;
        case 'dr':
            shipment.drImageLoaded = true;
          break;
        case 'wr':
            shipment.wrImageLoaded = true;
          break;
        default:
      }
    }

    return retry;
  }

  searchShipments() {
    const self = this;
    this.resetMessages();
    this.shipmentTrackingRes = [];
    if (this.selection) {
      this.selection.clear();
    }
    if (this.formGroup.valid) {
      this.loading = true;

      const shipmentTrackingReq = new ShipmentTrackingRequest();
      const temp = JSON.stringify(self.criteria.value);

      shipmentTrackingReq.search = JSON.parse(temp.replace(/\\n/g, '£'));
      this.numSearchValues = shipmentTrackingReq.search.split('£').length;

      shipmentTrackingReq.type = self.searchBy.value;

      const authToken = this.authService.getAuthToken();
      if (authToken) {
        shipmentTrackingReq.session = authToken.session;
      }

      shipmentTrackingReq.page = (this.paginator.pageIndex + 1).toString();
      shipmentTrackingReq.rowsPerPage = this.paginator.pageSize.toString();

      this.formSubmitted = true;
      this.hasOnlyDeliveredShipments = true;
      this.hasPartyShipments = false;
      /**
       * To fix collapse issue (toggle) DP2-4459
       */
      this.hasExpandedItems = false;

      this.shipmentTrackingService
        .searchShipments(shipmentTrackingReq)
        .subscribe(
          (data: ShipmentTrackingResponse[]) => {
            if (data) {
              let numValidResults = 0;
              let numErrors = 0;
              for (const item of data) {
                if (item.error && item.errorInfo.message) {
                  numErrors += 1;
                  let itemNum = item.pro;
                  switch (self.searchBy.value) {
                    case 'PO':
                    case 'LOADNUMBER':
                      itemNum = item.poNum;
                      break;
                    default:
                      itemNum = item.pro;
                      break;
                  }
                  // this.messages.push({
                  // 	type: 'danger',
                  // 	message: item.errorInfo.message + ' ' + itemNum
                  // });
                } else {
                  numValidResults += 1;
                }
                if (!this.validDate(item.deliveryDate)) {
                  this.hasOnlyDeliveredShipments = false;
                }
                if (item.isParty === 'Y') {
                  this.hasPartyShipments = true;
                }
                this.shipmentTrackingRes.push(item);
              }
              // console.log('hasOnlyDeliveredShipments:', this.hasOnlyDeliveredShipments);
              // console.log('hasPartyShipments:', this.hasPartyShipments);
              this.dataSource = new MatTableDataSource(
                this.shipmentTrackingRes
              );
              this.loading = false;
              if (numValidResults > 0) {
                if (numErrors > 0) {
                  this.snackbar.success(
                    `Some errors were found. See the search results in the table below.`
                  );
                } else {
                  this.snackbar.success(
                    `See the search results in the table below.`
                  );
                }
              } else if (numErrors > 0) {
                this.snackbar.error(
                  `No valid results returned. Check the results table below for errors.`
                );
              }
            }
          },
          err => {
            // console.error('Error calling service: ShipmentTrackingService', err);
            self.loading = false;
            self.shipmentTrackingRes = [];
            self.formSubmitted = false;
            self.messages.push({
              type: 'danger',
              message: 'Search request failed'
            });
          }
        );
    } else {
      this.snackbar.error(
        `Invalid search criteria. Check the search form for errors.`
      );
      // this.getFormValidationErrors();
    }
  }

  getFormValidationErrors() {
    // console.log('getFormValidationErrors');
    Object.keys(this.formGroup.controls).forEach(key => {
      const controlErrors: ValidationErrors = this.formGroup.get(key).errors;
      if (controlErrors != null) {
        Object.keys(controlErrors).forEach(keyError => {
          // console.error('Key control: ' + key + ', keyError: ' + keyError + ', err value: ', controlErrors[keyError]);
        });
      }
    });
  }

  closeAlert(alert: Alert) {
    this.messages.splice(this.messages.indexOf(alert), 1);
  }

  resetMessages() {
    this.messages = [];
  }

  resetResults() {
    // console.log('resetResults');
    this.resetMessages();
    this.shipmentTrackingForm.resetForm({
      searchBy: 'PRO',
      criteria: ''
    });
    this.shipmentTrackingRes = [];
    this.formSubmitted = false;
    this.dataSource = new MatTableDataSource(this.shipmentTrackingRes);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  validTime(timeStr: string) {
    const timeRegex = /\d{2}\:\d{2}\:\d{2}/;
    return timeRegex.test(timeStr);
  }

  validDate(dateStr: string) {
    const dateRegex = /^\d{4}-\d{2}-\d{2}$/;
    return dateRegex.test(dateStr);
  }

  ngOnInit() {
    const self = this;
    this.formGroup = this.fb.group({
      searchBy: ['PRO', MyEstesValidators.required],
      criteria: [
        '',
        [
          MyEstesValidators.required,
          textAreaValidator(
            RegEx.anythingButWhitespace,
            `Please enter a valid value per line.`
          )
        ]
      ]
    });
    this.searchBy.valueChanges.subscribe(next => {
      // console.log('Search by changed to: ', next);
      for (const option in self.searchByOptions) {
        if (next === self.searchByOptions[option].value) {
          self.hintText = self.searchByOptions[option].viewValue;
        }
      }
    });
    this.dataSource = new MatTableDataSource();

    const authCredentials = this.authService.getAuthToken();
    if (authCredentials != null) {
      this.accountCode = authCredentials.accountCode;
      this.isAuthenticated = true;
    } else {
      this.displayedColumns = [
        'select',
        'pro',
        'pickupDate',
        'status',
        'toggle'
      ];
    }
    this.paginator.pageSize = 25;
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    let searchBy = this.activatedRoute.snapshot.queryParamMap.get('type');
    // console.log('param.type: ' + searchBy);
    let validSearchType = false;
    if (searchBy) {
      searchBy = searchBy.trim();
      for (const option of this.searchByOptions) {
        if (option.value === searchBy) {
          validSearchType = true;
        }
      }
      if (validSearchType) {
        this.searchBy.setValue(searchBy);
      } else {
        searchBy = null;
      }
    }
    const searchVal = this.activatedRoute.snapshot.queryParamMap.get('query');
    // console.log('param.query: ' + searchVal);
    if (validSearchType && searchBy && searchVal) {
      this.criteria.setValue(searchVal.trim());
      this.searchShipments();
    }
    this.leftNavigationService.setNavigation(`Track`, `/track`);
    this.authService.authStateSet.subscribe((authState: string) => {
      // console.log('ShipmentTrackingComponent.authState: ', authState);
      this.promoService.setAppId('shipment-tracking');
      this.promoService.setAppState(authState);
      if (this.isAuthenticated && authState === 'unauthenticated') {
        this.route.navigate(['login']);
      }
      this.isAuthenticated = 'authenticated' === authState ? true : false;
      // console.log('ShipmentTrackingComponent.isAuthenticated: ', this.isAuthenticated);
    });
  }

  ngAfterViewInit() {
    this.paginator.pageSize = 25;
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.tableSub = merge(this.sort.sortChange, this.paginator.page).subscribe(
      data => {
        this.searchShipments();
      }
    );

    if (this.isAuthenticated) {
      this.promoService.setAppId('shipment-tracking');
      this.promoService.setAppState('authenticated');
    } else {
      this.promoService.setAppId('shipment-tracking');
      this.promoService.setAppState('unauthenticated');
    }
  }

  /**
   * Shipment Tracking call utilService to open multiple images in a new tab & show only one link for multipages.
   * @param imageDetails
   */

  openHtml(imageDetails) {
    const imageLocations = [];
    imageDetails.forEach(i => {
      imageLocations.push(this.servicesBaseURL + i.imagePath);
    });
    this.utilService.openImagesInNewTab(imageLocations);
   }

}
