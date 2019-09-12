import { BillTo } from './bill-to.model';
import { CodRemitTo } from './cod-remit-to.model';

export class BillingInformation {
  billTo: BillTo;
  codRemitTo: boolean;
  codRemitToInfo: CodRemitTo;

  constructor() {
    this.billTo = new BillTo();
    this.codRemitToInfo = new CodRemitTo();
  }
}
