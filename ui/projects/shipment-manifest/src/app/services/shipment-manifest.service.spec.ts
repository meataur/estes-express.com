import { TestBed, inject } from '@angular/core/testing';

import { ShipmentManifestService } from './shipment-manifest.service';

describe('ShipmentManifestService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ShipmentManifestService]
    });
  });

  it('should be created', inject([ShipmentManifestService], (service: ShipmentManifestService) => {
    expect(service).toBeTruthy();
  }));
});
