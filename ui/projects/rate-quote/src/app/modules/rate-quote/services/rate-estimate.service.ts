import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RateEstimateService {
  private reviseQuoteSource = new Subject<boolean>();
  reviseQuote$ = this.reviseQuoteSource.asObservable();

  constructor() {}

  reviseQuote() {
    this.reviseQuoteSource.next(true);
  }
}
