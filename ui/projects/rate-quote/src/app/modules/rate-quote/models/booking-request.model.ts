export class BookingRequest {
  action: string;
  addresses: Array<string>;
  level: number;
  quoteId: string;
  quoteRefNum: number;
  target: 'BOL' | 'PU';
  app: string;
}
