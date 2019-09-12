import { ValidatorFn, AbstractControl } from '@angular/forms';

/**
 * @description Validates a form control to be true.
 */
export function mustBeTrueValidator(message: string): ValidatorFn {
  return (control: AbstractControl): { [key: string]: any } | null => {
    return control.value !== true ? { mustBeTrue: { value: message } } : null;
  };
}
