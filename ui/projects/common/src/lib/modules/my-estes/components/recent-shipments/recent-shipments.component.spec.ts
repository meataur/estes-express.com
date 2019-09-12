import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RecentShipmentsComponent } from './recent-shipments.component';

describe('RecentShipmentsComponent', () => {
  let component: RecentShipmentsComponent;
  let fixture: ComponentFixture<RecentShipmentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RecentShipmentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RecentShipmentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
