import { Injectable } from '@angular/core';
import {
  RateQuoteFormSectionService,
  RatingRequest,
  RateQuoteType,
  RateQuote,
  TermsEnum,
  RoleEnum
} from '../models';
import { FormBuilder, FormGroup } from '@angular/forms';
import { RequestorForm } from '../models/requestor-form.model';
import { takeUntil } from 'rxjs/operators';
import { RateQuoteTypeService } from './rate-quote-type.service';
import { AuthService } from 'common';

@Injectable({
  providedIn: 'root'
})
export class RequestorService extends RateQuoteFormSectionService {
  initValueChangeWatchers(fg: FormGroup) {
    const role = fg.controls['role'];

    role.valueChanges.pipe(takeUntil(this.stop$)).subscribe(next => {
      this.roleChanged(next);
    });
  }
  initFormState(fg: FormGroup) {
    const rqt = this.rateQuoteTypeService.getRateQuoteType();
    RequestorForm.initValidators(fg, rqt, !this.auth.isLoggedIn);
  }

  valid(): boolean {
    return this.isValid(this.formModelSource.getValue());
  }

  constructor(
    fb: FormBuilder,
    private rateQuoteTypeService: RateQuoteTypeService,
    private auth: AuthService
  ) {
    super(fb);
    super.initFormSource(RequestorForm);
  }

  roleChanged(val: any) {
    const fg = this.formModelSource.getValue();
    const terms = fg.controls['terms'];

    if (val === Object.keys(RoleEnum).find(e => RoleEnum[e] === RoleEnum.C)) {
      terms.setValue(Object.keys(TermsEnum).find(e => TermsEnum[e] === TermsEnum.COL));
      terms.disable();
    } else {
      terms.reset('');
      terms.enable();
    }

    terms.updateValueAndValidity();
  }

  resetForm(quote?: RateQuote) {
    const newForm = this.fb.group(new RequestorForm(quote));

    this.formModelSource.next(newForm);
  }

  populateModel(
    ratingRequest: RatingRequest,
    rateQuoteType: RateQuoteType,
    isRateEstimate: boolean = false
  ) {
    const form = this.formModelSource.getValue().getRawValue();

    if (isRateEstimate) {
      ratingRequest.contactName = form.contactName;
      ratingRequest.discount = form.discount;
    } else {
      ratingRequest.accountCode = form.accountCode;
      ratingRequest.role = form.role;
      ratingRequest.terms = form.terms;

      if (!rateQuoteType.isLTLOnly) {
        ratingRequest.contactName = form.contactName;
        ratingRequest.contactEmail = form.contactEmail;
        ratingRequest.contactPhone = form.contactPhone;
        ratingRequest.contactPhoneExt = form.contactExtension;
      }
    }
  }
}
