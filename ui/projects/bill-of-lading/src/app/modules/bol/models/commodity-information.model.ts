import { Commodity } from './commodity.model';

export class CommodityInformation {
  commodityList: Array<Commodity>;
  contactName: string;
  phone: string;
  phoneExt: string;
  totalCube: string;
  specialIns: string;

  constructor() {
    this.commodityList = [];
  }
}
