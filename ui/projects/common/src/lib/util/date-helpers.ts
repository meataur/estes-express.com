import { DatePipe } from '@angular/common';

// Takes a string date with the format of yyyyMMdd and returns the Date representation
export function extractDateFromString(dateString: string): Date {
  if (dateString && dateString.length === 8) {
    try {
      const year = dateString.substr(0, 4);
      const month = dateString.substr(4, 2);
      const day = dateString.substr(6, 2);

      const date = new Date(Number(year), Number(month) - 1, Number(day));

      return date;
    } catch (err) {
      throw new Error(`Error while parsing date from string: ${dateString}`);
    }
  }
  return null;
}

// Formats a string date formatted as yyyyMMdd and returns it in the format MM/dd/yyyy
export function getFormattedDateFromString(dateString: string): string {
  const date = extractDateFromString(dateString);
  const datePipe = new DatePipe('en-US');

  return datePipe.transform(date, 'MM/dd/yyyy');
}

export function yyyymmdd(date: Date) {
  if (date) {
    var mm = date.getMonth() + 1; // getMonth() is zero-based
    var dd = date.getDate();

    return [date.getFullYear(), (mm > 9 ? '' : '0') + mm, (dd > 9 ? '' : '0') + dd].join('');
  } else {
    return '';
  }
}

/**
 *
 * @param hours
 * @param minutes
 * @param AmOrPm
 * @param date The desired date object to be assigned.  If not supplied, a new instance will be created.
 */
export function convertTime12to24(
  hours: number,
  minutes: number,
  AmOrPm: 'AM' | 'PM',
  date?: Date
) {
  const d = date || new Date();

  if (hours === 12) {
    hours = 0;
  }

  if (AmOrPm === 'PM') {
    hours += 12;
  }

  d.setHours(hours, minutes);
  return d;
}
