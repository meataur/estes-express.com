import { FormControl, Validators } from '@angular/forms';
import { AddressInformation } from './address-information.model';
import { MessageConstants, RegEx, patternValidator, MyEstesValidators, MyEstesFormatters } from 'common';
import { CountryEnum } from './country.enum';

export class AddressInformationForm {
  companyName = new FormControl('');
  firstName = new FormControl('');
  lastName = new FormControl('');
  location = new FormControl('');
  phone = new FormControl('');
  phoneExt = new FormControl('');
  fax = new FormControl('');
  country = new FormControl(Object.keys(CountryEnum).find(c => CountryEnum[c] === CountryEnum.US));
  address1 = new FormControl('');
  address2 = new FormControl('');
  city = new FormControl('');
  state = new FormControl('');
  zip = new FormControl('');
  email = new FormControl('');

  constructor(a: AddressInformation) {
    this.companyName.setValidators([MyEstesValidators.required, Validators.maxLength(30)]);
    this.firstName.setValidators([Validators.maxLength(25)]); // Required if user selects generate pickup request
    this.lastName.setValidators(Validators.maxLength(25));
    this.location.setValidators(Validators.maxLength(10));
    this.phone.setValidators(patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone));
    this.phoneExt.setValidators(Validators.maxLength(5));
    this.fax.setValidators(patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone));
    this.country.setValidators([MyEstesValidators.required]);
    this.address1.setValidators([MyEstesValidators.required, Validators.maxLength(30)]);
    this.address2.setValidators(Validators.maxLength(30));
    this.city.setValidators([MyEstesValidators.required, Validators.maxLength(20)]);
    this.state.setValidators([MyEstesValidators.required, Validators.maxLength(2)]);
    this.zip.setValidators([
      MyEstesValidators.required,
      patternValidator(RegEx.USorCNPostalCode, MessageConstants.invalidUSorCNPostalCode)
    ]);
    // tslint:disable-next-line:max-line-length
    this.email.setValidators([
      patternValidator(RegEx.email, MessageConstants.invalidEmail),
      Validators.maxLength(50)
    ]); // Required if user selects generate pickup request

    if (a.companyName) {
      this.companyName.setValue(a.companyName);
    }
    if (a.firstName) {
      this.firstName.setValue(a.firstName);
    }
    if (a.lastName) {
      this.lastName.setValue(a.lastName);
    }
    if (a.location) {
      this.location.setValue(a.location);
    }
    if (a.phone) {
      this.phone.setValue(MyEstesFormatters.formatPhone(a.phone));
    }
    if (a.phoneExt) {
      this.phoneExt.setValue(a.phoneExt);
    }
    if (a.fax) {
      this.fax.setValue(MyEstesFormatters.formatPhone(a.fax));
    }
    if (a.country) {
      this.country.setValue(a.country);
    }
    if (a.address1) {
      this.address1.setValue(a.address1);
    }
    if (a.address2) {
      this.address2.setValue(a.address2);
    }
    if (a.city) {
      this.city.setValue(a.city);
    }
    if (a.state) {
      this.state.setValue(a.state);
    }
    if (a.zip) {
      this.zip.setValue(a.zip);
    }
    if (a.email) {
      this.email.setValue(a.email);
    }
    this.companyName.updateValueAndValidity();
    this.firstName.updateValueAndValidity();
    this.lastName.updateValueAndValidity();
    this.location.updateValueAndValidity();
    this.phone.updateValueAndValidity();
    this.phoneExt.updateValueAndValidity();
    this.fax.updateValueAndValidity();
    this.country.updateValueAndValidity();
    this.address1.updateValueAndValidity();
    this.address2.updateValueAndValidity();
    this.city.updateValueAndValidity();
    this.state.updateValueAndValidity();
    this.zip.updateValueAndValidity();
    this.email.updateValueAndValidity();
  }
}
