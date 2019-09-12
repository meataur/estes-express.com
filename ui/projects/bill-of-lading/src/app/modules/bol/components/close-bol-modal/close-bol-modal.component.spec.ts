import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CloseBolModalComponent } from './close-bol-modal.component';

describe('CloseBolModalComponent', () => {
  let component: CloseBolModalComponent;
  let fixture: ComponentFixture<CloseBolModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CloseBolModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CloseBolModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
