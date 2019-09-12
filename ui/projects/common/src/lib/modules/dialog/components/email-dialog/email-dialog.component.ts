import { Component, OnInit, Inject } from '@angular/core';
import { EmailDialogData } from '../../models';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import {
  FormBuilder,
  FormGroup,
  Validators,
  FormControl,
  AbstractControl
} from '@angular/forms';
import { EmailService } from '../../services/email.service';
import { FormService } from '../../../../util/services/form.service';
import { RegEx } from '../../../../util/regular-expressions/regular-expressions';
import { MessageConstants } from '../../../../util/messages/messages';
import { textAreaValidator } from '../../../../util/validators/text-area-validator';
import { validateFormFields } from '../../../../util/validate-form-fields';
import { SnackbarService } from '../../../snackbar/public_api';
import { FeedbackTypes } from '../../../components/models';
import { MyEstesValidators } from '../../../../util/validators/myestes-validators.validator';

@Component({
  selector: 'lib-email-dialog',
  templateUrl: './email-dialog.component.html',
  styleUrls: ['./email-dialog.component.scss'],
  providers: [EmailService]
})
export class EmailDialogComponent implements OnInit {
  loading = false;
  formGroup: FormGroup;
  errorMessage: [FeedbackTypes, string];

  constructor(
    private emailService: EmailService,
    private fb: FormBuilder,
    private snackbarService: SnackbarService,
    public formService: FormService,
    public dialogRef: MatDialogRef<EmailDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: EmailDialogData
  ) {}

  ngOnInit() {
    if (!this.data.emailKey) {
      this.data.emailKey = 'email';
    }
    if (!this.data.formatKey) {
      this.data.formatKey = 'fileFormat';
    }
    if (!this.data.formats) {
      this.data.formats = [
        {
          viewValue: 'Excel Worksheet File (*.xls)',
          value: 'XLS'
        },
        {
          viewValue: 'Text/CSV File (*.csv)',
          value: 'CSV'
        },
        {
          viewValue: 'Text Delimited File (*.txt)',
          value: 'TXT'
        }
      ]
    }
    this.formGroup = this.fb.group({
      [this.data.formatKey]: [this.data.formats[0].value, MyEstesValidators.required],
      [this.data.emailKey]: [
        '',
        [
          MyEstesValidators.required,
          textAreaValidator(RegEx.email, MessageConstants.invalidEmailTextArea)
        ]
      ]
    });

  }

  get fileFormat(): FormControl {
    return this.formGroup.controls[this.data.formatKey] as FormControl;
  }
  get email(): FormControl {
    return this.formGroup.controls[this.data.emailKey] as FormControl;
  }

  onNoClick() {
    this.dialogRef.close(false);
  }

  onSubmit() {
    this.errorMessage = null;
    if (this.formGroup.valid) {
      this.loading = true;
      this.data.requestPayload[this.data.emailKey] = this.email.value;
      this.data.requestPayload[this.data.formatKey] = this.fileFormat.value;
      this.emailService
        .sendEmailRequest(this.data.url, this.data.requestPayload)
        .subscribe(
          next => {
            if (next.errorCode && next.errorCode.toUpperCase() === 'ERROR') {
              this.errorMessage = ['error', next.message];
            } else {
              let successMessage = this.data.successMessage || next.message || '';
              this.snackbarService.success(successMessage);
              this.dialogRef.close(next);
            }
          },
          err => {
            this.loading = false;
            this.snackbarService.error(`Error: ${err.error.message}`);
          }
        );
    } else {
      validateFormFields(this.formGroup);
      this.snackbarService.validationError();
    }
  }
}
