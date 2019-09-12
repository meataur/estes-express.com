import { Injectable, OnDestroy } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, Validators } from '@angular/forms';
import { BehaviorSubject, Observable, merge, Subject } from 'rxjs';
import { BolSection, BolSectionService, BillOfLading, TemplateSectionForm } from '../models';
import { BolService } from './bol.service';
import { tap, filter, take, takeUntil } from 'rxjs/operators';
import {
  DialogService,
  patternValidator,
  RegEx,
  MessageConstants,
  MyEstesValidators
} from 'common';

@Injectable({
  providedIn: 'root'
})
export class TemplateSectionFormService extends BolSectionService implements OnDestroy {
  private stop$ = new Subject<boolean>();
  private saveTemplateSource: BehaviorSubject<boolean> = new BehaviorSubject(false);
  private templateSectionForm: BehaviorSubject<FormGroup | undefined> = new BehaviorSubject(
    this.fb.group(new TemplateSectionForm())
  );

  saveTemplate$: Observable<boolean> = this.saveTemplateSource.asObservable();
  get saveTemplate() {
    return this.saveTemplateSource.getValue();
  }

  templateSectionForm$: Observable<FormGroup> = this.templateSectionForm.asObservable().pipe(
    tap(fg => {
      this.stop$.next(true);
      const saveTemplate = fg.controls['saveTemplate'];
      const templateName = fg.controls['templateName'];

      saveTemplate.valueChanges.pipe(takeUntil(this.stop$)).subscribe(val => {
        if (val === false) {
          templateName.reset('');
          templateName.setValidators([Validators.maxLength(255)]);
        } else if (val === true) {
          templateName.setValidators([MyEstesValidators.required, Validators.maxLength(255)]);
        }
        this.saveTemplateSource.next(val);
        templateName.updateValueAndValidity();
      });
      saveTemplate.updateValueAndValidity();

      if (this.bolService.modified !== true) {
        fg.valueChanges
          .pipe(
            filter(() => fg.dirty === true),
            take(1),
            takeUntil(merge(this.stop$, this.bolService.stopModified$))
          )
          .subscribe(() => {
            // console.log(`Form modified: Template Section`);
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

  resetForm(saveTemplate = false, templateName = '', disabled = false) {
    const newForm = this.fb.group(new TemplateSectionForm(saveTemplate, templateName, disabled));

    this.templateSectionForm.next(newForm);
  }

  getTemplateName(): string {
    const form = this.templateSectionForm.getValue();
    return form.controls['templateName'].value;
  }

  valid(): boolean {
    return this.isValid(this.templateSectionForm.getValue());
  }

  ngOnDestroy() {
    this.stop$.next(true);
    this.stop$.unsubscribe();
  }

  populateModel(bol: BillOfLading) {}
}
