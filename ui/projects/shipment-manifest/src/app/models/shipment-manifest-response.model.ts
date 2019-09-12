export class ShipmentManifestResponse {
  first: boolean;
  last: boolean;
  number: number; // page number
  numberOfElements: number; // number of rows returned for this page
  size: number; // page size
  totalElements: number; // total number of results in the set
  totalPages: number; // total pages
  content: Array<ShipmentManifest>;
}

export class ShipmentManifest {
  proNumber: string;
  bol: string;
  purchaseOrder: string;
  pickupDate: string;
  deliveryDate: string;
  origin: string;
  destination: string;
  pieces: string;
  weight: string;
  charges: string;
  status: string;
  receivedBy: string;
  firstDeliveryAttempt: string;
  deliveryTime: string;
  appointmentDate: string;
  appointmentTime: string;
  shipper: string;
  shipperAddress: {
    streetAddress: string;
    streetAddress2: string;
    city: string;
    state: string;
    zip: string;
    zip4: string;
    country: string;
  };
  consignee: string;
  consigneeAddress: {
    streetAddress: string;
    streetAddress2: string;
    city: string;
    state: string;
    zip: string;
    zip4: string;
    country: string;
  };
  destinationTerminal: string;
  phoneNumber: string;
  faxNumber: string;
}
