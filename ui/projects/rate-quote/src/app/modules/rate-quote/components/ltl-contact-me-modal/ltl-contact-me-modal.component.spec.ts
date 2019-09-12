import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LtlContactMeModalComponent } from './ltl-contact-me-modal.component';

describe('LtlContactMeModalComponent', () => {
  let component: LtlContactMeModalComponent;
  let fixture: ComponentFixture<LtlContactMeModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LtlContactMeModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LtlContactMeModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
