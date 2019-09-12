import { RoleEnum } from './role.enum';
import { FormControl, Validators } from '@angular/forms';
import { MessageConstants, patternValidator, RegEx, MyEstesValidators, MyEstesFormatters, dateValidator } from 'common';
import { PickupDetailInfo } from './pickup-detail-info.model';

export class PickupDetailInfoForm {
  pickupRequest = new FormControl(false);
  accepted = new FormControl(false);
  accountCode = new FormControl('');
  completed = new FormControl(false);
  email = new FormControl('');
  endTime = new FormControl('');
  food = new FormControl(false);
  freeze = new FormControl(false);
  hookOrDrop = new FormControl(false);
  liftgateRequired = new FormControl(false);
  name = new FormControl('');
  noStackPallet = new FormControl(false);
  oversize = new FormControl(false);
  phone = new FormControl('');
  phoneExt = new FormControl('');
  pickupDate = new FormControl('', dateValidator());
  poison = new FormControl(false);
  rejected = new FormControl(false);
  role = new FormControl('');
  specialInstruction = new FormControl('');
  availableByHour = new FormControl('08');
  availableByMinutes = new FormControl('00');
  availableByAmPm = new FormControl('AM');
  closesByHour = new FormControl('05');
  closesByMinutes = new FormControl('00');
  closesByAmPm = new FormControl('PM');

  constructor(p: PickupDetailInfo) {
    this.name.setValidators([MyEstesValidators.required, Validators.maxLength(30)]);
    this.email.setValidators([
      MyEstesValidators.required,
      patternValidator(RegEx.email, MessageConstants.invalidEmail),
      Validators.maxLength(50)
    ]);
    this.phone.setValidators([
      MyEstesValidators.required,
      patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone)
    ]);
    this.phoneExt.setValidators(Validators.maxLength(5));
    this.role.setValidators(MyEstesValidators.required);
    this.accountCode.setValidators([Validators.maxLength(7), Validators.minLength(7)]);
    this.pickupDate.setValidators([MyEstesValidators.required, dateValidator()]);
    this.availableByHour.setValidators([MyEstesValidators.required]);
    this.availableByMinutes.setValidators([MyEstesValidators.required]);
    this.availableByAmPm.setValidators([MyEstesValidators.required]);
    this.closesByHour.setValidators([MyEstesValidators.required]);
    this.closesByMinutes.setValidators([MyEstesValidators.required]);
    this.closesByAmPm.setValidators([MyEstesValidators.required]);
    this.specialInstruction.setValidators([Validators.maxLength(256)]);

    if (p) {
      if (p.accepted) {
        this.pickupRequest.setValue(true);
        this.accepted.setValue(p.accepted);
      }
      if (p.accountCode) {
        this.pickupRequest.setValue(true);
        this.accountCode.setValue(p.accountCode);
      }
      if (p.completed) {
        this.pickupRequest.setValue(true);
        this.completed.setValue(p.completed);
      }
      if (p.email) {
        this.pickupRequest.setValue(true);
        this.email.setValue(p.email);
      }
      if (p.endTime) {
        this.pickupRequest.setValue(true);
        this.endTime.setValue(p.endTime);
      }
      if (p.food) {
        this.pickupRequest.setValue(true);
        this.food.setValue(p.food);
      }
      if (p.freeze) {
        this.pickupRequest.setValue(true);
        this.freeze.setValue(p.freeze);
      }
      if (p.hookOrDrop) {
        this.pickupRequest.setValue(true);
        this.hookOrDrop.setValue(p.hookOrDrop);
      }
      if (p.liftgateRequired) {
        this.pickupRequest.setValue(true);
        this.liftgateRequired.setValue(p.liftgateRequired);
      }
      if (p.name) {
        this.pickupRequest.setValue(true);
        this.name.setValue(p.name);
      }
      if (p.noStackPallet) {
        this.pickupRequest.setValue(true);
        this.noStackPallet.setValue(p.noStackPallet);
      }
      if (p.oversize) {
        this.pickupRequest.setValue(true);
        this.oversize.setValue(p.oversize);
      }
      if (p.phone) {
        this.pickupRequest.setValue(true);
        this.phone.setValue(MyEstesFormatters.formatPhone(p.phone));
      }
      if (p.phoneExt) {
        this.pickupRequest.setValue(true);
        this.phoneExt.setValue(p.phoneExt);
      }
      if (p.pickupDate) {
        this.pickupRequest.setValue(true);
        this.pickupDate.setValue(p.pickupDate);
      }
      if (p.poision) {
        this.pickupRequest.setValue(true);
        this.poison.setValue(p.poision);
      }
      if (p.rejected) {
        this.rejected.setValue(p.rejected);
      }
      if (p.role) {
        this.pickupRequest.setValue(true);
        this.role.setValue(p.role);
      }
      if (p.specialInstruction) {
        this.pickupRequest.setValue(true);
        this.specialInstruction.setValue(p.specialInstruction);
      }
      if (p.startTime) {
        // 06:45 AM
        const hour = p.startTime.substr(0, 2);
        const min = p.startTime.substr(3, 2);
        const amPm = p.startTime.substr(6, 2);

        this.availableByHour.setValue(hour);
        this.availableByMinutes.setValue(min);
        this.availableByAmPm.setValue(amPm);
      }
      if (p.endTime) {
        // 06:45 AM
        const hour = p.endTime.substr(0, 2);
        const min = p.endTime.substr(3, 2);
        const amPm = p.endTime.substr(6, 2);

        this.closesByHour.setValue(hour);
        this.closesByMinutes.setValue(min);
        this.closesByAmPm.setValue(amPm);
      }
    }
  }
}
