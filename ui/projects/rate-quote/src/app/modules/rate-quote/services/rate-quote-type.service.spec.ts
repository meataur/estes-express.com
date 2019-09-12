import { TestBed, inject } from '@angular/core/testing';

import { RateQuoteTypeService } from './rate-quote-type.service';

describe('RateQuoteTypeService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RateQuoteTypeService]
    });
  });

  it('should be created', inject([RateQuoteTypeService], (service: RateQuoteTypeService) => {
    expect(service).toBeTruthy();
  }));
});
