import { FormControl, Validators } from '@angular/forms';
import { EmailAndFaxNotification } from './email-and-fax-notification.model';
import { RegEx, patternValidator, MessageConstants, MyEstesFormatters } from 'common';
import { PickupDetailInfo } from './pickup-detail-info.model';
import { purePipeDef } from '@angular/core/src/view';

export class EmailAndFaxNotificationForm {
  hasEmail = new FormControl(true);
  hasFax = new FormControl(false);
  fax = new FormControl('');
  consigneeEmail = new FormControl('');
  otherEmail = new FormControl('');
  shipperEmail = new FormControl('');
  thirdPartyEmail = new FormControl('');
  consigneeEmailBolUpdate = new FormControl(false);
  otherEmailBolUpdate = new FormControl(false);
  shipperEmailBolUpdate = new FormControl(false);
  thirdPartyEmailBolUpdate = new FormControl(false);
  consigneeEmailTrackingUpdate = new FormControl(false);
  otherEmailTrackingUpdate = new FormControl(false);
  shipperEmailTrackingUpdate = new FormControl(false);
  thirdPartyEmailTrackingUpdate = new FormControl(false);
  consigneeEmailPickupNotice = new FormControl(false);
  otherEmailPickupNotice = new FormControl(false);
  shipperEmailPickupNotice = new FormControl(false);
  thirdPartyEmailPickupNotice = new FormControl(false);
  consigneeEmailShippingLabel = new FormControl(false);
  otherEmailShippingLabel = new FormControl(false);
  shipperEmailShippingLabel = new FormControl(false);
  thirdPartyEmailShippingLabel = new FormControl(false);
  consigneeFaxBolUpdate = new FormControl(false);
  otherFaxBolUpdate = new FormControl(false);
  shipperFaxBolUpdate = new FormControl(false);
  thirdPartyFaxBolUpdate = new FormControl(false);
  consigneeFax = new FormControl('');
  otherFax = new FormControl('');
  shipperFax = new FormControl('');
  thirdPartyFax = new FormControl('');
  acceptedNotify = new FormControl(false);
  completedNotify = new FormControl(false);
  rejectedNotify = new FormControl({ value: false, disabled: true });

