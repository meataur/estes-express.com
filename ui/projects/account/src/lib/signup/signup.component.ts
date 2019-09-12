import { AfterViewInit, Component, ViewChild, OnDestroy, OnInit } from '@angular/core';
import { SignupRequest } from './signup-request.model';
import { SignupService } from './signup.service';
import {
  Alert,
  SnackbarService,
  PromoService,
  MyEstesValidators,
  validateFormFields,
  FeedbackTypes,
  FormService,
  patternValidator,
  RegEx,
  MessageConstants
} from 'common';
import {
  NgForm,
  FormGroup,
  FormBuilder,
  FormControl,
  FormGroupDirective,
  AbstractControl,
  Validators
} from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material';
import { Subscription } from 'rxjs';

// See https://stackoverflow.com/a/51606362
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    return control.parent.hasError('passwordMatch');
  }
}

@Component({
  selector: 'lib-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements AfterViewInit, OnInit, OnDestroy {
  @ViewChild('signupForm') public signupForm: NgForm;

  public isLoading: boolean;
  public submitted: boolean;
  public feedback: [FeedbackTypes, string | string[]];
  public showSelectedSignupPathError: boolean;

  // public messages: Alert[];
  // public signupRequest: SignupRequest;
  public confirmpassword: any;
  public phoneMask: any;
  public paths: any[];
  public signupPath: string;
  public selectedSignupPath: string;
  public formGroup: FormGroup;
  public matcher = new MyErrorStateMatcher();
  private submitSub: Subscription;

  constructor(
    private fb: FormBuilder,
    public signupService: SignupService,
    public snackbarService: SnackbarService,
    public promoService: PromoService,
    public formService: FormService,
  ) {
    // this.signupRequest = new SignupRequest();
    this.phoneMask = [
      '(',
      /[1-9]/,
      /\d/,
      /\d/,
      ')',
      ' ',
      /\d/,
      /\d/,
      /\d/,
      '-',
      /\d/,
      /\d/,
      /\d/,
      /\d/
    ];
    this.signupPath = 'start';
    this.selectedSignupPath = 'start';
    // this.messages = [];
    this.paths = [
      {
        value: 'signup',
        display: `Yes, and I have my company's account number.`
      },
      {
        value: 'account',
        display: `Yes, but I don't know my company's account number.`
      },
      {
        value: 'contact',
        display: 'Not yet.'
      }
    ];
  }

  ngOnInit() {
    this.initForm();
  }

  ngOnDestroy() {
    if (this.submitSub) {
      this.submitSub.unsubscribe();
    }
  }

  checkPasswords(group: FormGroup) {
    const pass = group.controls.password.value;
    const confirmPass = group.controls.confirmPassword.value;

    return pass === confirmPass ? null : { passwordMatch: true };
  }

  onSubmit() {
    this.feedback = null;

    if (this.formGroup.valid) {
      const payload = this.populateModel();

      this.isLoading = true;
      this.submitted = true;

      this.submitSub = this.signupService.signup(payload).subscribe(
        response => {
          if (response) {
            this.isLoading = false;
            this.feedback = ['success', response[0].message];
          }
        },
        err => {
          console.error(err);
          this.isLoading = false;
          this.submitted = false;
          const messages = [];
          for (const error of err) {
            if (error) {
              messages.push(error);
            }
          }
          if (messages.length) {
            this.feedback = ['error', messages];
          }
        }
      );
    } else {
      validateFormFields(this.formGroup);
      this.snackbarService.validationError();
    }
  }

  get companyAccountNumber() {
    return this.formGroup.controls['companyAccountNumber'];
  }
  get companyName() {
    return this.formGroup.controls['companyName'];
  }
  get firstName() {
    return this.formGroup.controls['firstName'];
  }
  get lastName() {
    return this.formGroup.controls['lastName'];
  }
  get email() {
    return this.formGroup.controls['email'];
  }
  get phone() {
    return this.formGroup.controls['phone'];
  }
  get username() {
    return this.formGroup.controls['username'];
  }
  get password() {
    return this.formGroup.controls['password'];
  }
  get confirmPassword() {
    return this.formGroup.controls['confirmPassword'];
  }

  private populateModel(): SignupRequest {
    const payload = new SignupRequest();
    payload.accountCode = this.companyAccountNumber.value;
    payload.company = this.companyName.value;
    payload.firstName = this.firstName.value;
    payload.lastName = this.lastName.value;
    payload.email = this.email.value;
    payload.phone = this.phone.value;
    payload.userName = this.username.value;
    payload.password = this.password.value;

    return payload;
  }

  private initValueChanges() {}

  private initForm() {
    this.formGroup = this.fb.group(
      {
        companyAccountNumber: ['', [MyEstesValidators.required, Validators.maxLength(7)]],
        companyName: ['', [MyEstesValidators.required]],
        firstName: ['', [MyEstesValidators.required]],
        lastName: ['', [MyEstesValidators.required]],
        email: [
          '',
          [MyEstesValidators.required, patternValidator(RegEx.email, MessageConstants.invalidEmail)]
        ],
        phone: [
          '',
          [
            MyEstesValidators.required,
            patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone)
          ]
        ],
        username: [
          '',
          [MyEstesValidators.required, Validators.minLength(5), Validators.maxLength(10)]
        ],
        password: [
          '',
          [MyEstesValidators.required, Validators.minLength(5), Validators.maxLength(10)]
        ],
        confirmPassword: ['', [MyEstesValidators.required]]
      },
      { validator: this.checkPasswords }
    );

    this.initValueChanges();
  }

  /**
   * submit submits the request for signup
   */
  // submit() {
  //   let ctrl = this;
  //   if (this.signupRequest.password !== this.confirmpassword) {
  //     this.signupForm.controls['inputConfirmPassword'].setErrors({
  //       passwordMismatch: 'Passwords do not match'
  //     });
  //   } else {
  //     this.signupForm.controls['inputConfirmPassword'].setErrors(null);
  //   }
  //   if (this.signupForm.valid) {
  //     ctrl.isLoading = true;
  //     ctrl.submitted = true;

  //     ctrl.signupService.signup(this.signupRequest).subscribe(
  //       response => {
  //         if (response) {
  //           ctrl.isLoading = false;
  //           ctrl.messages.push({
  //             type: 'success',
  //             message: response[0].message
  //           });
  //           console.log('Success: ', response);
  //         }
  //       },
  //       err => {
  //         //console.error('Error calling SignupService.signup:', err);
  //         console.error(err);
  //         ctrl.isLoading = false;
  //         ctrl.submitted = false;
  //         for (const error of err) {
  //           //console.log(error);
  //           if (error) {
  //             ctrl.messages.push({
  //               type: 'danger',
  //               message: error
  //             });
  //           }
  //         }
  //       }
  //     );
  //   }
  // }

  setPath(path: string) {
    this.signupPath = path;
    this.selectedSignupPath = path;
    this.feedback = null;
  }

  changePath() {
    if (this.selectedSignupPath === 'start') {
      this.showSelectedSignupPathError = true;
      this.snackbarService.validationError();
    } else {
      this.showSelectedSignupPathError = false;
    }
    this.signupPath = this.selectedSignupPath;
    this.feedback = null;
  }

  // closeAlert(alert: Alert) {
  //   this.messages.splice(this.messages.indexOf(alert), 1);
  // }

  // resetMessages() {
  //   this.messages = [];
  // }

  ngAfterViewInit() {
    this.promoService.setAppId('sign-up');
    this.promoService.setAppState('any');
	
  }
}
