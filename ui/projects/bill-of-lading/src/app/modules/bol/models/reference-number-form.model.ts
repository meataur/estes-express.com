import { FormControl } from '@angular/forms';
import { Reference } from './reference.model';

export class ReferenceNumberForm {
  referenceId = new FormControl('');
  referenceNumber = new FormControl('');
  referenceType = new FormControl(null);
  cartoon = new FormControl('');
  weight = new FormControl('');

  constructor(r: Reference) {
    if (r.referenceId) {
      this.referenceId.setValue(r.referenceId);
    }
    if (r.referenceNumber) {
      this.referenceNumber.setValue(r.referenceNumber);
    }
    if (r.referenceType) {
      this.referenceType.setValue(r.referenceType);
    }
    if (r.cartoon) {
      this.cartoon.setValue(r.cartoon);
    }
    if (r.weight) {
      this.weight.setValue(r.weight);
    }
  }
}
