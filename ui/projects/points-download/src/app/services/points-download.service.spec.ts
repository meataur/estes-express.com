import { TestBed, inject } from '@angular/core/testing';

import { PointsDownloadService } from './points-download.service';

describe('PointsDownloadService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PointsDownloadService]
    });
  });

  it('should be created', inject([PointsDownloadService], (service: PointsDownloadService) => {
    expect(service).toBeTruthy();
  }));
});
