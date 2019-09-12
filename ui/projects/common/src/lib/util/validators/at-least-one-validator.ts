import { ValidatorFn, FormArray } from '@angular/forms';

/**
 * @description This validator is applied to a form array and iterates through each of its form controls.
 * If at least one form control is not set to "true", it will produce a custom error.
 */
export function atLeastOneValidator(): ValidatorFn {
  return (control: FormArray): { [key: string]: any } | null => {
    for (const ctrl of control.controls) {
      if (ctrl.value === true) {
        return null;
      }
    }
    return { atLeastOne: { value: `Please select at least one option.` } };
  };
}
