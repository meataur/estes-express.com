import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RateEstimateIntroComponent } from './rate-estimate-intro.component';

describe('RateEstimateIntroComponent', () => {
  let component: RateEstimateIntroComponent;
  let fixture: ComponentFixture<RateEstimateIntroComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RateEstimateIntroComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RateEstimateIntroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
