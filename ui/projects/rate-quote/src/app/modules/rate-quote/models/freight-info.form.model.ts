import { FormControl, FormGroup, Validators } from '@angular/forms';
import { RateQuote } from './rate-quote.model';
import { patternValidator, RegEx, MessageConstants } from 'common';

export class FreightInfoForm {
  declaredValue = new FormControl('');
  declaredValueWaived = new FormControl(false);
  /**
   * @description This field is for the UI only and does not map to a rate request object.
   * Its purpose is to conditionally show and hide the foodWarehouse form field.
   */
  deliversAtFoodWarehouse = new FormControl(false);
  foodWarehouse = new FormControl('');
  stackable = new FormControl(false);
  linearFeet = new FormControl('');
  comments = new FormControl('');

  constructor(q?: RateQuote) {
    if (q) {
      this.declaredValue.setValue(q.declaredValue);
      if ((q.declaredValue as any) === '0' || q.declaredValue === 0) {
        this.declaredValueWaived.setValue(false);
        this.declaredValueWaived.disable();
      }
      this.declaredValueWaived.setValue(!q.declaredValueWaived);
      this.stackable.setValue(q.stackable);
      this.linearFeet.setValue(q.linearFeet);
      this.comments.setValue(q.comments);
      if (q.foodWarehouseId) {
        this.foodWarehouse.setValue(q.foodWarehouseId);
        this.deliversAtFoodWarehouse.setValue(true);
      }
    }
  }

  static initValidators(
    fg: FormGroup,
    isLTLOnly: boolean,
    isLTLAndTC: boolean,
    isTCOnly: boolean,
    isRateEstimate: boolean
  ) {
    const declaredValue = fg.controls['declaredValue'];
    const declaredValueWaived = fg.controls['declaredValueWaived'];
    const deliversAtFoodWarehouse = fg.controls['deliversAtFoodWarehouse'];
    const foodWarehouse = fg.controls['foodWarehouse'];
    const stackable = fg.controls['stackable'];
    const linearFeet = fg.controls['linearFeet'];
    const comments = fg.controls['comments'];

    declaredValue.setValidators([
      patternValidator(RegEx.numbers, MessageConstants.invalidNumbers),
      Validators.maxLength(7)
    ]);
    comments.setValidators([Validators.maxLength(100)]);

    if (isLTLOnly || isRateEstimate) {
      declaredValueWaived.reset(false);
      deliversAtFoodWarehouse.reset(false);
      foodWarehouse.reset('');
      stackable.reset(false);
      linearFeet.reset('');
      comments.reset('');
    }

    if (isLTLAndTC || isTCOnly) {
      linearFeet.reset('');
    }

    if (this.linearFeetOnUI(isLTLOnly, isLTLAndTC, isTCOnly, isRateEstimate)) {
      linearFeet.setValidators([
        patternValidator(RegEx.numbers, MessageConstants.invalidNumbers),
        Validators.maxLength(2)
      ]);
    } else {
      linearFeet.clearValidators();
    }
  }

  private static linearFeetOnUI(
    isLTLOnly: boolean,
    isLTLAndTC: boolean,
    isTCOnly: boolean,
    isRateEstimate: boolean
  ): boolean {
    if (!isLTLOnly && !isLTLAndTC && !isTCOnly && !isRateEstimate) {
      return true;
    }

    return false;
  }
}
