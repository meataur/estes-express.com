import { Component, OnInit, OnDestroy, Inject } from '@angular/core';
import { FormGroup, FormBuilder, AbstractControl, Validators, FormControl, FormGroupDirective, NgForm } from '@angular/forms';
import {
  FeedbackTypes,
  AuthService,
  validateFormFields,
  FormService,
  SnackbarService,
  MyEstesValidators
} from 'common';
import { Subscription } from 'rxjs';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { HttpErrorResponse } from '@angular/common/http';
import { Password } from '../../../profile/models';
import { ProfileService } from '../../services/profile.service';

@Component({
  selector: 'lib-edit-password',
  templateUrl: './edit-password.component.html',
  styleUrls: ['./edit-password.component.scss']
})
export class EditPasswordComponent implements OnInit, OnDestroy {
  formGroup: FormGroup;
  feedback: [FeedbackTypes, string];
  loading = false;
  sub: Subscription;
  editInfo: [FeedbackTypes, string] = ['info', 'Changing your password will force you to log in again with your new user credentials.'];

  constructor(
    private fb: FormBuilder,
    private profileService: ProfileService,
    public dialogRef: MatDialogRef<EditPasswordComponent>,
    private snackbarService: SnackbarService,
    public formService: FormService
  ) {}

  checkPasswords(group: FormGroup) {
    const pass = group.controls.password.value;
    const confirmPass = group.controls.confirmPassword.value;
    if (!confirmPass) return null;
    if (pass === confirmPass) {
      group.controls.confirmPassword.setErrors(null);
      return null;
    } else {
      group.controls.confirmPassword.setErrors({ passwordMatch: true });
    }
  }

  ngOnInit() {
    this.formGroup = this.fb.group({
      password: ['',
      [
        MyEstesValidators.required,
        Validators.minLength(5),
        Validators.maxLength(10)
      ]],
      confirmPassword: ['']
    }, { validator: this.checkPasswords });
  }

  ngOnDestroy() {
    if (this.sub) {
      this.sub.unsubscribe();
    }
  }

  public get password(): AbstractControl {
    return this.formGroup.controls['password'];
  }
  public get confirmPassword(): AbstractControl {
    return this.formGroup.controls['confirmPassword'];
  }

  onSubmit() {
    this.feedback = null;
    if (this.formGroup.valid) {
      this.loading = true;
      this.sub = this.profileService
        .changePassword({
          password: this.password.value,
          confirmPassword: this.confirmPassword.value
        })
        .subscribe(
          next => {
            this.snackbarService.success(next.message);
            this.dialogRef.close(true);
          },
          err => {
            this.loading = false;
            let msg = `An unexpected error has occurred.  Please try again.`;
            if (err instanceof HttpErrorResponse) {
              if (err.status === 400) {
                msg = err.error[0].message;
              } else if (err.status === 500) {
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
