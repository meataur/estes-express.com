import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TransitTimeCalculatorComponent } from './transit-time-calculator.component';

describe('TransitTimeCalculatorComponent', () => {
  let component: TransitTimeCalculatorComponent;
  let fixture: ComponentFixture<TransitTimeCalculatorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TransitTimeCalculatorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransitTimeCalculatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
