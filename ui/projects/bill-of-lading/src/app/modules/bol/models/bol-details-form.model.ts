import { FormControl, Validators, FormGroup } from '@angular/forms';
import { GeneralInformation } from './general-information.model';
import {
  MyEstesValidators,
  patternValidator,
  dateValidator,
  RegEx,
  MessageConstants,
  MyEstesFormatters
} from 'common';

export class BolDetailsForm {
  bolReferenceNumber = new FormControl('');
  bolDate = new FormControl(new Date());
  autoAssignPro = new FormControl(false);
  reservedPro = new FormControl('');
  masterBol = new FormControl(false);
  masterBolNumber = new FormControl('');

  constructor(g: GeneralInformation) {
    this.bolReferenceNumber.setValidators([MyEstesValidators.required, Validators.maxLength(25)]);
    this.bolDate.setValidators([MyEstesValidators.required, dateValidator()]);
    this.autoAssignPro.setValidators([MyEstesValidators.required]);
    this.masterBolNumber.setValidators([Validators.maxLength(25)]);
    this.reservedPro.setValidators([
      // MyEstesValidators.required,
      Validators.maxLength(12),
      patternValidator(RegEx.proNumber, MessageConstants.invalidProNumber)
    ]);

    if (g.bolNumber) {
      this.bolReferenceNumber.setValue(g.bolNumber);
    }
    if (g.bolDate) {
      const d = new Date(g.bolDate);
      this.bolDate.setValue(d);
    }
    /**
     * According to requirements, a reset form state from any action (create from bol,
     * continue draft) does not affect these fields.
     */
    // if (g.assignProNumber) {
    //   this.autoAssignPro.setValue(g.assignProNumber);
    // }
    // if (g.proNumber) {
    //   this.reservedPro.setValue(g.proNumber);

    //   this.autoAssignPro.setValue(false);
    // }
    if (g.masterBol) {
      this.masterBol.setValue(g.masterBol);

      if (g.masterBol === true) {
        this.masterBolNumber.setValidators([MyEstesValidators.required, Validators.maxLength(25)]);
      }
    }
    if (g.masterBolNumber) {
      this.masterBolNumber.setValue(g.masterBolNumber);
    }

    this.bolReferenceNumber.updateValueAndValidity();
    this.bolDate.updateValueAndValidity();
    this.autoAssignPro.updateValueAndValidity();
    this.reservedPro.updateValueAndValidity();
    this.masterBol.updateValueAndValidity();
    this.masterBolNumber.updateValueAndValidity();
  }
}
