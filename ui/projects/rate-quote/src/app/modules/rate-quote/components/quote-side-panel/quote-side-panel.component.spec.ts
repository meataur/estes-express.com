import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuoteSidePanelComponent } from './quote-side-panel.component';

describe('QuoteSidePanelComponent', () => {
  let component: QuoteSidePanelComponent;
  let fixture: ComponentFixture<QuoteSidePanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuoteSidePanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuoteSidePanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
