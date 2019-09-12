import { BillingInformation } from './billing-information.model';
import { FormControl, FormGroup, Validators, FormBuilder } from '@angular/forms';
import { BillToOptionValueEnum } from './bill-to-option-value.enum';
import { patternValidator, RegEx, MessageConstants, MyEstesValidators } from 'common';
import { FeeEnum } from './fee.enum';
import { AddressInformationForm } from './address-information-form.model';
import { AddressInformation } from './address-information.model';

export class BillingInformationForm {
  billTo = new FormControl(
    null
    // Object.keys(BillToOptionValueEnum).find(
    //   e => BillToOptionValueEnum[e] === BillToOptionValueEnum.S
    // )
  );
  fee = new FormControl(null); // ['', MyEstesValidators.required],
  billingAddressInfo: FormGroup;
  codRemitTo = new FormControl(false);
  codRemitToInfo: FormGroup;
  codRemitToFee = new FormControl(null);
  paymentType = new FormControl(null);
  amount = new FormControl('');

  constructor(
    billingAddressInfo: FormGroup,
    codRemitToInfo: FormGroup,
    b: BillingInformation,
    fb: FormBuilder
  ) {
    this.billingAddressInfo = billingAddressInfo;
    this.codRemitToInfo = codRemitToInfo;

    this.billTo.setValidators([MyEstesValidators.required]);
    this.fee.setValidators([MyEstesValidators.required]);
    this.codRemitTo.setValidators([MyEstesValidators.required]);

    if (b.billTo.payor) {
      this.billTo.setValue(b.billTo.payor);
    }
    if (b.billTo.fee) {
      this.fee.setValue(b.billTo.fee);
    }
    if (b.codRemitTo) {
      this.codRemitTo.setValue(b.codRemitTo);
      if (b.codRemitToInfo.fee) {
        this.codRemitToFee.setValue(b.codRemitToInfo.fee);
      }
      if (b.codRemitToInfo.paymentType) {
        this.paymentType.setValue(b.codRemitToInfo.paymentType);
      }
      if (b.codRemitToInfo.amount) {
        this.amount.setValue(b.codRemitToInfo.amount);
      }
    } else {
      this.disableCodRemitToSection();
    }

    this.billingAddressInfo.disable();
    switch (BillToOptionValueEnum[this.billTo.value]) {
      case BillToOptionValueEnum.S:
        this.fee.setValue(Object.keys(FeeEnum).find(e => FeeEnum[e] === FeeEnum.PPD));
        this.fee.disable();
        this.billingAddressInfo = fb.group(new AddressInformationForm(new AddressInformation()));
        this.billingAddressInfo.disable();
        break;
      case BillToOptionValueEnum.C:
        this.fee.setValue(Object.keys(FeeEnum).find(e => FeeEnum[e] === FeeEnum.COL));
        this.fee.disable();
        this.billingAddressInfo = fb.group(new AddressInformationForm(new AddressInformation()));
        this.billingAddressInfo.disable();
        break;
      case BillToOptionValueEnum.T:
        this.billingAddressInfo.enable();
        break;
    }
  }

  private disableCodRemitToSection() {
    this.codRemitToInfo.disable();
    this.codRemitToFee.clearValidators();
    this.paymentType.clearValidators();
    this.amount.clearValidators();

    // this.codRemitToInfo.reset();
    this.codRemitToFee.reset();
    this.paymentType.reset();
    this.amount.reset();
  }

  // private initFormState() {
  //   switch (this.billTo.value) {
  //     case BillToOptionValueEnum.S:
  //       // this.billToEmailSource.next('');
  //       // this.stopBillToEmail$.next(true);
  //       this.fee.setValue(Object.keys(FeeEnum).find(e => FeeEnum[e] === FeeEnum.PPD));
  //       this.fee.disable();
  //       this.billingAddressInfo = this.fb.group(
  //         new AddressInformationForm(new AddressInformation())
  //       );
  //       this.billingAddressInfo.disable();
  //       break;
  //     case BillToOptionValueEnum.C:
  //       this.billToEmailSource.next('');
  //       this.stopBillToEmail$.next(true);
  //       this.fee.setValue(Object.keys(FeeEnum).find(e => FeeEnum[e] === FeeEnum.COL));
  //       this.fee.disable();
  //       this.billingAddressInfo = this.fb.group(
  //         new AddressInformationForm(new AddressInformation())
  //       );
  //       fg.controls['billingAddressInfo'].disable();
  //       break;
  //     case BillToOptionValueEnum.T:
  //       this.fee.setValue('');
  //       this.fee.enable();
  //       fg.controls['billingAddressInfo'].enable();
  //       (fg.controls['billingAddressInfo'] as FormGroup).controls['email'].valueChanges
  //         .pipe(takeUntil(this.stopBillToEmail$))
  //         .subscribe(email => {
  //           this.billToEmailSource.next(email);
  //         });
  //       break;
  //     }
  // }
}
