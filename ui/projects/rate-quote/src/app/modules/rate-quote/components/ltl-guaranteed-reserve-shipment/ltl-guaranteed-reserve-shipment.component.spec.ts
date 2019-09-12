import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LtlGuaranteedReserveShipmentComponent } from './ltl-guaranteed-reserve-shipment.component';

describe('LtlGuaranteedReserveShipmentComponent', () => {
  let component: LtlGuaranteedReserveShipmentComponent;
  let fixture: ComponentFixture<LtlGuaranteedReserveShipmentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LtlGuaranteedReserveShipmentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LtlGuaranteedReserveShipmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
