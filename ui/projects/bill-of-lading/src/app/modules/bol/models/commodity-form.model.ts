import { FormControl, Validators } from '@angular/forms';
import { Commodity } from './commodity.model';
import { patternValidator, MessageConstants, RegEx, MyEstesValidators } from 'common';

export class CommodityForm {
  commodityId = new FormControl();
  hazmat = new FormControl(false);
  goodsUnit = new FormControl();
  goodsType = new FormControl();
  goodsWeight = new FormControl();
  shipmentClass = new FormControl();
  numberOfPackage = new FormControl('');
  packageType = new FormControl('');
  nmfc = new FormControl();
  nmfcExt = new FormControl();
  description = new FormControl();

  constructor(c: Commodity) {
    this.goodsUnit.setValidators([
      Validators.maxLength(5),
      patternValidator(RegEx.numberGreaterThanZero, MessageConstants.invalidNumberGreaterThanZero)
    ]);
    this.goodsWeight.setValidators([
      Validators.maxLength(7),
      patternValidator(RegEx.numberGreaterThanZero, MessageConstants.invalidNumberGreaterThanZero)
    ]);
    this.numberOfPackage.setValidators([Validators.maxLength(5)]);
    this.nmfc.setValidators([Validators.maxLength(6)]);
    this.nmfcExt.setValidators([Validators.maxLength(4)]);
    this.description.setValidators([Validators.maxLength(512)]);

    if (c.commodityId) {
      this.commodityId.setValue(c.commodityId);
    }
    if (c.hazmat) {
      this.hazmat.setValue(c.hazmat);
      this.description.setValidators([MyEstesValidators.required, Validators.maxLength(512)]);
    }
    if (c.goodsUnit) {
      this.goodsUnit.setValue(c.goodsUnit);
    }
    if (c.goodsType) {
      this.goodsType.setValue(c.goodsType);
    }
    if (c.goodsWeight) {
      this.goodsWeight.setValue(c.goodsWeight);
    }
    if (c.shipmentClass) {
      this.shipmentClass.setValue(c.shipmentClass);
    }
    if (c.numberOfPackage) {
      this.numberOfPackage.setValue(c.numberOfPackage);
    }
    if (c.packageType) {
      this.packageType.setValue(c.packageType);
    }
    if (c.nmfc) {
      this.nmfc.setValue(c.nmfc);
    }
    if (c.nmfcExt) {
      this.nmfcExt.setValue(c.nmfcExt);
    }
    if (c.description) {
      this.description.setValue(c.description);
    }
  }
}
