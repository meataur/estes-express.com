import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GuaranteedNextStepsComponent } from './guaranteed-next-steps.component';

describe('GuaranteedNextStepsComponent', () => {
  let component: GuaranteedNextStepsComponent;
  let fixture: ComponentFixture<GuaranteedNextStepsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GuaranteedNextStepsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GuaranteedNextStepsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
