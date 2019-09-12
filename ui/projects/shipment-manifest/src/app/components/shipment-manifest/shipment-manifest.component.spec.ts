import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShipmentManifestComponent } from './shipment-manifest.component';

describe('ShipmentManifestComponent', () => {
  let component: ShipmentManifestComponent;
  let fixture: ComponentFixture<ShipmentManifestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShipmentManifestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShipmentManifestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
