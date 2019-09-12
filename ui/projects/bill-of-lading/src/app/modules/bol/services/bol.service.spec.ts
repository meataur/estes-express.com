import { TestBed, inject } from '@angular/core/testing';

import { BolService } from './bol.service';

describe('BolService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [BolService]
    });
  });

  it('should be created', inject([BolService], (service: BolService) => {
    expect(service).toBeTruthy();
  }));
});
