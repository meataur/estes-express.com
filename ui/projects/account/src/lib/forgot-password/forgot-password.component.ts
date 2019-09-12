import { AfterViewInit, Component, Inject, OnInit, OnDestroy } from '@angular/core';
import { ForgotPasswordRequest } from './forgot-password.model';
import { ForgotPasswordService } from './forgot-password.service';
import {
  FeedbackTypes,
  FormService,
  validateFormFields,
  patternValidator,
  RegEx,
  MessageConstants,
  MyEstesValidators,
  SnackbarService
} from 'common';
import { HttpErrorResponse } from '@angular/common/http';
import { PromoService } from 'common';
import { APP_CONFIG } from '../models/app.config';
import { AppConfig } from '../models/app-config.interface';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { Subject, Subscription } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'lib-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss']
})
export class ForgotPasswordComponent implements OnInit, AfterViewInit, OnDestroy {
  private stop$ = new Subject<boolean>();
  formSub: Subscription;
  formGroup: FormGroup;
  public loading: boolean;
  submitted = false;
  // public forgotPswdRequest: ForgotPasswordRequest;
  feedback: [FeedbackTypes, string];

  constructor(
    public forgotPswdService: ForgotPasswordService,
    public promoService: PromoService,
    @Inject(APP_CONFIG) private appConfig: AppConfig,
    private fb: FormBuilder,
    public formService: FormService,
    private snackbarService: SnackbarService
  ) {
    // this.forgotPswdRequest = new ForgotPasswordRequest();
  }

  ngOnInit() {
    this.initForm();
    this.initValueChanges();
  }

  ngOnDestroy() {
    this.stop$.next(true);
    this.stop$.unsubscribe();

    if (this.formSub) {
      this.formSub.unsubscribe();
    }
  }

  onSubmit() {
    if (this.formGroup.valid) {
      this.loading = true;

      const passwordRequest = new ForgotPasswordRequest();
      passwordRequest.selectionType = this.isUsername.value === true ? 'userName' : 'email';
      passwordRequest.searchCriteria = this.emailUsername.value;

      this.formSub = this.forgotPswdService.email(passwordRequest).subscribe(
        response => {
          if (response) {
            this.loading = false;
            this.submitted = true;
            this.feedback = ['success', response.message];
          }
        },
        err => {
          this.loading = false;
          let msg = `An unexpected error has occurred.  Please try again.`;
          if (err) {
            msg = err;
          }
          this.feedback = ['error', msg];
        },
        () => (this.loading = false)
      );
    } else {
      validateFormFields(this.formGroup);
      this.snackbarService.validationError();
    }
  }

  private initForm() {
    this.formGroup = this.fb.group({
      isUsername: [true],
      emailUsername: ['', [MyEstesValidators.required]]
    });
  }

  private initValueChanges() {
    this.isUsername.valueChanges.pipe(takeUntil(this.stop$)).subscribe(next => {
      this.emailUsername.reset('');
      if (next === true) {
        this.emailUsername.setValidators([MyEstesValidators.required]);
      } else {
        this.emailUsername.setValidators([
          MyEstesValidators.required,
          patternValidator(RegEx.email, MessageConstants.invalidEmail)
        ]);
      }
    });
  }

  /**
   * submit submits the retrieve password request via the ForgotPasswordService
   * @param isValid is the forgot password form validity flag
   */
  // submit(isValid: boolean) {
  //   this.feedback = null;
  //   // this.successMessage = null;
  //   // this.errorMessages = [];
  //   const ctrl = this;
  //   if (isValid) {
  //     ctrl.loading = true;

  //     ctrl.forgotPswdService.email(ctrl.forgotPswdRequest).subscribe(
  //       response => {
  //         if (response) {
  //           ctrl.loading = false;
  //           this.submitted = true;
  //           this.feedback = ['success', response.message];
  //         }
  //       },
  //       err => {
  //         this.loading = false;
  //         let msg = `An unexpected error has occurred.  Please try again.`;
  //         if (err) {
  //           msg = err;
  //         }
  //         this.feedback = ['error', msg];
  //       },
  //       () => (this.loading = false)
  //     );
  //   }
  // }

  get emailUsernameFieldText() {
    return this.isUsername.value === true ? 'Username' : 'Email Address';
    // switch (this.forgotPswdRequest.selectionType) {
    //   case `userName`:
    //     return `Username`;
    //   case `email`:
    //     return `Email`;
    // }
  }

  get faqUrl() {
    return `${this.appConfig.cms.faq}`;
  }

  get contactUsUrl() {
    return `${this.appConfig.cms.contactUs}`;
  }

  get emailUsername() {
    return this.formGroup.controls['emailUsername'];
  }

  get isUsername() {
    return this.formGroup.controls['isUsername'];
  }

  ngAfterViewInit() {
    this.promoService.setAppId('forgot-password');
    this.promoService.setAppState('any');
  }
}
