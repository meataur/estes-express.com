import { TestBed, inject } from '@angular/core/testing';

import { RequestAccountNumberService } from './request-account-number.service';

describe('RequestAccountNumberService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RequestAccountNumberService]
    });
  });

  it('should be created', inject([RequestAccountNumberService], (service: RequestAccountNumberService) => {
    expect(service).toBeTruthy();
  }));
});
