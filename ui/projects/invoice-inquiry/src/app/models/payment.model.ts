import { FormGroup } from '@angular/forms';
export class Payment {
  explain: string;
  invoiceDate: number;
  openAmount: number;
  payment: number;
  pendingPayAmount: number;
  pro: string;
  reasonCode: string;
  statementNum: number;
  fg: FormGroup;
}