import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InvoiceInquiryComponent } from './invoice-inquiry.component';

describe('InvoiceInquiryComponent', () => {
  let component: InvoiceInquiryComponent;
  let fixture: ComponentFixture<InvoiceInquiryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InvoiceInquiryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InvoiceInquiryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
