import { Component, OnInit, Inject, OnDestroy } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { FormBuilder, FormGroup } from '@angular/forms';
import { EmailForm } from '../../models/email-form.model';
import {
  validateFormFields,
  UtilService,
  SnackbarService,
  FormService,
  FeedbackTypes
} from 'common';
import { Subscription } from 'rxjs';
import { RateQuoteService } from '../../services/rate-quote.service';
import { EmailRequest, RateQuote } from '../../models';

@Component({
  selector: 'app-email-address-prompt-modal',
  templateUrl: './email-address-prompt-modal.component.html',
  styleUrls: ['./email-address-prompt-modal.component.scss']
})
export class EmailAddressPromptModalComponent implements OnInit, OnDestroy {
  private submitSub: Subscription;
  formGroup: FormGroup;

  constructor(
    private dialogRef: MatDialogRef<EmailAddressPromptModalComponent>,
    private fb: FormBuilder,
    private utilService: UtilService,
    public formService: FormService
  ) {
    this.initForm();
  }

  ngOnInit() {}

  ngOnDestroy() {
    if (this.submitSub) {
      this.submitSub.unsubscribe();
    }
  }

  get emailAddresses() {
    return this.formGroup.controls['emailAddresses'];
  }

  onNoClick() {
    this.dialogRef.close();
  }

  onSubmit() {
    if (this.formGroup.valid) {
      const emailAddresses = this.utilService.extractTextAreaLineItems(this.emailAddresses.value);

      this.dialogRef.close(emailAddresses);
    } else {
      validateFormFields(this.formGroup);
    }
  }

  private initForm() {
    this.formGroup = this.fb.group(new EmailForm());
  }
}
