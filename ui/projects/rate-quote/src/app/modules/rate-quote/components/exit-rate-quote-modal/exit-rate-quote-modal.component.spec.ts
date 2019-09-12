import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ExitRateQuoteModalComponent } from './exit-rate-quote-modal.component';

describe('ExitRateQuoteModalComponent', () => {
  let component: ExitRateQuoteModalComponent;
  let fixture: ComponentFixture<ExitRateQuoteModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ExitRateQuoteModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExitRateQuoteModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
