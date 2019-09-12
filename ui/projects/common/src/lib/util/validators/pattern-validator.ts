import { ValidatorFn, AbstractControl } from '@angular/forms';

export function patternValidator(
  exp: RegExp,
  errorMessage: string
): ValidatorFn {
  return (control: AbstractControl): { [key: string]: any } | null => {
    const patternMatch = exp.test(control.value);
    return !patternMatch ? { customPattern: { value: errorMessage } } : null;
  };
}
