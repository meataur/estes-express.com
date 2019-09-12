import { Injectable, OnDestroy } from '@angular/core';
import { FormGroup, FormBuilder, FormArray } from '@angular/forms';
import { BehaviorSubject, Observable, Subject, Subscription, merge } from 'rxjs';
import {
  BolSourceForm,
  DocumentTypeEnum,
  BolSection,
  BolSectionService,
  BillOfLading,
  Template
} from '../models';
import { tap, takeUntil, take, filter, distinctUntilChanged, debounceTime } from 'rxjs/operators';
import { BolService } from './bol.service';

@Injectable({
  providedIn: 'root'
})
export class BolSourceFormService extends BolSectionService implements OnDestroy {
  private stop$ = new Subject<boolean>();
  private documentTypeSource = new BehaviorSubject<DocumentTypeEnum>(DocumentTypeEnum.E);
  private templateSelection: Subject<Template> = new Subject<Template>();
  private bolSourceForm: BehaviorSubject<FormGroup | undefined> = new BehaviorSubject(
    this.fb.group(new BolSourceForm(DocumentTypeEnum.E))
  );

  templateSelection$ = this.templateSelection.asObservable();
  bolSourceForm$: Observable<FormGroup> = this.bolSourceForm.asObservable().pipe(
    tap(fg => {
      this.stop$.next(true);

      // const templateName = fg.controls['templateName'];

      // templateName.valueChanges
      //   .pipe(
      //     debounceTime(500),
      //     distinctUntilChanged()
      //   )
      //   .subscribe(next => {
      //     console.log(next);
      //   });

      fg.controls['documentType'].valueChanges.pipe(takeUntil(this.stop$)).subscribe(v => {
        this.documentTypeSource.next(v);
      });

      fg.controls['documentType'].updateValueAndValidity();

      if (this.bolService.modified !== true) {
        fg.valueChanges
          .pipe(
            filter(() => fg.dirty === true),
            take(1),
            takeUntil(merge(this.stop$, this.bolService.stopModified$))
          )
          .subscribe(() => {
            // console.log(`Form modified: Bol Source`);
            this.bolService.setModified();
          });
      }
    })
  );

  documentTypeChanged$ = this.documentTypeSource.asObservable();

  constructor(private fb: FormBuilder, private bolService: BolService) {
    super();
  }

  valid(): boolean {
    return this.isValid(this.bolSourceForm.getValue());
  }

  populateModel(bol: BillOfLading) {
    const form = this.bolSourceForm.getValue();

    bol.documentType = form.controls['documentType'].value;
  }

  onTemplateSelected(t: Template) {
    this.templateSelection.next(t);
  }

  resetForm(d: DocumentTypeEnum) {
    if (!d) {
      d = DocumentTypeEnum.E;
    }
    const newForm = this.fb.group(new BolSourceForm(d));

    this.bolSourceForm.next(newForm);
  }

  ngOnDestroy() {
    this.stop$.next(true);
    this.stop$.unsubscribe();
    this.documentTypeSource.unsubscribe();
    this.bolSourceForm.unsubscribe();
  }
}
