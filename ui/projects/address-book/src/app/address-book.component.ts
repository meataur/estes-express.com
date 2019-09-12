import { Component, OnInit, ViewChild } from '@angular/core';
import { BookAddress } from './book-address.model';
import { AddressBookService } from './address-book.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatPaginator, MatSort, MatTableDataSource, MatDialog } from '@angular/material';
import { AddressDialogComponent } from './dialog-components/address-details/address-dialog.component';
import { DeleteAddressDialogComponent } from './dialog-components/delete-address/delete-address-dialog.component';
import { AddressSearch } from './address-search.model';
import { UtilService, SnackbarService, FormService, AuthService, LeftNavigationService, PromoService } from 'common';
import { forkJoin } from 'rxjs';
import { MatMenuTrigger } from '@angular/material';
import { FormControl, Validators, FormGroup, FormBuilder, FormArray } from '@angular/forms';
import {animate, state, style, transition, trigger} from '@angular/animations';


@Component({
  selector: 'app-address-book',
  templateUrl: './address-book.component.html',
  styleUrls: ['./address-book.component.scss'],
  animations: [
    trigger('searchExpand', [
      state('collapsed, void', style({ height: '0px', minHeight: '0', display: 'none', border: 'none' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('0.15s ease-in')),
      transition('collapsed <=> void', animate('0.15s ease-out'))
    ])
  ]
})
export class AddressBookComponent implements OnInit {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  public addressListResponse: MatTableDataSource<BookAddress>;
  public errorMessages: String[];
  public loading: boolean;
  public loadingSearch: boolean;
  public loadingReset: boolean;
  public expandSearch: boolean;
  public advancedSearchCriteria: FormGroup;
  public activeAdvancedSearch: boolean;

  // public filters: Object;

  public unitedStatesList: String[];
  public provincesList: String[];

  public displayedColumns: string[];
  public menuTrigger: MatMenuTrigger;

  constructor(
    public addressBookService: AddressBookService,
    public utilService: UtilService,
    public dialog: MatDialog,
    public snackbarService: SnackbarService,
	public leftNavigationService: LeftNavigationService,
	public promoService: PromoService,
	public authService: AuthService,
	public router: Router,
    public fb: FormBuilder,
    public formService: FormService
  ) {}



   atLeastOne(group: FormGroup) {
    const city = group.controls.city.value;
    const street = group.controls.streetAddress.value;
    const locationNumber = group.controls.locationNumber.value;
    const company = group.controls.company.value;
    const state = group.controls.state.value;
    const zip = group.controls.zip.value;

    return (city || street || locationNumber || company || state || zip) ? null : { value: 'No Search Criteria Entered' };
  }

  ngOnInit() {
    this.loading = true;
    this.displayedColumns = ['company', 'address', 'shipper', 'consignee', 'thirdParty', 'cod', 'contact', 'locationNumber', 'actions', 'mobileView'];
    this.addressListResponse = new MatTableDataSource();
    this.initializeAdvancedSearchForm();
    this.leftNavigationService.setNavigation(`My Estes`, `/menus/account`);
    this.promoService.setAppId('address-book');
    this.promoService.setAppState('authenticated');
    
 	this.authService.authStateSet.subscribe((authState: string) => {
      if ('unauthenticated' === authState) {
        this.router.navigate(['login']);
	  }
    });

    forkJoin(
      this.addressBookService.getAddressList(),
      this.utilService.getStates("US"),
      this.utilService.getStates("CN")
    )
    .subscribe(([res1, res2, res3]) => {
      this.setTableData(res1);
      this.unitedStatesList = res2;
      this.provincesList = res3;
      this.loading = false;
    },
    err => {
      this.errorMessages = err;
      this.loading = false;
    })
  }

  setTableData(data) {
    this.addressListResponse = new MatTableDataSource(data);
    this.addressListResponse.paginator = this.paginator;
    this.addressListResponse.sort = this.sort;

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

    this.addressListResponse.sortingDataAccessor = (item, property) => {
      switch(property) {
        case 'address': return item.address.streetAddress;
        case 'contact': return item.firstName;
        default: return item[property];
      }
    };
  }

  openAddressDetails(bookAddress?: BookAddress, requestType?: string) {
    let bookAddressToEdit : BookAddress = JSON.parse(JSON.stringify(bookAddress));
    const addressDialogRef = this.dialog.open(AddressDialogComponent, {
      width: '50rem',
      panelClass: ['estes-app', 'estes-modal'],
      data: {
        bookAddress: bookAddressToEdit,
        unitedStatesList: this.unitedStatesList,
        provincesList: this.provincesList,
        requestType: requestType //add, read or edit
      }
    });

    addressDialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.snackbarService.success(result.message);
        this.reloadAddressBook();
      }
    });
  }

  deleteAddress(bookAddress?: BookAddress) {
    const deleteDialogRef = this.dialog.open(DeleteAddressDialogComponent, {
      width: '28rem',
      panelClass: ['estes-app', 'estes-modal'],
      data: {
        bookAddress: bookAddress
      }
    });

    deleteDialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loading = true;
        this.addressBookService.deleteAddress(result)
        .subscribe((result) => {
          this.snackbarService.success(result.message);
          this.reloadAddressBook();
        },
        err => {
          this.errorMessages = err;
          this.loading = false;
        })
      }
    });

  }

  initializeAdvancedSearchForm() {
    this.advancedSearchCriteria = this.fb.group({
      city: [''],
      cityContains: [true],
      cityExact: [false],
      company: [''],
      companyContains: [true],
      companyExact: [false],
      locationNumber: [''],
      locContains: [true],
      locExact: [false],
      state: [''],
      stateContains: [true],
      stateExact: [false],
      streetAddress: [''],
      streetContains: [true],
      streetExact: [false],
      user: [''],
      zip: [''],
      zipContains: [true],
      zipExact: [false]
    }, {
      validator: this.atLeastOne
    });
  }

  openNewAddress() {
    this.openAddressDetails(new BookAddress({}), 'add');
  }

  reloadAddressBook() {
    this.addressListResponse = new MatTableDataSource();
    this.loading = true;
    this.addressBookService.getAddressList()
    .subscribe(data => {
      this.setTableData(data);
      this.loading = false;
    },
    err => {
      this.errorMessages = err;
      this.loading = false;
    })
  }
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.addressListResponse.filter = filterValue;
  }
  advancedSearch() {
    this.loadingSearch = true;
    this.addressBookService.search(this.advancedSearchCriteria.value)
    .subscribe(data => {
      this.addressListResponse = new MatTableDataSource(data);
      this.loadingSearch = false;
      this.activeAdvancedSearch = true;
      this.expandSearch = false;
    },
    err => {
      this.errorMessages = err;
      this.loadingSearch = false;
    })
  }
  clearAdvancedSearch() {
    this.loadingReset = true;
    this.addressBookService.getAddressList()
    .subscribe(data => {
      this.addressListResponse = new MatTableDataSource(data);
      this.loadingReset = false;
      this.initializeAdvancedSearchForm();
      this.activeAdvancedSearch = false;
    },
    err => {
      this.errorMessages = err;
      this.loadingReset = false;
    })
  }

  openAddressMenu(trigger) {
    this.menuTrigger = trigger;
    this.menuTrigger.openMenu();
  }
  closeAddressMenu() {
    if (this.menuTrigger.menuOpen) {
      this.menuTrigger.toggleMenu();
      this.menuTrigger = null;
    }
  }
}
