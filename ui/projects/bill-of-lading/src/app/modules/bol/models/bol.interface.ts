export interface Bol {
  bolId: number;
  bolNumber: string;
  bolDate: string;
  proNumber: string;
  shipperFirstName: string;
  shipperLastName: string;
  shipperCompanyName: string;
  consigneeFirstName: string;
  consigneeLastName: string;
  consigneeCompanyName: string;
  hasShippingLabel: boolean;
}
