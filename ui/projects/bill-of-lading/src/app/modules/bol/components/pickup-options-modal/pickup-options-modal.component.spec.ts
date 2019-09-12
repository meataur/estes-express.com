import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PickupOptionsModalComponent } from './pickup-options-modal.component';

describe('PickupOptionsModalComponent', () => {
  let component: PickupOptionsModalComponent;
  let fixture: ComponentFixture<PickupOptionsModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PickupOptionsModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PickupOptionsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
