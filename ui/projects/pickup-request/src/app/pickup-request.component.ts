import { Component, OnInit, OnDestroy, ViewChild, ElementRef } from '@angular/core';
import { DatePipe } from '@angular/common';
import { PickupRequestService } from './pickup-request.service';
import { Party } from './models/party.model';
import {
  FormBuilder,
  FormGroup,
  FormArray,
  Validators,
  FormControl
} from '@angular/forms';
import { UtilService, FormService, SubAccount, AccountInfo, AuthService, Masks, AddressSelectorDialogService, AccountSelectorDialogService, validateFormFields, startEndTimeValidator, MessageConstants, RegEx, Address, patternValidator, ProfileDTO, MyEstesValidators } from 'common';
import { pickupServiceMarkup, DialogService, SnackbarService, PromoService, LeftNavigationService, MyEstesFormatters, dateValidator } from 'common';
import { forkJoin, Subscription, combineLatest } from 'rxjs';
import { startWith, switchMap } from 'rxjs/operators';
import { UserInformation } from './models/user-information.model';
import { Commodity } from './models/commodity.model';
import { PickupRequest } from './models/pickup-request.model';
import { Shipment } from './models/shipment.model';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-pickup-request',
  templateUrl: './pickup-request.component.html',
  styleUrls: ['./pickup-request.component.scss']
})
export class PickupRequestComponent implements OnInit, OnDestroy {
  @ViewChild('myForm') myFormElement: ElementRef;

  tabSelectedIndex: number;
  shippers: Party[];
  users: UserInformation[];
  commodities: Commodity[];
  errorMessages: string[];
  successMessage: string[];
  loading: boolean;
  formGroup: FormGroup;
  subAccounts: SubAccount[];
  profileInfo: ProfileDTO;
  role: string;
  accountInfo: AccountInfo;
  phoneMask = Masks.phone;
  statesList: string[];
  today: Date;
  maxDate: Date;
  useInfoCheckboxChecked: string;
  datePipe: DatePipe;
  results: PickupRequest;
  guaranteedCount: number;
  isSubmitted: boolean;
  params: any;
  paramSub: Subscription;
  isTimeInvalid: boolean = false;
  timeSub: Subscription;
  infoSub: Subscription;
  stateSub: Subscription;
  pickupSub: Subscription;

  freezeHtml = pickupServiceMarkup.freeze;
  liftgateHtml = pickupServiceMarkup.liftgate;
  oversizeHtml = pickupServiceMarkup.oversize;
  noStackPalletsHtml = pickupServiceMarkup.stackPallets;
  foodHtml = pickupServiceMarkup.food;
  poisonHtml = pickupServiceMarkup.poison;
  efwHtml = pickupServiceMarkup.efw;
  guaranteedHtml = pickupServiceMarkup.guaranteed;
  hookDropHtml = pickupServiceMarkup.hookDrop;
  pickupRequestHtml = pickupServiceMarkup.pickupRequest;
  acceptedRequestHtml = pickupServiceMarkup.acceptedRequest;
  completedPickupHtml = pickupServiceMarkup.completedPickup;
  guaranteedTermsHtml = pickupServiceMarkup.guaranteedTerms;

  constructor(
    private pickupRequestService: PickupRequestService,
    private fb: FormBuilder,
    private formService: FormService,
    private authService: AuthService,
    private utilService: UtilService,
    private accountSelectorDialogService: AccountSelectorDialogService,
    private addressDialog: AddressSelectorDialogService,
    private dialogService: DialogService,
    private snackbarService: SnackbarService,
    private route: ActivatedRoute,
    private router: Router,
    private promoService: PromoService,
	private leftNavigationService: LeftNavigationService
  ) { }

