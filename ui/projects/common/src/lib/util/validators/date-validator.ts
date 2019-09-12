import { ValidatorFn, AbstractControl } from '@angular/forms';

/**
 * @description This validator is applied to a material-date-picker input and it will produce a custom error.
 */
export function dateValidator(): ValidatorFn {
  return (control: AbstractControl): { [key: string]: any } | null => {
    if (control && control.value) {
      const date = new Date(control.value);
      if (date.getFullYear().toString().length > 4) {
        return { invalidDate: { value: `Invalid date.` } };
      }
    }
    return null;
  };
}
