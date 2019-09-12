import { Component, OnInit, OnDestroy, Inject } from '@angular/core';
import {
  FormGroup,
  FormBuilder,
  AbstractControl,
  Validators
} from '@angular/forms';
import {
  FeedbackTypes,
  validateFormFields,
  FormService,
  SnackbarService,
  patternValidator,
  RegEx,
  MessageConstants,
  MyEstesValidators
} from 'common';
import { Subscription } from 'rxjs';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { HttpErrorResponse } from '@angular/common/http';
import { ProfileService } from '../../services/profile.service';

@Component({
  selector: 'lib-edit-email-address',
  templateUrl: './edit-email-address.component.html',
  styleUrls: ['./edit-email-address.component.scss']
})
export class EditEmailAddressComponent implements OnInit, OnDestroy {
  currentEmail: string;
  formGroup: FormGroup;
  feedback: [FeedbackTypes, string];
  loading = false;
  sub: Subscription;

  constructor(
    private fb: FormBuilder,
    private profileService: ProfileService,
    public dialogRef: MatDialogRef<EditEmailAddressComponent>,
    private snackbarService: SnackbarService,
    public formService: FormService,
    @Inject(MAT_DIALOG_DATA) public data: string,
  ) {}

  ngOnInit() {
    this.formGroup = this.fb.group({
      email: ['', [
        MyEstesValidators.required,
        patternValidator(RegEx.email, MessageConstants.invalidEmail)
      ]]
    });
    this.currentEmail = this.data;
  }

  ngOnDestroy() {
    if (this.sub) {
      this.sub.unsubscribe();
    }
  }

  public get email(): AbstractControl {
    return this.formGroup.controls['email'];
  }

  onSubmit() {
    this.feedback = null;
    if (this.formGroup.valid) {
      this.loading = true;
      this.sub = this.profileService
        .changeEmail({ email: this.email.value })
        .subscribe(
          next => {
            this.snackbarService.success(next.message);
            this.dialogRef.close(true);
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
    this.dialogRef.close(false);
  }
}
