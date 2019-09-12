import { Pipe } from '@angular/core';

@Pipe({
  name: 'phone'
})
export class PhonePipe {
  transform(tel) {
    var value = tel
      .toString()
      .trim()
      .replace(/^\+/, '');

    if (value.match(/[^0-9]/)) {
      return tel;
    }
    var areaCode, number;

    areaCode = value.slice(0, 3);
    number = value.slice(3);
    number = number.slice(0, 3) + '-' + number.slice(3);

    return ('(' + areaCode + ') ' + number).trim();
  }
}
