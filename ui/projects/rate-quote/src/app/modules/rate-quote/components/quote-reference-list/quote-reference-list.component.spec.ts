import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuoteReferenceListComponent } from './quote-reference-list.component';

describe('QuoteReferenceListComponent', () => {
  let component: QuoteReferenceListComponent;
  let fixture: ComponentFixture<QuoteReferenceListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuoteReferenceListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuoteReferenceListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
