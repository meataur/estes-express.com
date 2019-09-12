
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import {
  FormBuilder,
  FormGroup,
	Validators
} from '@angular/forms';
import {
	FormService,
	MyEstesValidators,
	textAreaValidator,
	RegEx,
	MessageConstants,
	validateFormFields,
	FeedbackTypes,
	SnackbarService
} from 'common';
import { InvoiceInquiryService } from '../invoice-inquiry.service';
import { AgingDetailRequest } from '../models/aging-detail-request.model';
import { AgingEmailRequest } from '../models/aging-email-request.model';

@Component({
  selector: 'app-email-dialog',
  templateUrl: './email-dialog.component.html',
  styleUrls: ['./email-dialog.component.scss']
})
export class EmailDialogComponent implements OnInit {

	formGroup: FormGroup;
	fileFormats: any;
	errorMessages: [FeedbackTypes, string];
	loading: boolean;

	constructor( public dialogRef: MatDialogRef<EmailDialogComponent>,
							 @Inject(MAT_DIALOG_DATA) public data: any,
							 private fb: FormBuilder,
							 public formService: FormService,
							 private snackbarService: SnackbarService,
							 private invoiceInquiryService: InvoiceInquiryService) {}


  ngOnInit() {
		this.fileFormats = [
			{
				viewValue: 'Excel Worksheet (*.xls)',
				value: 'xls'
			},
			{
				viewValue: 'Text/CSV File (*.csv)',
				value: 'csv'
			},
			{
				viewValue: 'Tab Delimited File (*.txt)',
				value: 'txt'
			}
		];
		this.formGroup = this.fb.group({
			emailAddresses: ['', [
				MyEstesValidators.required,
				textAreaValidator(RegEx.email, MessageConstants.invalidEmailTextArea)
			]],
			fileFormat: ['xls', Validators.required],
			submitType: []
		});

		if (this.data.bucket) {
			this.submitType.setValue('bucket');
		} else {
			this.submitType.setValue('pros');
		}
		
	}
	
	get emailAddresses() {
		return this.formGroup.controls['emailAddresses'];
	}
	get fileFormat() {
		return this.formGroup.controls['fileFormat'];
	}
	get submitType() {
		return this.formGroup.controls['submitType'];
	}


	onSubmit() {
		if (this.formGroup.valid && this.data && this.data.selectedPros && this.data.selectedPros.length > 0) {
			//clear error messages and set loading
			this.errorMessages = null;
			this.loading = true;
			//create new email request
			let req = new AgingEmailRequest();
			//set the email addresses
			req.emailAddresses = this.emailAddresses.value.split('\n');
			//set the file format
			req.fileFormat = this.fileFormat.value;
			//if bucket is selected, set bucket property and empty pros, else set pros and empty bucket
			let submitType = this.submitType.value;
		  if (submitType === 'bucket') {
				req.bucket = this.data.bucket;
				req.pros = [];
			} else {
				req.bucket = '';
				let pros = [];
				this.data.selectedPros.forEach(item => {
					pros.push(item.pro);
				});
				req.pros = pros;
			}
		
			this.invoiceInquiryService.email(req).subscribe(data => {
				this.dialogRef.close(data);
				this.loading = false;
			}, err => {
				this.errorMessages = ['error', err];
				this.loading = false;
			});
		} else {
			validateFormFields(this.formGroup);
			this.snackbarService.validationError();
		}
	}

	onNoClick(): void {
		this.dialogRef.close();
	}

}