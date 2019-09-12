import { TestBed, inject } from '@angular/core/testing';

import { MyestesService } from './myestes.service';

describe('MyestesService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [MyestesService]
    });
  });

  it('should be created', inject([MyestesService], (service: MyestesService) => {
    expect(service).toBeTruthy();
  }));
});
