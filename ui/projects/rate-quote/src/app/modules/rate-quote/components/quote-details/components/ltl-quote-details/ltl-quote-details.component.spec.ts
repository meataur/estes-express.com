import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LtlQuoteDetailsComponent } from './ltl-quote-details.component';

describe('LtlQuoteDetailsComponent', () => {
  let component: LtlQuoteDetailsComponent;
  let fixture: ComponentFixture<LtlQuoteDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LtlQuoteDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LtlQuoteDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
