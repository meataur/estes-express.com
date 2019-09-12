import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadAddressBookComponent } from './upload-address-book.component';

describe('UploadAddressBookComponent', () => {
  let component: UploadAddressBookComponent;
  let fixture: ComponentFixture<UploadAddressBookComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UploadAddressBookComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UploadAddressBookComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
