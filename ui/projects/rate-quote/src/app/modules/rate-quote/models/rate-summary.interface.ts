import { Point } from './point.model';
import { RateQuote } from './rate-quote.model';

export interface RateSummary {
  /**
   * @description Full rate quote object.  This property will be set when the user is viewing
   * their quote history and they click on a row to view their quote details.
   */
  rateQuote?: RateQuote;
  /**
   * @description This field is used to indicate that the row, upon being clicked, is actively
   * pulling rate quote details.
   */
  isSelecting?: boolean;
  dest: Point;
  estCharges: number;
  origin: Point;
  quoteDate: string;
  serviceLevel: string;
  source: string;
  quoteID: string;
  deliveryDate: string;
  deliveryTime: string;
}
