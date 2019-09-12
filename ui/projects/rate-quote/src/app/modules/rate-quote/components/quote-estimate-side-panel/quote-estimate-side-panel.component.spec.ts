import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuoteEstimateSidePanelComponent } from './quote-estimate-side-panel.component';

describe('QuoteEstimateSidePanelComponent', () => {
  let component: QuoteEstimateSidePanelComponent;
  let fixture: ComponentFixture<QuoteEstimateSidePanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuoteEstimateSidePanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuoteEstimateSidePanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
