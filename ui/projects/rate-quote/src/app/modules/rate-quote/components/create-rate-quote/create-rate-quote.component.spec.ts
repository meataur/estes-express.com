import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateRateQuoteComponent } from './create-rate-quote.component';

describe('CreateRateQuoteComponent', () => {
  let component: CreateRateQuoteComponent;
  let fixture: ComponentFixture<CreateRateQuoteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateRateQuoteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateRateQuoteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
