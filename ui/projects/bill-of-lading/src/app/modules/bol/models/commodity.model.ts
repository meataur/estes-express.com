import { GoodsType } from './goods-type.model';
import { ShipmentClass } from './shipment-class.model';

export class Commodity {
  commodityId: number;
  hazmat: boolean;
  goodsUnit: string;
  goodsType: GoodsType;
  goodsWeight: string;
  shipmentClass: ShipmentClass;
  nmfc: string;
  nmfcExt: string;
  numberOfPackage: string; // Display only if VICS BOL is selected; required
  packageType: string; // Display only if VICS BOL is selected; required
  description: string;
  // height: string;
  // length: string;
  // weight: string;
}
