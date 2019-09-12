import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PickupHistoryComponent } from './pickup-history.component';

describe('PickupHistoryComponent', () => {
  let component: PickupHistoryComponent;
  let fixture: ComponentFixture<PickupHistoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PickupHistoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PickupHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
