import { TestBed, inject } from '@angular/core/testing';

import { PickupDetailsService } from './pickup-details.service';

describe('PickupDetailsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PickupDetailsService]
    });
  });

  it('should be created', inject([PickupDetailsService], (service: PickupDetailsService) => {
    expect(service).toBeTruthy();
  }));
});
