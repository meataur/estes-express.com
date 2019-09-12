import { FormControl, ValidatorFn, AbstractControl } from '@angular/forms';
import { RatingRequest } from './rating-request.model';
import { MyEstesValidators } from 'common';
import { RateQuote } from './rate-quote.model';
import { Point } from './point.model';

export function validPoint(): ValidatorFn {
  return (control: AbstractControl): { [key: string]: any } | null => {
    const value = control.value as Point | any;
    if (typeof value === 'object' && value.state && value.city && value.zip) {
      return null;
    }
    if (value === '' || value == 'null') {
      return null;
    }
    return { invalidPoint: { value: `Please use the typeahead to select a valid point.` } };
  };
}

export class RoutingInfoForm {
  origin = new FormControl(null, [MyEstesValidators.required]);
  destination = new FormControl(null, [MyEstesValidators.required]);

  constructor(origin?: Point, destination?: Point) {
    if (origin) {
      this.origin.setValue(origin);
    }
    if (destination) {
      this.destination.setValue(destination);
    }
  }
}
