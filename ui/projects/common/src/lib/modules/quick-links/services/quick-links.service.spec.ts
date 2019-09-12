import { TestBed, inject } from '@angular/core/testing';

import { QuickLinksService } from './quick-links.service';

describe('QuickLinksService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [QuickLinksService]
    });
  });

  it('should be created', inject([QuickLinksService], (service: QuickLinksService) => {
    expect(service).toBeTruthy();
  }));
});
