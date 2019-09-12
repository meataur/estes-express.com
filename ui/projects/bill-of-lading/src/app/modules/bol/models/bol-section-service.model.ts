import { FormGroup } from '@angular/forms';
import { validateFormFields } from 'common';
import { BillOfLading } from './bill-of-lading.model';
import { Input } from '@angular/core';

export abstract class BolSectionService {
  protected modified: boolean;
  abstract valid(): boolean;
  abstract populateModel(BillOfLading);

  protected isValid: (FormGroup) => boolean = function(fg: FormGroup) {
    if (fg.valid) {
      return true;
    } else {
      validateFormFields(fg);

      return false;
    }
  };
}
