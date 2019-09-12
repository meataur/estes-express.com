import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RateQuoteComponent } from './rate-quote.component';

describe('RateQuoteComponent', () => {
  let component: RateQuoteComponent;
  let fixture: ComponentFixture<RateQuoteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RateQuoteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RateQuoteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
