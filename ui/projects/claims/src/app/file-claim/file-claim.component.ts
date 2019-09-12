import { Component, OnInit, Output, EventEmitter, OnDestroy, ViewChild } from '@angular/core';

import {
  FormBuilder,
  FormGroup,
  FormGroupDirective,
  FormControl,
  Validators
} from '@angular/forms';
import { UtilService, FormService, SubAccount, AuthService, Masks, AddressSelectorDialogService, textAreaValidator, MessageConstants, RegEx, Address, patternValidator, MyEstesValidators, UserRoleEnum } from 'common';
import { FeedbackTypes, SnackbarService, DialogService, AccountSelectorDialogService, AccountInfo, MyEstesFormatters, dateValidator } from 'common';
import { MatDialog } from '@angular/material';
import { forkJoin, of, Subscription } from 'rxjs';

import { ClaimsService } from '../claims.service';
import { ClaimantResponse } from '../models/claimant-response.model';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-file-claim',
  templateUrl: './file-claim.component.html',
  styleUrls: ['./file-claim.component.scss']
})
export class FileClaimComponent implements OnInit, OnDestroy {
  @ViewChild(FormGroupDirective) fileClaimsForm;

  @Output() updateSelectedTabEvent = new EventEmitter<string>();

  showClaimForm: boolean;
  showAccountDropdown: boolean;
  loading: boolean;
  submitLoading: boolean;
  isSubmitted: boolean;
  acceptedFileTypes: string;
  maxFileSize: number;
  additionalDetailIndices: number[];

  errorMessages: string[];
  proFeedback: [FeedbackTypes, string];
  confirmationFeedback: [FeedbackTypes, string];
  files: File[];
  formData: FormData;

  formGroup: FormGroup;

  claimantInfo: any = {};
  claimantInfoEmail: string;
  statesProvincesList: String[];

  proMask = Masks.pronumber;
  phoneMask = Masks.phone;

  claimTypes: string[];
  freightTypes: any[];

  shipperName: string;
  shipperAddress: Address;
  consigneeName: string;
  consigneeAddress: Address;

  showClaimantMyInfoError: boolean;
  showRemitMyInfoError: boolean;

