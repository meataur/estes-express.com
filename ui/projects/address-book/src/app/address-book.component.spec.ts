import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TextMaskModule } from 'angular2-text-mask';
import { AccountModule } from 'account';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ServiceResponse } from 'account';

import { BookAddress } from './book-address.model';
import { AddressBookService } from './address-book.service';
import { UtilService } from 'common';
import { AddressBookComponent } from './address-book.component';

import { SharedModule } from 'common';
import { Address } from 'common';
import { AddressDialogComponent } from './dialog-components/address-details/address-dialog.component';
import { DeleteAddressDialogComponent } from './dialog-components/delete-address/delete-address-dialog.component';

import {
  MatAutocompleteModule,
  MatButtonModule,
  MatButtonToggleModule,
  MatCardModule,
  MatCheckboxModule,
  MatDialogModule,
  MatDividerModule,
  MatExpansionModule,
  MatFormFieldModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatPaginatorModule,
  MatProgressSpinnerModule,
  MatRadioModule,
  MatRippleModule,
  MatSelectModule,
  MatSortModule,
  MatTableModule,
  MatTreeModule
} from '@angular/material';

fdescribe('AddressBookComponent', () => {
  let component: AddressBookComponent;
  let fixture: ComponentFixture<AddressBookComponent>;

  let getAddressListSpy: jasmine.Spy;
  let getStatesByCountrySpy: jasmine.Spy;

  let testBookAddress: BookAddress;
  let testBookAddress2: BookAddress;
  let testAddress: Address;
  let testAddress2: Address;
  let testBookAddressList: BookAddress[];

  let addressBookService: any;
  let utilService: any;

  let testStatesList: string[];

  beforeEach(async(() => {

    addressBookService = jasmine.createSpyObj('AddressBookService', ['getAddressList', 'addAdress', 'deleteAddress', 'updateAddress']);
    utilService = jasmine.createSpyObj('UtilService', ['getStatesByCountry']);

    testAddress = {"streetAddress":"99 test ave","streetAddress2":"apt c","city":"richmond","state":"VA","zip":"23235","zip4":"","country":"US"};
    testAddress2 = {"streetAddress":"100 test ave","streetAddress2":"apt d","city":"richmond","state":"VA","zip":"23235","zip4":"","country":"US"};
    
    testBookAddress = new BookAddress({"user":"TEST1","seq":1,"shipper":false,"consignee":false,"thirdParty":true,"cod":false,"company":"TestCompany1","locationNumber":"1","firstName":"Will","lastName":"Toledo","address":testAddress,"phone":"0000000000","phoneExt":"","fax":"1230000000","email":""});
    testBookAddress2 = new BookAddress({"user":"TEST2","seq":2,"shipper":true,"consignee":true,"thirdParty":false,"cod":true,"company":"TestCompany2","locationNumber":"1","firstName":"Courtney","lastName":"Barnett","address":testAddress2,"phone":"9999999999","phoneExt":"","fax":"1234445555","email":"cbarn@yahoo.com"});

    testBookAddressList = [testBookAddress, testBookAddress2];

    testStatesList = ['AZ', 'KS', 'NC', 'NJ', 'VA'];

    getAddressListSpy = addressBookService.getAddressList.and.returnValue(of(testBookAddressList));
    getStatesByCountrySpy = utilService.getStates.and.returnValue(of(testStatesList));



    TestBed.configureTestingModule({
      declarations: [ AddressBookComponent, AddressDialogComponent, DeleteAddressDialogComponent ],
      providers: [
        { provide: UtilService, useValue: utilService },
        { provide: AddressBookService, useValue: addressBookService }
      ],
      imports: [
        AccountModule,
        SharedModule,
        MatAutocompleteModule,
        MatButtonModule,
        MatButtonToggleModule,
        MatCardModule,
        MatCheckboxModule,
        MatDialogModule,
        MatDividerModule,
        MatExpansionModule,
        MatFormFieldModule,
        MatGridListModule,
        MatIconModule,
        MatInputModule,
        MatListModule,
        MatMenuModule,
        MatPaginatorModule,
        MatProgressSpinnerModule,
        MatRadioModule,
        MatRippleModule,
        MatSelectModule,
        MatSortModule,
        MatTableModule,
        MatTreeModule,
        FormsModule,
        ReactiveFormsModule,
        TextMaskModule,
        NgbModule,
        RouterTestingModule.withRoutes([])
        ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddressBookComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('should call UtilService, AddressBookService, and set addressListResponse onInit', fakeAsync(() => {
    component.ngOnInit();
    expect(getStatesByCountrySpy.calls.any()).toBe(true, 'getStatesByCountry called');
    expect(getAddressListSpy.calls.any()).toBe(true, 'getAddressList called');
    expect(component.unitedStatesList).toBe(testStatesList);
    expect(component.provincesList).toBe(testStatesList);
    expect(component.addressListResponse.data).toBeDefined();
    expect(component.addressListResponse.data).toBe(testBookAddressList);
  }));
});