import { ValidatorFn, AbstractControl } from '@angular/forms';

export function textAreaValidator(exp: RegExp, errorMessage: string, max?: number): ValidatorFn {
  return (control: AbstractControl): { [key: string]: any } | null => {
    if (control && control.value) {
      const splitLines = (control.value as string).split(/\r?\n/g).filter(Boolean);
      if (max && splitLines.length > max) {
        return {
          customTextArea: {
            value: `You have surpassed the maximum number of lines allowed (${max}).`
          }
        };
      } else {
        for (const s of splitLines) {
          if (!exp.test(s)) {
            return { customTextArea: { value: errorMessage } };
          }
          continue;
        }
      }
    }
    return null;
  };
}
