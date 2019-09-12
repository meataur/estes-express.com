import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PickupRequestComponent } from './pickup-request.component';

describe('PickupRequestComponent', () => {
  let component: PickupRequestComponent;
  let fixture: ComponentFixture<PickupRequestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PickupRequestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PickupRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
