import { TestBed, inject } from '@angular/core/testing';

import { ShipmentTrackingService } from './shipment-tracking.service';

describe('ShipmentTrackingService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ShipmentTrackingService]
    });
  });

  it('should be created', inject([ShipmentTrackingService], (service: ShipmentTrackingService) => {
    expect(service).toBeTruthy();
  }));
});
