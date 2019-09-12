import { Component, OnInit, ViewChild } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  FormArray,
  FormControl,
  FormGroupDirective
} from '@angular/forms';
import {
  textAreaValidator,
  patternValidator,
  RegEx,
  MessageConstants,
  FormService,
  validateFormFields,
  Masks,
  SnackbarService,
  atLeastOneValidator,
  FeedbackTypes,
  MyEstesValidators,
  AuthService,
  PromoService,
  LeftNavigationService
} from 'common';
import { Router } from '@angular/router';
import { ImageRetrievalRequest } from '../../models';
import { DocumentRetrievalService } from '../../services/document-retrieval.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-document-retrieval',
  templateUrl: './document-retrieval.component.html',
  styleUrls: ['./document-retrieval.component.scss']
})
export class DocumentRetrievalComponent implements OnInit {
  @ViewChild(FormGroupDirective) myForm;
  loading = false;
  formGroup: FormGroup;
  errorMessage: [FeedbackTypes, string];
  docTypes = [
    { value: 'BOL', text: 'Bill of Lading (BOL)' },
    { value: 'DR', text: 'Delivery Receipt (DR)' }
  ];
  mask = Masks.phone;
  // tslint:disable:max-line-length
  trackingNumberTooltip = `Enter the tracking number for your shipment. The following are valid tracking number options: PRO, BOL Number, Interline PRO and Purchase Order Number.`;
  destinationZipTooltip = `Enter the 5-digit United States ZIP Code or 6-character alphanumeric Canadian postal code.`;
  docTypesTooltip = `This application supports the retrieval of the Bill of Lading and Delivery Receipt for a single shipment. To request multiple documents at once or for access to Weight & Research results, simply sign up or log in to My Estes.`;
  deliveryMethodTooltip = `This application supports delivery via fax or email of the Bill of Lading and Delivery Receipt for a single shipment. To view and/or request multiple documents, simply sign up or log in to My Estes.`;
  emailTooltip = `Enter up to 5 email addresses (one per line). At least one email address is required.`;
  faxNumberTooltip = `Enter a domestic fax number in the following format: (XXX) XXX-XXXX.`;
  // tslint:enable:max-line-length

  constructor(
    private fb: FormBuilder,
    public formService: FormService,
    private docService: DocumentRetrievalService,
    private snackbarService: SnackbarService,
    private router: Router,
    private authService: AuthService,
    private promoService: PromoService,
	private leftNavigationService: LeftNavigationService
  ) {}

  ngOnInit() {
    this.formGroup = this.fb.group({
      trackingNumber: ['', [MyEstesValidators.required, Validators.maxLength(25)]],
      destinationZip: [
        '',
        [
          MyEstesValidators.required,
          Validators.minLength(5),
          Validators.maxLength(6)
        ]
      ],
      docType: new FormArray([new FormControl(false), new FormControl(false)], {
        validators: atLeastOneValidator()
      }),
      emailOrFax: ['email', MyEstesValidators.required],
      email: [
        '',
        [
          MyEstesValidators.required,
          Validators.maxLength(400),
          textAreaValidator(RegEx.email, MessageConstants.invalidEmailTextArea, 5)
        ]
      ],
      faxNumber: ['', patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone)],
      attention: ['', Validators.maxLength(200)]
    });

    this.emailOrFax.valueChanges.subscribe(next => {
      next = (next as string).toUpperCase();
      if (next === 'EMAIL') {
        this.faxNumber.clearValidators();
        this.faxNumber.reset('');
        this.faxNumber.updateValueAndValidity();
        this.attention.clearValidators();
        this.attention.reset('');
        this.attention.updateValueAndValidity();

        this.email.setValidators([
          MyEstesValidators.required,
          Validators.maxLength(400),
          textAreaValidator(RegEx.email, MessageConstants.invalidEmailTextArea, 5)
        ]);
      } else if (next === 'FAX') {
        this.email.clearValidators();
        this.email.reset('');
        this.email.updateValueAndValidity();

        this.faxNumber.setValidators([MyEstesValidators.required, patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone)]);
        this.attention.setValidators(Validators.maxLength(200));
      }
    });

    this.leftNavigationService.setNavigation(`Manage`, `/manage`);
    this.promoService.setAppId('document-retrieval');
    this.promoService.setAppState('unauthenticated');
    

 	this.authService.authStateSet.subscribe((authState: string) => {
      this.promoService.setAppId('document-retrieval');
      this.promoService.setAppState(authState);
    });
  }

  get trackingNumber() {
    return this.formGroup.controls['trackingNumber'];
  }
  get destinationZip() {
    return this.formGroup.controls['destinationZip'];
  }
  get docType(): FormArray {
    return this.formGroup.controls['docType'] as FormArray;
  }
  get emailOrFax() {
    return this.formGroup.controls['emailOrFax'];
  }
  get email() {
    return this.formGroup.controls['email'];
  }
  get faxNumber() {
    return this.formGroup.controls['faxNumber'];
  }
  get attention() {
    return this.formGroup.controls['attention'];
  }

  resetForm() {
    this.myForm.resetForm({
      trackingNumber: '',
      destinationZip: '',
      docType: new FormArray([new FormControl(false), new FormControl(false)]),
      emailOrFax: 'email',
      email: '',
      faxNumber: '',
      attention: ''
    });
    this.errorMessage = null;
  }

  onSubmit() {
    this.errorMessage = null;
    if (this.formGroup.valid) {
      const payload = new ImageRetrievalRequest();
      payload.deliveryMethod = this.emailOrFax.value;
      payload.destZip = this.destinationZip.value;
      payload.trackingNum = this.trackingNumber.value;

      let docTypesReqString = '';
      for (let i = 0; i < this.docType.controls.length; i++) {
        if (this.docType.controls[i].value === true) {
          if (docTypesReqString.length > 0) {
            docTypesReqString += `,${this.docTypes[i].value}`;
          } else {
            docTypesReqString += this.docTypes[i].value;
          }
        }
      }

      payload.documentTypes = docTypesReqString;

      switch ((this.emailOrFax.value as string).toUpperCase()) {
        case 'EMAIL':
          // Replace returns with commas, per the swagger docs.
          payload.email = (this.email.value as string).replace(RegEx.textAreaNewLines, ',');
          break;
        case 'FAX':
          payload.fax = this.faxNumber.value;
          payload.faxAttention = this.attention.value;
          break;
      }

      this.loading = true;
      this.docService.sendDocumentRetrievalRequest(payload).subscribe(
        res => {
          if (res && res.hasOwnProperty('message') && res.message) {
            this.snackbarService.success(res.message);
          } else {
            this.snackbarService.success('Your document request has been processed.');
          }
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
