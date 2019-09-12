import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuoteContactInformationComponent } from './quote-contact-information.component';

describe('QuoteContactInformationComponent', () => {
  let component: QuoteContactInformationComponent;
  let fixture: ComponentFixture<QuoteContactInformationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuoteContactInformationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuoteContactInformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
