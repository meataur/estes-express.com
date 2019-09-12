import { Point } from './point.model';

export class RateSearch {
  accountCode: string;
  dest: Point;
  fromDate: string;
  maxCharges: number;
  minCharges: number;
  origin: Point;
  serviceLevel: number;
  toDate: string;
  quoteID: string;
}
