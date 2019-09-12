import { TestBed, inject } from '@angular/core/testing';

import { AccessorialsService } from './accessorials.service';

describe('AccessorialsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AccessorialsService]
    });
  });

  it('should be created', inject([AccessorialsService], (service: AccessorialsService) => {
    expect(service).toBeTruthy();
  }));
});
