import { TestBed, inject } from '@angular/core/testing';

import { EmailDialogService } from './email-dialog.service';

describe('EmailDialogService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [EmailDialogService]
    });
  });

  it('should be created', inject([EmailDialogService], (service: EmailDialogService) => {
    expect(service).toBeTruthy();
  }));
});
