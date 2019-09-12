import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuoteHistorySidePanelComponent } from './quote-history-side-panel.component';

describe('QuoteHistorySidePanelComponent', () => {
  let component: QuoteHistorySidePanelComponent;
  let fixture: ComponentFixture<QuoteHistorySidePanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuoteHistorySidePanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuoteHistorySidePanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
