import { Commodity } from './commodity.model';

export class CommodityPricing {
  commodity: Commodity;
  deficitRate: boolean;
  rate: number;
  charge: number;

  constructor() {
    this.commodity = new Commodity();
  }
}
