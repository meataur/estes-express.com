export class EmailRequest {
  addresses: Array<string>;
  level: number;
  quoteId: string;
  quoteRefNum: number;
  /**
   * @description Set to 'C' for Contact Me, or 'E' for Email Quote
   */
  action: 'C' | 'E';
  app: string;
}
