import {
  Component,
  OnInit,
  ViewChild,
  OnDestroy,
  AfterViewInit,
  OnChanges,
  SimpleChanges
} from '@angular/core';
import { AddressDetailsComponent } from '../address-details/address-details.component';
import {
  AddressSelectorDialogService,
  DialogService,
  validateFormFields,
  patternValidator,
  RegEx,
  MessageConstants,
  UtilService,
  MyEstesValidators,
  AccountInfo
} from 'common';
import { AddAddressModalComponent } from '../add-address-modal/add-address-modal.component';
import {
  BookAddress,
  RoleEnum,
  AddressInformation,
  BolSection,
  BillOfLading,
  RateQuote,
  CountryEnum
} from '../../models';
import { PickupRequestFormService } from '../../services/pickup-request-form.service';
import { Subscription, Subject, of } from 'rxjs';
import { FormGroup, Validators } from '@angular/forms';
import { ShipperConsigneeFormService } from '../../services/shipper-consignee-form.service';
import { takeUntil, switchMap, map, tap } from 'rxjs/operators';
import { ActivatedRoute } from '@angular/router';
import { QuoteDetailsFormService } from '../../services/quote-details.service';

@Component({
  selector: 'app-shipper-consignee-details',
  templateUrl: './shipper-consignee-details.component.html',
  styleUrls: ['./shipper-consignee-details.component.scss']
})
export class ShipperConsigneeDetailsComponent extends BolSection
  implements OnInit, OnDestroy, OnChanges {
  stop$ = new Subject<boolean>();
  formGroup: FormGroup;
  formSub: Subscription;
  roleSelectedSub: Subscription;
  generatePickupSub: Subscription;
  lastRoleSelected: string;
  hideShipperAddressBook: boolean = false;
  queryParamConfig: {
    populate: 'consignee' | 'shipper';
    street1: string;
    street2: string;
    city: string;
    state: string;
    zip: string;
  };
  queryParamSub: Subscription;

  constructor(
    private dialogService: DialogService,
    private pickupRequestFormService: PickupRequestFormService,
    private shipperConsigneeFormService: ShipperConsigneeFormService,
    private utilService: UtilService,
    private quoteFormService: QuoteDetailsFormService,
    private route: ActivatedRoute
  ) {
    super();
  }

  ngOnInit() {
    this.queryParamSub = this.route.queryParamMap.subscribe(next => {
      const paramMap = next;

      if (paramMap.has('populate')) {
        this.queryParamConfig = {
          populate: paramMap.get('populate') as 'consignee' | 'shipper',
          street1: paramMap.get('street1') || '',
          street2: paramMap.get('street2') || '',
          city: paramMap.get('city') || '',
          state: paramMap.get('state') || '',
          zip: paramMap.get('zip') || ''
        };
      }
    });

    this.formSub = this.shipperConsigneeFormService.shipperConsigneeForm$.subscribe(fg => {
      this.stop$.next(true);
      this.formGroup = fg;

      this.quoteFormService.rateQuote$.pipe(takeUntil(this.stop$)).subscribe(next => {
        this.shipperConsigneeFormService.rateQuote = next;

        if (next) {
          const shipperCountry = this.shipperInfo.controls['country'];
          const shipperCity = this.shipperInfo.controls['city'];
          const shipperState = this.shipperInfo.controls['state'];
          const shipperZip = this.shipperInfo.controls['zip'];

          shipperCountry.setValue(next.origin.country);
          shipperCity.setValue(next.origin.city);
          shipperState.setValue(next.origin.state);
          shipperZip.setValue(next.origin.zip);

          const consigneeCountry = this.consigneeInfo.controls['country'];
          const consigneeCity = this.consigneeInfo.controls['city'];
          const consigneeState = this.consigneeInfo.controls['state'];
          const consigneeZip = this.consigneeInfo.controls['zip'];

          consigneeCountry.setValue(next.dest.country);
          consigneeCity.setValue(next.dest.city);
          consigneeState.setValue(next.dest.state);
          consigneeZip.setValue(next.dest.zip);
        }
      });

      this.pickupRequestFormService.generatePickupRequest$
        .pipe(takeUntil(this.stop$))
        .subscribe(next => {
          const email = this.shipperInfo.controls['email'];
          const firstName = this.shipperInfo.controls['firstName'];
          const phone = this.shipperInfo.controls['phone'];
          if (next === true) {
            email.setValidators([
              MyEstesValidators.required,
              patternValidator(RegEx.email, MessageConstants.invalidEmail),
              Validators.maxLength(50)
            ]);
            firstName.setValidators([MyEstesValidators.required, Validators.maxLength(25)]);
            phone.setValidators([
              MyEstesValidators.required,
              patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone)
            ]);
          } else if (next === false) {
            email.setValidators([
              patternValidator(RegEx.email, MessageConstants.invalidEmail),
              Validators.maxLength(50)
            ]);
            firstName.setValidators([Validators.maxLength(25)]);
            phone.setValidators(patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone));
            if (this.lastRoleSelected === RoleEnum.SHIPPER) {
              this.resetShipperInfo();
            }
          }

          email.updateValueAndValidity();
          firstName.updateValueAndValidity();
        });

    });


    this.roleSelectedSub = this.pickupRequestFormService.roleSelected$
      .pipe(
        tap(next => {
          if (this.lastRoleSelected === RoleEnum.SHIPPER && next && next[0] && RoleEnum[next[0]] !== RoleEnum.SHIPPER) {
            this.resetShipperInfo();
          }

          if (next && next[0]) {
            this.lastRoleSelected = RoleEnum[next[0]];
          } else {
            this.lastRoleSelected = null;
          }
        }),
        switchMap(next => {
          if (next && next[0] && RoleEnum[next[0]] === RoleEnum.SHIPPER && next[1]) {
            return this.utilService.getAccountInfo(next[1]);
          } else {
            return of(null);
          }
        }),
        map(next => {
          return next;
        })
      )
      .subscribe(next => {
        if (next) {
          this.setShipperInfoForRoleShipper(next);
        }
      });

      this.tryPopulateFromQueryParams(this.formGroup);

  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.draft && changes.draft.currentValue) {
      this.shipperConsigneeFormService.resetForm(
        (changes.draft.currentValue as BillOfLading).shipperInfo,
        (changes.draft.currentValue as BillOfLading).consigneeInfo
      );
    }
  }

  ngOnDestroy() {
    if (this.roleSelectedSub) {
      this.roleSelectedSub.unsubscribe();
    }
    if (this.queryParamSub) {
      this.queryParamSub.unsubscribe();
    }
    this.stop$.next(true);
    this.stop$.unsubscribe();
  }

  addAddress(fg: FormGroup) {
    this.openAddressDialog(fg);
  }

  get shipperInfo(): FormGroup {
    return this.formGroup.controls['shipperInfo'] as FormGroup;
  }
  get consigneeInfo(): FormGroup {
    return this.formGroup.controls['consigneeInfo'] as FormGroup;
  }

  private setShipperInfoForRoleShipper(accountInfo: AccountInfo) {
    const companyName = this.shipperInfo.controls['companyName'];
    const email = this.shipperInfo.controls['email'];
    const country = this.shipperInfo.controls['country'];
    const address1 = this.shipperInfo.controls['address1'];
    const address2 = this.shipperInfo.controls['address2'];
    const city = this.shipperInfo.controls['city'];
    const state = this.shipperInfo.controls['state'];
    const zip = this.shipperInfo.controls['zip'];
    const firstName = this.shipperInfo.controls['firstName'];
    const phone = this.shipperInfo.controls['phone'];
    if (accountInfo) {
      this.hideShipperAddressBook = true;
      this.shipperConsigneeFormService.onAccountInfoReceived(accountInfo);
      this.prepopulateShipperInfoEmail();

      companyName.disable({ emitEvent: false });
      country.disable({ emitEvent: false });
      address1.disable({ emitEvent: false });
      address2.disable({ emitEvent: false });
      city.disable({ emitEvent: false });
      state.disable({ emitEvent: false });
      zip.disable({ emitEvent: false });
    }
  }

  private resetShipperInfo() {
    const companyName = this.shipperInfo.controls['companyName'];
    const firstName = this.shipperInfo.controls['firstName'];
    const lastName = this.shipperInfo.controls['lastName'];
    const location = this.shipperInfo.controls['location'];
    const phone = this.shipperInfo.controls['phone'];
    const phoneExt = this.shipperInfo.controls['phoneExt'];
    const fax = this.shipperInfo.controls['fax'];
    const country = this.shipperInfo.controls['country'];
    const address1 = this.shipperInfo.controls['address1'];
    const address2 = this.shipperInfo.controls['address2'];
    const city = this.shipperInfo.controls['city'];
    const state = this.shipperInfo.controls['state'];
    const zip = this.shipperInfo.controls['zip'];
    const email = this.shipperInfo.controls['email'];

    this.hideShipperAddressBook = false;
    companyName.enable();
    country.enable();
    address1.enable();
    address2.enable();
    city.enable();
    state.enable();
    zip.enable();

    companyName.reset('');
    firstName.reset('');
    lastName.reset('');
    location.reset('');
    phone.reset('');
    phoneExt.reset('');
    fax.reset('');
    country.reset(Object.keys(CountryEnum).find(c => CountryEnum[c] === CountryEnum.US));
    address1.reset('');
    address2.reset('');
    city.reset('');
    state.reset('');
    zip.reset('');
    email.reset('');

    if (this.shipperConsigneeFormService.rateQuote) {
      country.setValue(this.shipperConsigneeFormService.rateQuote.origin.country);
      city.setValue(this.shipperConsigneeFormService.rateQuote.origin.city);
      state.setValue(this.shipperConsigneeFormService.rateQuote.origin.state);
      zip.setValue(this.shipperConsigneeFormService.rateQuote.origin.zip);
    }
  }

  private resetConsigneeInfo() {
    const companyName = this.consigneeInfo.controls['companyName'];
    const firstName = this.consigneeInfo.controls['firstName'];
    const lastName = this.consigneeInfo.controls['lastName'];
    const location = this.consigneeInfo.controls['location'];
    const phone = this.consigneeInfo.controls['phone'];
    const phoneExt = this.consigneeInfo.controls['phoneExt'];
    const fax = this.consigneeInfo.controls['fax'];
    const country = this.consigneeInfo.controls['country'];
    const address1 = this.consigneeInfo.controls['address1'];
    const address2 = this.consigneeInfo.controls['address2'];
    const city = this.consigneeInfo.controls['city'];
    const state = this.consigneeInfo.controls['state'];
    const zip = this.consigneeInfo.controls['zip'];
    const email = this.consigneeInfo.controls['email'];

    companyName.reset('');
    firstName.reset('');
    lastName.reset('');
    location.reset('');
    phone.reset('');
    phoneExt.reset('');
    fax.reset('');
    country.reset(Object.keys(CountryEnum).find(c => CountryEnum[c] === CountryEnum.US));
    address1.reset('');
    address2.reset('');
    city.reset('');
    state.reset('');
    zip.reset('');
    email.reset('');
  }

  private tryPopulateFromQueryParams(fg: FormGroup) {
    if (this.queryParamConfig) {
      const targetAddress: FormGroup =
        this.queryParamConfig.populate === 'consignee'
          ? (fg.controls['consigneeInfo'] as FormGroup)
          : (fg.controls['shipperInfo'] as FormGroup);

      targetAddress.controls['address1'].setValue(this.queryParamConfig.street1);
      targetAddress.controls['address2'].setValue(this.queryParamConfig.street2);
      targetAddress.controls['city'].setValue(this.queryParamConfig.city);
      targetAddress.controls['state'].setValue(this.queryParamConfig.state);
      targetAddress.controls['zip'].setValue(this.queryParamConfig.zip);
    }
  }

  private prepopulateShipperInfoEmail() {
    this.utilService.getProfileInfo().subscribe(next => {
      const email = this.shipperInfo.controls['email'];
      email.setValue(next.email);
    });
  }

  private openAddressDialog(fg: FormGroup) {
    if (fg.valid) {
      const address = AddressInformation.getAddressInformation(fg.value);
      const bookAddress = new BookAddress();

      bookAddress.company = address.companyName;
      bookAddress.firstName = address.firstName;
      bookAddress.lastName = address.lastName;
      bookAddress.locationNumber = address.location;
      bookAddress.email = address.email;
      bookAddress.phone = address.phone;
      bookAddress.phoneExt = address.phoneExt;
      bookAddress.fax = address.fax;
      bookAddress.address = {
        streetAddress: address.address1,
        streetAddress2: address.address2,
        city: address.city,
        state: address.state,
        zip: address.zip,
        country: address.country
      };

      this.dialogService.prompt(AddAddressModalComponent, bookAddress);
    } else {
      validateFormFields(fg);
    }
  }
}
