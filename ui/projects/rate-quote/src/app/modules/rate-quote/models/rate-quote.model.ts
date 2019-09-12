import { AccessorialPricing } from './accessorial-pricing.model';
import { CommodityPricing } from './commodity-pricing.model';
import { Point } from './point.model';
import { Charge } from './charge.model';

export class RateQuote {
  /**
   * @description For front-end use only.  Used to call selectQuote in service when user opens
   * a quote to view on Step 2 of Rate Quote application.
   */
  selected?: boolean;
  /**
   * @description For front-end use only.  Used to show row is waiting for selectQuote() in service
   * to complete on Step 2 of Rate Quote application.
   */
  isSelecting?: boolean;
  accessorials: Array<AccessorialPricing>;
  accountCode: string;
  accountName: string;
  charges: Array<Charge>;
  comments: string;
  commodities: Array<CommodityPricing>;
  contactEmail: string;
  contactName: string;
  contactPhone: string;
  contactPhoneExt: string;
  declaredValue: number;
  declaredValueWaived: boolean;
  delivery: {
    date: string;
    time: string;
  };
  dest: Point;
  disclaimers: Array<string>;
  foodWarehouse: string;
  foodWarehouseId: number;
  guaranteed: boolean;
  info: Array<string>;
  lane: number;
  linearFeet: number;
  origin: Point;
  pickupAvail: string;
  pickupClose: string;
  pickupDate: string;
  pricing: {
    discount: number;
    display: 'C' | 'H' | 'S'; // C=Contact me/H=hide/S=Show
    totalDiscount: number;
    totalPrice: number;
  };
  quoteDate: string;
  role: string;
  selectedApps: ['LTL' | 'TC' | 'VTL'];
  service: {
    id: number;
    text: string;
  };
  stackable: boolean;
  terms: string;
  transitMessage: string;
  quoteID: string;
  quoteRefNum: string;
  app: 'VTL' | 'TC' | 'LTL';
}
