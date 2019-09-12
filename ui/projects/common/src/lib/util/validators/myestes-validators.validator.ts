import { AbstractControl, ValidationErrors } from '@angular/forms';

// See https://github.com/angular/angular/blob/master/packages/forms/src/validators.ts

export function isEmptyInputValue(value: any): boolean {
  // Check for whitespace if the value is a string
  if (typeof value === 'string') {
    value = value.trim();
  }
  // we don't check for string here so it also works with arrays
  return value == null || value.length === 0;
}

export class MyEstesValidators {
  static required(control: AbstractControl): ValidationErrors | null {
    return isEmptyInputValue(control.value) ? { required: true } : null;
  }
}
