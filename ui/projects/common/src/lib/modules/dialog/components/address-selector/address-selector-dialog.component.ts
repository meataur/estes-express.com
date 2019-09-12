import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { AddressBookService } from '../../services/address-book.service';
import { BookAddress } from '../../models/book-address.model';
import {animate, state, style, transition, trigger} from '@angular/animations';
import { FormControl, FormGroup, FormBuilder } from '@angular/forms';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';



@Component({
  selector: 'address-selector-dialog',
  templateUrl: './address-selector-dialog.component.html',
  styleUrls: ['./address-selector-dialog.component.scss'],
  animations: [
    trigger('searchExpand', [
      state('collapsed, void', style({ height: '0px', minHeight: '0', display: 'none', border: 'none' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('0.15s ease-in')),
      transition('collapsed <=> void', animate('0.15s ease-out'))
    ])
  ]
})
export class AddressSelectorDialogComponent implements OnInit {

  public addressListResponse = new MatTableDataSource<BookAddress>();

  public displayedColumns: string[];
  public loading: boolean;
  public loadingSearch: boolean;
  public advancedSearchCriteria: FormGroup;
  public activeAdvancedSearch: boolean;
  public expandSearch: boolean = false;


  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor( public dialogRef: MatDialogRef<AddressSelectorDialogComponent>,
               @Inject(MAT_DIALOG_DATA) public data: BookAddress[],
               public addressBookService: AddressBookService,
               private fb: FormBuilder ) {
    this.loading = true;
    this.displayedColumns = ['company', 'address', 'shipper', 'consignee', 'thirdParty', 'cod', 'contact', 'locationNumber', 'mobileView'];
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  selectAddress(address: BookAddress): void {
    this.dialogRef.close(address);
  }

  ngOnInit() {
    this.initializeAdvancedSearchForm();

    this.addressBookService.getAddressList()
    .subscribe(data => {
      this.addressListResponse = new MatTableDataSource(data);
      this.addressListResponse.sort = this.sort;
      this.addressListResponse.paginator = this.paginator;

      this.initializeFilterPredicate();
        
      this.addressListResponse.sortingDataAccessor = (item, property) => {
        switch(property) {
          case 'address': return item.address.streetAddress;
          case 'contact': return item.firstName;
          default: return item[property];
        }
      };
      this.loading = false;
    }, err => {
      this.loading = false;
    });
  }
  applyFilter(filterValue: string) {
    if (!this.activeAdvancedSearch) {
      filterValue = filterValue.trim(); // Remove whitespace
      filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
      this.addressListResponse.filter = filterValue;
    }
  }
  initializeAdvancedSearchForm() {
    this.advancedSearchCriteria = this.fb.group({
      city: [''],
      cityExact: [false],
      company: [''],
      companyExact: [false],
      location: [''],
      locationExact: [false],
      state: [''],
      stateExact: [false],
      street: [''],
      streetExact: [false],
      user: [''],
      zip: [''],
      zipExact: [false]
    }, {
      validator: this.atLeastOne
    });
  }

  get searchCity() {
    return this.advancedSearchCriteria.controls['city'] as FormControl;
  }
  get searchCityExact() {
    return this.advancedSearchCriteria.controls['cityExact'] as FormControl;
  }
  get searchCompany() {
    return this.advancedSearchCriteria.controls['company'] as FormControl;
  }
  get searchCompanyExact() {
    return this.advancedSearchCriteria.controls['companyExact'] as FormControl;
  }
  get searchLocation() {
    return this.advancedSearchCriteria.controls['location'] as FormControl;
  }  
  get searchLocationExact() {
    return this.advancedSearchCriteria.controls['locationExact'] as FormControl;
  }  
  get searchState() {
    return this.advancedSearchCriteria.controls['state'] as FormControl;
  }  
  get searchStateExact() {
    return this.advancedSearchCriteria.controls['stateExact'] as FormControl;
  }  
  get searchStreet() {
    return this.advancedSearchCriteria.controls['street'] as FormControl;
  }  
  get searchStreetExact() {
    return this.advancedSearchCriteria.controls['streetExact'] as FormControl;
  }  
  get searchZip() {
    return this.advancedSearchCriteria.controls['zip'] as FormControl;
  }
  get searchZipExact() {
    return this.advancedSearchCriteria.controls['zipExact'] as FormControl;
  }

  initializeFilterPredicate() {
    this.addressListResponse.filterPredicate =
    (data: BookAddress, filter: string) => {
      filter = filter.toLowerCase();
      return (data.firstName.toLowerCase().indexOf(filter) > -1) ||
      (data.lastName.toLowerCase().indexOf(filter) > -1) ||
      ((data.firstName.toLowerCase() + ' ' + data.lastName.toLowerCase()).indexOf(filter) > -1) ||
      (data.company.toLowerCase().indexOf(filter) > -1) ||
      (data.locationNumber.toLowerCase().indexOf(filter) > -1) ||
      (data.address.streetAddress.toLowerCase().indexOf(filter) > -1) ||
      (data.address.streetAddress2.toLowerCase().indexOf(filter) > -1) ||
      (data.address.city.toLowerCase().indexOf(filter) > -1) ||
      (data.address.country.toLowerCase().indexOf(filter) > -1) ||
      (data.address.state.toLowerCase().indexOf(filter) > -1) ||
      (data.address.zip.toLowerCase().indexOf(filter) > -1);
    };
    this.addressListResponse.filter = '';
  }

  advancedSearch() {
    this.addressListResponse.filterPredicate =
    (data: BookAddress, filter: string) => {

      let filterResult = true;
      filterResult = this.advancedFilter(this.searchCity.value, data.address.city, filterResult, this.searchCityExact.value);
      filterResult = this.advancedFilter(this.searchCompany.value, data.company, filterResult, this.searchCompanyExact.value);
      filterResult = this.advancedFilter(this.searchLocation.value, data.locationNumber, filterResult, this.searchLocationExact.value);
      filterResult = this.advancedFilter(this.searchStreet.value, data.address.streetAddress, filterResult, this.searchStreetExact.value);
      filterResult = this.advancedFilter(this.searchState.value, data.address.state, filterResult, this.searchStateExact.value);
      filterResult = this.advancedFilter(this.searchZip.value, data.address.zip, filterResult, this.searchZipExact.value);

      return filterResult;

    };
    this.addressListResponse.filter = 'triggerFilter'; //trigger the filterPredicate function by updating the fitler
    this.activeAdvancedSearch = true;
    this.expandSearch = false;
  }
  advancedFilter(filterValue: string, dataValue: string, filterResult: boolean, filterExact: string) {
    if (filterValue) {
      if (filterExact) {
        return filterResult && dataValue.toLowerCase() === filterValue.toLowerCase();
      } else {
        return filterResult && dataValue.toLowerCase().indexOf(filterValue.toLowerCase()) > -1;
      }
    } else {
      return filterResult;
    }
  }
  clearAdvancedSearch() {
    this.initializeAdvancedSearchForm();
    this.initializeFilterPredicate();
    this.activeAdvancedSearch = false;
  }

  atLeastOne(group: FormGroup) {
    const city = group.controls.city.value;
    const street = group.controls.street.value;
    const location = group.controls.location.value;
    const company = group.controls.company.value;
    const state = group.controls.state.value;
    const zip = group.controls.zip.value;

    return (city || street || location || company || state || zip) ? null : { value: 'No Search Criteria Entered' };
  }
}
