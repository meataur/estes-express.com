import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AccessorialComponent } from './accessorial.component';

describe('AccessorialComponent', () => {
  let component: AccessorialComponent;
  let fixture: ComponentFixture<AccessorialComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AccessorialComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccessorialComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
