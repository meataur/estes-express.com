import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ClaimsInquiryComponent } from './claims-inquiry.component';

describe('ClaimsInquiryComponent', () => {
  let component: ClaimsInquiryComponent;
  let fixture: ComponentFixture<ClaimsInquiryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ClaimsInquiryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClaimsInquiryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
