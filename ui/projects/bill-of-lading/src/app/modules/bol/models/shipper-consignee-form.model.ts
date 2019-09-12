import { FormGroup } from '@angular/forms';

export class ShipperConsigneeForm {
  shipperInfo: FormGroup;
  consigneeInfo: FormGroup;

  constructor(shipperInfo: FormGroup, consigneeInfo: FormGroup) {
    this.shipperInfo = shipperInfo;
    this.consigneeInfo = consigneeInfo;
  }
}
