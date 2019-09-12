import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RequestAccountNumberComponent } from './request-account-number.component';

describe('RequestAccountNumberComponent', () => {
  let component: RequestAccountNumberComponent;
  let fixture: ComponentFixture<RequestAccountNumberComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RequestAccountNumberComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RequestAccountNumberComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
