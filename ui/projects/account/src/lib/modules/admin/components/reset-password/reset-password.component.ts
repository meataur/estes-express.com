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
import { MatDialogRef, MAT_DIALOG_DATA, ErrorStateMatcher } from '@angular/material';
import { HttpErrorResponse } from '@angular/common/http';
import { AdminService } from '../../services/admin.service';
import { Password } from '../../../profile/models';

// See https://stackoverflow.com/a/51606362
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(
    control: FormControl | null,
    form: FormGroupDirective | NgForm | null
  ): boolean {
    return control.parent.hasError('passwordMatch');
  }
}

@Component({
  selector: 'lib-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent implements OnInit, OnDestroy {
  targetUsername: string;
  formGroup: FormGroup;
  feedback: [FeedbackTypes, string];
  loading = false;
  sub: Subscription;
  matcher = new MyErrorStateMatcher();

  constructor(
    private fb: FormBuilder,
    private adminService: AdminService,
    public dialogRef: MatDialogRef<ResetPasswordComponent>,
    private snackbarService: SnackbarService,
    public formService: FormService,
    @Inject(MAT_DIALOG_DATA) public data: string
  ) {}

  checkPasswords(group: FormGroup) {
    const pass = group.controls.password.value;
    const confirmPass = group.controls.confirmPassword.value;

    return pass === confirmPass ? null : { passwordMatch: true };
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
    this.targetUsername = this.data;
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
      this.sub = this.adminService
        .changeUserPassword(this.targetUsername, {
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
