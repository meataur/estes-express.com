import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HorizontalOverflowComponent } from './horizontal-overflow.component';

describe('HorizontalOverflowComponent', () => {
  let component: HorizontalOverflowComponent;
  let fixture: ComponentFixture<HorizontalOverflowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HorizontalOverflowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HorizontalOverflowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
