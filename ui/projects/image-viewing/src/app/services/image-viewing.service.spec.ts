import { TestBed, inject } from '@angular/core/testing';

import { ImageViewingService } from './image-viewing.service';

describe('ImageViewingService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ImageViewingService]
    });
  });

  it('should be created', inject([ImageViewingService], (service: ImageViewingService) => {
    expect(service).toBeTruthy();
  }));
});
