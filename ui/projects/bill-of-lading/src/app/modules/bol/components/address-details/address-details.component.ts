import {
  Component,
  OnInit,
  Input,
  OnChanges,
  OnDestroy,
  SimpleChanges,
  Output,
  EventEmitter,
  AfterViewInit
} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {
  CountryEnum,
  BookAddress,
  AddressInformation,
  AddressSourceEnum,
  AddressInformationForm
} from '../../models';
import {
  UtilService,
  FormService,
  patternValidator,
  RegEx,
  MessageConstants,
  Masks,
  SnackbarService,
  AddressSelectorDialogService,
  MyEstesFormatters,
  AuthService
} from 'common';
import { MatCheckboxChange } from '@angular/material';
import { BolService } from '../../services/bol.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Observable, Subscription, forkJoin, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-address-details',
  templateUrl: './address-details.component.html',
  styleUrls: ['./address-details.component.scss']
})
export class AddressDetailsComponent implements OnInit, OnDestroy, OnChanges {
  stop$ = new Subject<boolean>();
  // tslint:disable-next-line:no-input-rename
  @Input('formGroupRef') formGroup: FormGroup;
  @Input() headerTitle: string;
  @Input() hideAddressBook: boolean;
  @Input() address: AddressInformation;
  @Input() addressBookSource = true;
  @Input() myestesAddressSource = false;
  @Input() shipperAddressSource = false;
  @Input() consigneeAddressSource = false;
  @Input() consigneeObs: Observable<AddressInformation>;
  @Input() shipperObs: Observable<AddressInformation>;

  useAccountInfo = false;
  loadingMyEstesAccount = false;
  loading = false;
  phoneMask = Masks.phone;
  countries: Array<{ value: string; text: string }> = Object.keys(CountryEnum).map(key => ({
    value: key,
    text: CountryEnum[key]
  }));
  stateLabelText = 'State';
  zipLabelText = 'ZIP Code';
  states$: Observable<Array<string>>;
  formAddressSources: Array<{ value: string; text: string }> = [];
  selectedAddressSource: AddressSourceEnum;
  shipperAddress: AddressInformation;
  consigneeAddress: AddressInformation;
  get disabled() {
    return this.formGroup.disabled;
  }

  constructor(
    private fb: FormBuilder,
    public formService: FormService,
    private bolService: BolService,
    private snackbarService: SnackbarService,
    private addressDialog: AddressSelectorDialogService,
    private utilService: UtilService,
    private auth: AuthService
  ) {}

  ngOnInit() {
    // this.country.disable();
    if (this.shipperObs) {
      this.shipperObs.subscribe(next => {
        this.shipperAddress = next;
      });
    }
    if (this.consigneeObs) {
      this.consigneeObs.subscribe(next => {
        this.consigneeAddress = next;
      });
    }

    if (this.myestesAddressSource) {
      this.formAddressSources.push({
        value: Object.keys(AddressSourceEnum).find(
          e => AddressSourceEnum[e] === AddressSourceEnum.Account
        ),
        text: 'Use My Estes Account info'
      });
    }
    if (this.shipperAddressSource) {
      this.formAddressSources.push({
        value: Object.keys(AddressSourceEnum).find(
          e => AddressSourceEnum[e] === AddressSourceEnum.Shipper
        ),
        text: 'Use Shipper Info above'
      });
    }
    if (this.consigneeAddressSource) {
      this.formAddressSources.push({
        value: Object.keys(AddressSourceEnum).find(
          e => AddressSourceEnum[e] === AddressSourceEnum.Consignee
        ),
        text: 'Use Consignee Info above'
      });
    }

    this.states$ = this.utilService.getStates(this.country.value);
    this.initFormChanges();
  }

  private initFormChanges() {
    this.country.valueChanges.pipe(takeUntil(this.stop$)).subscribe((next: CountryEnum) => {
      this.onCountryChanged(next);

      this.state.reset('');
    });
  }

  private onCountryChanged(val: any) {
    this.states$ = this.utilService.getStates(val);
    switch (CountryEnum[val]) {
      case CountryEnum.CN:
        this.stateLabelText = 'Province';
        this.zipLabelText = 'Postal Code';
        break;
      case CountryEnum.US:
        this.stateLabelText = 'State';
        this.zipLabelText = 'ZIP Code';
        break;
    }
  }

