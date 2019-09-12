import { Injectable, OnDestroy } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, Validators } from '@angular/forms';
import { BehaviorSubject, Observable, Subject, Subscription, merge } from 'rxjs';
import {
  BolSection,
  QuoteDetailsForm,
  GeneralInformation,
  BolSectionService,
  BillOfLading,
  RateQuote
} from '../models';
import { mustBeTrueValidator } from 'common';
import { takeUntil, tap, filter, take } from 'rxjs/operators';
import { BolService } from './bol.service';

@Injectable({
  providedIn: 'root'
})
export class QuoteDetailsFormService extends BolSectionService implements OnDestroy {
  private stop$ = new Subject<boolean>();
  private rateQuoteSource = new BehaviorSubject<RateQuote | null>(null);
  private quoteDetailsForm: BehaviorSubject<FormGroup | undefined> = new BehaviorSubject(
    this.fb.group(new QuoteDetailsForm(new GeneralInformation()))
  );
  hasMexicoCountry: boolean;
  hasExpiredQuote: boolean;

  rateQuote$ = this.rateQuoteSource.asObservable();

  quoteDetailsForm$: Observable<FormGroup> = this.quoteDetailsForm.asObservable().pipe(
    tap(fg => {
      this.stop$.next(true);

      const guaranteed = fg.controls['guaranteed'];
      const tosChecked = fg.controls['tosChecked'];

      guaranteed.valueChanges.pipe(takeUntil(this.stop$)).subscribe(next => {
        if (!next) {
          tosChecked.clearValidators();
        } else {
          tosChecked.setValidators([mustBeTrueValidator(`You must accept the Terms of Service.`)]);
        }
        tosChecked.reset('');
        tosChecked.updateValueAndValidity();
      });

      guaranteed.updateValueAndValidity();

      if (this.bolService.modified !== true) {
        fg.valueChanges
          .pipe(
            filter(() => fg.dirty === true),
            take(1),
            takeUntil(merge(this.stop$, this.bolService.stopModified$))
          )
          .subscribe(() => {
            // console.log(`Form modified: Quote Details`);
            this.bolService.setModified();
          });
      }
    })
  );

  constructor(private fb: FormBuilder, private bolService: BolService) {
    super();
  }

  onQuoteNumberChanged(quote: RateQuote, expired?: boolean) {
    this.handleIncomingQuoteCountry(quote);
    if (expired) {
      this.hasExpiredQuote = true;
    } else {
      this.hasExpiredQuote = false;
    }
    this.rateQuoteSource.next(quote);
  }

  private handleIncomingQuoteCountry(quote: RateQuote) {
    if (quote && quote.hasMexicoPoint === true) {
      this.hasMexicoCountry = true;
      return;
    }
    this.hasMexicoCountry = false;
  }

  resetForm(g?: GeneralInformation) {
    if (!g) {
      g = new GeneralInformation();
    }
    const newForm = this.fb.group(new QuoteDetailsForm(g));

    this.quoteDetailsForm.next(newForm);
  }

  ngOnDestroy() {
    this.stop$.next(true);
    this.stop$.unsubscribe();
    this.quoteDetailsForm.unsubscribe();
  }

  valid(): boolean {
    return this.isValid(this.quoteDetailsForm.getValue());
  }

  populateModel(bol: BillOfLading) {
    const form = this.quoteDetailsForm.getValue();

    bol.generalInfo.guranteed = form.controls['guaranteed'].value;
    bol.generalInfo.quote = form.controls['quoteNumber'].value;
    bol.generalInfo.tosChecked = form.controls['tosChecked'].value;
  }
}
