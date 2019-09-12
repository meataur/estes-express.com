import { Component, OnInit, OnDestroy, OnChanges, SimpleChanges } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { EmailAndFaxNotificationFormService } from '../../services/email-fax-notification-form.service';
import { Subscription, Subject, merge } from 'rxjs';
import { MatSlideToggleChange } from '@angular/material';
import {
  FormService,
  Masks,
  DialogService,
  patternValidator,
  RegEx,
  MessageConstants,
  MyEstesValidators
} from 'common';
import { takeUntil, take } from 'rxjs/operators';
import { ProNumberModalComponent } from '../pro-number-modal/pro-number-modal.component';
import { BolDetailsFormService } from '../../services/bol-details-form.service';
import { PickupRequestFormService } from '../../services/pickup-request-form.service';
import { ShipperConsigneeFormService } from '../../services/shipper-consignee-form.service';
import { BillingInformationFormService } from '../../services/billing-information-form.service';
import { BolSection, BillOfLading } from '../../models';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.scss']
})
export class NotificationsComponent extends BolSection implements OnInit, OnDestroy, OnChanges {
  // private proNumberResolved: boolean;
  infoEmail = {
    shipper: undefined,
    consignee: undefined,
    thirdParty: undefined
  };
  // shipperInfoEmail: string;
  // consigneeInfoEmail: string;
  // thirdPartyInfoEmail: string;
  formGroup: FormGroup;
  formSub: Subscription;
  bolDetailsSub: Subscription;
  showFax = false;
  showEmail = true;
  phoneMask = Masks.phone;
  stop$ = new Subject<boolean>();
  proNumberResolved: boolean;
  pickupRequestSub: Subscription;
  showPickupNotifications: boolean;

  constructor(
    public formService: FormService,
    private emailFaxFormService: EmailAndFaxNotificationFormService,
    private dialogService: DialogService,
    private bolDetailsFormService: BolDetailsFormService,
    private pickupRequestFormService: PickupRequestFormService,
    private shipperConsigneeFormService: ShipperConsigneeFormService,
    private billingInformationService: BillingInformationFormService
  ) {
    super();
  }

