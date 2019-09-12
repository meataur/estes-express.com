import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import {
  FormService,
  validateFormFields,
  MyEstesValidators,
  patternValidator,
  RegEx,
  MessageConstants,
  Masks
} from 'common';

@Component({
  selector: 'app-pro-number-modal',
  templateUrl: './pro-number-modal.component.html',
  styleUrls: ['./pro-number-modal.component.scss']
})
export class ProNumberModalComponent implements OnInit, OnDestroy {
  formGroup: FormGroup;
  assignProSub: Subscription;
  proNumberMask = Masks.pronumber;

  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<ProNumberModalComponent>,
    public formService: FormService
  ) {}

  ngOnInit() {
    this.formGroup = this.fb.group({
      autoAssignPro: [false],
      reservedPro: ['']
    });

    this.assignProSub = this.autoAssignPro.valueChanges.subscribe(next => {
      if (next) {
        this.reservedPro.clearValidators();
      } else {
        this.reservedPro.setValidators([
          MyEstesValidators.required,
          Validators.maxLength(12),
          patternValidator(RegEx.proNumber, MessageConstants.invalidProNumber)
        ]);
      }
      this.reservedPro.reset('');
      this.reservedPro.updateValueAndValidity();
    });
  }

  onNoClick() {
    this.dialogRef.close();
  }

  get autoAssignPro() {
    return this.formGroup.controls['autoAssignPro'];
  }
  get reservedPro() {
    return this.formGroup.controls['reservedPro'];
  }

  onSubmit() {
    if (this.formGroup.valid) {
      const payload = [this.autoAssignPro.value, this.reservedPro.value];

      this.dialogRef.close(payload);
    } else {
      validateFormFields(this.formGroup);
    }
  }

  ngOnDestroy() {
    if (this.assignProSub) {
      this.assignProSub.unsubscribe();
    }
  }
}
