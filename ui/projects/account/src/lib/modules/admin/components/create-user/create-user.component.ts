import { Component, OnInit, OnDestroy } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  AbstractControl,
  FormControl,
  FormGroupDirective,
  NgForm
} from '@angular/forms';
import { Observable, Subscription } from 'rxjs';
import {
  validateFormFields,
  FormService,
  patternValidator,
  RegEx,
  MessageConstants,
  SnackbarService,
  FeedbackTypes,
  Masks,
  MyEstesValidators
} from 'common';
import { HttpErrorResponse } from '@angular/common/http';
import { AdminService } from '../../services/admin.service';
import { User } from '../../models';
import { ErrorStateMatcher } from '@angular/material';
import { Router } from '@angular/router';

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
  selector: 'lib-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.scss']
})
export class CreateUserComponent implements OnInit, OnDestroy {
  loading = false;
  formGroup: FormGroup;
  formSubmissionSub: Subscription;
  errorMessage: [FeedbackTypes, string];
  phoneMask = Masks.phone;
  matcher = new MyErrorStateMatcher();

  constructor(
    private fb: FormBuilder,
    private adminService: AdminService,
    public formService: FormService,
    public router: Router,
    private snackbarService: SnackbarService
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

  ngOnDestroy() {
    if (this.formSubmissionSub) {
      this.formSubmissionSub.unsubscribe();
    }
  }

  ngOnInit() {
    this.formGroup = this.fb.group(
      {
        firstName: ['', [MyEstesValidators.required, Validators.maxLength(25)]],
        lastName: ['', [MyEstesValidators.required, Validators.maxLength(25)]],
        phone: [
          '',
          [
            MyEstesValidators.required,
            patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone)
          ]
        ],
        email: [
          '',
          [
            MyEstesValidators.required,
            patternValidator(RegEx.email, MessageConstants.invalidEmail)
          ]
        ],
        username: [
          '',
          [
            MyEstesValidators.required,
            Validators.minLength(5),
            Validators.maxLength(10)
          ]
        ],
        password: [
          '',
          [
            MyEstesValidators.required,
            Validators.minLength(5),
            Validators.maxLength(10)
          ]
        ],
        confirmPassword: ['', MyEstesValidators.required]
      },
      { validator: this.checkPasswords }
    );
  }

  get email(): AbstractControl {
    return this.formGroup.controls['email'];
  }
  get phone(): AbstractControl {
    return this.formGroup.controls['phone'];
  }
  get firstName(): AbstractControl {
    return this.formGroup.controls['firstName'];
  }
  get lastName(): AbstractControl {
    return this.formGroup.controls['lastName'];
  }
  get username(): AbstractControl {
    return this.formGroup.controls['username'];
  }
  get password(): AbstractControl {
    return this.formGroup.controls['password'];
  }
  get confirmPassword(): AbstractControl {
    return this.formGroup.controls['confirmPassword'];
  }

  onSubmit() {
    this.errorMessage = null;
    if (this.formGroup.valid) {
      this.loading = true;

      const user = new User();
      user.firstName = this.firstName.value;
      user.lastName = this.lastName.value;
      user.email = this.email.value;
      user.phone = this.phone.value;
      user.username = this.username.value;
      user.password = this.password.value;
      user.confirmPassword = this.confirmPassword.value;
      user.notify = 'N';

      this.formSubmissionSub = this.adminService.createUser(user).subscribe(
        res => {
          this.snackbarService.success(res.message);
          this.router.navigate(['/admin/users/' + this.username.value]);
        },
        err => {
          this.errorMessage = ['error', err];
          this.loading = false;
        },
        () => (this.loading = false)
      );
    } else {
      validateFormFields(this.formGroup);
      this.snackbarService.validationError();
    }
  }
}
