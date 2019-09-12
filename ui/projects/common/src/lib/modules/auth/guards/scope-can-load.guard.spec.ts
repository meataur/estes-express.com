import { TestBed, async, inject } from '@angular/core/testing';

import { ScopeCanLoadGuard } from './scope-can-load.guard';

describe('ScopeCanLoadGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ScopeCanLoadGuard]
    });
  });

  it('should ...', inject([ScopeCanLoadGuard], (guard: ScopeCanLoadGuard) => {
    expect(guard).toBeTruthy();
  }));
});
