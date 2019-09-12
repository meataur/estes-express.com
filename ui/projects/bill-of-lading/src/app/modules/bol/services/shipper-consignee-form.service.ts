import { Injectable } from '@angular/core';
import { FormGroup, FormBuilder, FormArray } from '@angular/forms';
import { BehaviorSubject, Observable, Subject, merge } from 'rxjs';
import {
  ShipperConsigneeForm,
  AddressInformationForm,
  AddressInformation,
  BolSection,
  BolSectionService,
  BillOfLading,
  CountryEnum,
  RateQuote
} from '../models';
import { tap, takeUntil, take, filter } from 'rxjs/operators';
import { BolService } from './bol.service';
import { AccountInfo, MyEstesFormatters } from 'common';

@Injectable({
  providedIn: 'root'
})
export class ShipperConsigneeFormService extends BolSectionService {
  private stop$ = new Subject<boolean>();
  private shipperEmailSource = new BehaviorSubject<string>(null);
  private consigneeEmailSource = new BehaviorSubject<string>(null);
  private shipperInfoSource = new BehaviorSubject<AddressInformation>(new AddressInformation());
  private consigneeInfoSource = new BehaviorSubject<AddressInformation>(new AddressInformation());
  private shipperConsigneeForm: BehaviorSubject<FormGroup | undefined> = new BehaviorSubject(
    this.fb.group(
      new ShipperConsigneeForm(
        this.fb.group(new AddressInformationForm(new AddressInformation())),
        this.fb.group(new AddressInformationForm(new AddressInformation()))
      )
    )
  );
  rateQuote: RateQuote;

  shipperConsigneeForm$: Observable<FormGroup> = this.shipperConsigneeForm.asObservable().pipe(
    tap(fg => {
      this.stop$.next(true);

      const shipperInfo = fg.controls['shipperInfo'] as FormGroup;
      const consigneeInfo = fg.controls['consigneeInfo'] as FormGroup;
      const shipperEmail = shipperInfo.controls['email'];
      const consigneeEmail = consigneeInfo.controls['email'];

      shipperEmail.valueChanges.pipe(takeUntil(this.stop$)).subscribe(next => {
        const value = (fg.controls['shipperInfo'] as FormGroup).controls['email'].value;
        this.shipperEmailSource.next(value);
      });

      consigneeEmail.valueChanges.pipe(takeUntil(this.stop$)).subscribe(next => {
        if (next) {
          const value = (fg.controls['consigneeInfo'] as FormGroup).controls['email'].value;
          this.consigneeEmailSource.next(value);
        }
      });

      shipperInfo.valueChanges.pipe(takeUntil(this.stop$)).subscribe(next => {
        const addressInformation = AddressInformation.getAddressInformation(shipperInfo.getRawValue());
        this.shipperInfoSource.next(addressInformation);
      });

      consigneeInfo.valueChanges.pipe(takeUntil(this.stop$)).subscribe(next => {
        const addressInformation = AddressInformation.getAddressInformation(next);
        this.consigneeInfoSource.next(addressInformation);
      });

      shipperInfo.updateValueAndValidity();
      consigneeInfo.updateValueAndValidity();
      shipperEmail.updateValueAndValidity();
      consigneeEmail.updateValueAndValidity();

      if (this.bolService.modified !== true) {
        fg.valueChanges
          .pipe(
            filter(() => fg.dirty === true),
            take(1),
            takeUntil(merge(this.stop$, this.bolService.stopModified$))
          )
          .subscribe(() => {
            console.log(`Form modified: Shipper Consignee Info`);
            this.bolService.setModified();
          });
      }
    })
  );

  shipperEmail$ = this.shipperEmailSource.asObservable();
  consigneeEmail$ = this.consigneeEmailSource.asObservable();
  shipperInfo$ = this.shipperInfoSource.asObservable();
  consigneeInfo$ = this.consigneeInfoSource.asObservable();

  constructor(private fb: FormBuilder, private bolService: BolService) {
    super();
  }

  resetForm(
    shipperInfo: AddressInformation = new AddressInformation(),
    consigneeInfo: AddressInformation = new AddressInformation()
  ) {
    const newForm = this.fb.group(
      new ShipperConsigneeForm(
        this.fb.group(new AddressInformationForm(shipperInfo)),
        this.fb.group(new AddressInformationForm(consigneeInfo))
      )
    );

    // console.log(`new form valid: ${newForm.valid}`);
    // console.log(`shipperInfo valid: ${newForm.controls['shipperInfo'].valid}`);
    // console.log(`consigneeInfo valid: ${newForm.controls['consigneeInfo'].valid}`);


    this.shipperConsigneeForm.next(newForm);
  }

