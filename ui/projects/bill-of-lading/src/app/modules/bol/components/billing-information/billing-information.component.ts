import {
  Component,
  OnInit,
  ViewChild,
  SimpleChanges,
  OnChanges,
  OnDestroy,
  Input,
  Output,
  EventEmitter
} from '@angular/core';
import { FormBuilder, FormGroup, Validators, ValidationErrors } from '@angular/forms';
import { AddressDetailsComponent } from '../address-details/address-details.component';
import {
  BillToOptionValueEnum,
  FeeEnum,
  PaymentTypeEnum,
  CodRemitToFeeEnum,
  AddressInformation,
  AddressSourceEnum,
  BolSection,
  BillOfLading
} from '../../models';
import { FormService, RegEx, MessageConstants, patternValidator } from 'common';
import { ShipperConsigneeFormService } from '../../services/shipper-consignee-form.service';
import { BillingInformationFormService } from '../../services/billing-information-form.service';
import { Subscription, Observable } from 'rxjs';

@Component({
  selector: 'app-billing-information',
  templateUrl: './billing-information.component.html',
  styleUrls: ['./billing-information.component.scss']
})
export class BillingInformationComponent extends BolSection
  implements OnInit, OnChanges, OnDestroy {
  formSub: Subscription;
  showBillToInfo = false;
  formGroup: FormGroup;
  selectedAddressSource: AddressSourceEnum;
  billToOptions: Array<{ value: string; text: string }> = Object.keys(BillToOptionValueEnum).map(
    key => ({
      value: key,
      text: BillToOptionValueEnum[key]
    })
  );
  feeOptions: Array<{ value: string; text: string }> = Object.keys(FeeEnum).map(key => ({
    value: key,
    text: FeeEnum[key]
  }));
  paymentTypes: Array<{ value: string; text: string }> = Object.keys(PaymentTypeEnum).map(key => ({
    value: key,
    text: PaymentTypeEnum[key]
  }));
  codRemitToFeeOptions: Array<{ value: string; text: string }> = Object.keys(CodRemitToFeeEnum).map(
    key => ({
      value: key,
      text: CodRemitToFeeEnum[key]
    })
  );
  addressSources: Array<{ value: string; text: string }> = Object.keys(AddressSourceEnum).map(
    key => ({
      value: key,
      text: AddressSourceEnum[key]
    })
  );

  constructor(
    public formService: FormService,
    public shipperConsigneeFormService: ShipperConsigneeFormService,
    private billingInfoFormService: BillingInformationFormService
  ) {
    super();
  }

  getFormValidationErrors(fg: FormGroup): any[] {
    let errorList = [];
    for (const key of Object.keys(fg.controls)) {
      const controlErrors: ValidationErrors = fg.get(key).errors;
      if (controlErrors != null) {
        Object.keys(controlErrors).forEach(keyError => {
          errorList.push({
            keyControl: key,
            keyError: keyError,
            errValue: controlErrors[keyError]
          });
        });
      }
      if (fg.get(key) instanceof FormGroup) {
        errorList = errorList.concat(this.getFormValidationErrors(fg.get(key) as FormGroup));
      }
    }
    Object.keys(fg.controls).forEach(key => {});

    return errorList;
  }

  ngOnInit() {
    this.formSub = this.billingInfoFormService.billingInfoForm$.subscribe(fg => {
      this.formGroup = fg;
    });
  }

  ngOnDestroy() {
    if (this.formSub) {
      this.formSub.unsubscribe();
    }
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.draft && changes.draft.currentValue) {
      this.billingInfoFormService.resetForm(
        (changes.draft.currentValue as BillOfLading).billingInfo
      );
    }
  }

  get billTo() {
    return this.formGroup.controls['billTo'];
  }
  get fee() {
    return this.formGroup.controls['fee'];
  }
  get codRemitTo() {
    return this.formGroup.controls['codRemitTo'];
  }
  get paymentType() {
    return this.formGroup.controls['paymentType'];
  }
  get codRemitToFee() {
    return this.formGroup.controls['codRemitToFee'];
  }
  get amount() {
    return this.formGroup.controls['amount'];
  }
  get billingAddressInfo() {
    return this.formGroup.controls['billingAddressInfo'] as FormGroup;
  }
  get codRemitToInfo() {
    return this.formGroup.controls['codRemitToInfo'] as FormGroup;
  }
}
