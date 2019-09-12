import { TestBed, inject } from '@angular/core/testing';

import { InvoiceInquiryService } from './invoice-inquiry.service';

describe('InvoiceInquiryService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [InvoiceInquiryService]
    });
  });

  it('should be created', inject([InvoiceInquiryService], (service: InvoiceInquiryService) => {
    expect(service).toBeTruthy();
  }));
});
