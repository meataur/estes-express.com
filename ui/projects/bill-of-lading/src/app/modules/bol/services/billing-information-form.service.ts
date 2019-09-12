import { Injectable, OnDestroy } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, Validators } from '@angular/forms';
import { BehaviorSubject, Observable, Subject, Subscription, merge } from 'rxjs';
import { tap, takeUntil, filter, take, startWith } from 'rxjs/operators';
import {
  BillingInformationForm,
  BillingInformation,
  AddressInformationForm,
  AddressInformation,
  BillToOptionValueEnum,
  FeeEnum,
  BolSectionService,
  BillOfLading
} from '../models';
import { BolService } from './bol.service';
import {
  patternValidator,
  RegEx,
  MessageConstants,
  DialogService,
  MyEstesValidators
} from 'common';

@Injectable({
  providedIn: 'root'
})
export class BillingInformationFormService extends BolSectionService implements OnDestroy {
  private stop$ = new Subject<boolean>();
  private stopBillToEmail$ = new Subject<boolean>();
  private billToEmailSource = new BehaviorSubject<string>(null);
  private billingInfoForm: BehaviorSubject<FormGroup | undefined> = new BehaviorSubject(
    this.fb.group(
      new BillingInformationForm(
        this.fb.group(new AddressInformationForm(new AddressInformation())),
        this.fb.group(new AddressInformationForm(new AddressInformation())),
        new BillingInformation(),
        this.fb
      )
    )
  );

  billingInfoForm$: Observable<FormGroup> = this.billingInfoForm.asObservable().pipe(
    tap(fg => {
      this.stop$.next(true);
      this.stopBillToEmail$.next(true);

      fg.controls['codRemitTo'].valueChanges.pipe(takeUntil(this.stop$)).subscribe(next => {
        const codRemitToFee = fg.controls['codRemitToFee'];
        const paymentType = fg.controls['paymentType'];
        const amount = fg.controls['amount'];
        const codRemitToInfo = fg.controls['codRemitToInfo'];
        if (!next) {
          this.dialog
            .confirm(
              `Are you sure?`,
              `If you proceed, all of your progress for this section will be cleared from this section.`
            )
            .subscribe(resp => {
              if (resp === true) {
                // this.generatePickupRequestSource.next(val);
                codRemitToInfo.disable();
                paymentType.clearValidators();
                amount.clearValidators();
                codRemitToFee.clearValidators();
                codRemitToFee.reset();
                paymentType.reset();
                amount.reset();
                codRemitToInfo.updateValueAndValidity();
                // this.resetForm();
              } else {
                fg.controls['codRemitTo'].setValue(false, { emitEvent: false });
                // pickupRequest.setValue(true, { emitEvent: false });
              }
            });
        } else {
          codRemitToInfo.enable();
          paymentType.setValidators([MyEstesValidators.required]);
          amount.setValidators([
            MyEstesValidators.required,
            patternValidator(
              RegEx.numberWithTwoDecimalPlaces,
              MessageConstants.invalidNumberWithTwoDecimals
            ),
            Validators.maxLength(9)
          ]);
          codRemitToFee.setValidators([MyEstesValidators.required]);
        }
        codRemitToInfo.updateValueAndValidity();
      });

      const billTo = fg.controls['billTo'];

      billTo.valueChanges
        .pipe(
          takeUntil(this.stop$)
        )
        .subscribe(next => {
          const fee = fg.controls['fee'];
          switch (BillToOptionValueEnum[next]) {
            case BillToOptionValueEnum.S:
              this.billToEmailSource.next('');
              this.stopBillToEmail$.next(true);
              fee.setValue(Object.keys(FeeEnum).find(e => FeeEnum[e] === FeeEnum.PPD));
              fee.disable();
              fg.controls['billingAddressInfo'] = this.fb.group(
                new AddressInformationForm(new AddressInformation())
              );
              fg.controls['billingAddressInfo'].disable();
              break;
            case BillToOptionValueEnum.C:
              this.billToEmailSource.next('');
              this.stopBillToEmail$.next(true);
              fee.setValue(Object.keys(FeeEnum).find(e => FeeEnum[e] === FeeEnum.COL));
              fee.disable();
              fg.controls['billingAddressInfo'] = this.fb.group(
                new AddressInformationForm(new AddressInformation())
              );
              fg.controls['billingAddressInfo'].disable();
              break;
            case BillToOptionValueEnum.T:
              fee.setValue('');
              fee.enable();
              fg.controls['billingAddressInfo'].enable();
              (fg.controls['billingAddressInfo'] as FormGroup).controls['email'].valueChanges
                .pipe(takeUntil(this.stopBillToEmail$))
                .subscribe(email => {
                  this.billToEmailSource.next(email);
                });
              break;
          }
          fg.controls['billingAddressInfo'].updateValueAndValidity({ emitEvent: false });
          fee.updateValueAndValidity();
        });

      if (this.bolService.modified !== true) {
        fg.valueChanges
          .pipe(
            filter(() => fg.dirty === true),
            take(1),
            takeUntil(merge(this.stop$, this.bolService.stopModified$))
          )
          .subscribe(() => {
            // console.log(`Form modified: Billing Information`);
            this.bolService.setModified();
          });
      }
    })
  );

  billToEmail$ = this.billToEmailSource.asObservable();

  constructor(
    private fb: FormBuilder,
    private bolService: BolService,
    private dialog: DialogService
  ) {
    super();
  }

