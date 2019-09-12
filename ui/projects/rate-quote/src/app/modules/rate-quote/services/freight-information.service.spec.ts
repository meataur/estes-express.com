import { TestBed, inject } from '@angular/core/testing';

import { FreightInformationService } from './freight-information.service';

describe('FreightInformationService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FreightInformationService]
    });
  });

  it('should be created', inject([FreightInformationService], (service: FreightInformationService) => {
    expect(service).toBeTruthy();
  }));
});
