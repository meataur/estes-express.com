import { Pipe, PipeTransform } from '@angular/core';
import { getFormattedDateFromString } from '../date-helpers';

@Pipe({
  name: 'displayDate'
})
export class DisplayDatePipe implements PipeTransform {
  transform(value: any) {
    if (typeof value == 'number') {
      value = value.toString();
    }
    return getFormattedDateFromString(value);
  }
}
