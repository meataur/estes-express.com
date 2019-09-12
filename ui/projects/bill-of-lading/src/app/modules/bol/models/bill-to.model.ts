import { BillToOptionValueEnum } from './bill-to-option-value.enum';
import { FeeEnum } from './fee.enum';
import { AddressInformation } from './address-information.model';

export class BillTo {
  payor: BillToOptionValueEnum;
  fee: FeeEnum;
  billingAddressInfo: AddressInformation;

  constructor() {
    this.billingAddressInfo = new AddressInformation();
  }
}
