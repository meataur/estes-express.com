import { FormControl, FormGroup } from '@angular/forms';
import { RatingRequest } from './rating-request.model';
import { RateQuoteType } from './rate-quote-type-form.model';
import { MyEstesValidators, dateValidator } from 'common';
import { RateQuote } from './rate-quote.model';
import { formatDate } from '@angular/common';

export class PickupDetailsForm {
  availableByHour = new FormControl('08');
  availableByMinutes = new FormControl('00');
  availableByAmPm = new FormControl('AM');
  closesByHour = new FormControl('05');
  closesByMinutes = new FormControl('00');
  closesByAmPm = new FormControl('PM');
  pickupDate = new FormControl('', dateValidator());

  constructor(r?: RateQuote) {
    if (r) {
      try {
        if (r.pickupDate) {
          this.pickupDate.setValue(new Date(r.pickupDate));
        }
      } catch (e) {
        console.error(e);
      }
      if (r.pickupAvail) {
        try {
          const splitTime = r.pickupAvail.split(':');
          const hours = +splitTime[0];
          const minutes = +splitTime[1];
          const seconds = +splitTime[2];

          const d = new Date();

          d.setHours(hours, minutes, seconds);

          const formattedHours = formatDate(d, 'hh', 'en-US');
          const formattedMinutes = formatDate(d, 'mm', 'en-US');
          const formattedAmPm = formatDate(d, 'a', 'en-US');

          this.availableByHour.setValue(formattedHours);
          this.populateMinutes(this.availableByMinutes, formattedMinutes);
          this.availableByAmPm.setValue(formattedAmPm);
        } catch (err) {
          console.warn(`Failed to parse pickup available time for pickup details`);
        }
      }
      if (r.pickupClose) {
        try {
          const splitTime = r.pickupClose.split(':');
          const hours = +splitTime[0];
          const minutes = +splitTime[1];
          const seconds = +splitTime[2];

          const d = new Date();

          d.setHours(hours, minutes, seconds);

          const formattedHours = formatDate(d, 'hh', 'en-US');
          const formattedMinutes = formatDate(d, 'mm', 'en-US');
          const formattedAmPm = formatDate(d, 'a', 'en-US');

          this.closesByHour.setValue(formattedHours);
          this.populateMinutes(this.closesByMinutes, formattedMinutes);
          this.closesByAmPm.setValue(formattedAmPm);
        } catch (err) {
          console.warn(`Failed to parse pickup close time for pickup details`);
        }
      }
    }
  }

  static initValidators(fg: FormGroup, ltlOnly: boolean) {
    const availableByHour = fg.controls['availableByHour'];
    const availableByMinutes = fg.controls['availableByMinutes'];
    const availableByAmPm = fg.controls['availableByAmPm'];
    const closesByHour = fg.controls['closesByHour'];
    const closesByMinutes = fg.controls['closesByMinutes'];
    const closesByAmPm = fg.controls['closesByAmPm'];
    const pickupDate = fg.controls['pickupDate'];

    if (ltlOnly) {
      availableByHour.reset('');
      availableByMinutes.reset('');
      availableByAmPm.reset('');
      closesByHour.reset('');
      closesByMinutes.reset('');
      closesByAmPm.reset('');
      pickupDate.reset('');

      availableByHour.clearValidators();
      availableByMinutes.clearValidators();
      availableByAmPm.clearValidators();
      closesByHour.clearValidators();
      closesByMinutes.clearValidators();
      closesByAmPm.clearValidators();
      pickupDate.clearValidators();
    } else {
      availableByHour.setValue('08');
      availableByMinutes.setValue('00');
      availableByAmPm.setValue('AM');
      closesByHour.setValue('05');
      closesByMinutes.setValue('00');
      closesByAmPm.setValue('PM');

      availableByHour.setValidators([MyEstesValidators.required]);
      availableByMinutes.setValidators([MyEstesValidators.required]);
      availableByAmPm.setValidators([MyEstesValidators.required]);
      closesByHour.setValidators([MyEstesValidators.required]);
      closesByMinutes.setValidators([MyEstesValidators.required]);
      closesByAmPm.setValidators([MyEstesValidators.required]);
      pickupDate.setValidators([MyEstesValidators.required, dateValidator()]);
    }

    availableByHour.updateValueAndValidity();
    availableByMinutes.updateValueAndValidity();
    availableByAmPm.updateValueAndValidity();
    closesByHour.updateValueAndValidity();
    closesByMinutes.updateValueAndValidity();
    closesByAmPm.updateValueAndValidity();
    pickupDate.updateValueAndValidity();
  }

  private populateMinutes(fc: FormControl, val: string) {
    if (['00', '15', '30', '45'].includes(val)) {
      fc.setValue(val);
    } else {
      fc.setValue('00');
    }
  }
}
