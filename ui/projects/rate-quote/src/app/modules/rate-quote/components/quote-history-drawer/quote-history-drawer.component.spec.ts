import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuoteHistoryDrawerComponent } from './quote-history-drawer.component';

describe('QuoteHistoryDrawerComponent', () => {
  let component: QuoteHistoryDrawerComponent;
  let fixture: ComponentFixture<QuoteHistoryDrawerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuoteHistoryDrawerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuoteHistoryDrawerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
