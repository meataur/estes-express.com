import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuoteChargeItemsComponent } from './quote-charge-items.component';

describe('QuoteChargeItemsComponent', () => {
  let component: QuoteChargeItemsComponent;
  let fixture: ComponentFixture<QuoteChargeItemsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuoteChargeItemsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuoteChargeItemsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
