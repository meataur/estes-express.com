import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OnlineReportingComponent } from './online-reporting.component';

describe('OnlineReportingComponent', () => {
  let component: OnlineReportingComponent;
  let fixture: ComponentFixture<OnlineReportingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OnlineReportingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OnlineReportingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
