import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmailQuoteComponent } from './email-quote.component';

describe('EmailQuoteComponent', () => {
  let component: EmailQuoteComponent;
  let fixture: ComponentFixture<EmailQuoteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmailQuoteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmailQuoteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
