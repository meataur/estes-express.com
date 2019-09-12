import { FormArray, FormControl } from '@angular/forms';

export class ReferenceNumbersInfoForm {
  hasReferenceNumbers = new FormControl(false);
  referenceNumbers = new FormArray([]);

  constructor() {

  }
}
