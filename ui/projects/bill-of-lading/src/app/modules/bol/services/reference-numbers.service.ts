import { Injectable, OnDestroy } from '@angular/core';
import { FormGroup, FormBuilder, FormArray } from '@angular/forms';
import { BehaviorSubject, Observable, Subject, merge } from 'rxjs';
import {
  ReferenceNumbersInfoForm,
  ReferenceNumberForm,
  Reference,
  BolSection,
  BolSectionService,
  BillOfLading
} from '../models';
import { filter, take, takeUntil, tap } from 'rxjs/operators';
import { BolService } from './bol.service';
import { DialogService } from 'common';

@Injectable({
  providedIn: 'root'
})
export class ReferenceNumbersFormService extends BolSectionService implements OnDestroy {
  hasReferenceNumbers = false;
  private stop$ = new Subject<boolean>();
  readonly INITIAL_REFERENCE_NUMBER_COUNT = 1;
  readonly MAX_REFERENCE_COUNT = 30;
  private referenceNumbersForm: BehaviorSubject<FormGroup | undefined> = new BehaviorSubject(
    this.fb.group(new ReferenceNumbersInfoForm())
  );

  referenceNumbersForm$: Observable<FormGroup> = this.referenceNumbersForm.asObservable().pipe(
    tap(fg => {
      this.stop$.next(true);

      fg.controls['hasReferenceNumbers'].setValue(this.hasReferenceNumbers);

      fg.controls['hasReferenceNumbers'].valueChanges.pipe(takeUntil(this.stop$)).subscribe(val => {
        if (val === false) {
          this.dialog
            .confirm(
              `Are you sure?`,
              `If you proceed, all of your progress for this section will be cleared from this section.`
            )
            .subscribe(next => {
              if (next === true) {
                this.hasReferenceNumbers = false;
                this.resetForm();
              } else {
                fg.controls['hasReferenceNumbers'].setValue(true, { emitEvent: false });
              }
            });
        } else if (val === true) {
          this.hasReferenceNumbers = true;
        }
      });

      if ((fg.controls['referenceNumbers'] as FormArray).controls.length === 0) {
        this.addReferenceNumber();
      }


      if (this.bolService.modified !== true) {
        fg.valueChanges
          .pipe(
            filter(() => fg.dirty === true),
            take(1),
            takeUntil(merge(this.stop$, this.bolService.stopModified$))
          )
          .subscribe(() => {
            // console.log(`Form modified: Reference Numbers`);
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

  addReferenceNumber(count = 1) {
    const currentForm = this.referenceNumbersForm.getValue();
    const currentReferenceNumbers = currentForm.get('referenceNumbers') as FormArray;

    if (currentReferenceNumbers.length < this.MAX_REFERENCE_COUNT) {
      for (let i = 0; i < count; i++) {
        currentReferenceNumbers.push(this.fb.group(new ReferenceNumberForm(new Reference())));
      }
      this.referenceNumbersForm.next(currentForm);
    }
  }

  deleteReferenceNumber(index: number) {
    const currentForm = this.referenceNumbersForm.getValue();
    const currentReferenceNumbers = currentForm.get('referenceNumbers') as FormArray;

    currentReferenceNumbers.removeAt(index);
    this.referenceNumbersForm.next(currentForm);
  }

  resetForm(r: Array<Reference> = []) {
    const newForm = this.fb.group(new ReferenceNumbersInfoForm());

    if (r.length) {
      this.hasReferenceNumbers = true;
      const refList = newForm.get('referenceNumbers') as FormArray;

      for (const ref of r) {
        refList.push(this.fb.group(new ReferenceNumberForm(ref)));
      }
    } else {
      this.hasReferenceNumbers = false;
    }

    this.referenceNumbersForm.next(newForm);
  }

  valid(): boolean {
    return this.isValid(this.referenceNumbersForm.getValue());
  }

  ngOnDestroy() {
    this.stop$.next(true);
    this.stop$.unsubscribe();
  }

  populateModel(bol: BillOfLading) {
    const form = this.referenceNumbersForm.getValue();
    const hasReferenceNumbers = form.controls['hasReferenceNumbers'].value;

    bol.references = [];

    if (hasReferenceNumbers) {
      for (const c of (form.controls['referenceNumbers'] as FormArray).controls) {
        const fg = c as FormGroup;

        const referenceNumber = fg.controls['referenceNumber'].value;
        const referenceType = fg.controls['referenceType'].value || null;
        const cartoon = fg.controls['cartoon'].value;
        const weight = fg.controls['weight'].value;

        if (!!(referenceNumber || referenceType || cartoon || weight)) {
          const reference = new Reference();
          reference.referenceId = fg.controls['referenceId'].value;
          reference.referenceNumber = referenceNumber;
          reference.referenceType = referenceType;
          reference.cartoon = cartoon;
          reference.weight = weight;

          bol.references.push(reference);
        }

        // referenceId = new FormControl('');
        // referenceNumber = new FormControl('');
        // referenceType = new FormControl('');
        // cartoon = new FormControl('');
        // weight = new FormControl('');
      }
    }
  }
}
