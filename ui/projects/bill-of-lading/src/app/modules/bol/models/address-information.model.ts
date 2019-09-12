import { CountryEnum } from './country.enum';
import { AddressInformationForm } from './address-information-form.model';
import { FormGroup } from '@angular/forms';

export class AddressInformation {
  companyName: string;
  firstName: string;
  lastName: string;
  location: string;
  phone: string;
  phoneExt: string;
  fax: string;
  country: CountryEnum;
  address1: string;
  address2: string;
  city: string;
  state: string;
  zip: string;
  email: string;

  static getAddressInformation(formGroupValue: any): AddressInformation {
    const a = new AddressInformation();
    a.companyName = formGroupValue.companyName;
    a.firstName = formGroupValue.firstName;
    a.lastName = formGroupValue.lastName;
    a.location = formGroupValue.location;
    a.phone = formGroupValue.phone;
    a.phoneExt = formGroupValue.phoneExt;
    a.fax = formGroupValue.fax;
    a.email = formGroupValue.email;
    a.country = formGroupValue.country;
    a.address1 = formGroupValue.address1;
    a.address2 = formGroupValue.address2;
    a.city = formGroupValue.city;
    a.state = formGroupValue.state;
    a.zip = formGroupValue.zip;

    return a;
  }
}
