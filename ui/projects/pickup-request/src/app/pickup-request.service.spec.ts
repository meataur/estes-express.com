import { TestBed, inject } from '@angular/core/testing';

import { PickupRequestService } from './pickup-request.service';

describe('PickupRequestService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PickupRequestService]
    });
  });

  it('should be created', inject([PickupRequestService], (service: PickupRequestService) => {
    expect(service).toBeTruthy();
  }));
});
