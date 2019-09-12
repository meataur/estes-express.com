import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateRateEstimateComponent } from './create-rate-estimate.component';

describe('CreateRateEstimateComponent', () => {
  let component: CreateRateEstimateComponent;
  let fixture: ComponentFixture<CreateRateEstimateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateRateEstimateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateRateEstimateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
