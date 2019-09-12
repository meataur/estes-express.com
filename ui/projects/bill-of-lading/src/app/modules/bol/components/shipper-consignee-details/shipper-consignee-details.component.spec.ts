import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShipperConsigneeDetailsComponent } from './shipper-consignee-details.component';

describe('ShipperConsigneeDetailsComponent', () => {
  let component: ShipperConsigneeDetailsComponent;
  let fixture: ComponentFixture<ShipperConsigneeDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShipperConsigneeDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShipperConsigneeDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
