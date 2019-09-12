import { ShippingLabel } from './shipping-label.model';
import { FormControl, Validators } from '@angular/forms';
import { MessageConstants, RegEx, patternValidator, MyEstesValidators } from 'common';

export class ShippingLabelForm {
  hasShippingLabel = new FormControl(false);
  labelType = new FormControl('');
  startLabel = new FormControl('');
  numberOfLabel = new FormControl('');

  constructor(s: ShippingLabel) {
    if (s.labelType) {
      this.setValidators();
      this.hasShippingLabel.setValue(true);
      this.labelType.setValue(s.labelType);
    }
    if (s.startLabel) {
      this.setValidators();
      this.hasShippingLabel.setValue(true);
      this.startLabel.setValue(s.startLabel);
    }
    if (s.numberOfLabel) {
      this.setValidators();
      this.hasShippingLabel.setValue(true);
      this.numberOfLabel.setValue(s.numberOfLabel);
    }
  }

  private setValidators() {
    this.labelType.setValidators([MyEstesValidators.required]);
    this.startLabel.setValidators([MyEstesValidators.required]);
    this.numberOfLabel.setValidators([
      Validators.maxLength(15),
      patternValidator(RegEx.numberGreaterThanZero, MessageConstants.invalidNumberGreaterThanZero)
    ]);
  }
}
