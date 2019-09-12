import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PayInvoicesComponent } from './pay-invoices.component';

describe('PayInvoicesComponent', () => {
  let component: PayInvoicesComponent;
  let fixture: ComponentFixture<PayInvoicesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PayInvoicesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PayInvoicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
