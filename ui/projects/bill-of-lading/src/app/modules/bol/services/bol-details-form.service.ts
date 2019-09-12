import { Injectable, OnDestroy } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, Validators } from '@angular/forms';
import { BehaviorSubject, Observable, Subject, Subscription, merge } from 'rxjs';
import { tap, takeUntil, take, takeWhile, filter, startWith } from 'rxjs/operators';
import {
  BolDetailsForm,
  GeneralInformation,
  BolSection,
  BolSectionService,
  BillOfLading
} from '../models';
import { BolSourceFormService } from './bol-source-form.service';
import { BolService } from './bol.service';
import { formatDate } from '@angular/common';
import { MyEstesValidators, patternValidator, RegEx, MessageConstants } from 'common';
import { resolveReflectiveProviders } from '@angular/core/src/di/reflective_provider';

@Injectable({
  providedIn: 'root'
})
export class BolDetailsFormService extends BolSectionService implements OnDestroy {
  // bolSourceFormSub: iption;
  private validProResolutionSource = new BehaviorSubject<boolean>(null);
  private stop$ = new Subject<boolean>();
  private bolDetailsForm: BehaviorSubject<FormGroup | undefined> = new BehaviorSubject(
    this.fb.group(new BolDetailsForm(new GeneralInformation()))
  );

  validProResolution$: Observable<boolean> = this.validProResolutionSource.asObservable();

  bolDetailsForm$: Observable<FormGroup> = this.bolDetailsForm.asObservable().pipe(
    tap(fg => {
      this.stop$.next(true);

      const reservedPro = fg.controls['reservedPro'];
      const autoAssignPro = fg.controls['autoAssignPro'];
      const masterBol = fg.controls['masterBol'];
      const masterBolNumber = fg.controls['masterBolNumber'];

      masterBol.valueChanges.pipe(takeUntil(this.stop$)).subscribe(val => {
        if (val === true) {
          masterBolNumber.setValidators([MyEstesValidators.required, Validators.maxLength(25)]);
          masterBolNumber.updateValueAndValidity();
        } else if (val === false) {
          masterBolNumber.clearValidators();
          masterBolNumber.reset('');
        }
      });

      merge(
        autoAssignPro.valueChanges.pipe(
          tap(val => {
            if (val) {
              // reservedPro.clearValidators();
              reservedPro.reset('', { emitEvent: false });
              reservedPro.updateValueAndValidity({ emitEvent: false });
            } else {
              // reservedPro.setValidators([
              //   // MyEstesValidators.required,
              //   Validators.maxLength(12),
              //   patternValidator(RegEx.proNumber, MessageConstants.invalidProNumber)
              // ]);
              // reservedPro.updateValueAndValidity({ emitEvent: false });
            }
          })
        ),
        reservedPro.valueChanges
      )
        .pipe(
          startWith({}),
          takeUntil(this.stop$)
        )
        .subscribe(res => {
          autoAssignPro.updateValueAndValidity({ emitEvent: false });

          const validProNumberResolution = !!(
            reservedPro.valid &&
            autoAssignPro.valid &&
            (autoAssignPro.value === true || reservedPro.value)
          );

          this.validProResolutionSource.next(validProNumberResolution);
        });

      if (this.bolService.modified !== true) {
        fg.valueChanges
          .pipe(
            filter(() => fg.dirty === true),
            take(1),
            takeUntil(merge(this.stop$, this.bolService.stopModified$))
          )
          .subscribe(() => {
            // console.log(`Form modified: Bol Details`);
            this.bolService.setModified();
          });
      }
    })
  );

  constructor(private fb: FormBuilder, private bolService: BolService) {
    super();
  }

  resetForm(g?: GeneralInformation) {
    if (!g) {
      g = new GeneralInformation();
    }
    const newForm = this.fb.group(new BolDetailsForm(g));

    // console.log(`bol details; valid: ${newForm.valid}`);

    this.bolDetailsForm.next(newForm);
  }

  ngOnDestroy() {
    this.bolDetailsForm.unsubscribe();
    // this.bolSourceFormSub.unsubscribe();
  }

  valid(): boolean {
    return this.isValid(this.bolDetailsForm.getValue());
  }

  populateModel(bol: BillOfLading) {
    const form = this.bolDetailsForm.getValue();

    try {
      bol.generalInfo.bolDate = formatDate(form.controls['bolDate'].value, 'MM/dd/yyyy', 'en-US');
    } catch (err) {
      console.warn(`Bol date formatting failed.`, err);
    }
    bol.generalInfo.assignProNumber = !!form.controls['autoAssignPro'].value;
    bol.generalInfo.proNumber = form.controls['reservedPro'].value;
    bol.generalInfo.masterBol = !!form.controls['masterBol'].value;
    bol.generalInfo.masterBolNumber = form.controls['masterBolNumber'].value;
    bol.generalInfo.bolNumber = form.controls['bolReferenceNumber'].value;
  }
}
