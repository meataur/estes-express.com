import { Component, OnInit, Input, Inject } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { RateQuote, BookingRequest } from '../../models';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import {
  MyEstesValidators,
  patternValidator,
  RegEx,
  MessageConstants,
  FeedbackTypes,
  SnackbarService,
  validateFormFields,
  FormService
} from 'common';
import { RateQuoteService } from '../../services/rate-quote.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-ltl-guaranteed-reserve-shipment',
  templateUrl: './ltl-guaranteed-reserve-shipment.component.html',
  styleUrls: ['./ltl-guaranteed-reserve-shipment.component.scss']
})
export class LtlGuaranteedReserveShipmentComponent implements OnInit {
  formGroup: FormGroup;
  errorFeedback: [FeedbackTypes, string | string[]];
  loading: boolean;
  formSub: any;

  constructor(
    private dialogRef: MatDialogRef<LtlGuaranteedReserveShipmentComponent>,
    @Inject(MAT_DIALOG_DATA) public data: RateQuote,
    private snackbar: SnackbarService,
    private rateQuoteService: RateQuoteService,
    private fb: FormBuilder,
    public formService: FormService
  ) {}

  ngOnInit() {
    this.initForm();
  }

  onSubmit() {
    if (this.formGroup.valid) {
      this.reserveShipment();
    } else {
      validateFormFields(this.formGroup);
    }
  }

  private reserveShipment() {
    this.errorFeedback = null;
    const payload = this.createPayload();

    this.loading = true;
    this.formSub = this.rateQuoteService.reserveShipment(payload).subscribe(
      next => {
        if (next.message) {
          this.snackbar.success(`${next.message}`);
        } else {
          this.snackbar.success(`Shipment request received successfully.`);
        }
        this.dialogRef.close(true);
      },
      err => {
        this.loading = false;
        if (err instanceof HttpErrorResponse) {
          if (err.error.message) {
            this.snackbar.error(`${err.error.message}`);
          } else {
            this.snackbar.error(
              `An error occurred while reserving the shipment.  Please try again.`
            );
          }
        }
      },
      () => (this.loading = false)
    );
  }

  private createPayload(): BookingRequest {
    const payload = new BookingRequest();
    payload.action = 'E';
    payload.addresses = [this.email.value];
    payload.app = this.data.app;
    payload.level = this.data.service.id;
    payload.quoteId = this.data.quoteID;
    payload.quoteRefNum = +this.data.quoteRefNum;
    payload.target = 'BOL';

    return payload;
  }

  private initForm() {
    this.formGroup = this.fb.group({
      email: [
        '',
        [MyEstesValidators.required, patternValidator(RegEx.email, MessageConstants.invalidEmail)]
      ]
    });
  }

  get email() {
    return this.formGroup.controls['email'];
  }
}