  ngOnInit() {
    this.tabSelectedIndex = 0;
    this.role = '';

    this.today = new Date();
    this.maxDate = new Date();
    this.maxDate.setDate(this.today.getDate() + 30);

    this.useInfoCheckboxChecked = null;

    this.datePipe = new DatePipe('en-US');

    this.errorMessages = null;
    this.successMessage = null;

    this.guaranteedCount = 0;

    this.isSubmitted = false;

    this.leftNavigationService.setNavigation(`Ship`, `/ship`);
    this.promoService.setAppId('pickup-request');
    this.promoService.setAppState('authenticated');
    

 	this.authService.authStateSet.subscribe((authState: string) => {
      if ('unauthenticated' === authState) {
        this.router.navigate(['login']);
	  }
    });

    this.params = {};

    this.paramSub = this.route.queryParams.subscribe(params => {
      this.params = params;
   });

   //get profile and account info
   this.infoSub = this.utilService.getProfileInfo().pipe(
     switchMap(data => {
       //set profile info
       this.profileInfo = data;
       this.formGroup.get('user.name').setValue(this.params['name'] || this.profileInfo.firstName + ' ' + this.profileInfo.lastName || '');
       this.formGroup.get('user.email').setValue(this.params['email'] || this.profileInfo.email || '');
      //Use account code from profile info to fetch account information. We use the account information to prepopulate
       if (data.accountCode) {
         return this.utilService.getAccountInfo(data.accountCode);
       }
     })
   ).subscribe(data => {
     if (data) {
       //set account info
      this.accountInfo = data;
      let phone = this.params['phone'] || this.accountInfo.phone;
      this.formGroup.get('user.phone').setValue(MyEstesFormatters.formatPhone(phone) || '');
     }
   });

   //Get state/province data
   let stateSources = [
    this.utilService.getStates('US'),
    this.utilService.getStates('CN')];

   this.stateSub = forkJoin(...stateSources)
    .subscribe(([res1, res2]) => {
      this.statesList = res1.concat(res2);
    }, err => {
      this.errorMessages = ['error', err];
    });

    //get pickup data
    let pickupSources = [ this.pickupRequestService.getShippers(10),
      this.pickupRequestService.getUsers(10),
      this.pickupRequestService.getCommodities(10)];

    this.pickupSub = forkJoin(...pickupSources)
    .subscribe(([res1, res2, res3]) => {
      this.shippers = res1;
      this.users = res2;
      this.commodities = res3;
    }, err => {
      this.errorMessages = ['error', err];
    });

    //intialize formgroup
    this.formGroup = this.fb.group({
      user: this.fb.group({
        name: [this.params['name'] || '', [MyEstesValidators.required, Validators.maxLength(30)]],
        phone: [this.params['phone'] || '', [MyEstesValidators.required, patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone)]],
        phoneExt: [this.params['ext'] || '', [Validators.maxLength(5), patternValidator(RegEx.numbers, MessageConstants.invalidNumbers)]],
        email: [this.params['email'] || '', [MyEstesValidators.required, patternValidator(RegEx.email, MessageConstants.invalidEmail)]],
        role: ['', MyEstesValidators.required]
      }),
      shipper: this.fb.group({
        accountCode: [''],
        name: ['', [MyEstesValidators.required, Validators.maxLength(30)]],
        company: ['', [MyEstesValidators.required, Validators.maxLength(30)]],
        addressLine1: ['', [MyEstesValidators.required, Validators.maxLength(30)]],
        addressLine2: ['', Validators.maxLength(30)],
        city: [this.params['city'] || '', [MyEstesValidators.required, Validators.maxLength(20)]],
        state: [this.params['state'] || '', [MyEstesValidators.required, Validators.maxLength(2), Validators.minLength(2)]],
        zip: [this.params['zip'] || '', [MyEstesValidators.required, Validators.maxLength(6), Validators.minLength(5)]],
        zipExt: ['', Validators.maxLength(4)],
        phone: ['', [MyEstesValidators.required, patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone)]],
        phoneExt: ['', [Validators.maxLength(5), patternValidator(RegEx.numbers, MessageConstants.invalidNumbers)]],
        email: ['', [MyEstesValidators.required, patternValidator(RegEx.email, MessageConstants.invalidEmail)]]
      }),
      pickupInfo: this.fb.group({
        pickupDate: [this.params['pickupDate'] ? new Date(this.params['pickupDate']) : '', [MyEstesValidators.required, dateValidator()]],
        availableByHour: [this.params['availableByHour'] || '08'],
        availableByMinutes: [this.params['availableByMinutes'] || '00'],
        availableByAmPm: [this.params['availableByAmPm'] || 'AM'],
        startTime: [''],
        closesByHour: [this.params['closesByHour'] || '05'],
        closesByMinutes: [this.params['closesByMinutes'] || '00'],
        closesByAmPm: [this.params['closesByAmPm'] || 'PM'],
        endTime: [''],
        hookOrDrop: [false],
        liftgateRequired: [false],
        noStackPallet: [(this.params['noStack'] === 'true') ? true : false]
      }),
      shipmentInfo: this.fb.array([]),
      partyNotificationList: this.fb.array([])
    });
    this.addNewShipmentInfo();
    this.intitTimeSub();

    this.accountNumber.valueChanges.subscribe(val => this.updateAccountNumber());
  }

  ngOnDestroy() {
    if (this.paramSub) {
      this.paramSub.unsubscribe();
    }
    if (this.timeSub) {
      this.timeSub.unsubscribe();
    }
    if (this.stateSub) {
      this.stateSub.unsubscribe();
    }
    if (this.infoSub) {
      this.infoSub.unsubscribe();
    }
  }

  get hasCreatePickupRequestAccess() {
    return this.authService.isInScope('PKN200');
  }

  get hasPickupHistoryAccess() {
    return this.authService.isInScope('PICKUPHIST');
  }

  addNewShipmentInfo() {
    let shipmentInfoArray = this.formGroup.get('shipmentInfo') as FormArray;
    shipmentInfoArray.push(
      this.fb.group({
        shipmentService: [this.params['shipmentService'] || 'LTL'],
        shipmentOption: this.fb.group({
          freeze: [false],
          oversize: [false],
          food: [false],
          poision: [false]
        }),
        notification: this.fb.group({
          accepted: [false],
          completed: [false],
          rejected: [true]
        }),
        commodity: this.fb.group({
          destinationZipCode: [this.params['dzip'] || '', [MyEstesValidators.required, Validators.maxLength(6), Validators.minLength(5)]],
          destinationZipCodeExt: ['', Validators.maxLength(4)],
          totalPieces: [this.params['pieces'] || null, [MyEstesValidators.required, Validators.max(999999), Validators.min(0)]],
          totalWeight: [this.params['weight'] || null, [MyEstesValidators.required, Validators.max(99999), Validators.min(0)]],
          totalSkids: [null, [Validators.max(99), Validators.min(0)]],
          hazmat: [false],
          specialInstructions: ['', Validators.maxLength(256)],
          referenceNumber: ['', Validators.maxLength(25)]
        })
      })
    );
  }

  intitTimeSub() {
    this.timeSub = combineLatest(
      this.availableByHour.valueChanges.pipe(startWith(this.availableByHour.value)),
      this.availableByMinutes.valueChanges.pipe(startWith(this.availableByMinutes.value)),
      this.availableByAmPm.valueChanges.pipe(startWith(this.availableByAmPm.value)),
      this.closesByHour.valueChanges.pipe(startWith(this.closesByHour.value)),
      this.closesByMinutes.valueChanges.pipe(startWith(this.closesByMinutes.value)),
      this.closesByAmPm.valueChanges.pipe(startWith(this.closesByAmPm.value))
    ).subscribe(() => {
      this.isTimeInvalid = startEndTimeValidator(this.availableByHour, this.availableByMinutes, this.availableByAmPm, this.closesByHour, this.closesByMinutes, this.closesByAmPm);
    });
  }

  get shipmentInfo(): FormArray {
    return this.formGroup.controls['shipmentInfo'] as FormArray;
  }

  get partyNotificationList(): FormArray {
    return this.formGroup.controls['partyNotificationList'] as FormArray;
  }

  get pickupInfo(): FormGroup {
    return this.formGroup.controls['pickupInfo'] as FormGroup;
  }

  get availableByHour() {
    return this.pickupInfo.controls['availableByHour'];
  }

  get availableByMinutes() {
    return this.pickupInfo.controls['availableByMinutes'];
  }

  get availableByAmPm() {
    return this.pickupInfo.controls['availableByAmPm'];
  }

  get closesByHour() {
    return this.pickupInfo.controls['closesByHour'];
  }

  get closesByMinutes() {
    return this.pickupInfo.controls['closesByMinutes'];
  }

  get closesByAmPm() {
    return this.pickupInfo.controls['closesByAmPm'];
  }

  get user(): FormGroup {
    return this.formGroup.controls['user'] as FormGroup;
  }

  get shipper(): FormGroup {
    return this.formGroup.controls['shipper'] as FormGroup;
  }

  get hookOrDrop() {
    return this.pickupInfo.controls['hookOrDrop'];
  }

  get liftgateRequired() {
    return this.pickupInfo.controls['liftgateRequired'];
  }

  get noStackPallet() {
    return this.pickupInfo.controls['noStackPallet'];
  }

  get accountNumber() {
    return this.shipper.controls['accountCode'];
  }

  trimShipmentInfoDetails(checked: boolean) {
    if (checked) {
      while(this.shipmentInfo.controls.length > 1) {
        this.shipmentInfo.controls.pop();
      }
    }
  }

  setUserInformationFields(user: UserInformation) {
    if (user) {
      this.formGroup.get('user.name').setValue(user.name || '');
      this.formGroup.get('user.phone').setValue(MyEstesFormatters.formatPhone(user.phone) || '');
      this.formGroup.get('user.phoneExt').setValue(user.phoneExt || '');
      this.formGroup.get('user.email').setValue(user.email || '');
      this.formGroup.get('user.role').setValue(user.role || '');
    } else {
      this.clearUserInformationFields();
    }
  }

  clearUserInformationFields() {
    this.user.setValue({name: '', phone: '', phoneExt: '', email: '', role: ''});
  }

  setShipperInformationFields(shipper: Party) {
    this.clearShipperInformationFields();
    if (shipper) {
      this.formGroup.get('shipper').setValue(shipper);
    }
  }

  clearShipperInformationFields() {
    this.formGroup.get('shipper').setValue({ accountCode: '', name: '', company: '', addressLine1: '', addressLine2: '', city: '',
                                      state: '', zip: '', zipExt: '', phone: '', phoneExt: '', email: ''});
  }

  setShipmentInformationFields(commodity: Commodity, index: number) {
    let shipmentInfoArray = this.formGroup.get('shipmentInfo') as FormArray;
    if (commodity) {
      shipmentInfoArray.at(index).get('commodity').setValue(commodity);
    } else {
      this.clearShipmentInformationFields(shipmentInfoArray.at(index) as FormGroup);
    }
  }

  clearShipmentInformationFields(shipmentInfo: FormGroup) {
    shipmentInfo.get('commodity').setValue({destinationZipCode: '', destinationZipCodeExt: '', totalPieces: null, totalWeight: null, totalSkids: null, hazmat: false, specialInstructions: '', referenceNumber: ''});
  }

  openAccountSearchDialog() {
    this.accountSelectorDialogService.openAccountDialog().subscribe(next => {
      if (next) {
        this.accountNumber.setValue(next);
      }
    });
  }

  updateRole(role: string) {
    this.role = role;
    if (role == 'SHIPPER') {
      this.accountNumber.setValidators(Validators.required);
      this.shipper.controls['company'].disable();
      this.shipper.controls['addressLine1'].disable();
      this.shipper.controls['addressLine2'].disable();
      this.shipper.controls['city'].disable();
      this.shipper.controls['state'].disable();
      this.shipper.controls['zip'].disable();
      this.shipper.controls['zipExt'].disable();
      if (this.authService.isLocalAccount) {
        this.useAccountInfoForShipper();
      }
    } else {
      this.accountNumber.setValidators([]);
      this.shipper.controls['company'].enable();
      this.shipper.controls['addressLine1'].enable();
      this.shipper.controls['addressLine2'].enable();
      this.shipper.controls['city'].enable();
      this.shipper.controls['state'].enable();
      this.shipper.controls['zip'].enable();
      this.shipper.controls['zipExt'].enable();
      this.clearShipperInformationFields();
    }
  }

  updateAccountNumber() {
    let accountNum = this.accountNumber.value;
    if (accountNum) {
      this.utilService.getAccountInfo(accountNum).subscribe(data => {
        this.accountInfo = data;
        this.useAccountInfoForShipper();
      }, err => {
        this.errorMessages = ['error', err]
      });
    }
  }

  useMyInfoCheckboxChange(checked: boolean) {
    this.clearShipperInformationFields();
    if (checked) {
      this.useInfoCheckboxChecked = 'useAccountInfo';
      this.useAccountInfoForShipper();
      this.accountNumber.setValue('');
    }
  }

  useRequestorInfoCheckboxChange(checked: boolean) {
    this.clearShipperInformationFields();
    if (checked) {
      this.useInfoCheckboxChecked = 'useRequestorInfo';
      this.formGroup.get('shipper.name').setValue(this.formGroup.get('user.name').value);
      this.formGroup.get('shipper.phone').setValue(MyEstesFormatters.formatPhone(this.formGroup.get('user.phone').value));
      this.formGroup.get('shipper.phoneExt').setValue(this.formGroup.get('user.phoneExt').value);
      this.formGroup.get('shipper.email').setValue(this.formGroup.get('user.email').value);
    }
  }

  useAccountInfoForShipper() {
    if (this.accountInfo) {
      this.useAddressForShipper(this.accountInfo.address);
      if (this.accountInfo.name) this.formGroup.get('shipper.company').setValue(this.accountInfo.name);
      if (this.accountInfo.phone) this.formGroup.get('shipper.phone').setValue(MyEstesFormatters.formatPhone(this.accountInfo.phone));
      if (this.accountInfo.contactName) this.formGroup.get('shipper.name').setValue(this.accountInfo.contactName);
    }
    if (this.profileInfo) {
      if (this.profileInfo.firstName && this.profileInfo.lastName) this.formGroup.get('shipper.name').setValue(this.profileInfo.firstName + ' ' + this.profileInfo.lastName);
      if (this.profileInfo.email) this.formGroup.get('shipper.email').setValue(this.profileInfo.email);
    }
  }

  // useProfileInfoForShipper() {
  //   this.formGroup.get('shipper.name').setValue(this.profileInfo.firstName + ' ' + this.profileInfo.lastName || '');
  //   this.formGroup.get('shipper.phone').setValue(MyEstesFormatters.formatPhone(this.profileInfo.phone) || '');
  //   this.formGroup.get('shipper.email').setValue(this.profileInfo.email || '');
  // }

  useAddressForShipper(address) {
    if (address) {
      this.formGroup.get('shipper.addressLine1').setValue(address.streetAddress);
      this.formGroup.get('shipper.addressLine2').setValue(address.streetAddress2);
      this.formGroup.get('shipper.city').setValue(address.city);
      this.formGroup.get('shipper.state').setValue(address.state);
      this.formGroup.get('shipper.zip').setValue(address.zip);
      this.formGroup.get('shipper.zipExt').setValue(address.zip4);
    }
  }

  openAddressDialogShipper() {
    this.addressDialog.openAddressDialog().subscribe(next => {
      if (next) {
        this.clearShipperInformationFields();
        this.formGroup.get('shipper.company').setValue(next.company);
        this.formGroup.get('shipper.name').setValue(next.firstName + ' ' + next.lastName);
        this.formGroup.get('shipper.phone').setValue(MyEstesFormatters.formatPhone(next.phone));
        this.formGroup.get('shipper.phoneExt').setValue(next.phoneExt);
        this.formGroup.get('shipper.email').setValue(next.email);
        this.useAddressForShipper(next.address);
        this.useInfoCheckboxChecked = null;
      }
    });
  }

  openAddressDialogRequestor() {
    this.addressDialog.openAddressDialog().subscribe(next => {
      if (next) {
        this.clearUserInformationFields();
        this.formGroup.get('user.name').setValue(next.firstName + ' ' + next.lastName);
        this.formGroup.get('user.phone').setValue(MyEstesFormatters.formatPhone(next.phone) || '');
        this.formGroup.get('user.phoneExt').setValue(next.phoneExt || '');
        this.formGroup.get('user.email').setValue(next.email || '');
      }
    });
  }

  openInfoModal(html: string) {
    this.dialogService.info('', html);
  }

  deleteShipmentLine(indexToRemove: number) {
    this.dialogService.confirm('', 'Are you sure you want to delete?').subscribe(confirm => {
      if (confirm) {
        this.shipmentInfo.removeAt(indexToRemove);
      }
    });
  }

  shipmentTypeChange() {
    let removeTos = true;
    this.shipmentInfo.controls.forEach(shipInfo => {
      if (shipInfo.get('shipmentService').value == 'GUARANTEED') {
        removeTos = false;
        if (!this.formGroup.controls['tos']) {
          this.formGroup.addControl('tos', new FormControl(false, Validators.requiredTrue));
        }
      }
    });
    if (removeTos) {
      this.formGroup.removeControl('tos');
    }
  }

  requestAnotherPickupClick() {
    let userName = this.user.controls['name'].value;
    let userEmail = this.user.controls['email'].value;
    let userPhone = this.user.controls['phone'].value;
    let userPhoneExt = this.user.controls['phoneExt'].value;
    let shipperCompany = this.shipper.controls['company'].value;
    let shipperName = this.shipper.controls['name'].value;
    let shipperStreet = this.shipper.controls['addressLine1'].value;
    let shipperStreet2 = this.shipper.controls['addressLine2'].value;
    let shipperCity = this.shipper.controls['city'].value;
    let shipperState = this.shipper.controls['state'].value;
    let shipperZip = this.shipper.controls['zip'].value;
    let shipperZipExt = this.shipper.controls['zipExt'].value;
    let shipperPhone = this.shipper.controls['phone'].value;
    let shipperPhoneExt = this.shipper.controls['phoneExt'].value;
    let shipperEmail = this.shipper.controls['email'].value;
    let pickupDate = new Date(this.pickupInfo.controls['pickupDate'].value);
    let availableByHour = this.pickupInfo.controls['availableByHour'].value;
    let availableByMinutes = this.pickupInfo.controls['availableByMinutes'].value;
    let availableByAmPm = this.pickupInfo.controls['availableByAmPm'].value;
    let closesByHour = this.pickupInfo.controls['closesByHour'].value;
    let closesByMinutes = this.pickupInfo.controls['closesByMinutes'].value;
    let closesByAmPm = this.pickupInfo.controls['closesByAmPm'].value;
    this.ngOnInit();
    this.shipper.controls['name'].setValue(userName);
    this.shipper.controls['email'].setValue(userEmail);
    this.shipper.controls['phone'].setValue(MyEstesFormatters.formatPhone(userPhone));
    this.shipper.controls['phoneExt'].setValue(userPhoneExt);
    this.shipper.controls['company'].setValue(shipperCompany);
    this.shipper.controls['name'].setValue(shipperName);
    this.shipper.controls['addressLine1'].setValue(shipperStreet);
    this.shipper.controls['addressLine2'].setValue(shipperStreet2);
    this.shipper.controls['city'].setValue(shipperCity);
    this.shipper.controls['state'].setValue(shipperState);
    this.shipper.controls['zip'].setValue(shipperZip);
    this.shipper.controls['zipExt'].setValue(shipperZipExt);
    this.shipper.controls['phone'].setValue(MyEstesFormatters.formatPhone(shipperPhone));
    this.shipper.controls['phoneExt'].setValue(shipperPhoneExt);
    this.shipper.controls['email'].setValue(shipperEmail);
    this.pickupInfo.controls['pickupDate'].setValue(pickupDate);
    this.pickupInfo.controls['availableByHour'].setValue(availableByHour);
    this.pickupInfo.controls['availableByMinutes'].setValue(availableByMinutes);
    this.pickupInfo.controls['availableByAmPm'].setValue(availableByAmPm);
    this.pickupInfo.controls['closesByHour'].setValue(closesByHour);
    this.pickupInfo.controls['closesByMinutes'].setValue(closesByMinutes);
    this.pickupInfo.controls['closesByAmPm'].setValue(closesByAmPm);

    window.scroll(0, 0);

  }

  onSubmit() {
    this.isSubmitted = true;
    if (this.formGroup.valid) {
      const availableByHour = this.formGroup.get('pickupInfo.availableByHour').value;
      const availableByMinutes = this.formGroup.get('pickupInfo.availableByMinutes').value;
      const availableByAmPm = this.formGroup.get('pickupInfo.availableByAmPm').value;
      const closesByHour = this.formGroup.get('pickupInfo.closesByHour').value;
      const closesByMinutes = this.formGroup.get('pickupInfo.closesByMinutes').value;
      const closesByAmPm = this.formGroup.get('pickupInfo.closesByAmPm').value;

      //set pickup info start and end time values
      this.pickupInfo.controls['startTime'].setValue(`${availableByHour}:${availableByMinutes} ${availableByAmPm}`);
      this.pickupInfo.controls['endTime'].setValue(`${closesByHour}:${closesByMinutes} ${closesByAmPm}`);

      //for each shipment info, if notification accepted or completed, rejected is false, else rejected is true
      this.shipmentInfo.controls.forEach(shipInfo => {
        if (!shipInfo.get('notification.accepted').value && !shipInfo.get('notification.completed').value) {
          shipInfo.get('notification.rejected').setValue(true);
        }
      });

      //format pickup date
      let request = this.formGroup.getRawValue();
      let pickupDate = this.pickupInfo.controls['pickupDate'].value;
      let formattedPickupDate = this.datePipe.transform(pickupDate, 'MM/dd/yyyy');
      request.pickupInfo.pickupDate = formattedPickupDate;

      this.partyNotificationList.push(this.user);

      this.loading = true;

      this.pickupRequestService.createPickupRequest(request).subscribe(data => {
        this.loading = false;
        this.successMessage = ['success', 'Your pickup request has been sent.'];
        window.scrollTo({ top: 0, behavior: 'smooth' });
        this.results = data;
      }, err => {
        this.loading = false;
        this.errorMessages = ['error', err];
      });
    } else {
      this.errorMessages = null;
      this.snackbarService.validationError();
      validateFormFields(this.formGroup);
      this.scrollToError();
    }
  }

  scrollToError(): void {
    const firstElementWithError = this.myFormElement.nativeElement.querySelector('.mat-form-field.ng-invalid');
    if(firstElementWithError) {
      firstElementWithError.scrollIntoView({ behavior: 'smooth', block: 'center' });
     }
 }

  weekendDateFilter = (d: Date): boolean => {
    return (d.getDay() !== 6 && d.getDay() !== 0);
  }

}