  constructor(model: EmailAndFaxNotification, p: PickupDetailInfo) {
    this.shipperEmail.setValidators([
      Validators.maxLength(50),
      patternValidator(RegEx.email, MessageConstants.invalidEmail)
    ]);
    this.consigneeEmail.setValidators([
      Validators.maxLength(50),
      patternValidator(RegEx.email, MessageConstants.invalidEmail)
    ]);
    this.thirdPartyEmail.setValidators([
      Validators.maxLength(50),
      patternValidator(RegEx.email, MessageConstants.invalidEmail)
    ]);
    this.otherEmail.setValidators([
      Validators.maxLength(50),
      patternValidator(RegEx.email, MessageConstants.invalidEmail)
    ]);
    this.shipperFax.setValidators([
      patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone)
    ]);
    this.consigneeFax.setValidators([
      patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone)
    ]);
    this.thirdPartyFax.setValidators([
      patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone)
    ]);
    this.otherFax.setValidators([
      patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone)
    ]);

    if (model && Object.keys(model).length > 0) {
      this.hasEmail.setValue(false);
    }

    if (model.fax) {
      this.fax.setValue(model.fax);
    }
    if (model.consigneeEmail) {
      this.consigneeEmail.setValue(model.consigneeEmail);
    }
    if (model.otherEmail) {
      this.otherEmail.setValue(model.otherEmail);
    }
    if (model.shipperEmail) {
      this.shipperEmail.setValue(model.shipperEmail);
    }
    if (model.thirdPartyEmail) {
      this.thirdPartyEmail.setValue(model.thirdPartyEmail);
    }
    if (model.consigneeEmailBolUpdate) {
      this.hasEmail.setValue(true);
      this.consigneeEmailBolUpdate.setValue(model.consigneeEmailBolUpdate);
    }
    if (model.otherEmailBolUpdate) {
      this.hasEmail.setValue(true);
      this.otherEmailBolUpdate.setValue(model.otherEmailBolUpdate);
    }
    if (model.shipperEmailBolUpdate) {
      this.hasEmail.setValue(true);
      this.shipperEmailBolUpdate.setValue(model.shipperEmailBolUpdate);
    }
    if (model.thirdPartyEmailBolUpdate) {
      this.hasEmail.setValue(true);
      this.thirdPartyEmailBolUpdate.setValue(model.thirdPartyEmailBolUpdate);
    }
    if (model.consigneeEmailTrackingUpdate) {
      this.hasEmail.setValue(true);
      this.consigneeEmailTrackingUpdate.setValue(model.consigneeEmailTrackingUpdate);
    }
    if (model.otherEmailTrackingUpdate) {
      this.hasEmail.setValue(true);
      this.otherEmailTrackingUpdate.setValue(model.otherEmailTrackingUpdate);
    }
    if (model.shipperEmailTrackingUpdate) {
      this.hasEmail.setValue(true);
      this.shipperEmailTrackingUpdate.setValue(model.shipperEmailTrackingUpdate);
    }
    if (model.thirdPartyEmailTrackingUpdate) {
      this.hasEmail.setValue(true);
      this.thirdPartyEmailTrackingUpdate.setValue(model.thirdPartyEmailTrackingUpdate);
    }
    if (model.consigneeEmailPickupNotice) {
      this.hasEmail.setValue(true);
      this.consigneeEmailPickupNotice.setValue(model.consigneeEmailPickupNotice);
    }
    if (model.otherEmailPickupNotice) {
      this.hasEmail.setValue(true);
      this.otherEmailPickupNotice.setValue(model.otherEmailPickupNotice);
    }
    if (model.shipperEmailPickupNotice) {
      this.hasEmail.setValue(true);
      this.shipperEmailPickupNotice.setValue(model.shipperEmailPickupNotice);
    }
    if (model.thirdPartyEmailPickupNotice) {
      this.hasEmail.setValue(true);
      this.thirdPartyEmailPickupNotice.setValue(model.thirdPartyEmailPickupNotice);
    }
    if (model.consigneeEmailShippingLabel) {
      this.hasEmail.setValue(true);
      this.consigneeEmailShippingLabel.setValue(model.consigneeEmailShippingLabel);
    }
    if (model.otherEmailShippingLabel) {
      this.hasEmail.setValue(true);
      this.otherEmailShippingLabel.setValue(model.otherEmailShippingLabel);
    }
    if (model.shipperEmailShippingLabel) {
      this.hasEmail.setValue(true);
      this.shipperEmailShippingLabel.setValue(model.shipperEmailShippingLabel);
    }
    if (model.thirdPartyEmailShippingLabel) {
      this.hasEmail.setValue(true);
      this.thirdPartyEmailShippingLabel.setValue(model.thirdPartyEmailShippingLabel);
    }
    if (model.consigneeFaxBolUpdate) {
      this.hasFax.setValue(model.consigneeFax ? true : false);
      this.consigneeFaxBolUpdate.setValue(model.consigneeFax ? true : false);
    }
    if (model.otherFaxBolUpdate) {
      this.hasFax.setValue(model.otherFax ? true : false);
      this.otherFaxBolUpdate.setValue(model.otherFax ? true : false);
    }
    if (model.shipperFaxBolUpdate) {
      this.hasFax.setValue(model.shipperFax ? true : false);
      this.shipperFaxBolUpdate.setValue(model.shipperFax ? true : false);
    }
    if (model.thirdPartyFaxBolUpdate) {
      this.hasFax.setValue(model.thirdPartyFax ? true : false);
      this.thirdPartyFaxBolUpdate.setValue(model.thirdPartyFax ? true : false);
    }
    if (model.consigneeFax) {
      this.consigneeFax.setValue(MyEstesFormatters.formatPhone(model.consigneeFax));
    }
    if (model.otherFax) {
      this.otherFax.setValue(MyEstesFormatters.formatPhone(model.otherFax));
    }
    if (model.shipperFax) {
      this.shipperFax.setValue(MyEstesFormatters.formatPhone(model.shipperFax));
    }
    if (model.thirdPartyFax) {
      this.thirdPartyFax.setValue(MyEstesFormatters.formatPhone(model.thirdPartyFax));
    }

    if (p) {
      if (p.accepted) {
        this.acceptedNotify.setValue(p.accepted);
      }
      if (p.completed) {
        this.completedNotify.setValue(p.completed);
      }
      if (p.rejected) {
        this.rejectedNotify.setValue(p.rejected);
      }
    }
  }
}
