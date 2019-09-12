import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RateQuoteTypeComponent } from './rate-quote-type.component';

describe('RateQuoteTypeComponent', () => {
  let component: RateQuoteTypeComponent;
  let fixture: ComponentFixture<RateQuoteTypeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RateQuoteTypeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RateQuoteTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
