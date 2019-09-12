import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import {
  FormBuilder,
  FormGroup,
  Validators,
  FormControl
} from '@angular/forms';
import { TerminalLookupService } from '../terminal-lookup.service';
import { FormService, RegEx, MessageConstants, textAreaValidator, validateFormFields, SnackbarService, FeedbackTypes, EmailDialogData, MyEstesValidators } from 'common';

@Component({
  templateUrl: './email-coverage-dialog.component.html'
})
export class EmailCoverageDialogComponent implements OnInit {
  loading = false;
  formGroup: FormGroup;
  errorMessage: [FeedbackTypes, string];

  constructor(
    private fb: FormBuilder,
    private snackbarService: SnackbarService,
    public formService: FormService,
    public dialogRef: MatDialogRef<EmailCoverageDialogComponent>,
    public terminalLookupService: TerminalLookupService,
    @Inject(MAT_DIALOG_DATA) public data: EmailDialogData

  ) {}

  ngOnInit() {

    this.formGroup = this.fb.group({
      fileFormat: ['XLS', MyEstesValidators.required],
      email: [
        '',
        [
          MyEstesValidators.required,
          textAreaValidator(RegEx.email, MessageConstants.invalidEmailTextArea)
        ]
      ],
      fileSort: ['CTY', MyEstesValidators.required]
    });

  }

  get fileFormat(): FormControl {
    return this.formGroup.controls['fileFormat'] as FormControl;
  }
  get email(): FormControl {
    return this.formGroup.controls['email'] as FormControl;
  }
  get fileSort(): FormControl {
    return this.formGroup.controls['fileSort'] as FormControl;
  }

  onNoClick() {
    this.dialogRef.close(false);
  }

  onSubmit() {
    this.errorMessage = null;
    if (this.formGroup.valid) {
      this.loading = true;
      this.data.requestPayload['email'] = this.email.value;
      this.data.requestPayload['fileFormat'] = this.fileFormat.value;
      this.data.requestPayload['fileSort'] = this.fileSort.value;
      this.terminalLookupService
        .emailCoverageByTerminal(this.data.requestPayload)
        .subscribe(
          next => {
            this.snackbarService.success(`Next day coverage email sent.`);
            this.dialogRef.close(next);
          },
          err => {
            this.loading = false;
            this.snackbarService.error(`Error: ${err}`);
          }
        );
    } else {
      validateFormFields(this.formGroup);
    }
  }
}
