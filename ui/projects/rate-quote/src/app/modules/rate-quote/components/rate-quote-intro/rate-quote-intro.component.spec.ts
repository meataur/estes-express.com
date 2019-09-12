import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RateQuoteIntroComponent } from './rate-quote-intro.component';

describe('RateQuoteIntroComponent', () => {
  let component: RateQuoteIntroComponent;
  let fixture: ComponentFixture<RateQuoteIntroComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RateQuoteIntroComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RateQuoteIntroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
