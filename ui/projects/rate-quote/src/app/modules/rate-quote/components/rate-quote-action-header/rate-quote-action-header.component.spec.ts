import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RateQuoteActionHeaderComponent } from './rate-quote-action-header.component';

describe('RateQuoteActionHeaderComponent', () => {
  let component: RateQuoteActionHeaderComponent;
  let fixture: ComponentFixture<RateQuoteActionHeaderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RateQuoteActionHeaderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RateQuoteActionHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
