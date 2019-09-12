import { Injectable } from '@angular/core';
import { AbstractControl, FormArray, FormGroup, ValidationErrors } from '@angular/forms';
import { formatDate } from '@angular/common';

// Usage:
// 1. Import the SharedModule into the application Module.
//
// 2. For the relevant component, declare a property with the signature:
// getErrorMessage: (FormControl) => string;
//
// 3. Inject service and assign property to service method
// constructor(
//   private formService: FormService
// ) {
//   this.getErrorMessage = this.formService.getErrorMessage;
// }
//
// 4. Use inside template:
// <mat-error *ngIf="email.invalid">{{ getErrorMessage(form-control-name) }}</mat-error>
//
@Injectable()
export class FormService {
  constructor() {}

  hasRequiredField = (abstractControl: AbstractControl): boolean => {
    if (abstractControl.validator) {
      const validator = abstractControl.validator({} as AbstractControl);
      if (validator && validator.required) {
        return true;
      }
    }
    if (abstractControl['controls']) {
      for (const controlName in abstractControl['controls']) {
        if (abstractControl['controls'][controlName]) {
          if (this.hasRequiredField(abstractControl['controls'][controlName])) {
            return true;
          }
        }
      }
    }
    return false;
  };

  getErrorMessage(ac: AbstractControl) {
    let msg = '';

    if (ac instanceof FormArray) {
      if (ac.errors.atLeastOne) {
        msg = ac.errors.atLeastOne.value;
      }
    } else {
      if (ac.errors.required) {
        return 'This field is required.';
      }

      if (ac.errors.customPattern) {
        msg = ac.errors.customPattern.value;
      } else if (ac.errors.mustBeTrue) {
        msg = ac.errors.mustBeTrue.value;
      } else if (ac.errors.invalidDate) {
        msg = ac.errors.invalidDate.value;
      } else if (ac.errors.customTextArea) {
        msg = ac.errors.customTextArea.value;
      } else if (ac.errors.maxlength) {
        msg = `This field has reached its maximum length of ${
          ac.errors.maxlength.requiredLength
        } characters.`;
      } else if (ac.errors.minlength) {
        msg = `This field has a minimum length of ${
          ac.errors.minlength.requiredLength
        } characters.`;
      } else if (ac.errors.matDatepickerParse) {
        msg = `Invalid date.`;
      } else if (ac.errors.matDatepickerMax) {
        msg = `Maximum date is ${this.formatDate(ac.errors.matDatepickerMax.max)}.`;
      } else if (ac.errors.matDatepickerFilter) {
        msg = `The date provided is excluded from selection.`;
      } else if (ac.errors.matDatepickerMin) {
        msg = `Minimum date is ${this.formatDate(ac.errors.matDatepickerMin.min)}.`;
      } else if (ac.errors.max) {
        msg = `Maximum is ${Number(ac.errors.max.max).toLocaleString('en-gb')}.`;
      } else if (ac.errors.min) {
        msg = `Minimum is ${Number(ac.errors.min.min).toLocaleString('en-gb')}.`;
      }
    }

    return msg;
  }

  formatDate(date: Date) {
    return formatDate(date, 'MM/dd/yyyy', 'en-US');
  }

  /**
   *
   * @description Useful utility method for development use only.  Returns
   * array of metadata detailing abstract control errors and messages.
   * @param fg FormGroup at the route of the form's tree.
   */
  getFormErrors(fg: FormGroup): any[] {
    let errorList = [];
    for (const key of Object.keys(fg.controls)) {
      const controlErrors: ValidationErrors = fg.get(key).errors;
      if (controlErrors != null) {
        Object.keys(controlErrors).forEach(keyError => {
          errorList.push({
            keyControl: key,
            keyError: keyError,
            errValue: controlErrors[keyError]
          });
        });
      }
      if (fg.get(key) instanceof FormGroup) {
        errorList = errorList.concat(this.getFormErrors(fg.get(key) as FormGroup));
      }
    }
    Object.keys(fg.controls).forEach(key => {});

    return errorList;
  }
}
