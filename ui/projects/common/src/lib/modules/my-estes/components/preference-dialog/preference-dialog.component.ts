import { Component, OnInit, Inject, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, AbstractControl } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { PreferenceData } from '../../models';
import { validateFormFields } from '../../../../util/validate-form-fields';
import { FeedbackTypes } from '../../../components/models';
import { MyestesService } from '../../services/myestes.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { SnackbarService } from '../../../snackbar/public_api';

@Component({
  selector: 'lib-preference-dialog',
  templateUrl: './preference-dialog.component.html',
  styleUrls: ['./preference-dialog.component.scss']
})
export class PreferenceDialogComponent implements OnInit, OnDestroy {
  formGroup: FormGroup;
  feedback: [FeedbackTypes, string];
  loading = false;
  prefSub: Subscription;

  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<PreferenceDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: PreferenceData,
    private myestesService: MyestesService,
    private snackbar: SnackbarService
  ) {}

  ngOnInit() {
    this.formGroup = this.fb.group({
      shipmentType: this.data.shipmentType
    });
  }

  ngOnDestroy() {
    if (this.prefSub) {
      this.prefSub.unsubscribe();
    }
  }

  public get shipmentType(): AbstractControl {
    return this.formGroup.controls['shipmentType'];
  }

  onSubmit() {
    this.feedback = null;
    if (this.formGroup.valid) {
      this.loading = true;
      this.myestesService.setDefaultParty(this.shipmentType.value).subscribe(
        next => {
          this.snackbar.success(next.message);
          this.dialogRef.close(this.shipmentType.value);
        },
        err => {
          this.loading = false;
          let msg = `An unexpected error has occurred.  Please try again.`;
          if (err instanceof HttpErrorResponse) {
            if (err.error.errorCode === 'ERROR' && err.error.message) {
              msg = err.error.message;
            }
          }
          this.feedback = ['error', msg];
        },
        () => (this.loading = false)
      );
    } else {
      validateFormFields(this.formGroup);
    }
  }

  onNoClick() {
    this.dialogRef.close();
  }
}
