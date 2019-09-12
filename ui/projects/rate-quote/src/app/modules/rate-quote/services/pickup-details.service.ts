import { Injectable } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import {
  RateQuoteFormSectionService,
  PickupDetailsForm,
  RatingRequest,
  RateQuoteType,
  RateQuote
} from '../models';
import { formatDate } from '@angular/common';
import { timeRangeValidator } from 'common';

@Injectable({
  providedIn: 'root'
})
export class PickupDetailsService extends RateQuoteFormSectionService {
  initValueChangeWatchers(fg: FormGroup) {
    fg.setValidators(timeRangeValidator);
  }
  initFormState() {}
  valid(): boolean {
    return this.isValid(this.formModelSource.getValue());
  }

  constructor(fb: FormBuilder) {
    super(fb);
    super.initFormSource(PickupDetailsForm);
  }

  resetForm(quote?: RateQuote) {
    const newForm = this.fb.group(new PickupDetailsForm(quote));

    this.formModelSource.next(newForm);
  }

  populateModel(ratingRequest: RatingRequest, rateQuoteType: RateQuoteType) {
    if (rateQuoteType.isLTLOnly) {
      return;
    }

    const form = this.formModelSource.getValue().getRawValue();

    try {
      ratingRequest.pickupDate = formatDate(form.pickupDate, 'MM/dd/yyyy', 'en-US');
    } catch (err) {
      console.warn(`Pickup date formatting failed.`);
    }
    const availableByHour = form.availableByHour;
    const availableByMinutes = form.availableByMinutes;
    const availableByAmPm = form.availableByAmPm;
    const closesByHour = form.closesByHour;
    const closesByMinutes = form.closesByMinutes;
    const closesByAmPm = form.closesByAmPm;

    ratingRequest.pickupAvail = `${availableByHour}:${availableByMinutes} ${availableByAmPm}`;
    ratingRequest.pickupClose = `${closesByHour}:${closesByMinutes} ${closesByAmPm}`;
  }
}
