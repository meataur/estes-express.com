import { FormControl, FormGroup, Validators } from '@angular/forms';
import {
  patternValidator,
  RegEx,
  MessageConstants,
  MyEstesValidators,
  MyEstesFormatters
} from 'common';
import { RateQuoteType } from './rate-quote-type-form.model';
import { RateQuote } from './rate-quote.model';

export class RequestorForm {
  accountCode = new FormControl('');
  contactName = new FormControl('');
  contactPhone = new FormControl('');
  contactEmail = new FormControl('');
  contactExtension = new FormControl('');
  role = new FormControl('');
  terms = new FormControl('');
  discount = new FormControl(40);

  constructor(quote?: RateQuote) {
    this.initValidators();
    this.prePopulate(quote);
  }

  static initValidators(fg: FormGroup, rateQuoteType: RateQuoteType, isEstimate: boolean) {
    if (isEstimate) {
      RequestorForm.initEstimateValidators(fg);
    } else {
      const contactName = fg.controls['contactName'];
      const contactPhone = fg.controls['contactPhone'];
      const contactEmail = fg.controls['contactEmail'];
      const contactExtension = fg.controls['contactExtension'];
      const discount = fg.controls['discount'];

      discount.clearValidators();
      discount.reset('');
      discount.disable();

      if (rateQuoteType.ltl && (!rateQuoteType.timeCritical && !rateQuoteType.volumeAndTruckload)) {
        contactName.reset('');
        contactEmail.reset('');
        contactPhone.reset('');
        contactExtension.reset('');
        contactName.clearValidators();
        contactEmail.clearValidators();
        contactPhone.clearValidators();
        contactExtension.clearValidators();
      } else {
        contactName.setValidators([MyEstesValidators.required, Validators.maxLength(100)]);
        contactPhone.setValidators([
          MyEstesValidators.required,
          patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone)
        ]);
        contactExtension.setValidators([
          patternValidator(RegEx.numbers, MessageConstants.invalidNumbers)
        ]);
        contactEmail.setValidators([
          MyEstesValidators.required,
          patternValidator(RegEx.email, MessageConstants.invalidEmail)
        ]);
      }

      contactName.updateValueAndValidity();
      contactPhone.updateValueAndValidity();
      contactEmail.updateValueAndValidity();
      contactExtension.updateValueAndValidity();
      discount.updateValueAndValidity();
    }
  }

  static initEstimateValidators(fg: FormGroup) {
    const accountCode = fg.controls['accountCode'];
    const contactName = fg.controls['contactName'];
    const contactPhone = fg.controls['contactPhone'];
    const contactEmail = fg.controls['contactEmail'];
    const contactExtension = fg.controls['contactExtension'];
    const role = fg.controls['role'];
    const terms = fg.controls['terms'];
    const discount = fg.controls['discount'];

    contactName.setValidators([MyEstesValidators.required, Validators.maxLength(100)]);
    discount.setValidators([
      patternValidator(RegEx.numbers, MessageConstants.invalidNumbers),
      Validators.maxLength(2)
    ]);

    accountCode.reset('');
    contactPhone.reset('');
    contactEmail.reset('');
    contactExtension.reset('');
    role.reset('');
    terms.reset('');

    accountCode.disable();
    contactPhone.disable();
    contactEmail.disable();
    contactExtension.disable();
    role.disable();
    terms.disable();
  }

  private prePopulate(quote?: RateQuote) {
    if (quote) {
      this.accountCode.setValue(quote.accountCode);
      this.contactName.setValue(quote.contactName);
      this.contactPhone.setValue(MyEstesFormatters.formatPhone(quote.contactPhone));
      this.contactEmail.setValue(quote.contactEmail);
      this.contactExtension.setValue(quote.contactPhoneExt);
      this.role.setValue(quote.role);
      this.terms.setValue(quote.terms);
      this.discount.setValue(quote.pricing.discount * 100);
    }
  }

  private initValidators() {
    this.contactName.setValidators([MyEstesValidators.required]);
    this.contactPhone.setValidators([
      MyEstesValidators.required,
      patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone)
    ]);
    this.contactEmail.setValidators([
      MyEstesValidators.required,
      patternValidator(RegEx.email, MessageConstants.invalidEmail)
    ]);
    this.accountCode.setValidators([MyEstesValidators.required]);
    this.role.setValidators([MyEstesValidators.required]);
    this.terms.setValidators([MyEstesValidators.required]);
  }
}
