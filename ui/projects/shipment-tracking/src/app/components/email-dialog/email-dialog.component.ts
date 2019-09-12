import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatTableDataSource } from '@angular/material';
import { EmailStatusRequest } from '../../models/email-status-request.model';
import { EmailStatusResponse } from '../../models/email-status-response.model';
import { ShipmentTrackingService } from '../../shipment-tracking.service';
import { SnackbarService, textAreaValidator, MessageConstants, RegEx, FormService } from 'common';
import { FormControl, Validators } from '@angular/forms';

@Component({
	selector: 'app-email-dialog',
	templateUrl: './email-dialog.component.html'
})
export class EmailDialogComponent implements OnInit {

	proList: any;
	emailStatusRequest: EmailStatusRequest;
	emailAddresses: FormControl;
	constructor(
		public dialogRef: MatDialogRef<EmailDialogComponent>,
		public shipmentTrackingService: ShipmentTrackingService,
		public formService: FormService,
		private snackbarService: SnackbarService,
		@Inject(MAT_DIALOG_DATA) public data: any) {

		this.emailStatusRequest = new EmailStatusRequest();
		this.emailStatusRequest.session = data.session;
		if (data.sharedData._selected) {
			this.proList = data.sharedData._selected;
		}

	}
	setProNumbers(checked: any, pro: string) {
		if (!checked) {
			this.emailStatusRequest.pros.push(pro);
		} else {
			const i = this.emailStatusRequest.pros.indexOf(pro);
			if (i >= 0) {
				this.emailStatusRequest.pros.splice(i, 1);
			}
		}
	}

	postEmailRequest(valid: boolean) {
		this.emailStatusRequest.addresses = this.emailAddresses.value;
		if (valid && this.emailAddresses.valid && this.emailStatusRequest.pros.length > 0) {
			this.shipmentTrackingService.postStatusEmailRequest(this.emailStatusRequest)
			.subscribe((data: EmailStatusResponse) => {
				if (data) {
					this.dialogRef.close(data);
				}
			});
		} else {
			this.snackbarService.validationError();
		}
	}

	onNoClick(): void {
		this.dialogRef.close();
	}

	ngOnInit() {
		this.emailAddresses = new FormControl('', [textAreaValidator(RegEx.email, MessageConstants.invalidEmailTextArea), Validators.required]);
		if (this.proList && this.proList.length > 0) {
			for (const i in this.proList) {
				if (this.proList.hasOwnProperty(i)) {
					this.setProNumbers(false, this.proList[i].pro);
				}
			}
		}
	}
}
