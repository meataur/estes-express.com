import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuoteCommentsComponent } from './quote-comments.component';

describe('QuoteCommentsComponent', () => {
  let component: QuoteCommentsComponent;
  let fixture: ComponentFixture<QuoteCommentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuoteCommentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuoteCommentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
