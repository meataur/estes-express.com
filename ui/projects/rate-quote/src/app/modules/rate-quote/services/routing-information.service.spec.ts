import { TestBed, inject } from '@angular/core/testing';

import { RoutingInformationService } from './routing-information.service';

describe('RoutingInformationService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RoutingInformationService]
    });
  });

  it('should be created', inject([RoutingInformationService], (service: RoutingInformationService) => {
    expect(service).toBeTruthy();
  }));
});
