import {CountryEnum} from './country.enum';
export interface AccountInfo {
  accountNumber: string;
  address: {
    city: string;
    country: CountryEnum;
    state: string;
    streetAddress: string;
    streetAddress2: string;
    zip: string;
    zip4: string;
  };
  contactName: string;
  name: string;
  phone: string;
}
