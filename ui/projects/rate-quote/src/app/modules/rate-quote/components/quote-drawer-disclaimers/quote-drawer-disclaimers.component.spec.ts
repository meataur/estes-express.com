import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuoteDrawerDisclaimersComponent } from './quote-drawer-disclaimers.component';

describe('QuoteDrawerDisclaimersComponent', () => {
  let component: QuoteDrawerDisclaimersComponent;
  let fixture: ComponentFixture<QuoteDrawerDisclaimersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuoteDrawerDisclaimersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuoteDrawerDisclaimersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
