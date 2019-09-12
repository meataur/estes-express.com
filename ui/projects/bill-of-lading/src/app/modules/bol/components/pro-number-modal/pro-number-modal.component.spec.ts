import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProNumberModalComponent } from './pro-number-modal.component';

describe('ProNumberModalComponent', () => {
  let component: ProNumberModalComponent;
  let fixture: ComponentFixture<ProNumberModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProNumberModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProNumberModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
