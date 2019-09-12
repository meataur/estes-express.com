import { CommodityPricing } from './commodity-pricing.model';

export class ContactRequest {
  app: string;
  comments: string;
  commodities: Array<CommodityPricing>;
  contactEmail: string;
  contactName: string;
  contactPhone: string;
  contactPhoneExt: string;
  pickupAvail: string;
  pickupClose: string;
  pickupDate: string;
  stackable: boolean;
  quoteId: string;
  quoteRefNum: number;

  constructor() {
    this.commodities = [];
  }
}
