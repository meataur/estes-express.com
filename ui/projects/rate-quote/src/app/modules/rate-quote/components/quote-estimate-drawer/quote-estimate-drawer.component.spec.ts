import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuoteEstimateDrawerComponent } from './quote-estimate-drawer.component';

describe('QuoteEstimateDrawerComponent', () => {
  let component: QuoteEstimateDrawerComponent;
  let fixture: ComponentFixture<QuoteEstimateDrawerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuoteEstimateDrawerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuoteEstimateDrawerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
