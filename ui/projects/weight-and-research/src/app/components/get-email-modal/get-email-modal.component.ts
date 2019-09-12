import { Component, OnInit, Inject, OnDestroy } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { WeightAndResearchService } from '../../services/weight-and-research.service';
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
import { WREmailRequest, WREmailResponse } from '../../models';

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
    @Inject(MAT_DIALOG_DATA) public proNumbers: Array<string>,
    private wrService: WeightAndResearchService,
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
      const payload = new WREmailRequest();
      payload.emailAddresses = emailAddresses;
      payload.proNumbers = this.proNumbers;

      this.loading = true;
      this.submitSub = this.wrService.sendEmail(payload).subscribe(
        (next: WREmailResponse) => {
          this.snackbar.success(next.message);
          this.dialogRef.close();
        },
        err => {
          this.loading = false;
          this.snackbar.error(`An error occurred while requesting the email.  Please try again.`);
        },
        () => (this.loading = false)
      );
    } else {
      validateFormFields(this.formGroup);
      this.snackbar.validationError();
    }
  }

  private initForm() {
    this.formGroup = this.fb.group(new EmailForm());
  }
}
