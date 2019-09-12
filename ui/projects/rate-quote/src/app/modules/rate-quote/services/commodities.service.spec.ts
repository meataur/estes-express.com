import { TestBed, inject } from '@angular/core/testing';

import { CommoditiesService } from './commodities.service';

describe('CommoditiesService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CommoditiesService]
    });
  });

  it('should be created', inject([CommoditiesService], (service: CommoditiesService) => {
    expect(service).toBeTruthy();
  }));
});
