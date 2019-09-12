import { Pipe, PipeTransform } from '@angular/core';
import { RateQuoteUtils } from '../services/rate-quote-utils';

@Pipe({
  name: 'timeAMPM'
})
export class TimePipe implements PipeTransform {
  transform(value: any) {
    return RateQuoteUtils.formatAMPM(value);
  }
}
