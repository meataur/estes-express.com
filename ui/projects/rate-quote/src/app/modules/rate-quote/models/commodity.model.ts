export class Commodity {
  description: string;
  dimensions: {
    length: number;
    width: number;
    height: number;
  };
  pieces: number;
  pieceType: string;
  shipClass: number;
  weight: number;

  constructor() {
    this.dimensions = {
      length: null,
      width: null,
      height: null
    };
  }
}
