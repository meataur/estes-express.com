import { TestBed, inject } from '@angular/core/testing';

import { OnlineReportingService } from './online-reporting.service';

describe('OnlineReportingService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [OnlineReportingService]
    });
  });

  it('should be created', inject([OnlineReportingService], (service: OnlineReportingService) => {
    expect(service).toBeTruthy();
  }));
});
