import { TestBed, inject } from '@angular/core/testing';

import { RequestorService } from './requestor.service';

describe('RequestorService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RequestorService]
    });
  });

  it('should be created', inject([RequestorService], (service: RequestorService) => {
    expect(service).toBeTruthy();
  }));
});
