import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TerminalLookupComponent } from './terminal-lookup.component';

describe('TerminalLookupComponent', () => {
  let component: TerminalLookupComponent;
  let fixture: ComponentFixture<TerminalLookupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TerminalLookupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TerminalLookupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
