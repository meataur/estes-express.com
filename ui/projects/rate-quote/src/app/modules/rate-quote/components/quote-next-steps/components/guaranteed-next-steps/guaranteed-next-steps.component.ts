import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { NextStepsChildComponent } from '../../models/quote-details-child-component.interface';
import { RateQuote, BookingRequest, ServiceLevelEnum } from '../../../../models';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {
  FormService,
  validateFormFields,
  FeedbackTypes,
  DialogService,
  SnackbarService
} from 'common';
import { RateQuoteService } from '../../../../services/rate-quote.service';
import { GetEmailModalComponent } from '../../../get-email-modal/get-email-modal.component';
import { switchMap } from 'rxjs/operators';
import { HttpErrorResponse } from '@angular/common/http';
import { EmailAddressPromptModalComponent } from '../../../email-address-prompt-modal/email-address-prompt-modal.component';
import { Subscription } from 'rxjs';
import { DisclosuresModalComponent } from '../../../disclosures-modal/disclosures-modal.component';
import { LtlGuaranteedReserveShipmentComponent } from '../../../ltl-guaranteed-reserve-shipment/ltl-guaranteed-reserve-shipment.component';

@Component({
  selector: 'app-guaranteed-next-steps',
  templateUrl: './guaranteed-next-steps.component.html',
  styleUrls: ['./guaranteed-next-steps.component.scss']
})
export class GuaranteedNextStepsComponent implements OnInit, OnDestroy, NextStepsChildComponent {
  formSub: Subscription;
  formGroup: FormGroup;
  reserved = false;
  loading = false;
  errorFeedback: [FeedbackTypes, string | string[]];
  showReserveShipmentMessage: boolean;

  @Input() quote: RateQuote;

  constructor(
    private fb: FormBuilder,
    public formService: FormService,
    private rateQuoteService: RateQuoteService,
    private dialog: DialogService,
    private snackbar: SnackbarService
  ) {}

  ngOnInit() {
    this.showReserveShipmentMessage =
      this.quote.service.id === ServiceLevelEnum.GuaranteedExclusiveUse ||
      this.quote.service.id === ServiceLevelEnum.GuaranteedLTLStandard10AM ||
      this.quote.service.id === ServiceLevelEnum.GuaranteedLTLStandard12PM ||
      this.quote.service.id === ServiceLevelEnum.GuaranteedLTLStandard5PM ||
      this.quote.service.id === ServiceLevelEnum.GuaranteedVTLStandard ||
      this.quote.service.id === ServiceLevelEnum.GuaranteedVTLEconomy;
    this.initForm();
  }

  ngOnDestroy() {
    if (this.formSub) {
      this.formSub.unsubscribe();
    }
  }

  openDisclosuresModal() {
    this.dialog.prompt(DisclosuresModalComponent, this.quote);
  }

  createBol() {
    this.rateQuoteService.navigateToBillOfLading(this.quote.quoteID);
  }

  requestPickup() {
    this.rateQuoteService.navigateToPickupRequest(this.quote);
  }

  onSubmit() {
    this.errorFeedback = null;
    if (this.formGroup.valid) {
      this.reserve();
    } else {
      validateFormFields(this.formGroup);
    }
  }

  private reserve() {
    // If LTL quote, prompt for email address first.
    if (this.quote.app === 'LTL') {
      this.dialog.prompt(LtlGuaranteedReserveShipmentComponent, this.quote).subscribe(success => {
        if (success === true) {
          this.reserved = true;
        }
      });
    } else {
      this.reserveShipment();
    }
  }

  private reserveShipment(addresses?: Array<string>) {
    this.errorFeedback = null;
    const payload = this.createPayload(addresses);

    this.loading = true;
    this.formSub = this.rateQuoteService.reserveShipment(payload).subscribe(
      next => {
        this.reserved = true;
        if (next.message) {
          this.snackbar.success(`${next.message}`);
        } else {
          this.snackbar.success(`Shipment request received successfully.`);
        }
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

  private createPayload(addresses?: Array<string>): BookingRequest {
    const payload = new BookingRequest();
    payload.action = 'E';
    payload.addresses = [this.quote.contactEmail];
    payload.app = this.quote.app;
    payload.level = this.quote.service.id;
    payload.quoteId = this.quote.quoteID;
    payload.quoteRefNum = +this.quote.quoteRefNum;
    payload.target = 'BOL';

    if (addresses) {
      payload.addresses = addresses;
    }

    return payload;
  }

  private initForm() {
    this.formGroup = this.fb.group({
      acceptedTC: [false, [Validators.requiredTrue]]
    });
  }

  get acceptedTC() {
    return this.formGroup.controls['acceptedTC'];
  }
}
