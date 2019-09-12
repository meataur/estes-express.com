import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuoteEstimateDetailsComponent } from './quote-estimate-details.component';

describe('QuoteEstimateDetailsComponent', () => {
  let component: QuoteEstimateDetailsComponent;
  let fixture: ComponentFixture<QuoteEstimateDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuoteEstimateDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuoteEstimateDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
