import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PointDetailsModalComponent } from './point-details-modal.component';

describe('PointDetailsModalComponent', () => {
  let component: PointDetailsModalComponent;
  let fixture: ComponentFixture<PointDetailsModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PointDetailsModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PointDetailsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
