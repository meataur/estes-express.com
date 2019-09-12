import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TosModalComponent } from './tos-modal.component';

describe('TosModalComponent', () => {
  let component: TosModalComponent;
  let fixture: ComponentFixture<TosModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TosModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TosModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
