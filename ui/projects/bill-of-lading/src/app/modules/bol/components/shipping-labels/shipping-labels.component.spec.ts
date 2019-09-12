import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShippingLabelsComponent } from './shipping-labels.component';

describe('ShippingLabelsComponent', () => {
  let component: ShippingLabelsComponent;
  let fixture: ComponentFixture<ShippingLabelsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShippingLabelsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShippingLabelsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
