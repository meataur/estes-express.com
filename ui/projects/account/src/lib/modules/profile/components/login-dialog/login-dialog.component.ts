import { Component, OnInit, OnDestroy } from '@angular/core';
import {
  FormGroup,
  FormBuilder,
  AbstractControl,
  Validators
} from '@angular/forms';
import {
  FeedbackTypes,
  AuthService,
  validateFormFields,
  FormService,
  MyEstesValidators
} from 'common';
import { Subscription } from 'rxjs';
import { MatDialogRef } from '@angular/material';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'lib-login-dialog',
  templateUrl: './login-dialog.component.html',
  styleUrls: ['./login-dialog.component.scss']
})
export class LoginDialogComponent implements OnInit, OnDestroy {
  formGroup: FormGroup;
  feedback: [FeedbackTypes, string];
  loading = false;
  sub: Subscription;

  constructor(
    private fb: FormBuilder,
    private auth: AuthService,
    public dialogRef: MatDialogRef<LoginDialogComponent>,
    public formService: FormService
  ) {}

  ngOnInit() {
    this.formGroup = this.fb.group({
      username: ['', MyEstesValidators.required],
      password: ['', MyEstesValidators.required]
    });
  }

  ngOnDestroy() {
    if (this.sub) {
      this.sub.unsubscribe();
    }
  }

  public get username(): AbstractControl {
    return this.formGroup.controls['username'];
  }
  public get password(): AbstractControl {
    return this.formGroup.controls['password'];
  }

  onSubmit() {
    this.feedback = null;
    if (this.formGroup.valid) {
      // Check the session token username against the username provided in the form.
      const token = this.auth.getAuthToken();
      if (
        !token ||
        token.username.toLowerCase() !==
          (this.username.value as string).toLowerCase()
      ) {
        this.feedback = ['error', `Invalid Credentials.`];
      } else {
        this.loading = true;
        this.sub = this.auth.login(this.username.value, this.password.value).subscribe(
          isLoggedIn => {
            if (isLoggedIn) {
              this.dialogRef.close(true);
            } else {
              this.feedback = ['error', `Invalid Credentials.`];
            }
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
      }
    } else {
      validateFormFields(this.formGroup);
    }
  }

  onNoClick() {
    this.dialogRef.close(false);
  }
}
