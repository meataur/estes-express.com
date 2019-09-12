import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NonGuaranteedNextStepsComponent } from './non-guaranteed-next-steps.component';

describe('NonGuaranteedNextStepsComponent', () => {
  let component: NonGuaranteedNextStepsComponent;
  let fixture: ComponentFixture<NonGuaranteedNextStepsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NonGuaranteedNextStepsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NonGuaranteedNextStepsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