  accountNumberSub: Subscription;
  profileInfoSub: Subscription;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private utilService: UtilService,
    public formService: FormService,
    public claimsService: ClaimsService,
    public dialog: MatDialog,
    public addressSelectorDialogService: AddressSelectorDialogService,
    public snackbarService: SnackbarService,
    public dialogService: DialogService,
    public accountSelectorDialogService: AccountSelectorDialogService
  ) {}

   ngOnInit() {
    this.showClaimForm = false;
    this.loading = true;

    this.showClaimantMyInfoError = false;
    this.showRemitMyInfoError = false;

    this.files = [];
    this.formData = new FormData();
    this.errorMessages = [];
    this.proFeedback = null;
    this.isSubmitted = false;

    this.claimTypes = ['Damage', 'Loss'];

    this.freightTypes = [
      {
        viewValue: 'Bags',
        value: 'Bg'
      },
      {
        viewValue: 'Bales',
        value: 'Bl'
      },
      {
        viewValue: 'Barrels',
        value: 'Br'
      },
      {
        viewValue: 'Boxes',
        value: 'Bx'
      },
      {
        viewValue: 'Buckets',
        value: 'Bk'
      },
      {
        viewValue: 'Bundles',
        value: 'Bd'
      },
      {
        viewValue: 'Cans',
        value: 'Cn'
      },
      {
        viewValue: 'Cartons',
        value: 'Ct'
      },
      {
        viewValue: 'Cases',
        value: 'Cs'
      },
      {
        viewValue: 'Crates',
        value: 'Cr'
      },
      {
        viewValue: 'Cylinders',
        value: 'Cy'
      },
      {
        viewValue: 'Drums',
        value: 'Dr'
      },
      {
        viewValue: 'Jerrican',
        value: 'Jc'
      },
      {
        viewValue: 'Kits',
        value: 'Kt'
      },
      {
        viewValue: 'Packages',
        value: 'Pk'
      },
      {
        viewValue: 'Pails',
        value: 'Pl'
      },
      {
        viewValue: 'Pallets',
        value: 'Pt'
      },
      {
        viewValue: 'Pieces',
        value: 'Pc'
      },
      {
        viewValue: 'Reels',
        value: 'Re'
      },
      {
        viewValue: 'Roles',
        value: 'Rl'
      },
      {
        viewValue: 'Skids',
        value: 'Sk'
      },
      {
        viewValue: 'Totes',
        value: 'To'
      },
      {
        viewValue: 'Truckload',
        value: 'Tl'
      }
    ];

    this.acceptedFileTypes = ".doc,.docx,.jpg,.jpeg,.tiff,.pdf,.png,.rtf,.txt,.xls,.xlsx";
    this.maxFileSize = 10485760;

    this.additionalDetailIndices = [];

    this.formGroup = this.fb.group({
      accountNumber: [this.authService.accountCode],
      otPro: ['', [MyEstesValidators.required, patternValidator(RegEx.proNumber, MessageConstants.invalidProNumber)]],
      proDate: [''],
      bol: ['', [MyEstesValidators.required, Validators.maxLength(25)]],
      bolDate: ['', [dateValidator()]],
      referenceNumber: ['', [Validators.maxLength(15)]],
      claimType: ['Damage', [MyEstesValidators.required]],
      freightType: [''],
      name: ['', [MyEstesValidators.required, Validators.maxLength(30)]],
      streetAddress1: ['', [MyEstesValidators.required, Validators.maxLength(30)]],
      streetAddress2: ['', [Validators.maxLength(30)]],
      city: ['', [MyEstesValidators.required, Validators.maxLength(20)]],
      state: ['', [MyEstesValidators.required]],
      zip: ['', [MyEstesValidators.required, Validators.maxLength(6)]],
      phone: ['', [MyEstesValidators.required, patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone)]],
      fax: ['', [patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone)]],
      email: ['', [MyEstesValidators.required, textAreaValidator(RegEx.email, MessageConstants.invalidEmailTextArea), Validators.maxLength(50)]],
      remitName: ['', [MyEstesValidators.required, Validators.maxLength(30)]],
      remitStreetAddress1: ['', [MyEstesValidators.required, Validators.maxLength(30)]],
      remitStreetAddress2: ['', [Validators.maxLength(30)]],
      remitCity: ['', [MyEstesValidators.required, Validators.maxLength(20)]],
      remitState: ['', [MyEstesValidators.required]],
      remitZip: ['', [MyEstesValidators.required, Validators.maxLength(6)]],
      remitPhone: ['', [MyEstesValidators.required, patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone)]],
      bolFile: [''],
      fbFile: [''],
      invoiceFile: ['', [MyEstesValidators.required]],
      otherFile: [''],
      additionalComments: ['', [Validators.maxLength(255)]],
      acknowledgement: [false, [Validators.requiredTrue]],
      autoTotal: ['', MyEstesValidators.required]
    });

    this.addClaimDetail(true);
    this.addClaimDetail();
    this.addClaimDetail();
    this.addClaimDetail();

    this.showAccountDropdown = this.authService.isGroupAccount
                            || this.authService.isNationalAccount
                            || this.authService.is91Account;

    if (this.showAccountDropdown) {
      this.accountNumber.setValidators(Validators.required);
      this.accountNumber.setValue('');
    }

    this.accountNumberSub = this.accountNumber.valueChanges.pipe(
      switchMap(data => {
        return this.utilService.getAccountInfo(data);
      })
    ).subscribe(data => {
      this.claimantInfo = data;
      if (this.claimantInfoEmail) this.claimantInfo.email = this.claimantInfoEmail;
    }, err => {});

    this.profileInfoSub = this.utilService.getProfileInfo().subscribe(res => {
      if (this.claimantInfo) this.claimantInfo.email = res.email || '';
      this.claimantInfoEmail = res.email || '';
    });

    this.initAccountCode();

    let sources = [
      this.utilService.getStates("US"),
      this.utilService.getStates("CN")
    ];

    forkJoin(...sources)
    .subscribe(([res2, res3]) => {
      this.statesProvincesList = res2.concat(res3);
      this.loading = false;
    },
    err => {
      this.errorMessages = err;
      this.loading = false;
    });
   }

   ngOnDestroy() {
     if (this.accountNumberSub) {
       this.accountNumberSub.unsubscribe();
     }
     if (this.profileInfoSub) {
       this.profileInfoSub.unsubscribe();
     }
   }

   private initAccountCode() {
    switch (this.authService.getUserRole()) {
      case UserRoleEnum.Local:
        const token = this.authService.getAuthToken();
        this.accountNumber.setValue(token.accountCode);
        break;
    }
  }

  get accountNumber() {
    return this.formGroup.controls['accountNumber'];
  }
  get otPro() {
    return this.formGroup.controls['otPro'];
  }
  get proDate() {
    return this.formGroup.controls['proDate'];
  }
  get bol() {
    return this.formGroup.controls['bol'];
  }
  get bolDate() {
    return this.formGroup.controls['bolDate'];
  }
  get referenceNumber() {
    return this.formGroup.controls['referenceNumber'];
  }
  get claimType() {
    return this.formGroup.controls['claimType'];
  }
  get freightType() {
    return this.formGroup.controls['freightType'];
  }
  get name() {
    return this.formGroup.controls['name'];
  }
  get streetAddress1() {
    return this.formGroup.controls['streetAddress1'];
  }
  get streetAddress2() {
    return this.formGroup.controls['streetAddress2'];
  }
  get city() {
    return this.formGroup.controls['city'];
  }
  get state() {
    return this.formGroup.controls['state'];
  }
  get zip() {
    return this.formGroup.controls['zip'];
  }
  get phone() {
    return this.formGroup.controls['phone'];
  }
  get fax() {
    return this.formGroup.controls['fax'];
  }
  get email() {
    return this.formGroup.controls['email'];
  }
  get remitName() {
    return this.formGroup.controls['remitName'];
  }
  get remitStreetAddress1() {
    return this.formGroup.controls['remitStreetAddress1'];
  }
  get remitStreetAddress2() {
    return this.formGroup.controls['remitStreetAddress2'];
  }
  get remitCity() {
    return this.formGroup.controls['remitCity'];
  }
  get remitState() {
    return this.formGroup.controls['remitState'];
  }
  get remitZip() {
    return this.formGroup.controls['remitZip'];
  }
  get remitPhone() {
    return this.formGroup.controls['remitPhone'];
  }
  get bolFile() {
    return this.formGroup.controls['bolFile'];
  }
  get fbFile() {
    return this.formGroup.controls['fbFile'];
  }
  get invoiceFile() {
    return this.formGroup.controls['invoiceFile'];
  }
  get otherFile() {
    return this.formGroup.controls['otherFile'];
  }
  get additionalComments() {
    return this.formGroup.controls['additionalComments'];
  }
  get autoTotal() {
    return this.formGroup.controls['autoTotal'];
  }

  getFormControl(formControlName: string) {
    return this.formGroup.controls[formControlName];
  }

  fetchProInfo() {
    if (this.otPro.valid) {
      let pro = this.otPro.value;
      forkJoin(this.claimsService.getProInfo(pro, this.accountNumber.value), this.claimsService.hasEnteredClaim(pro, this.accountNumber.value))
      .subscribe(([data]) => {
        if (data) {
          this.proDate.setValue(data.proDate);
          this.bol.setValue(data.bolNum);
          this.bolDate.setValue(data.bolDate);
          this.proFeedback = null;
          this.shipperName = data.shipperName;
          this.shipperAddress = data.shipperAddress;
          this.consigneeName = data.consigneeName;
          this.consigneeAddress = data.consigneeAddress;
        }
      }, err => {
        this.proFeedback = ['error', err];
      })
    }
  }

  useMyInfo(useForType: string) {
    if (this.claimantInfo) {
      if (useForType == 'claimant') {
        this.setClaimantInfo(this.claimantInfo);
        this.showClaimantMyInfoError = false;
      }
      if (useForType == 'remittance') {
        this.setRemitInfo(this.claimantInfo);
        this.showRemitMyInfoError = false;
      }
    } else {
        switch (useForType) {
          case 'claimant':
            this.showClaimantMyInfoError = true;
            break;
          case 'remittance':
            this.showRemitMyInfoError = true;
            break;
      }
    }
  }

  openAddressDialog(useForType: string) {
    this.addressSelectorDialogService.openAddressDialog().subscribe(next => {
      if (next) {
        if (useForType == 'claimant') {
          this.setClaimantInfo(next);
          this.showClaimantMyInfoError = false;
        }
        if (useForType == 'remittance') {
          this.setRemitInfo(next);
          this.showRemitMyInfoError = false;
        }
      }
    });
  }

  openAccountSearchDialog() {
    this.accountSelectorDialogService.openAccountDialog().subscribe(next => {
      if (next) {
        this.formGroup.controls['accountNumber'].setValue(next);
      }
    });
  }

  useClaimantDataForRemittance() {
    this.remitName.setValue(this.name.value);
    this.remitStreetAddress1.setValue(this.streetAddress1.value);
    this.remitStreetAddress2.setValue(this.streetAddress2.value);
    this.remitCity.setValue(this.city.value);
    this.remitState.setValue(this.state.value);
    this.remitZip.setValue(this.zip.value);
    this.remitPhone.setValue(MyEstesFormatters.formatPhone(this.phone.value));
  }

  setClaimantInfo(userData) {
    if (userData) {
      this.name.setValue(userData.name || (userData.company));
      this.streetAddress1.setValue(userData.address.streetAddress);
      this.streetAddress2.setValue(userData.address.streetAddress2);
      this.city.setValue(userData.address.city);
      this.state.setValue(userData.address.state);
      this.zip.setValue(userData.address.zip);
      this.phone.setValue(MyEstesFormatters.formatPhone(userData.phone));
      this.fax.setValue(MyEstesFormatters.formatPhone(userData.fax));
      this.email.setValue(userData.email);
    }
  }
  setRemitInfo(userData) {
    if (userData) {
      this.remitName.setValue(userData.name || (userData.company));
      this.remitStreetAddress1.setValue(userData.address.streetAddress);
      this.remitStreetAddress2.setValue(userData.address.streetAddress2);
      this.remitCity.setValue(userData.address.city);
      this.remitState.setValue(userData.address.state);
      this.remitZip.setValue(userData.address.zip);
      this.remitPhone.setValue(MyEstesFormatters.formatPhone(userData.phone));
    }
  }
  validateFile(event, formField) {

    if (event.target.files && event.target.files[0]) {
      let file: File = event.target.files[0];
      let fileExt = file.name.split('.').pop();
      if (file.size > this.maxFileSize) {
        formField.reset();
        formField.setErrors({'maxSize': 'File exceeds size limit of 10MB'});
      } else if (!this.acceptedFileTypes.includes(fileExt.toLowerCase())) {
        formField.reset();
        formField.setErrors({'fileType': 'Not an accepted file type.'});
      } else {
        formField.setErrors();
        this.files[event.target.name] = event.target.files[0];
        //this.formData.append(event.target.name, event.target.files[0]);
      }
    }
  }

  addClaimDetail(required?: boolean) {
    let index = this.additionalDetailIndices.length + 1;
    if (index <= 10) {
      this.formGroup.addControl('descriptionDetails' + index.toString(), new FormControl('', Validators.maxLength(35)));
      this.formGroup.addControl('costDetails' + index.toString(), new FormControl('', required ? [MyEstesValidators.required, Validators.max(99999.99), patternValidator(RegEx.numberWithTwoDecimalPlaces, MessageConstants.invalidNumberWithTwoDecimals)] : [patternValidator(RegEx.numberWithTwoDecimalPlaces, MessageConstants.invalidNumberWithTwoDecimals), Validators.max(99999.99)]));
      this.formGroup.addControl('fileDetails' + index.toString(), new FormControl(''));
      this.formGroup.addControl('qtyDetails' + index.toString(), new FormControl('', [patternValidator(RegEx.numbers, MessageConstants.invalidNumbers), Validators.max(999)]));
      this.additionalDetailIndices.push(index);
    }
  }
  removeClaimDetail() {
    let index = this.additionalDetailIndices.pop();
    this.formGroup.removeControl('descriptionDetails' + index.toString());
    this.formGroup.removeControl('costDetails' + index.toString());
    this.formGroup.removeControl('fileDetails' + index.toString());
    this.formGroup.removeControl('qtyDetails' + index.toString());
  }
  calculateAutoTotal() {
    let gTotal = 0.0;
    let ctrl = this;
    this.additionalDetailIndices.forEach( index => {
      let val = ctrl.getFormControl('costDetails' + index.toString()).value.toString();
      gTotal += val ? parseFloat(val) : 0;
      gTotal = +gTotal.toFixed(2);
    });
    this.autoTotal.setValue(gTotal);
  }

  cancelClaim() {
    if (this.formGroup.touched) {
      this.dialogService.confirm('', 'Are you sure you want to cancel?').subscribe(confirm => {
        if (confirm) {
          this.ngOnInit();
          this.updateSelectedTabEvent.next('claims-inquiry');
        }
      });
    } else {
      this.ngOnInit();
      this.updateSelectedTabEvent.next('claims-inquiry');
    }
  }

  onSubmit() {
    this.isSubmitted = true;
    if (this.formGroup.valid) {
      let ctrl = this;
      ctrl.errorMessages = null;
      ctrl.submitLoading = true;
      const rawFormData = ctrl.formGroup.getRawValue();
      this.confirmationFeedback = null;
      const formData = new FormData();
      Object.keys(rawFormData).forEach(key => {
        if (!key.toLowerCase().includes('file')) {
          formData.append(key, rawFormData[key]);
        } else {
          if ( !! rawFormData[key]) {
            formData.append(key, this.files[key]);
          }

        }
      });
      ctrl.claimsService.submitClaim(formData)
      .subscribe(data => {
        ctrl.snackbarService.success('Claim Submitted Successfully');
        ctrl.submitLoading = false;
        this.accountNumberSub.unsubscribe();
        ctrl.fileClaimsForm.resetForm();
        ctrl.ngOnInit();
        this.confirmationFeedback = ['success', data.message];
      }, err => {
        ctrl.errorMessages = ['error', err];
        ctrl.submitLoading = false;
      });
    } else {
      this.snackbarService.error('There are validation errors in the form.  Please review the form and submit again.');
    }
  }
}
