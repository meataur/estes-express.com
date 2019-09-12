import { FormGroup, FormControl, FormArray } from '@angular/forms';

export function validateFormFields(fg: FormGroup | FormArray) {
  Object.keys(fg.controls).forEach(field => {
    const control = fg.get(field);
    if (control instanceof FormControl) {
      control.markAsTouched({ onlySelf: false });
    } else if (control instanceof FormGroup || control instanceof FormArray) {
      validateFormFields(control);
    }
  });
}
