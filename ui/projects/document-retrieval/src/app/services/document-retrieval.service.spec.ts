import { TestBed, inject } from '@angular/core/testing';

import { DocumentRetrievalService } from './document-retrieval.service';

describe('DocumentRetrievalService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DocumentRetrievalService]
    });
  });

  it('should be created', inject([DocumentRetrievalService], (service: DocumentRetrievalService) => {
    expect(service).toBeTruthy();
  }));
});
