import { FormArray, FormControl, Validators } from '@angular/forms';
import { CommodityInformation } from './commodity-information.model';
import { patternValidator, MessageConstants, RegEx, MyEstesFormatters } from 'common';
import { Commodity } from './commodity.model';

export class CommodityInformationForm {
  commodityList = new FormArray([]);
  contactName = new FormControl('');
  phone = new FormControl('');
  phoneExt = new FormControl('');
  totalCube = new FormControl('');
  specialIns = new FormControl('');

  constructor(ci: CommodityInformation) {
    this.contactName.setValidators([Validators.maxLength(30)]);
    this.phone.setValidators([patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone)]);
    this.phoneExt.setValidators(Validators.maxLength(5));
    this.totalCube.setValidators([
      patternValidator(
        RegEx.numberWithTwoDecimalPlaces,
        MessageConstants.invalidNumberWithTwoDecimals
      ),
      Validators.maxLength(7)
    ]);
    this.specialIns.setValidators([Validators.maxLength(512)]);

    // if (ci.commodityList.length === 0) {
    //   ci.commodityList.push(new Commodity());
    // }

    if (ci.contactName) {
      this.contactName.setValue(ci.contactName);
    }
    if (ci.phone) {
      this.phone.setValue(MyEstesFormatters.formatPhone(ci.phone));
    }
    if (ci.phoneExt) {
      this.phoneExt.setValue(ci.phoneExt);
    }
    if (ci.totalCube) {
      this.totalCube.setValue(ci.totalCube);
    }
    if (ci.specialIns) {
      this.specialIns.setValue(ci.specialIns);
    }
  }
}
