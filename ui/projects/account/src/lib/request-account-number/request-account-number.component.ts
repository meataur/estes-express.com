import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { RequestInfoRequest } from './request-account-number.model';
import { ServiceResponse } from '../service-response.model';
import { RequestAccountNumberService } from './request-account-number.service';
import {
  NgForm,
  FormGroup,
  FormBuilder,
  AbstractControl,
  FormGroupDirective,
  Validators
} from '@angular/forms';
import { Subscription } from 'rxjs';
import {
  MyEstesValidators,
  FormService,
  validateFormFields,
  FeedbackTypes,
  SnackbarService,
  LeftNavigationService,
  PromoService,
  patternValidator,
  RegEx,
  MessageConstants
} from 'common';

@Component({
  selector: 'lib-request-account-number',
  templateUrl: './request-account-number.component.html',
  styleUrls: ['./request-account-number.component.scss']
})
export class RequestAccountNumberComponent implements OnInit, OnDestroy {
  private submitSub: Subscription;
  formGroup: FormGroup;

  // @ViewChild('accountForm') public accountForm: NgForm;
  @ViewChild(FormGroupDirective) myForm: FormGroupDirective;

  feedback: [FeedbackTypes, string | string[]];
  errorMessages: [FeedbackTypes, string | string[]];
  loading: boolean;
  public phoneMask: any;

  constructor(
    public accountService: RequestAccountNumberService,
    private fb: FormBuilder,
    public formService: FormService,
    private snackbar: SnackbarService,
    public promoService: PromoService,
    public leftNavigationService: LeftNavigationService
  ) {
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
  }

  ngOnInit() {
    this.initForm();
    
    this.promoService.setAppId('request-account-number');
    this.promoService.setAppState('any');
    this.leftNavigationService.setNavigation(`My Estes`, `/menus/account`);
  }

  ngOnDestroy() {
    if (this.submitSub) {
      this.submitSub.unsubscribe();
    }
  }

  onSubmit() {
    if (this.formGroup.valid) {
      const payload = this.generatePayload();
      this.feedback = null;
      this.errorMessages = null;

      this.loading = true;
      this.accountService.requestAccountNumber(payload).subscribe(
        (data: ServiceResponse) => {
          if (data && data.message) {
            this.feedback = ['success', data.message];
          }
        },
        err => {
          this.loading = false;
          if (!err) err = `An unexpected error occurred.  Please try again.`;
          this.errorMessages = ['error', err];
        },
        () => (this.loading = false)
      );
    } else {
      validateFormFields(this.formGroup);
      this.snackbar.validationError();
    }
  }

  get addresses() {
    return this.formGroup.controls['addresses'];
  }
  get company() {
    return this.formGroup.controls['company'];
  }
  get email() {
    return this.formGroup.controls['email'];
  }
  get name() {
    return this.formGroup.controls['name'];
  }
  get phone() {
    return this.formGroup.controls['phone'];
  }
  get parentCompany() {
    return this.formGroup.controls['parentCompany'];
  }
  get source() {
    return this.formGroup.controls['source'];
  }

  private initForm() {
    this.formGroup = this.fb.group({
      addresses: ['', [MyEstesValidators.required]],
      company: ['', [MyEstesValidators.required, Validators.maxLength(30)]],
      email: [
        '',
        [MyEstesValidators.required, patternValidator(RegEx.email, MessageConstants.invalidEmail)]
      ],
      name: ['', [MyEstesValidators.required, Validators.maxLength(30)]],
      phone: [
        '',
        [
          MyEstesValidators.required,
          patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone)
        ]
      ],
      parentCompany: ['', [MyEstesValidators.required]]
    });
  }

  private generatePayload(): RequestInfoRequest {
    const payload = new RequestInfoRequest();
    payload.addresses = this.addresses.value;
    payload.company = this.company.value;
    payload.email = this.email.value;
    payload.name = this.name.value;
    payload.phone = this.phone.value;
    payload.parentCompany = this.parentCompany.value;

    return payload;
  }

  resetForm() {
    this.feedback = null;
    this.myForm.resetForm({
      addresses: '',
      company: '',
      email: '',
      name: '',
      phone: '',
      parentCompany: ''
    });
  }
}
