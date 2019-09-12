/**
 * This mask will also accept an empty string to account
 * for fields that are NOT required.
 */
const emailRegEx = /^$|^[a-zA-Z0-9.!#$%&’*+\/=?^_`{|}~-]+@[a-zA-Z0-9-]*\.?[a-zA-Z0-9-]+\.[a-zA-Z0-9-]{2,}$/;
const USorCNPostalCode = /^\d{5}$|^[a-zA-Z0-9]{6}$/;
const textAreaNewLines = /\r?\n|\r/g;
const numberWithTwoDecimalPlaces = /^\d*(\.\d{1,2})?$/;
const numbersWithDashes = /^([0-9\n-]*)$/;
/**
 * This mask will also accept an empty string to account
 * for fields that are NOT required.
 */
const phoneNumber = /^$|^\(\d{3}\) \d{3}-\d{4}$/;
const numberGreaterThanZero = /^$|^\d*[1-9]\d*$/;
const numbers = /^[0-9]*$/;
const anythingButWhitespace = /^$|[^\s]/;
const proNumber = /^$|^\d{3}-\d{7}/;

export const RegEx = {
  email: emailRegEx,
  USorCNPostalCode: USorCNPostalCode,
  textAreaNewLines: textAreaNewLines,
  phoneNumber: phoneNumber,
  numberWithTwoDecimalPlaces: numberWithTwoDecimalPlaces,
  numberGreaterThanZero: numberGreaterThanZero,
  numbers: numbers,
  numbersWithDashes: numbersWithDashes,
  anythingButWhitespace: anythingButWhitespace,
  proNumber: proNumber
};
