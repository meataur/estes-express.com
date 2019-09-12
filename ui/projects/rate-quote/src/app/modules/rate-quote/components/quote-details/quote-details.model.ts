import { Input } from '@angular/core';
import { RateQuote, ServiceLevelEnum } from '../../models';

export abstract class QuoteDetails {
  @Input() quote: RateQuote;
}
