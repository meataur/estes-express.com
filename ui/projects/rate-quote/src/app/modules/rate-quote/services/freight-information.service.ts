import { Injectable } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import {
  FreightInfoForm,
  RateQuoteFormSectionService,
  RatingRequest,
  RateQuoteType,
  RateQuote
} from '../models';
import { takeUntil, startWith } from 'rxjs/operators';
import { MyEstesValidators } from 'common';

@Injectable({
  providedIn: 'root'
})
export class FreightInformationService extends RateQuoteFormSectionService {
  initValueChangeWatchers(fg: FormGroup) {
    const deliversAtFoodWarehouse = fg.controls['deliversAtFoodWarehouse'];
    const foodWarehouse = fg.controls['foodWarehouse'];
    const declaredValue = fg.controls['declaredValue'];

    declaredValue.valueChanges
      .pipe(
        takeUntil(this.stop$)
      )
      .subscribe(next => {
        this.declaredValueChanged(next, fg);
      });
    deliversAtFoodWarehouse.valueChanges
      .pipe(
        startWith(deliversAtFoodWarehouse.value),
        takeUntil(this.stop$)
      )
      .subscribe(next => {
        if (next) {
          foodWarehouse.setValidators([MyEstesValidators.required]);
        } else {
          foodWarehouse.clearValidators();
          foodWarehouse.reset('');
        }
        foodWarehouse.updateValueAndValidity();
      });
  }
  initFormState() {}

  valid(): boolean {
    return this.isValid(this.formModelSource.getValue());
  }

  constructor(fb: FormBuilder) {
    super(fb);
    super.initFormSource(FreightInfoForm);
  }

  declaredValueChanged(value: any, fg: FormGroup) {
    const declaredValueWaived = fg.controls['declaredValueWaived'];

    if (value === '0' || value === 0 || (typeof value === 'string' && value.trim() === '')) {
      declaredValueWaived.setValue(false);
      declaredValueWaived.disable();
    } else {
      declaredValueWaived.setValue(true);
      declaredValueWaived.enable();
    }
  }

  resetForm(q?: RateQuote) {
    const newForm = this.fb.group(new FreightInfoForm(q));

    this.formModelSource.next(newForm);
  }

  populateModel(ratingRequest: RatingRequest, rateQuoteType: RateQuoteType) {
    const form = this.formModelSource.getValue().getRawValue();

    if (rateQuoteType.isLTLOnly) {
      return;
    }

    ratingRequest.declaredValue = form.declaredValue;
    ratingRequest.declaredValueWaived = !form.declaredValueWaived;
    ratingRequest.foodWarehouseId = form.foodWarehouse;
    ratingRequest.stackable = form.stackable;
    ratingRequest.comments = form.comments;

    if (!rateQuoteType.isTCOnly && !rateQuoteType.isLTLandTCOnly) {
      ratingRequest.linearFeet = form.linearFeet;
    }
  }
}
