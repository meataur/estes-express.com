import { TestBed, async, inject } from '@angular/core/testing';

import { SubAccountsListGuard } from './sub-accounts-list.guard';

describe('SubAccountsListGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SubAccountsListGuard]
    });
  });

  it('should ...', inject([SubAccountsListGuard], (guard: SubAccountsListGuard) => {
    expect(guard).toBeTruthy();
  }));
});
