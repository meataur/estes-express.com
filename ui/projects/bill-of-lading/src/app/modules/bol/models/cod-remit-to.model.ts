import { PaymentTypeEnum } from './payment-type.enum';
import { FeeEnum } from './fee.enum';
import { AddressInformation } from './address-information.model';

export class CodRemitTo {
  amount: string;
  paymentType: PaymentTypeEnum;
  fee: FeeEnum;
  codAddressInfo: AddressInformation;

  constructor() {
    this.codAddressInfo = new AddressInformation();
  }
}
