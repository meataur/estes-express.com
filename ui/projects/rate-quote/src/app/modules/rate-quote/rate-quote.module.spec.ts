import { RateQuoteModule } from './rate-quote.module';

describe('RateQuoteModule', () => {
  let rateQuoteModule: RateQuoteModule;

  beforeEach(() => {
    rateQuoteModule = new RateQuoteModule();
  });

  it('should create an instance', () => {
    expect(rateQuoteModule).toBeTruthy();
  });
});
