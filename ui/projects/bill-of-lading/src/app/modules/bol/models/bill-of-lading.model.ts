import { DocumentTypeEnum } from './document-type.enum';
import { GeneralInformation } from './general-information.model';
import { AddressInformation } from './address-information.model';
import { CommodityInformation } from './commodity-information.model';
import { Reference } from './reference.model';
import { BillingInformation } from './billing-information.model';
import { Accessorial } from './accessorial.model';
import { ShippingLabel } from './shipping-label.model';
import { EmailAndFaxNotification } from './email-and-fax-notification.model';
import { PickupDetailInfo } from './pickup-detail-info.model';

export class BillOfLading {
  bolId: number;
  documentType: DocumentTypeEnum;
  generalInfo: GeneralInformation;
  pickupDetailInfo: PickupDetailInfo;
  shipperInfo: AddressInformation;
  consigneeInfo: AddressInformation;
  commodityInfo: CommodityInformation;
  references: Array<Reference>;
  billingInfo: BillingInformation;
  accessorials: Array<Accessorial>;
  shippingLabel: ShippingLabel;
  emailAndFaxNotification: EmailAndFaxNotification;

  constructor() {
    this.bolId = null;
    this.generalInfo = new GeneralInformation();
    this.pickupDetailInfo = new PickupDetailInfo();
    this.shipperInfo = new AddressInformation();
    this.consigneeInfo = new AddressInformation();
    this.commodityInfo = new CommodityInformation();
    this.references = [];
    this.billingInfo = new BillingInformation();
    this.accessorials = [];
    this.shippingLabel = new ShippingLabel();
    this.emailAndFaxNotification = new EmailAndFaxNotification();
  }
}