  onAddressSourceChecked(checkbox: MatCheckboxChange, source: AddressSourceEnum) {
    if (!this.disabled && checkbox && checkbox.checked) {
      this.selectedAddressSource = source;
      switch (AddressSourceEnum[this.selectedAddressSource]) {
        case AddressSourceEnum.Account:
          this.setAccountInfoToForm();
          break;
        case AddressSourceEnum.Consignee:
          this.setAddressForm(this.consigneeAddress);
          break;
        case AddressSourceEnum.Shipper:
          this.setAddressForm(this.shipperAddress);
          break;
      }
    }
    if (!this.disabled && checkbox && !checkbox.checked) {
      this.clearAddressForm();
    }
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.formGroup && changes.formGroup.currentValue) {
      this.stop$.next(true);
      this.initFormChanges();

      this.onCountryChanged(
        (changes.formGroup.currentValue as FormGroup).controls['country'].value
      );
    }
    // if (changes.disabled && changes.disabled.currentValue) {
    //   if (changes.disabled.currentValue) {
    //     // this.formGroup.disable();
    //     // this.country.disable();
    //     // this.country
    //     // this.state.disable();
    //     console.log(`disabling...`);
    //   } else {
    //     this.formGroup.enable();
    //     console.log(`enabling...`);
    //   }
    // }
    // if (changes.address && changes.address.currentValue) {
    //   const newAddress = changes.address.currentValue;
    //   this.companyName.setValue(newAddress.companyName);
    //   this.firstName.setValue(newAddress.firstName);
    //   this.lastName.setValue(newAddress.lastName);
    //   this.location.setValue(newAddress.location);
    //   this.phone.setValue(newAddress.phone);
    //   this.phoneExt.setValue(newAddress.phoneExt);
    //   this.fax.setValue(newAddress.fax);
    //   this.email.setValue(newAddress.email);
    //   this.country.setValue(newAddress.country);
    //   this.address1.setValue(newAddress.address1);
    //   this.address2.setValue(newAddress.address2);
    //   this.city.setValue(newAddress.city);
    //   this.state.setValue(newAddress.state);
    //   this.zip.setValue(newAddress.zip);
    // }
    // if (changes.disabled) {
    //   const disabled = changes.disabled.currentValue;
    //   if (disabled === true) {
    //     if (this.formGroup) {
    //       this.formGroup.disable();
    //     }
    //   } else if (disabled === false) {
    //     if (this.formGroup) {
    //       this.formGroup.enable();
    //     }
    //   }
    // }
  }

  getAddressFromForm(): AddressInformation {
    const a = new AddressInformation();

    a.companyName = this.companyName.value;
    a.firstName = this.firstName.value;
    a.lastName = this.lastName.value;
    a.location = this.location.value;
    a.phone = this.phone.value;
    a.phoneExt = this.phoneExt.value;
    a.fax = this.fax.value;
    a.email = this.email.value;
    a.country = this.country.value;
    a.address1 = this.address1.value;
    a.address2 = this.address2.value;
    a.city = this.city.value;
    a.state = this.state.value;
    a.zip = this.zip.value;

    return a;
  }

  setAddressForm(a: AddressInformation) {
    if (a && a instanceof AddressInformation) {
      let addressInfoForm = new AddressInformationForm(a);
      this.companyName.setValue(addressInfoForm.companyName.value || '');
      this.firstName.setValue(addressInfoForm.firstName.value || '');
      this.lastName.setValue(addressInfoForm.lastName.value || '');
      this.location.setValue(addressInfoForm.location.value || '');
      this.phone.setValue(addressInfoForm.phone.value || '');
      this.phoneExt.setValue(addressInfoForm.phoneExt.value || '');
      this.fax.setValue(addressInfoForm.fax.value || '');
      this.country.setValue(addressInfoForm.country.value || '');
      this.address1.setValue(addressInfoForm.address1.value || '');
      this.address2.setValue(addressInfoForm.address2.value || '');
      this.city.setValue(addressInfoForm.city.value || '');
      this.state.setValue(addressInfoForm.state.value || '');
      this.zip.setValue(addressInfoForm.zip.value || '');
      this.email.setValue(addressInfoForm.email.value || '');
    }
  }

  clearAddressForm() {
    this.companyName.setValue('');
    this.firstName.setValue('');
    this.lastName.setValue('');
    this.location.setValue('');
    this.phone.setValue('');
    this.phoneExt.setValue('');
    this.fax.setValue('');
    this.country.setValue(Object.keys(CountryEnum).find(c => CountryEnum[c] === CountryEnum.US));
    this.address1.setValue('');
    this.address2.setValue('');
    this.city.setValue('');
    this.state.setValue('');
    this.zip.setValue('');
    this.email.setValue('');
  }

  openAddressDialog() {
    if (!this.disabled && this.addressBookSource === true) {
      this.addressDialog.openAddressDialog().subscribe(next => {
        if (next) {
          this.useAccountInfo = false;
          this.companyName.setValue(next.company);
          this.firstName.setValue(next.firstName);
          this.lastName.setValue(next.lastName);
          this.location.setValue(next.locationNumber);
          this.phone.setValue(MyEstesFormatters.formatPhone(next.phone));
          this.phoneExt.setValue(next.phoneExt);
          this.fax.setValue(MyEstesFormatters.formatPhone(next.fax));
          this.country.setValue(next.address.country);
          this.address1.setValue(next.address.streetAddress);
          this.address2.setValue(next.address.streetAddress2);
          this.city.setValue(next.address.city);
          this.state.setValue(next.address.state);
          this.zip.setValue(next.address.zip);
          this.email.setValue(next.email);

          this.onAddressSourceChecked(null, null);
        }
      });
    }
  }

  ngOnDestroy() {
    this.stop$.next(true);
    this.stop$.unsubscribe();
  }

  private setAccountInfoToForm() {
    this.loadingMyEstesAccount = true;

    const token = this.auth.getAuthToken();
    const accountCode = token.accountCode;

    forkJoin(
      this.utilService.getProfileInfo(),
      this.utilService.getAccountInfo(accountCode)
    ).subscribe(
      next => {
        const profile = next[0];
        const account = next[1];

        if (account) {
          this.companyName.setValue(account.name);
          this.phone.setValue(MyEstesFormatters.formatPhone(account.phone));
          this.country.setValue(account.address.country);
          this.address1.setValue(account.address.streetAddress);
          this.address2.setValue(account.address.streetAddress2);
          this.city.setValue(account.address.city);
          this.state.setValue(account.address.state);
          this.zip.setValue(account.address.zip);
        }
        if (profile) {
          this.email.setValue(profile.email);
          this.firstName.setValue(profile.firstName);
          this.lastName.setValue(profile.lastName);
        }

        this.location.setValue('');
        this.phoneExt.setValue('');
        this.fax.setValue('');

        this.formGroup.updateValueAndValidity();
      },
      err => {
        this.loadingMyEstesAccount = false;
        let msg = `An unexpected error has occurred.  Please try again.`;
        if (err instanceof HttpErrorResponse) {
          if (err.status === 400) {
            msg = err.error[0].message;
          } else if (err.status === 500) {
            msg = err.error.message;
          }
        }
        this.snackbarService.error(msg);
      },
      () => (this.loadingMyEstesAccount = false)
    );
    // this.utilService.getProfileInfo().subscribe(
    //   next => {
    //     if (next) {
    //     }
    //   },
    //   err => {
    //     this.loadingMyEstesAccount = false;
    //     let msg = `An unexpected error has occurred.  Please try again.`;
    //     if (err instanceof HttpErrorResponse) {
    //       if (err.status === 400) {
    //         msg = err.error[0].message;
    //       } else if (err.status === 500) {
    //         msg = err.error.message;
    //       }
    //     }
    //     this.snackbarService.error(msg);
    //   },
    //   () => (this.loadingMyEstesAccount = false)
    // );
  }

  get companyName() {
    return this.formGroup.controls['companyName'];
  }
  get firstName() {
    return this.formGroup.controls['firstName'];
  }
  get lastName() {
    return this.formGroup.controls['lastName'];
  }
  get location() {
    return this.formGroup.controls['location'];
  }
  get phone() {
    return this.formGroup.controls['phone'];
  }
  get phoneExt() {
    return this.formGroup.controls['phoneExt'];
  }
  get fax() {
    return this.formGroup.controls['fax'];
  }
  get country() {
    return this.formGroup.controls['country'];
  }
  get address1() {
    return this.formGroup.controls['address1'];
  }
  get address2() {
    return this.formGroup.controls['address2'];
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
  get email() {
    return this.formGroup.controls['email'];
  }
}
