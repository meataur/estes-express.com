import { Injectable } from '@angular/core';
import { RateQuoteFormSectionService } from '../models/rate-quote-form-section-service.model';
import { BehaviorSubject } from 'rxjs';
import { RateQuoteTypeForm, RateQuoteType, RatingRequest, RateQuote } from '../models';
import { FormBuilder, FormGroup } from '@angular/forms';
import { takeUntil, tap } from 'rxjs/operators';
import { AuthService } from 'common';

@Injectable({
  providedIn: 'root'
})
export class RateQuoteTypeService extends RateQuoteFormSectionService {
  private typeSource = new BehaviorSubject<RateQuoteType>(new RateQuoteType());

  public rateQuoteType$ = this.typeSource.asObservable();

  constructor(fb: FormBuilder, private auth: AuthService) {
    super(fb);
    super.initFormSource(RateQuoteTypeForm);
  }

  getRateQuoteType() {
    const fg = this.formModelSource.getValue();
    const rqt = new RateQuoteType();
    const ltl = fg.controls['ltl'];
    const volumeAndTruckload = fg.controls['volumeAndTruckload'];
    const timeCritical = fg.controls['timeCritical'];

    rqt.ltl = ltl.value;
    rqt.timeCritical = timeCritical.value;
    rqt.volumeAndTruckload = volumeAndTruckload.value;

    return rqt;
  }

  get hasLimitedRateQuoteAccess() {
    return !!(!this.hasLTLAccess || !this.hasTCAccess || !this.hasVTLAccess);
  }

  get hasLTLAccess() {
    return this.auth.isInScope('LTLRATEQOT');
  }
  get hasTCAccess() {
    return this.auth.isInScope('TIMECRIT');
  }
  get hasVTLAccess() {
    return this.auth.isInScope('VTLQUOTE');
  }

  initFormState(fg: FormGroup) {
    const rqt = this.getRateQuoteType();
    const ltl = fg.controls['ltl'];
    const volumeAndTruckload = fg.controls['volumeAndTruckload'];
    const timeCritical = fg.controls['timeCritical'];

    if (rqt.isLTLOnly) {
      ltl.disable({ emitEvent: false });
    }
    if (rqt.isTCOnly) {
      timeCritical.disable({ emitEvent: false });
    }
    if (rqt.isVTLOnly) {
      volumeAndTruckload.disable({ emitEvent: false });
    }

    if (!this.hasLTLAccess) {
      rqt.ltl = false;
      ltl.setValue(false, { emitEvent: false });
      rqt.volumeAndTruckload = true;
      rqt.timeCritical = true;
      timeCritical.setValue(true, {emitEvent: false});
      volumeAndTruckload.setValue(true, {emitEvent: false});
      ltl.disable({ emitEvent: false });
    }
    if (!this.hasTCAccess) {
      rqt.timeCritical = false;
      timeCritical.setValue(false, { emitEvent: false });
      timeCritical.disable({ emitEvent: false });
    }
    if (!this.hasVTLAccess) {
      rqt.volumeAndTruckload = false;
      volumeAndTruckload.setValue(false, { emitEvent: false });
      volumeAndTruckload.disable({ emitEvent: false });
    }

    if (!ltl.value && !timeCritical.value && !volumeAndTruckload.value) {
      if (this.hasLTLAccess) {
        rqt.ltl = true;
        ltl.setValue(true, { emitEvent: false });
      } 
      if (this.hasVTLAccess) {
        rqt.volumeAndTruckload = true;
        volumeAndTruckload.setValue(true, {emitEvent: false});
      }
      if (this.hasTCAccess) {
        rqt.timeCritical = true;
        timeCritical.setValue(true, {emitEvent: false});
      }
    }

    ltl.updateValueAndValidity();
    timeCritical.updateValueAndValidity();
    volumeAndTruckload.updateValueAndValidity();

    this.typeSource.next(rqt);
  }

  valid(): boolean {
    /**
     * In the edge case that only only quote type is accessible to a user, ALL fields
     * are disabled, and the formGroup validity is false.  Hence, always return true.
     */
    return true;
  }

  resetForm(selectedApps?: Array<'LTL' | 'VTL' | 'TC'>) {
    const newForm = this.fb.group(new RateQuoteTypeForm(selectedApps));

    this.formModelSource.next(newForm);
  }

  populateModel(ratingRequest: RatingRequest) {
    const fg = this.formModelSource.getValue().getRawValue();

    if (fg.volumeAndTruckload === true) {
      ratingRequest.apps.push(`VTL`);
    }
    if (fg.timeCritical === true) {
      ratingRequest.apps.push(`TC`);
    }
    if (fg.ltl === true) {
      ratingRequest.apps.push(`LTL`);
    }
  }

  initValueChangeWatchers(fg: FormGroup) {
    /**
     * This particular value change watcher works under the assumption that RateQuoteType
     * and RateQuoteTypeForm have the same properties and names.
     */
    fg.valueChanges.pipe(takeUntil(this.stop$)).subscribe(next => {
      const rqt = new RateQuoteType();
      next = fg.getRawValue();
      rqt.ltl = next.ltl;
      rqt.volumeAndTruckload = next.volumeAndTruckload;
      rqt.timeCritical = next.timeCritical;
      this.quoteOptionChanged(rqt);
    });
  }

  private quoteOptionChanged(rqt: RateQuoteType) {
    const fg = this.formModelSource.getValue();
    const ltl = fg.controls['ltl'];
    const volumeAndTruckload = fg.controls['volumeAndTruckload'];
    const timeCritical = fg.controls['timeCritical'];

    if (
      [ltl.value, volumeAndTruckload.value, timeCritical.value].filter(o => o === true).length === 1
    ) {
      if (ltl.value === true) {
        ltl.disable({ emitEvent: false });
      }
      if (volumeAndTruckload.value === true) {
        volumeAndTruckload.disable({ emitEvent: false });
      }
      if (timeCritical.value === true) {
        timeCritical.disable({ emitEvent: false });
      }
    } else {
      if (this.hasLTLAccess) {
        ltl.enable({ emitEvent: false });
      }
      if (this.hasVTLAccess) {
        volumeAndTruckload.enable({ emitEvent: false });
      }
      if (this.hasTCAccess) {
        timeCritical.enable({ emitEvent: false });
      }
    }

    this.typeSource.next(rqt);
  }
}
