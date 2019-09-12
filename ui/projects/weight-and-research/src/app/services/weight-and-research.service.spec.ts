import { TestBed, inject } from '@angular/core/testing';

import { WeightAndResearchService } from './weight-and-research.service';

describe('WeightAndResearchService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [WeightAndResearchService]
    });
  });

  it('should be created', inject([WeightAndResearchService], (service: WeightAndResearchService) => {
    expect(service).toBeTruthy();
  }));
});
