import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VtlQuoteDetailsComponent } from './vtl-quote-details.component';

describe('VtlQuoteDetailsComponent', () => {
  let component: VtlQuoteDetailsComponent;
  let fixture: ComponentFixture<VtlQuoteDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VtlQuoteDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VtlQuoteDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
