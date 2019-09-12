import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RateRequestResultsComponent } from './rate-request-results.component';

describe('RateRequestResultsComponent', () => {
  let component: RateRequestResultsComponent;
  let fixture: ComponentFixture<RateRequestResultsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RateRequestResultsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RateRequestResultsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
