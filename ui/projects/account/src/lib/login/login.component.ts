import {
  AfterViewInit,
  AfterContentInit,
  Component,
  OnInit,
  OnDestroy,
  Inject,
  ViewChild,
  ElementRef
} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import {
  AuthService,
  Login,
  FormService,
  validateFormFields,
  FeedbackTypes,
  MyEstesValidators,
  SnackbarService
} from 'common';
import { PromoService} from 'common';
import { filter } from 'rxjs/operators';
import { APP_CONFIG } from '../models/app.config';
import { AppConfig } from '../models/app-config.interface';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';

@Component({
  selector: 'lib-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit, OnDestroy, AfterViewInit, AfterContentInit {
  loginSub: Subscription;
  logoutSub: Subscription;
  returnUrl: string;
  loading: boolean;
  formGroup: FormGroup;
  feedback: [FeedbackTypes, string | string[]];
  @ViewChild('usernameinput') userNameField: ElementRef;

  constructor(
    public router: Router,
    private authService: AuthService,
    public promoService: PromoService,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    public formService: FormService,
    private snackbarService: SnackbarService,
    @Inject(APP_CONFIG) private appConfig: AppConfig
  ) {
    this.loading = false;
  }

  ngOnInit() {
    let token: string = null;
    try {
      token  = this.route.snapshot.queryParamMap.get('token');
    } catch (e) {
    }

    this.initForm();
    this.returnUrl = this.route.snapshot.queryParamMap.get('returnUrl');
    // Log out user if already logged in.  I don't see why this is necessary.
    if (this.authService.isLoggedIn === true) {
      this.logoutSub = this.authService.logout(this.returnUrl).subscribe();
    }
    /**
     * Login By Token
     */
    if (!!token) {
      this.loginByToken(token);
    }
  }

  ngAfterViewInit() {
    this.promoService.setAppId('login');
    this.promoService.setAppState('any');
  }

  ngAfterContentInit() {
    this.userNameField.nativeElement.focus();
  }

  ngOnDestroy() {
    if (this.loginSub) {
      this.loginSub.unsubscribe();
    }
    if (this.logoutSub) {
      this.logoutSub.unsubscribe();
    }
  }

  initForm() {
    this.formGroup = this.fb.group({
      username: ['', [MyEstesValidators.required]],
      password: ['', [MyEstesValidators.required]]
    });
  }

  loginByToken(token: string) {
      this.feedback = null;
      this.loading = true;
      this.loginSub = this.authService.loginByToken(token).subscribe(
        isLoggedIn => {
          if (isLoggedIn) {
            if (this.returnUrl) {
              this.router.navigate([this.returnUrl]);
            } else {
              this.router.navigate(['/']);
            }
          } else {
            this.feedback = [
              'error',
              'The token you are attempting to use is invalid. Please try again using a new token or log in with a valid username and password.'
            ];
          }
          this.loading = false;
        },
        error => {
          this.feedback = ['error', error];
        }
      );
  }

  login() {
    this.feedback = null;

    if (this.formGroup.valid) {
      this.loading = true;
      this.loginSub = this.authService.login(this.username.value, this.password.value).subscribe(
        isLoggedIn => {
          if (isLoggedIn) {
            if (this.returnUrl) {
              this.router.navigate([this.returnUrl], {
                queryParams: this.route.snapshot.queryParams
              });
            } else {
              this.router.navigate(['/'], { queryParams: this.route.snapshot.queryParams });
            }
          } else {
            this.feedback = [
              'error',
              'The username/password you entered does not match our records.  Please try again.'
            ];
          }
          this.loading = false;
        },
        error => {
          this.feedback = ['error', error];
        }
      );
    } else {
      validateFormFields(this.formGroup);
      this.snackbarService.validationError();
    }
  }

  get faqUrl() {
    return `${this.appConfig.cms.faq}`;
  }

  get contactUsUrl() {
    return `${this.appConfig.cms.contactUs}`;
  }

  get username() {
    return this.formGroup.controls['username'];
  }

  get password() {
    return this.formGroup.controls['password'];
  }
}
