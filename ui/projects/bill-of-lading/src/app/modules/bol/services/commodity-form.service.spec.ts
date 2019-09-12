import { TestBed, inject } from '@angular/core/testing';

import { CommodityFormService } from './commodity-form.service';

describe('CommodityFormService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CommodityFormService]
    });
  });

  it('should be created', inject([CommodityFormService], (service: CommodityFormService) => {
    expect(service).toBeTruthy();
  }));
});