  ngOnInit() {
    this.formSub = this.emailFaxFormService.emailFaxForm$.subscribe(fg => {
      this.stop$.next(true);
      this.formGroup = fg;

      this.initTrackingUpdates();
      this.initCheckBoxEmailWatch(
        this.shipperEmail as FormControl,
        this.shipperEmailBolUpdate as FormControl,
        this.shipperEmailTrackingUpdate as FormControl,
        this.shipperEmailShippingLabel as FormControl,
        this.shipperEmailPickupNotice as FormControl,
        'shipper'
      );
      this.initCheckBoxEmailWatch(
        this.consigneeEmail as FormControl,
        this.consigneeEmailBolUpdate as FormControl,
        this.consigneeEmailTrackingUpdate as FormControl,
        this.consigneeEmailShippingLabel as FormControl,
        this.consigneeEmailPickupNotice as FormControl,
        'consignee'
      );
      this.initCheckBoxEmailWatch(
        this.thirdPartyEmail as FormControl,
        this.thirdPartyEmailBolUpdate as FormControl,
        this.thirdPartyEmailTrackingUpdate as FormControl,
        this.thirdPartyEmailShippingLabel as FormControl,
        this.thirdPartyEmailPickupNotice as FormControl,
        'thirdParty'
      );
      this.initCheckBoxEmailWatch(
        this.otherEmail as FormControl,
        this.otherEmailBolUpdate as FormControl,
        this.otherEmailTrackingUpdate as FormControl,
        this.otherEmailShippingLabel as FormControl,
        this.otherEmailPickupNotice as FormControl
      );
    });

    this.shipperConsigneeFormService.shipperEmail$.subscribe(next => {
      this.infoEmail.shipper = next;
      if (this.showPickupNotifications) {
        this.shipperEmail.setValue(next);
      }
    });

    this.shipperConsigneeFormService.consigneeEmail$.subscribe(next => {
      this.infoEmail.consignee = next;
    });

    this.billingInformationService.billToEmail$.subscribe(next => {
      this.infoEmail.thirdParty = next;
    });

    this.bolDetailsSub = this.bolDetailsFormService.validProResolution$.subscribe(next => {
      if (next === true) {
        this.proNumberResolved = true;
      } else if (next === false) {
        this.proNumberResolved = false;
        this.clearTrackingUpdateCheckboxes();
      }
    });

    this.pickupRequestSub = this.pickupRequestFormService.generatePickupRequest$.subscribe(next => {
      if (next === true) {
        this.showPickupNotifications = true;
        this.rejectedNotify.setValue(true);
        this.hasEmail.setValue(true);
        this.shipperEmailPickupNotice.setValue(true);
        this.hasEmail.disable({ emitEvent: false });
      } else if (next === false) {
        this.consigneeEmailPickupNotice.reset(false);
        this.otherEmailPickupNotice.reset(false);
        this.shipperEmailPickupNotice.reset(false);
        this.thirdPartyEmailPickupNotice.reset(false);
        this.acceptedNotify.reset(false);
        this.completedNotify.reset(false);
        this.rejectedNotify.reset(false);
        this.showPickupNotifications = false;
        this.hasEmail.enable();
      }
    });
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.draft && changes.draft.currentValue) {
      this.emailFaxFormService.resetForm(
        (changes.draft.currentValue as BillOfLading).emailAndFaxNotification,
        (changes.draft.currentValue as BillOfLading).pickupDetailInfo
      );
    }
  }

  private initCheckBoxEmailWatch(
    email: FormControl,
    bolUpdate: FormControl,
    trackingUpdate: FormControl,
    shippingLabel: FormControl,
    pickupNotifications: FormControl,
    emailInfoKey?: string
  ) {
    merge(
      bolUpdate.valueChanges,
      trackingUpdate.valueChanges,
      shippingLabel.valueChanges,
      pickupNotifications.valueChanges
    )
      .pipe(takeUntil(this.stop$))
      .subscribe(next => {
        this.evaluateEmailCheckboxes(
          email,
          bolUpdate,
          trackingUpdate,
          shippingLabel,
          pickupNotifications,
          emailInfoKey
        );
      });

    // Check state of form after it is populated from the form service.
    this.evaluateEmailCheckboxes(
      email,
      bolUpdate,
      trackingUpdate,
      shippingLabel,
      pickupNotifications,
      emailInfoKey
    );
  }

  private evaluateEmailCheckboxes(
    email: FormControl,
    bolUpdate: FormControl,
    trackingUpdate: FormControl,
    shippingLabel: FormControl,
    pickupNotifications: FormControl,
    emailInfoKey?: string
  ) {
    if (
      bolUpdate.value === true ||
      trackingUpdate.value === true ||
      shippingLabel.value === true ||
      pickupNotifications.value === true
    ) {
      email.setValidators([
        MyEstesValidators.required,
        Validators.maxLength(50),
        patternValidator(RegEx.email, MessageConstants.invalidEmail)
      ]);

      if (!email.value && !!emailInfoKey) {
        email.setValue(this.infoEmail[emailInfoKey] || '');
        email.updateValueAndValidity();
      }
    } else {
      email.setValidators([
        Validators.maxLength(50),
        patternValidator(RegEx.email, MessageConstants.invalidEmail)
      ]);
    }
    email.updateValueAndValidity();
  }

  private initTrackingUpdates() {
    merge(
      this.thirdPartyEmailTrackingUpdate.valueChanges,
      this.consigneeEmailTrackingUpdate.valueChanges,
      this.shipperEmailTrackingUpdate.valueChanges,
      this.otherEmailTrackingUpdate.valueChanges
    )
      .pipe(takeUntil(this.stop$))
      .subscribe(next => {
        if (next === true && !this.proNumberResolved) {
          this.dialogService.prompt(ProNumberModalComponent, null).subscribe(resp => {
            if (!resp) {
              this.clearTrackingUpdateCheckboxes();
            } else {
              const autoAssignPro = resp[0];
              const proNumber = resp[1];

              this.emailFaxFormService.onProNumberResolved(autoAssignPro, proNumber);
              this.proNumberResolved = true;
            }
          });
        }
      });
  }

  private clearTrackingUpdateCheckboxes() {
    this.thirdPartyEmailTrackingUpdate.setValue(false, { emitEvent: true });
    this.consigneeEmailTrackingUpdate.setValue(false, { emitEvent: true });
    this.shipperEmailTrackingUpdate.setValue(false, { emitEvent: true });
    this.otherEmailTrackingUpdate.setValue(false, { emitEvent: true });
  }

  ngOnDestroy() {
    this.stop$.next(true);
    this.stop$.unsubscribe();
    this.formSub.unsubscribe();
    this.bolDetailsSub.unsubscribe();
  }

  onEmailChange(c: MatSlideToggleChange) {
    if (c.checked) {
      this.showEmail = true;
    } else {
      // this.referenceNumbersService.resetForm();
      this.showEmail = false;
    }
  }

  get hasEmail() {
    return this.formGroup.controls['hasEmail'];
  }
  get hasFax() {
    return this.formGroup.controls['hasFax'];
  }
  get fax() {
    return this.formGroup.controls['fax'];
  }
  get consigneeEmail() {
    return this.formGroup.controls['consigneeEmail'];
  }
  get otherEmail() {
    return this.formGroup.controls['otherEmail'];
  }
  get shipperEmail() {
    return this.formGroup.controls['shipperEmail'];
  }
  get thirdPartyEmail() {
    return this.formGroup.controls['thirdPartyEmail'];
  }
  get consigneeEmailBolUpdate() {
    return this.formGroup.controls['consigneeEmailBolUpdate'];
  }
  get otherEmailBolUpdate() {
    return this.formGroup.controls['otherEmailBolUpdate'];
  }
  get shipperEmailBolUpdate() {
    return this.formGroup.controls['shipperEmailBolUpdate'];
  }
  get thirdPartyEmailBolUpdate() {
    return this.formGroup.controls['thirdPartyEmailBolUpdate'];
  }
  get consigneeEmailTrackingUpdate() {
    return this.formGroup.controls['consigneeEmailTrackingUpdate'];
  }
  get otherEmailTrackingUpdate() {
    return this.formGroup.controls['otherEmailTrackingUpdate'];
  }
  get shipperEmailTrackingUpdate() {
    return this.formGroup.controls['shipperEmailTrackingUpdate'];
  }
  get thirdPartyEmailTrackingUpdate() {
    return this.formGroup.controls['thirdPartyEmailTrackingUpdate'];
  }
  get consigneeEmailPickupNotice() {
    return this.formGroup.controls['consigneeEmailPickupNotice'];
  }
  get otherEmailPickupNotice() {
    return this.formGroup.controls['otherEmailPickupNotice'];
  }
  get shipperEmailPickupNotice() {
    return this.formGroup.controls['shipperEmailPickupNotice'];
  }
  get thirdPartyEmailPickupNotice() {
    return this.formGroup.controls['thirdPartyEmailPickupNotice'];
  }
  get consigneeEmailShippingLabel() {
    return this.formGroup.controls['consigneeEmailShippingLabel'];
  }
  get otherEmailShippingLabel() {
    return this.formGroup.controls['otherEmailShippingLabel'];
  }
  get shipperEmailShippingLabel() {
    return this.formGroup.controls['shipperEmailShippingLabel'];
  }
  get thirdPartyEmailShippingLabel() {
    return this.formGroup.controls['thirdPartyEmailShippingLabel'];
  }
  get consigneeFaxBolUpdate() {
    return this.formGroup.controls['consigneeFaxBolUpdate'];
  }
  get otherFaxBolUpdate() {
    return this.formGroup.controls['otherFaxBolUpdate'];
  }
  get shipperFaxBolUpdate() {
    return this.formGroup.controls['shipperFaxBolUpdate'];
  }
  get thirdPartyFaxBolUpdate() {
    return this.formGroup.controls['thirdPartyFaxBolUpdate'];
  }
  get consigneeFax() {
    return this.formGroup.controls['consigneeFax'];
  }
  get otherFax() {
    return this.formGroup.controls['otherFax'];
  }
  get shipperFax() {
    return this.formGroup.controls['shipperFax'];
  }
  get thirdPartyFax() {
    return this.formGroup.controls['thirdPartyFax'];
  }
  get acceptedNotify() {
    return this.formGroup.controls['acceptedNotify'];
  }
  get completedNotify() {
    return this.formGroup.controls['completedNotify'];
  }
  get rejectedNotify() {
    return this.formGroup.controls['rejectedNotify'];
  }
}
