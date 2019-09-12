import { Injectable, OnDestroy } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, Validators } from '@angular/forms';
import { BehaviorSubject, Observable, merge, Subject } from 'rxjs';
import {
  ShippingLabelForm,
  ShippingLabel,
  BolSection,
  BolSectionService,
  BillOfLading
} from '../models';
import { BolService } from './bol.service';
import { tap, filter, take, takeUntil } from 'rxjs/operators';
import { DialogService, patternValidator, RegEx, MessageConstants, MyEstesValidators } from 'common';

@Injectable({
  providedIn: 'root'
})
export class ShippingLabelsFormService extends BolSectionService implements OnDestroy {
  private stop$ = new Subject<boolean>();
  private shippingLabelsForm: BehaviorSubject<FormGroup | undefined> = new BehaviorSubject(
    this.fb.group(new ShippingLabelForm(new ShippingLabel()))
  );

  shippingLabelForm$: Observable<FormGroup> = this.shippingLabelsForm.asObservable().pipe(
    tap(fg => {
      const hasShippingLabel = fg.controls['hasShippingLabel'];
      const labelType = fg.controls['labelType'];
      const startLabel = fg.controls['startLabel'];
      const numberOfLabel = fg.controls['numberOfLabel'];

      hasShippingLabel.valueChanges.pipe(takeUntil(this.stop$)).subscribe(val => {
        if (val === false) {
          this.dialog
            .confirm(
              `Are you sure?`,
              `If you proceed, all of your progress for this section will be cleared from this section.`
            )
            .subscribe(next => {
              if (next === true) {
                this.resetForm();
              } else {
                hasShippingLabel.setValue(true, { emitEvent: false });
              }
            });
        } else if (val === true) {
          labelType.setValidators([MyEstesValidators.required]);
          startLabel.setValidators([MyEstesValidators.required]);
          numberOfLabel.setValidators([
            Validators.maxLength(15),
            patternValidator(
              RegEx.numberGreaterThanZero,
              MessageConstants.invalidNumberGreaterThanZero
            )
          ]);
        }
      });

      if (this.bolService.modified !== true) {
        fg.valueChanges
          .pipe(
            filter(() => fg.dirty === true),
            take(1),
            takeUntil(merge(this.stop$, this.bolService.stopModified$))
          )
          .subscribe(() => {
            // console.log(`Form modified: Shipping Labels`);
            this.bolService.setModified();
          });
      }
    })
  );

  constructor(
    private fb: FormBuilder,
    private bolService: BolService,
    private dialog: DialogService
  ) {
    super();
  }

  resetForm(s: ShippingLabel = new ShippingLabel()) {
    const newForm = this.fb.group(new ShippingLabelForm(s));

    this.shippingLabelsForm.next(newForm);
  }

  valid(): boolean {
    return this.isValid(this.shippingLabelsForm.getValue());
  }

  ngOnDestroy() {
    this.stop$.next(true);
    this.stop$.unsubscribe();
  }

  populateModel(bol: BillOfLading) {
    const form = this.shippingLabelsForm.getValue();
    const hasShippingLabel = form.controls['hasShippingLabel'].value;

    // labelType = new FormControl('1');
    // startLabel = new FormControl('1');
    // numberOfLabel = new FormControl('');

    if (hasShippingLabel) {
      bol.shippingLabel.labelType = form.controls['labelType'].value;
      bol.shippingLabel.startLabel = form.controls['startLabel'].value;
      bol.shippingLabel.numberOfLabel = form.controls['numberOfLabel'].value;
    } else {
      bol.shippingLabel = null;
    }
  }
}
