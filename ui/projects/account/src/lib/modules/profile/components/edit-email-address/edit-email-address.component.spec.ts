import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditEmailAddressComponent } from './edit-email-address.component';

describe('EditEmailAddressComponent', () => {
  let component: EditEmailAddressComponent;
  let fixture: ComponentFixture<EditEmailAddressComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditEmailAddressComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditEmailAddressComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
