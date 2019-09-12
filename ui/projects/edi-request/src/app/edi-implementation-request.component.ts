import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import {
  FormGroup,
  FormArray,
  FormBuilder,
  Validators,
  FormGroupDirective
} from '@angular/forms';
import {animate, state, style, transition, trigger} from '@angular/animations';

import {
  FeedbackTypes,
  validateFormFields,
  FormService,
  SnackbarService,
  patternValidator,
  RegEx,
  MessageConstants,
  Masks,
  MyEstesValidators,
} from 'common';

import { EdiRequestService } from './edi-request.service';

@Component({
  selector: 'app-edi-implementation-request',
  templateUrl: './edi-implementation-request.component.html',
  styleUrls: ['./edi-implementation-request.component.scss'],
  animations: [
    trigger('expand', [
      state('collapsed, void', style({ height: '0px', minHeight: '0', display: 'none', border: 'none', overflow: 'hidden' })),
      state('expanded', style({ height: '*', overflow: 'hidden' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
      transition('expanded <=> void', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)'))
    ])
  ]
})
export class EdiImplementationRequestComponent implements OnInit {
  
  @ViewChild(FormGroupDirective) myForm;
  @ViewChild('myForm') myFormElement: ElementRef;

  expandAccountsPayableContact: boolean;
  expandBusinessContact: boolean;
  expandTrafficContact: boolean;
  expandAdditionalContact: boolean;
  contactFormConfigList: any[];
  loading: boolean;
  feedback: [FeedbackTypes, string];

  formGroup: FormGroup;
  phoneMask = Masks.phone;

  kleinschmidtChecked: boolean;
  otherChecked: boolean;


  constructor(
    private fb: FormBuilder,
    private formService: FormService,
    private ediRequestService: EdiRequestService,
    private snackbarService: SnackbarService
  ) { }

  ngOnInit() {

    this.initFormGroup();

    this.contactFormConfigList = [{
      title: 'Secondary EDI Contact',
      key: 'secondaryEdiContact',
      expandable: false
    },
    {
      title: 'Accounts Payable Contact',
      key: 'accountsPayableContact',
      expandable: true
    },
    {
      title: 'Business Contact',
      key: 'businessContact',
      expandable: true
    },
    {
      title: 'Traffic Contact',
      key: 'trafficContact',
      expandable: true
    },
    {
      title: 'Additional Contact',
      key: 'additionalContact',
      expandable: true
    }];
  }

  get isL2L() {
    return this.formGroup.controls['isL2L'];
  }
  get ediBillingType() {
    return this.formGroup.controls['ediBillingType'];
  }
  get formComments() {
    return this.formGroup.controls['formComments'];
  }
  get estesAccountNumber(): FormArray {
    return this.formGroup.controls['estesAccountNumber'] as FormArray;
  }
  get ediAddressLocation(): FormArray {
    return this.formGroup.controls['ediAddressLocation'] as FormArray;
  }
  get thirdPartyNetworks() {
    return this.formGroup.controls['thirdPartyNetworks'];
  }
  get otherThirdPartyCheck() {
    return this.formGroup.controls['otherThirdPartyCheck'];
  }
  get otherThirdPartyValue() {
    return this.formGroup.controls['otherThirdPartyValue'];
  }
  get ediHeaderType() {
    return this.formGroup.controls['ediHeaderType'];
  }
  get ftpServerAddress() {
    return this.formGroup.controls['ftpServerAddress'];
  }
  get ftpDirectoryPath() {
    return this.formGroup.controls['ftpDirectoryPath'];
  }
  get ftpUsername() {
    return this.formGroup.controls['ftpUsername'];
  }
  get ftpPassword() {
    return this.formGroup.controls['ftpPassword'];
  }
  get prodBg01Password() {
    return this.formGroup.controls['prodBg01Password']
  }
  get testBg01Password() {
    return this.formGroup.controls['testBg01Password'];
  }
  get prodBg03ReceiverId() {
    return this.formGroup.controls['prodBg03ReceiverId']
  }
  get testBg03ReceiverId() {
    return this.formGroup.controls['testBg03ReceiverId']
  }
  get prodISAQualifier() {
    return this.formGroup.controls['prodISAQualifier'];
  }
  get testISAQualifier() {
    return this.formGroup.controls['testISAQualifier'];
  }
  get prodISAReceiverId() {
    return this.formGroup.controls['prodISAReceiverId'];
  }
  get testISAReceiverId() {
    return this.formGroup.controls['testISAReceiverId'];
  }
  get prodGSId() {
    return this.formGroup.controls['prodGSId'];
  }
  get testGSId() {
    return this.formGroup.controls['testGSId'];
  }
  get tdccAnsiVersion() {
    return this.formGroup.controls['tdccAnsiVersion'];
  }
  get fillersName() {
    return this.formGroup.controls['fillersName'];
  }
  get fillersPhone() {
    return this.formGroup.controls['fillersPhone'];
  }
  get fillersExtn() {
    return this.formGroup.controls['fillersExtn'];
  }
  get fillersEmail() {
    return this.formGroup.controls['fillersEmail'];
  }
  get sendCopyInMail() {
    return this.formGroup.controls['sendCopyInMail'];
  }

  initFormGroup() {
    this.formGroup = this.fb.group({
      isL2L: ['no', MyEstesValidators.required],
      customer: this.fb.group({
        name: ['', [MyEstesValidators.required, Validators.maxLength(60)]],
        address: ['', [MyEstesValidators.required, Validators.maxLength(50)]],
        city: ['', [MyEstesValidators.required, Validators.maxLength(50)]],
        state: ['', [MyEstesValidators.required, Validators.maxLength(2)]],
        zip: ['', [MyEstesValidators.required, Validators.maxLength(6)]],
        phone: ['', [MyEstesValidators.required, patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone)]],
        extension: ['', Validators.maxLength(6)],
        fax: ['', [patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone)]],
        website: ['', Validators.maxLength(100)],
        freightPaymentAgency: ['', Validators.maxLength(100)],
        freightManagementCompany: ['', Validators.maxLength(100)]
      }),
      primaryEdiContact: this.fb.group({
        name: ['', [MyEstesValidators.required, Validators.maxLength(60)]],
        title: ['', Validators.maxLength(50)],
        email: ['', [MyEstesValidators.required, patternValidator(RegEx.email, MessageConstants.invalidEmail)]],
        phone: ['', [MyEstesValidators.required, patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone)]],
        extension: ['', Validators.maxLength(6)],
        fax: ['', [patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone)]]
      }),
      secondaryEdiContact: this.generateContact(),
      accountsPayableContact: this.generateContact(),
      businessContact: this.generateContact(),
      trafficContact: this.generateContact(),
      additionalContact: this.generateContact(),
      ediBillingType: [''],
      form204: [false],
      form210: [false],
      form211: [false],
      form212: [false],
      form214: [false],
      form990: [false],
      formOther: [false],
      autoAccept: ['N'],
      use211AsPickupRequest: ['N'],
      sendReserveProsInBOL06: ['N'],
      formComments: ['', Validators.maxLength(100)],
      has214ReportCard: ['N'],
      estesAccountNumber: this.fb.array([
        this.fb.control('', [Validators.maxLength(7)])
      ]),
      ediAddressLocation: this.fb.array([
        this.fb.control('', Validators.maxLength(100))
      ]),
      willReservedBillsUsed: ['N'],
      thirdPartyNetworks: [false],
      otherThirdPartyCheck: [false],
      otherThirdPartyValue: [''],
      ediHeaderType: [''],
      ftpServerAddress: ['', Validators.maxLength(30)],
      ftpDirectoryPath: ['', Validators.maxLength(30)],
      ftpUsername: ['', Validators.maxLength(30)],
      ftpPassword: ['', Validators.maxLength(30)],
      prodBg01Password: ['', Validators.maxLength(30)],
      testBg01Password: ['', Validators.maxLength(30)],
      prodBg03ReceiverId: ['', Validators.maxLength(30)],
      testBg03ReceiverId: ['', Validators.maxLength(30)],
      prodISAQualifier: ['', Validators.maxLength(30)],
      testISAQualifier: ['', Validators.maxLength(30)],
      prodISAReceiverId: ['', Validators.maxLength(30)],
      testISAReceiverId: ['', Validators.maxLength(30)],
      prodGSId: ['', Validators.maxLength(30)],
      testGSId: ['', Validators.maxLength(30)],
      tdccAnsiVersion: ['', Validators.maxLength(100)],
      fillersName: ['', Validators.maxLength(60)],
      fillersPhone: ['', patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone)],
      fillersExtn: ['', Validators.maxLength(6)],
      fillersEmail: ['', [patternValidator(RegEx.email, MessageConstants.invalidEmail)]],
      sendCopyInMail: ['N']
    });
  }

  addAccountNumberControl() {
    this.estesAccountNumber.push(this.fb.control('', Validators.maxLength(7)));
  }

  addAddressControl() {
    this.ediAddressLocation.push(this.fb.control('', Validators.maxLength(100)));
  }

  toggleThirdPartyCheckbox(checkboxKey: string, checkBoolean: boolean) {
    this.thirdPartyNetworks.setValue('N');
    this.otherThirdPartyCheck.setValue('N');
    this.kleinschmidtChecked = false;
    this.otherChecked = false;
    this.formGroup.controls[checkboxKey].setValue('Y');
    checkBoolean = true;
  }



  generateContact(): FormGroup {
    return this.fb.group({
      name: ['', Validators.maxLength(60)],
      title: ['', Validators.maxLength(50)],
      email: ['', [patternValidator(RegEx.email, MessageConstants.invalidEmail)]],
      phone: ['', [patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone)]],
      extension: ['', Validators.maxLength(6)],
      fax: ['', [patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone)]]
    });
  }

  usePrimaryContact(checked: boolean, modelName: string) {
    if (checked) {
      this.formGroup.get(modelName + '.name').setValue(this.formGroup.get('primaryEdiContact.name').value);
      this.formGroup.get(modelName + '.title').setValue(this.formGroup.get('primaryEdiContact.title').value);
      this.formGroup.get(modelName + '.email').setValue(this.formGroup.get('primaryEdiContact.email').value);
      this.formGroup.get(modelName + '.phone').setValue(this.formGroup.get('primaryEdiContact.phone').value);
      this.formGroup.get(modelName + '.extension').setValue(this.formGroup.get('primaryEdiContact.extension').value);
      this.formGroup.get(modelName + '.fax').setValue(this.formGroup.get('primaryEdiContact.fax').value);
    } else {
      this.formGroup.get(modelName + '.name').setValue('');
      this.formGroup.get(modelName + '.title').setValue('');
      this.formGroup.get(modelName + '.email').setValue('');
      this.formGroup.get(modelName + '.phone').setValue('');
      this.formGroup.get(modelName + '.extension').setValue('');
      this.formGroup.get(modelName + '.fax').setValue('');
    }
  }
  printForm() {
    let printContents, popupWin;
    printContents = document.getElementById('print-section').innerHTML;
    popupWin = window.open('', '_blank', 'top=0,left=0,height=100%,width=auto');
    popupWin.document.open();
    popupWin.document.write(`
      <html>
        <head>
          <title>Print tab</title>
          <style>
            svg {
              height: 0;
            }
          </style>
        </head>
    <body onload="window.print();window.close()">${printContents}</body>
      </html>`
    );
    popupWin.document.close();
  }

  sendCopyInMailClick(checked: boolean) {
    if (checked) {
      this.sendCopyInMail.setValue('Y');
      this.fillersEmail.setValidators([MyEstesValidators.required, patternValidator(RegEx.email, MessageConstants.invalidEmail)]);
    } else {
      this.sendCopyInMail.setValue('N');
      this.fillersEmail.setValidators([patternValidator(RegEx.email, MessageConstants.invalidEmail)]);
    }
    this.fillersEmail.updateValueAndValidity();
  }

  onSubmit() {
    if (this.formGroup.valid) {
      this.feedback = null;
      this.loading = true;
      let request = this.formGroup.value;
      request.thirdPartyNetworks = this.thirdPartyNetworks.value ? 'Y' : 'N';
      request.otherThirdPartyCheck = this.otherThirdPartyCheck.value ? 'Y' : 'N';
      this.ediRequestService.save(request)
        .subscribe(data => {
          this.loading = false;
          this.feedback = ['success', data.message];
          this.initFormGroup();
          this.myForm.resetForm();
        }, err => {
          this.feedback = ['error', err];
          this.loading = false;
        })
    } else {
      this.feedback = null;
      this.snackbarService.validationError();
      validateFormFields(this.formGroup);
      this.scrollToError();     
    }
  }
 
 scrollToError(): void {
    const firstElementWithError = this.myFormElement.nativeElement.querySelector('.mat-form-field.ng-invalid');
    if(firstElementWithError) { 
      firstElementWithError.scrollIntoView({ behavior: 'smooth', block: 'center' });
     }
 }
 
}
