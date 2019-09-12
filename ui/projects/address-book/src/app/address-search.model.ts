export class AddressSearch {
  city: string;
  cityContains: boolean;
  cityExact: boolean;
  company: string;
  companyContains: boolean;
  companyExact: boolean;
  locContains: boolean;
  locExact: boolean;
  locationNumber: string;
  state: string;
  stateContains: boolean;
  stateExact: boolean;
  streetAddress: string;
  streetContains: boolean;
  streetExact: boolean;
  user: string;
  zip: string;
  zipContains: boolean;
  zipExact: boolean;

  public constructor(init?: Partial<AddressSearch>) {
      Object.assign(this, init);
  }
}