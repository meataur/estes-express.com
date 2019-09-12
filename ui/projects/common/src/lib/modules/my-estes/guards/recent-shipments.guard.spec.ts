import { TestBed, async, inject } from '@angular/core/testing';

import { RecentShipmentsGuard } from './recent-shipments.guard';

describe('RecentShipmentsGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RecentShipmentsGuard]
    });
  });

  it('should ...', inject([RecentShipmentsGuard], (guard: RecentShipmentsGuard) => {
    expect(guard).toBeTruthy();
  }));
});
