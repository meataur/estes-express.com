export interface RateQuote {
  hasMexicoPoint?: boolean;
  accessorials: Array<{
    accessorial: {
      code: string;
      appID: string;
      display: boolean;
      description: string;
    };
    charge: number;
  }>;
  accountCode: string;
  accountName: string;
  comments: string;
  commodities: Array<{
    commodity: {
      description: string;
      dimensions: {
        length: number;
        width: number;
        height: number;
      };
      pieceType: string;
      pieces: number;
      shipClass: number;
      weight: number;
    };
    deficitRate: boolean;
    rate: number;
    charge: number;
  }>;
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
  dest: {
    city: string;
    country: string;
    state: string;
    zip: string;
  };
  disclaimers: Array<string>;
  expireDate: string;
  expireTime: string;
  foodWarehouse: string;
  guaranteed: boolean;
  info: Array<string>;
  lane: number;
  linearFeet: number;
  origin: {
    city: string;
    country: string;
    state: string;
    zip: string;
  };
  pickupAvail: string;
  pickupClose: string;
  pickupDate: string;
  pricing: {
    discount: number;
    display: string;
    totalDiscount: number;
    totalPrice: number;
  };
  role: string;
  service: {
    id: number;
    text: string;
  };
  stackable: boolean;
  terms: string;
  transitMessage: string;
  quoteID: string;
  quoteRefNum: string;
  app: string;
}
