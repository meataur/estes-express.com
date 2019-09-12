import { Component, OnInit, OnDestroy, Inject } from '@angular/core';
import { FormGroup, FormBuilder, AbstractControl, Validators } from '@angular/forms';
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
import { ProfileService } from '../../services/profile.service';

@Component({
  selector: 'lib-edit-username',
  templateUrl: './edit-username.component.html',
  styleUrls: ['./edit-username.component.scss']
})
export class EditUsernameComponent implements OnInit, OnDestroy {
  currentUsername: string;
  formGroup: FormGroup;
  feedback: [FeedbackTypes, string];
  loading = false;
  sub: Subscription;
  editInfo: [FeedbackTypes, string] = [
    'info',
    'Changing your username will force you to log in again with your new user credentials.'
  ];

  constructor(
    private fb: FormBuilder,
    private profileService: ProfileService,
    public dialogRef: MatDialogRef<EditUsernameComponent>,
    private snackbarService: SnackbarService,
    public formService: FormService,
    @Inject(MAT_DIALOG_DATA) public data: string
  ) {}

  ngOnInit() {
    this.formGroup = this.fb.group({
      username: [
        '',
        [MyEstesValidators.required, Validators.minLength(5), Validators.maxLength(10)]
      ]
    });
    this.currentUsername = this.data;
  }

  ngOnDestroy() {
    if (this.sub) {
      this.sub.unsubscribe();
    }
  }

  public get username(): AbstractControl {
    return this.formGroup.controls['username'];
  }

  onSubmit() {
    this.feedback = null;
    if (this.formGroup.valid) {
      this.loading = true;
      this.sub = this.profileService.changeUsername({ username: this.username.value }).subscribe(
        next => {
          this.snackbarService.success(next.message);
          this.dialogRef.close(true);
        },
        err => {
          this.loading = false;
          this.feedback = ['error', err];
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
