import { TestBed, inject } from '@angular/core/testing';

import { PcRaterDownloadService } from './pc-rater-download.service';

describe('PcRaterDownloadService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PcRaterDownloadService]
    });
  });

  it('should be created', inject([PcRaterDownloadService], (service: PcRaterDownloadService) => {
    expect(service).toBeTruthy();
  }));
});
