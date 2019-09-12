import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { BolService } from '../../services/bol.service';
import { BillOfLading } from '../../models';
import {
  FeedbackTypes,
  SnackbarService,
  MyEstesValidators,
  FormService,
  validateFormFields
} from 'common';

@Component({
  selector: 'app-close-bol-modal',
  templateUrl: './close-bol-modal.component.html',
  styleUrls: ['./close-bol-modal.component.scss']
})
export class CloseBolModalComponent implements OnInit {
  loading = false;
  validBillOfLading = true;
  formGroup: FormGroup;
  feedback: [FeedbackTypes, string | string[]];

  constructor(
    private dialogRef: MatDialogRef<CloseBolModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: BillOfLading,
    private bolService: BolService,
    private snackbar: SnackbarService,
    private fb: FormBuilder,
    public formService: FormService
  ) {}

  ngOnInit() {
    this.formGroup = this.fb.group({
      bolReferenceNumber: [
        this.data.generalInfo.bolNumber,
        [MyEstesValidators.required, Validators.maxLength(25)]
      ]
    });
    this.validBillOfLading = this.isBillOfLadingValid();
  }

  saveForm() {
    this.feedback = null;

    if (this.formGroup.valid) {
      this.loading = true;
      if (!this.validBillOfLading) {
        this.data.generalInfo.bolNumber = this.bolReferenceNumber.value;
      }
      this.bolService.saveBillOfLading(this.data).subscribe(
        next => {
          this.snackbar.success('Draft saved successfully.');
          this.dialogRef.close(true);
        },
        err => {
          this.loading = false;
          this.feedback = [
            'error',
            'An error occurred while attempting to save the Bill of Lading.  Please try again.'
          ];
        },
        () => (this.loading = false)
      );
    } else {
      validateFormFields(this.formGroup);
    }
  }

  get bolReferenceNumber() {
    return this.formGroup.controls['bolReferenceNumber'];
  }

  private isBillOfLadingValid(): boolean {
    return !!this.data.generalInfo.bolNumber.trim();
  }

  onNoClick() {
    this.dialogRef.close(false);
  }
}
