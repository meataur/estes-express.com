import { TestBed, async, inject } from '@angular/core/testing';

import { ScopeGuard } from './scope.guard';

describe('ScopeGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ScopeGuard]
    });
  });

  it('should ...', inject([ScopeGuard], (guard: ScopeGuard) => {
    expect(guard).toBeTruthy();
  }));
});
