import { TestBed, inject } from '@angular/core/testing';

import { RateQuoteService } from './rate-quote.service';

describe('RateQuoteService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RateQuoteService]
    });
  });

  it('should be created', inject([RateQuoteService], (service: RateQuoteService) => {
    expect(service).toBeTruthy();
  }));
});
