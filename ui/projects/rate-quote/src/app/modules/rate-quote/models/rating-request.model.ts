import { Accessorial } from './accessorial.model';
import { Commodity } from './commodity.model';
import { Point } from './point.model';

export class RatingRequest {
  /**
   * @description Set app value corresponding to quote type selections:
   * app = VTL:
   * a. VTL
   * b. VTL + TC
   * c. VTL + TC + LTL
   * d. VTL + LTL
   * app = TC:
   * a. TC
   * b. TC + LTL
   * app = LTL:
   * a. LTL
   */
  apps: Array<'VTL' | 'TC' | 'LTL'>;
  accountCode: string;
  contactName: string;
  contactPhone: string;
  contactEmail: string;
  contactPhoneExt: string;
  discount: number;
  role: string;
  terms: string;
  origin: Point;
  dest: Point;
  pickupDate: string;
  pickupAvail: string;
  pickupClose: string;
  commodities: Array<Commodity>;
  declaredValue: number;
  declaredValueWaived: boolean;
  foodWarehouseId: number;
  stackable: boolean;
  linearFeet: number;
  comments: string;
  accessorials: Array<Accessorial>;

  constructor() {
    this.apps = [];
    this.commodities = [];
    this.accessorials = [];
    this.origin = new Point();
    this.dest = new Point();
  }
}
