import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TcQuoteDetailsComponent } from './tc-quote-details.component';

describe('TcQuoteDetailsComponent', () => {
  let component: TcQuoteDetailsComponent;
  let fixture: ComponentFixture<TcQuoteDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TcQuoteDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TcQuoteDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
