import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GuaranteedOptionMessageComponent } from './guaranteed-option-message.component';

describe('GuaranteedOptionMessageComponent', () => {
  let component: GuaranteedOptionMessageComponent;
  let fixture: ComponentFixture<GuaranteedOptionMessageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GuaranteedOptionMessageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GuaranteedOptionMessageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
