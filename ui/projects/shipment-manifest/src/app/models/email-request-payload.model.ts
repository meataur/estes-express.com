export class EmailRequestPayload {
  shipmentTypes: 'THIRDPARTY' | 'OUTBOUND' | 'INBOUND' | 'ALL';
  sortBy: 'ASCENDING' | 'DESCENDING';
  searchBy: 'PICKUPDATE' | 'DELIVERYDATE';
  account: string;
  startDate: string; // YYYYMMDD
  endDate: string; // YYYYMMDD
  viewCharges: boolean;
  email: string;
  format: string;
}
