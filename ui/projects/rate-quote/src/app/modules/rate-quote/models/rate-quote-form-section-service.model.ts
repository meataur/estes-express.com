import { FormBuilder, FormGroup } from '@angular/forms';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { OnDestroy } from '@angular/core';
import { validateFormFields } from 'common';
import { RatingRequest } from './rating-request.model';
import { RateQuoteType } from './rate-quote-type-form.model';

export abstract class RateQuoteFormSectionService implements OnDestroy {
  protected stop$ = new Subject<boolean>();
  protected formModelSource: BehaviorSubject<FormGroup>;
  public formModel$: Observable<FormGroup>;

  protected initFormSource<T>(form: new () => T) {
    this.formModelSource = new BehaviorSubject<FormGroup>(this.fb.group(new form()));

    this.formModel$ = this.formModelSource.asObservable().pipe(
      tap(fg => {
        this.stop$.next(true);

        this.initValueChangeWatchers(fg);

        this.initFormState(fg);
      })
    );
  }

  protected isValid: (FormGroup) => boolean = function(fg: FormGroup) {
    if (fg.valid) {
      return true;
    } else {
      validateFormFields(fg);

      return false;
    }
  };

  ngOnDestroy() {
    this.stop$.next(true);
    this.stop$.unsubscribe();
  }

  abstract initValueChangeWatchers(FormGroup): void;
  abstract initFormState(FormGroup): void;
  abstract valid(): boolean;
  abstract populateModel(ratingRequest: RatingRequest, ...params): void;

  constructor(protected fb: FormBuilder) {}
}
