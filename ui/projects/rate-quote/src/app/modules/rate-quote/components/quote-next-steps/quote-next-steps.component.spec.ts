import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuoteNextStepsComponent } from './quote-next-steps.component';

describe('QuoteNextStepsComponent', () => {
  let component: QuoteNextStepsComponent;
  let fixture: ComponentFixture<QuoteNextStepsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuoteNextStepsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuoteNextStepsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
