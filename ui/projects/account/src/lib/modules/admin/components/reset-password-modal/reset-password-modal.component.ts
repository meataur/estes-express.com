import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { FeedbackTypes, FormService, validateFormFields, SnackbarService } from 'common';
import { Subscription } from 'rxjs';
import { AdminService } from '../../services/admin.service';


@Component({
  selector: 'lib-reset-password-modal',
  templateUrl: './reset-password-modal.component.html',
  styleUrls: []
})
export class ResetPasswordModalComponent implements OnInit {

  formGroup: FormGroup;
  username: string;
  errorMessages: [FeedbackTypes, string];
  loading: boolean;
  sub: Subscription;

  constructor( public dialogRef: MatDialogRef<ResetPasswordModalComponent>,
               @Inject(MAT_DIALOG_DATA) public data: any,
               public formService: FormService,
               private adminService: AdminService,
               private snackbarService: SnackbarService,
               private fb: FormBuilder) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit() {

    this.username = this.data.username;

    this.formGroup = this.fb.group({
      password: ['', [Validators.required, Validators.maxLength(10), Validators.minLength(5)]],
      confirmPassword: ['', [Validators.required, Validators.maxLength(10), Validators.minLength(5)]]
    }, { validator: this.checkPasswords });
  }

  get password() {
    return this.formGroup.controls['password'];
  }
  get confirmPassword() {
    return this.formGroup.controls['confirmPassword'];
  }

  onSubmit() {
    this.errorMessages = null;

    if (this.formGroup.valid) {
      this.loading = true;
      this.sub = this.adminService
        .changeUserPassword(this.username, {
          password: this.password.value,
          confirmPassword: this.confirmPassword.value
        })
        .subscribe(
          next => {
            this.snackbarService.success(next.message);
            this.dialogRef.close(true);
            this.loading = false;
          },
          err => {
            this.loading = false;
            this.errorMessages = ['error', (typeof err === 'string') ? err : 'An unexpected error has occurred.  Please try again.'];
          }
        );
    } else {
      validateFormFields(this.formGroup);
    }

  }

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
}



    
