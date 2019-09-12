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
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-get-email-modal',
  templateUrl: './get-email-modal.component.html',
  styleUrls: ['./get-email-modal.component.scss']
})
export class GetEmailModalComponent implements OnInit, OnDestroy {
  loading = false;
  private submitSub: Subscription;
  formGroup: FormGroup;
  feedback: [FeedbackTypes, string | string[]];

  constructor(
    private dialogRef: MatDialogRef<GetEmailModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: RateQuote,
    private rateQuoteService: RateQuoteService,
    private fb: FormBuilder,
    private utilService: UtilService,
    private snackbar: SnackbarService,
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
      const payload = new EmailRequest();
      payload.addresses = emailAddresses;
      payload.action = 'E';
      payload.quoteId = this.data.quoteID;
      payload.app = this.data.app;
      payload.level = this.data.service.id;
      payload.quoteRefNum = +this.data.quoteRefNum;

      this.loading = true;
      this.submitSub = this.rateQuoteService.email(payload).subscribe(
        next => {
          this.snackbar.success(next.message);
          this.dialogRef.close();
        },
        err => {
          this.loading = false;
          let msg = `An unexpected error occurred.  Please try again.`;
          if (err instanceof HttpErrorResponse) {
            if (err.error.message) {
              msg = err.error.message;
            }
          }
          this.feedback = ['error', msg];
        },
        () => (this.loading = false)
      );
    } else {
      validateFormFields(this.formGroup);
    }
  }

  private initForm() {
    this.formGroup = this.fb.group(new EmailForm());
  }
}
