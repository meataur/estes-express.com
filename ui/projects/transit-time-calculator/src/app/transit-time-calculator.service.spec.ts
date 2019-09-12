import { TestBed, inject } from '@angular/core/testing';

import { TransitTimeCalculatorService } from './transit-time-calculator.service';

describe('TransitTimeCalculatorService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TransitTimeCalculatorService]
    });
  });

  it('should be created', inject([TransitTimeCalculatorService], (service: TransitTimeCalculatorService) => {
    expect(service).toBeTruthy();
  }));
});
