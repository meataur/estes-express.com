export interface SubAccountsResponse {
  first: boolean;
  last: boolean;
  number: number;
  numberOfElements: number;
  size: number;
  totalElements: number;
  totalPages: number;
  content: Array<SubAccount>;
}

export interface SubAccount {
  accountNumber: string;
  name: string;
  address: {
    streetAddress: string;
    streetAddress2: string;
    city: string;
    state: string;
    zip: string;
    zip4: string;
    country: string;
  };
}
