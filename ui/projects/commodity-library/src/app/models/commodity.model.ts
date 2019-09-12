export class Commodity {
  declaredValue: number;
  description: string;
  dimensions: {
    height: number;
    length: number;
    width: number;
  };
  goodsQuantity: number;
  goodsType: string;
  hazmat: string;
  id: string;
  nmfc: string;
  nmfcSub: string;
  shipClass: string;
  weight: number;

  constructor() {
    this.dimensions = {
      height: null,
      length: null,
      width: null
    };
  }
}
