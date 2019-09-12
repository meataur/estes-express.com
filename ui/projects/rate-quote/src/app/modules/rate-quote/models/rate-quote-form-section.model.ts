import { Subject } from 'rxjs';
import { FormGroup } from '@angular/forms';
import { OnInit, OnDestroy, Input } from '@angular/core';
import { RateQuote } from './rate-quote.model';
import { AuthService } from 'common';

export abstract class RateQuoteFormSection implements OnInit, OnDestroy {
  @Input() quote: RateQuote;
  protected stop$ = new Subject<boolean>();
  public get isRateEstimate() {
    return !this.auth.isLoggedIn;
  }
  public formGroup: FormGroup;

  constructor(protected auth: AuthService) {}

  ngOnInit() {
    this.initForm();
  }

  ngOnDestroy() {
    this.stop$.next(true);
    this.stop$.unsubscribe();
  }

  /**
   * @description Invoked by parent class in ngOnInit().
   */
  abstract initForm(): void;
}
