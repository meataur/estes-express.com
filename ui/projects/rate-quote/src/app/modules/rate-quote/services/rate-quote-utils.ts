import { formatDate } from '@angular/common';

export class RateQuoteUtils {
  /**
   *
   * @param date Date value expected format: hh:mm:ss
   */
  static formatAMPM(date: string): string {
    try {
      const splitTime = date.split(':');
      const hours = +splitTime[0];
      const minutes = +splitTime[1];
      const seconds = +splitTime[2];
      const d = new Date();

      d.setHours(hours, minutes, seconds);
      return formatDate(d, 'hh:mm a', 'en-US');
    } catch (e) {
      console.warn(`Failed to parse time.`);
      return date;
    }
  }
  /**
   * @description Returns tuple with hours, minutes, and AM or PM as each key.
   * [hours, minutes, AM/PM]
   * @param date Date value expected format: hh:mm:ss
   */
  static extractHoursMinutesAmPm(date: string): [string, string, string] {
    try {
      const splitTime = date.split(':');
      const hours = +splitTime[0];
      const minutes = +splitTime[1];
      const seconds = +splitTime[2];

      const d = new Date();

      d.setHours(hours, minutes, seconds);

      const formattedHours = formatDate(d, 'hh', 'en-US');
      const formattedMinutes = formatDate(d, 'mm', 'en-US');
      const formattedAmPm = formatDate(d, 'a', 'en-US');

      return [formattedHours, formattedMinutes, formattedAmPm];
    } catch (e) {
      console.warn(`Failed to parse time.`);
      return ['', '', ''];
    }
  }
}
