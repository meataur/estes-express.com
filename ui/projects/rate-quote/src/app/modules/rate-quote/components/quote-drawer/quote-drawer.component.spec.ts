import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuoteDrawerComponent } from './quote-drawer.component';

describe('QuoteDrawerComponent', () => {
  let component: QuoteDrawerComponent;
  let fixture: ComponentFixture<QuoteDrawerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuoteDrawerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuoteDrawerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
