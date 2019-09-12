import { Component, OnInit, Input, Output, EventEmitter, AfterViewInit } from '@angular/core';
import { FormGroup, Validators, FormControl, FormGroupDirective, NgForm } from '@angular/forms';
import { FormService, patternValidator, RegEx, MessageConstants } from 'common';
import { ErrorStateMatcher } from '@angular/material';
import { ReferenceTypeEnum } from '../../models';

// See https://stackoverflow.com/a/51606362
export class ReferenceNumberErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    return control.parent.hasError('referenceNumberRequired');
  }
}
export class ReferenceTypeErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    return control.parent.hasError('referenceTypeRequired');
  }
}

@Component({
  selector: 'app-reference-number',
  templateUrl: './reference-number.component.html',
  styleUrls: ['./reference-number.component.scss']
})
export class ReferenceNumberComponent implements OnInit {
  @Input() index: number;
  @Input() referenceNumberForm: FormGroup;
  @Input() canDelete: boolean;
  @Output() deleteReferenceNumber: EventEmitter<number> = new EventEmitter<number>();
  referenceNumberMatcher: ErrorStateMatcher = new ReferenceNumberErrorStateMatcher();
  referenceTypeMatcher: ErrorStateMatcher = new ReferenceTypeErrorStateMatcher();
  referenceTypes: Array<{ value: string; text: string }> = Object.keys(ReferenceTypeEnum).map(
    key => ({
      value: key,
      text: ReferenceTypeEnum[key]
    })
  );

  constructor(public formService: FormService) {}

  ngOnInit() {
    this.cartoon.setValidators([Validators.maxLength(5)]);
    this.weight.setValidators([
      Validators.maxLength(5),
      patternValidator(RegEx.numberGreaterThanZero, MessageConstants.invalidNumberGreaterThanZero)
    ]);

    this.referenceNumberForm.setValidators([this.checkFields]);
  }

  checkFields(group: FormGroup) {
    const referenceNumberPopulated = !!group.controls['referenceNumber'].value;
    const referenceTypePopulated = !!group.controls['referenceType'].value;

    const typeRequired = referenceNumberPopulated;
    const numberRequired = referenceTypePopulated;
    const numberAndTypeRequired = !!(
      group.controls['cartoon'].value || group.controls['weight'].value
    );

    if (typeRequired && !referenceTypePopulated) {
      return { referenceTypeRequired: true };
    }

    if (numberRequired && !referenceNumberPopulated) {
      return { referenceNumberRequired: true };
    }

    if (numberAndTypeRequired && (!referenceNumberPopulated || !referenceTypePopulated)) {
      if (!referenceNumberPopulated && referenceTypePopulated) {
        return { referenceNumberRequired: true };
      } else if (referenceNumberPopulated && !referenceTypePopulated) {
        return { referenceTypeRequired: true };
      } else {
        return { referenceNumberRequired: true, referenceTypeRequired: true };
      }
    }

    return null;
  }

  delete() {
    if (this.canDelete) {
      this.deleteReferenceNumber.emit(this.index);
    }
  }

  get referenceId() {
    return this.referenceNumberForm.controls['referenceId'];
  }
  get referenceNumber() {
    return this.referenceNumberForm.controls['referenceNumber'];
  }
  get referenceType() {
    return this.referenceNumberForm.controls['referenceType'];
  }
  get cartoon() {
    return this.referenceNumberForm.controls['cartoon'];
  }
  get weight() {
    return this.referenceNumberForm.controls['weight'];
  }
}
