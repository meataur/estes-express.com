import { FormControl, Validators } from '@angular/forms';
import { Commodity } from './commodity.model';
import { patternValidator, RegEx, MessageConstants } from 'common';

export class CommodityFormModel {
  hazmat = new FormControl(false);
  id = new FormControl('');
  goodsQuantity = new FormControl('');
  goodsType = new FormControl('');
  description = new FormControl('');
  weight = new FormControl('');
  nmfc = new FormControl('');
  nmfcSub = new FormControl('');
  shipClass = new FormControl('');

  constructor(commodity?: Commodity) {
    this.initValidators();

    if (commodity) {
      if (commodity.hazmat) {
        this.hazmat.setValue(commodity.hazmat === 'Y' ? true : false);
      }
      if (commodity.id) {
        this.id.setValue(commodity.id);
      }
      if (commodity.goodsQuantity) {
        this.goodsQuantity.setValue(commodity.goodsQuantity);
      }
      if (commodity.goodsType) {
        this.goodsType.setValue(commodity.goodsType);
      }
      if (commodity.description) {
        this.description.setValue(commodity.description);
      }
      if (commodity.weight) {
        this.weight.setValue(commodity.weight);
      }
      if (commodity.nmfc) {
        this.nmfc.setValue(commodity.nmfc);
      }
      if (commodity.nmfcSub) {
        this.nmfcSub.setValue(commodity.nmfcSub);
      }
      if (commodity.shipClass) {
        this.shipClass.setValue(commodity.shipClass);
      }
    }
  }

  private initValidators() {
    this.id.setValidators([Validators.required, Validators.maxLength(30)]);
    this.description.setValidators([Validators.required]);
    this.goodsQuantity.setValidators([
      Validators.maxLength(5),
      patternValidator(RegEx.numberGreaterThanZero, MessageConstants.invalidNumberGreaterThanZero)
    ]);
    this.weight.setValidators([
      Validators.maxLength(7),
      patternValidator(RegEx.numberGreaterThanZero, MessageConstants.invalidNumberGreaterThanZero)
    ]);
    this.nmfc.setValidators([Validators.maxLength(6)]);
    this.nmfcSub.setValidators([Validators.maxLength(2)]);
  }
}
