import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DraftsBolComponent } from './drafts-bol.component';

describe('DraftsBolComponent', () => {
  let component: DraftsBolComponent;
  let fixture: ComponentFixture<DraftsBolComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DraftsBolComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DraftsBolComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