  valid(): boolean {
    // console.log(this.shipperConsigneeForm.getValue());
    return this.isValid(this.shipperConsigneeForm.getValue());
  }

  onAccountInfoReceived(a: AccountInfo) {
    if (a) {
      const form = this.shipperConsigneeForm.getValue();
      const shipperInfoForm = form.controls['shipperInfo'] as FormGroup;

      shipperInfoForm.controls['companyName'].setValue(a.name, { emitEvent: false });
      shipperInfoForm.controls['country'].setValue(a.address.country, { emitEvent: false });
      shipperInfoForm.controls['city'].setValue(a.address.city, { emitEvent: false });
      shipperInfoForm.controls['state'].setValue(a.address.state, { emitEvent: false });
      shipperInfoForm.controls['zip'].setValue(a.address.zip, { emitEvent: false });
      shipperInfoForm.controls['address1'].setValue(a.address.streetAddress, { emitEvent: false });
      shipperInfoForm.controls['address2'].setValue(a.address.streetAddress2, { emitEvent: false });
      shipperInfoForm.controls['phone'].setValue(MyEstesFormatters.formatPhone(a.phone), { emitEvent: false });
    }
  }

  populateModel(bol: BillOfLading) {
    const form = this.shipperConsigneeForm.getValue();

    // companyName = new FormControl('');
    // firstName = new FormControl('');
    // lastName = new FormControl('');
    // location = new FormControl('');
    // phone = new FormControl('');
    // phoneExt = new FormControl('');
    // fax = new FormControl('');
    // country = new FormControl(Object.keys(CountryEnum).find(c => CountryEnum[c] === CountryEnum.US));
    // address1 = new FormControl('');
    // address2 = new FormControl('');
    // city = new FormControl('');
    // state = new FormControl('');
    // zip = new FormControl('');
    // email = new FormControl('');

    const shipperInfo = form.controls['shipperInfo'] as FormGroup;
    const consigneeInfo = form.controls['consigneeInfo'] as FormGroup;

    bol.shipperInfo.companyName = shipperInfo.controls['companyName'].value;
    bol.shipperInfo.firstName = shipperInfo.controls['firstName'].value;
    bol.shipperInfo.lastName = shipperInfo.controls['lastName'].value;
    bol.shipperInfo.location = shipperInfo.controls['location'].value;
    bol.shipperInfo.phone = shipperInfo.controls['phone'].value;
    bol.shipperInfo.phoneExt = shipperInfo.controls['phoneExt'].value;
    bol.shipperInfo.fax = shipperInfo.controls['fax'].value;
    bol.shipperInfo.country = shipperInfo.controls['country'].value;
    bol.shipperInfo.address1 = shipperInfo.controls['address1'].value;
    bol.shipperInfo.address2 = shipperInfo.controls['address2'].value;
    bol.shipperInfo.city = shipperInfo.controls['city'].value;
    bol.shipperInfo.state = shipperInfo.controls['state'].value;
    bol.shipperInfo.zip = shipperInfo.controls['zip'].value;
    bol.shipperInfo.email = shipperInfo.controls['email'].value;

    bol.consigneeInfo.companyName = consigneeInfo.controls['companyName'].value;
    bol.consigneeInfo.firstName = consigneeInfo.controls['firstName'].value;
    bol.consigneeInfo.lastName = consigneeInfo.controls['lastName'].value;
    bol.consigneeInfo.location = consigneeInfo.controls['location'].value;
    bol.consigneeInfo.phone = consigneeInfo.controls['phone'].value;
    bol.consigneeInfo.phoneExt = consigneeInfo.controls['phoneExt'].value;
    bol.consigneeInfo.fax = consigneeInfo.controls['fax'].value;
    bol.consigneeInfo.country = consigneeInfo.controls['country'].value;
    bol.consigneeInfo.address1 = consigneeInfo.controls['address1'].value;
    bol.consigneeInfo.address2 = consigneeInfo.controls['address2'].value;
    bol.consigneeInfo.city = consigneeInfo.controls['city'].value;
    bol.consigneeInfo.state = consigneeInfo.controls['state'].value;
    bol.consigneeInfo.zip = consigneeInfo.controls['zip'].value;
    bol.consigneeInfo.email = consigneeInfo.controls['email'].value;
  }
}
