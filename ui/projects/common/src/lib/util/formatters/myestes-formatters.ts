import { RegEx } from '../regular-expressions/regular-expressions';

export class MyEstesFormatters {
  /**
   * @description Returns a formatted phone number in the format '(XXX) XXX-XXXX'.  Safely
   * returns the phone number if it is already formatted.
   * @param phoneNumber The phone number in the format XXXXXXXXXX (10 characters)
   */
  static formatPhone(phoneNumber: string): string {

    if (phoneNumber === '0000000000' || phoneNumber === '(000) 000-0000') return '';
    if (!phoneNumber) phoneNumber = '';
    const isValid = RegEx.phoneNumber.test(phoneNumber);

    if (!isValid) {
      const match = phoneNumber.match(/^(\d{3})(\d{3})(\d{4})$/);

      if (match) {
        const areaCode = match[1];
        const firstThree = match[2];
        const lastFour = match[3];

        phoneNumber = `(${areaCode}) ${firstThree}-${lastFour}`;
      }
    }

    return phoneNumber;
  }
}