  resetForm(b: BillingInformation = new BillingInformation()) {
    const newForm = this.fb.group(
      new BillingInformationForm(
        this.fb.group(new AddressInformationForm(b.billTo.billingAddressInfo)),
        this.fb.group(new AddressInformationForm(b.codRemitToInfo.codAddressInfo)),
        b,
        this.fb
      )
    );

    this.initResetFormState(newForm);

    this.billingInfoForm.next(newForm);
  }

  ngOnDestroy() {
    this.billingInfoForm.unsubscribe();
  }

  valid(): boolean {
    return this.isValid(this.billingInfoForm.getValue());
  }

  populateModel(bol: BillOfLading) {
    const form = this.billingInfoForm.getValue();
    const billToAddress = form.controls['billingAddressInfo'] as FormGroup;
    const remitToAddress = form.controls['codRemitToInfo'] as FormGroup;

    const hasBillToAddress =
      BillToOptionValueEnum[form.controls['billTo'].value] === BillToOptionValueEnum.T;
    const hasCodRemitTo = form.controls['codRemitTo'].value === true;

    bol.billingInfo.billTo.fee = form.controls['fee'].value || null;
    bol.billingInfo.billTo.payor = form.controls['billTo'].value || null;

    if (hasBillToAddress === true) {
      bol.billingInfo.billTo.billingAddressInfo.companyName =
        billToAddress.controls['companyName'].value;
      bol.billingInfo.billTo.billingAddressInfo.firstName =
        billToAddress.controls['firstName'].value;
      bol.billingInfo.billTo.billingAddressInfo.lastName = billToAddress.controls['lastName'].value;
      bol.billingInfo.billTo.billingAddressInfo.location = billToAddress.controls['location'].value;
      bol.billingInfo.billTo.billingAddressInfo.phone = billToAddress.controls['phone'].value;
      bol.billingInfo.billTo.billingAddressInfo.phoneExt = billToAddress.controls['phoneExt'].value;
      bol.billingInfo.billTo.billingAddressInfo.fax = billToAddress.controls['fax'].value;
      bol.billingInfo.billTo.billingAddressInfo.country = billToAddress.controls['country'].value;
      bol.billingInfo.billTo.billingAddressInfo.address1 = billToAddress.controls['address1'].value;
      bol.billingInfo.billTo.billingAddressInfo.address2 = billToAddress.controls['address2'].value;
      bol.billingInfo.billTo.billingAddressInfo.city = billToAddress.controls['city'].value;
      bol.billingInfo.billTo.billingAddressInfo.state = billToAddress.controls['state'].value;
      bol.billingInfo.billTo.billingAddressInfo.zip = billToAddress.controls['zip'].value;
      bol.billingInfo.billTo.billingAddressInfo.email = billToAddress.controls['email'].value;
    } else {
      bol.billingInfo.billTo.billingAddressInfo = null;
    }

    bol.billingInfo.codRemitTo = form.controls['codRemitTo'].value;
    if (hasCodRemitTo) {
      bol.billingInfo.codRemitToInfo.fee = form.controls['codRemitToFee'].value || null;
      bol.billingInfo.codRemitToInfo.amount = form.controls['amount'].value;
      bol.billingInfo.codRemitToInfo.paymentType = form.controls['paymentType'].value || null;
      bol.billingInfo.codRemitToInfo.codAddressInfo.companyName =
        remitToAddress.controls['companyName'].value;
      bol.billingInfo.codRemitToInfo.codAddressInfo.firstName =
        remitToAddress.controls['firstName'].value;
      bol.billingInfo.codRemitToInfo.codAddressInfo.lastName =
        remitToAddress.controls['lastName'].value;
      bol.billingInfo.codRemitToInfo.codAddressInfo.location =
        remitToAddress.controls['location'].value;
      bol.billingInfo.codRemitToInfo.codAddressInfo.phone = remitToAddress.controls['phone'].value;
      bol.billingInfo.codRemitToInfo.codAddressInfo.phoneExt =
        remitToAddress.controls['phoneExt'].value;
      bol.billingInfo.codRemitToInfo.codAddressInfo.fax = remitToAddress.controls['fax'].value;
      bol.billingInfo.codRemitToInfo.codAddressInfo.country =
        remitToAddress.controls['country'].value;
      bol.billingInfo.codRemitToInfo.codAddressInfo.address1 =
        remitToAddress.controls['address1'].value;
      bol.billingInfo.codRemitToInfo.codAddressInfo.address2 =
        remitToAddress.controls['address2'].value;
      bol.billingInfo.codRemitToInfo.codAddressInfo.city = remitToAddress.controls['city'].value;
      bol.billingInfo.codRemitToInfo.codAddressInfo.state = remitToAddress.controls['state'].value;
      bol.billingInfo.codRemitToInfo.codAddressInfo.zip = remitToAddress.controls['zip'].value;
      bol.billingInfo.codRemitToInfo.codAddressInfo.email = remitToAddress.controls['email'].value;
    } else {
      bol.billingInfo.codRemitToInfo = null;
    }
  }

  private initResetFormState(fg: FormGroup) {
    const billTo = fg.controls['billTo'];
    const billingAddressInfo = fg.controls['billingAddressInfo'] as FormGroup;

    switch (BillToOptionValueEnum[billTo.value]) {
      case BillToOptionValueEnum.S:
        this.billToEmailSource.next('');
        this.stopBillToEmail$.next(true);
        break;
      case BillToOptionValueEnum.C:
        this.billToEmailSource.next('');
        this.stopBillToEmail$.next(true);
        break;
      case BillToOptionValueEnum.T:
        billingAddressInfo.controls['email'].valueChanges
          .pipe(takeUntil(this.stopBillToEmail$))
          .subscribe(email => {
            this.billToEmailSource.next(email);
          });
        break;
    }
  }
}
