import { Component, Inject, OnInit, OnDestroy } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatTableDataSource } from '@angular/material';
import { BookAddress } from '../../book-address.model';
import { isDefined } from '@ng-bootstrap/ng-bootstrap/util/util';
import { AddressBookService } from '../../address-book.service';
import {NgForm} from '@angular/forms';
import { FeedbackTypes, Masks, RegEx, MessageConstants, patternValidator, SnackbarService, FormService, MyEstesFormatters, MyEstesValidators } from 'common';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { Subscription } from 'rxjs';

@Component({
  selector: 'address-dialog',
  templateUrl: './address-dialog.component.html',
  styleUrls: []
})
export class AddressDialogComponent implements OnInit, OnDestroy {

  public readOnlyMode: boolean = true;
  public requestType: string; //add or edit
  public bookAddress: BookAddress;
  public formGroup: FormGroup;

  public countries: any;
  public states: String[];
  public stateLabelText: String;
  public zipLabelText: String;

  public loading: boolean;
  public errorMessages: [FeedbackTypes, string];
  private addressSub: Subscription;

  public phoneMask = Masks.phone;

  constructor(public dialogRef: MatDialogRef<AddressDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              public formService: FormService,
              public addressBookService: AddressBookService,
              private fb: FormBuilder,
              private snackbarService: SnackbarService){}

  ngOnInit() {
    this.bookAddress = this.data.bookAddress;
    this.requestType = this.data.requestType;
    this.readOnlyMode = !(this.requestType === 'edit' || this.requestType === 'add');

    this.readOnlyMode = !(this.requestType === 'edit' || this.requestType === 'add');

    this.initForm();

    //If no country selected, set to United States
    this.bookAddress.address.country = this.bookAddress.address.country || 'US';
    this.countrySelected(this.bookAddress.address.country);

    this.countries = [
      {viewValue: 'United States', value: 'US'},
      {viewValue: 'Canada', value: 'CN'}
    ];

    this.errorMessages = null;
  }

  ngOnDestroy() {
    if (this.addressSub) {
      this.addressSub.unsubscribe();
    }
  }

  initForm() {
    this.formGroup = this.fb.group({
      company: [this.bookAddress.company || '', [MyEstesValidators.required, Validators.maxLength(30)]],
      firstName: [this.bookAddress.firstName || '', Validators.maxLength(25)],
      lastName: [this.bookAddress.lastName || '', Validators.maxLength(25)],
      email: [this.bookAddress.email || '', patternValidator(RegEx.email, MessageConstants.invalidEmail)],
      locationNumber: [this.bookAddress.locationNumber || '', Validators.maxLength(10)],
      shipper: [this.bookAddress.shipper || false],
      consignee: [this.bookAddress.consignee || false],
      thirdParty: [this.bookAddress.thirdParty || false],
      cod: [this.bookAddress.cod || false],
      address: this.fb.group({
        country: [this.bookAddress.address.country || '', MyEstesValidators.required],
        streetAddress: [this.bookAddress.address.streetAddress || '', [MyEstesValidators.required, Validators.maxLength(30)]],
        streetAddress2: [this.bookAddress.address.streetAddress2 || '', Validators.maxLength(30)],
        city: [this.bookAddress.address.city || '', [MyEstesValidators.required, Validators.maxLength(30)]],
        state: [this.bookAddress.address.state || '', MyEstesValidators.required],
        zip: [this.bookAddress.address.zip, [MyEstesValidators.required, Validators.maxLength(6)]]
      }),
      phone: [MyEstesFormatters.formatPhone(this.bookAddress.phone) || '', [MyEstesValidators.required, patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone)]],
      phoneExt: [this.bookAddress.phoneExt || '', Validators.maxLength(4)],
      fax: [MyEstesFormatters.formatPhone(this.bookAddress.fax) || '',  patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone)],
      seq: [this.bookAddress.seq || '']
    });
  }

  get company() {
    return this.formGroup.controls['company'];
  }

  get firstName() {
    return this.formGroup.controls['firstName'];
  }

  get lastName() {
    return this.formGroup.controls['lastName'];
  }

  get email() {
    return this.formGroup.controls['email'];
  }

  get locationNumber() {
    return this.formGroup.controls['locationNumber'];
  }

  get shipper() {
    return this.formGroup.controls['shipper'];
  }

  get consignee() {
    return this.formGroup.controls['consignee'];
  }

  get thirdParty() {
    return this.formGroup.controls['thirdParty'];
  }

  get cod() {
    return this.formGroup.controls['cod'];
  }

  get country() {
    return this.formGroup.get('address.country');
  }

  get streetAddress() {
    return this.formGroup.get('address.streetAddress')
  }

  get streetAddress2() {
    return this.formGroup.get('address.streetAddress2');
  }

  get city() {
    return this.formGroup.get('address.city');
  }

  get state() {
    return this.formGroup.get('address.state');
  }

  get zip() {
    return this.formGroup.get('address.zip');
  }

  get phone() {
    return this.formGroup.controls['phone'];
  }

  get phoneExt() {
    return this.formGroup.controls['phoneExt'];
  }

  get fax() {
    return this.formGroup.controls['fax'];
  }


  onNoClick(): void {
    this.dialogRef.close();
  }

  editBookAddress(): void {
    this.readOnlyMode = false;
    this.requestType = 'edit';
  }

  onSubmit() {
    if (this.formGroup.valid) {
      this.loading = true;
      this.errorMessages = null;
      if (this.requestType == 'add') {
        this.addressSub = this.addressBookService.addAddress(this.formGroup.value)
        .subscribe((data) => {
          this.dialogRef.close(data);
          this.loading = false;
        },
        err => {
          this.errorMessages = ['error', err[0]];
          this.loading = false;
        });
      } else {
        this.addressSub = this.addressBookService.updateAddress(this.formGroup.value)
        .subscribe((data) => {
          this.dialogRef.close(data);
          this.loading = false;
        },
        err => {
          this.errorMessages = ['error', err[0]];
          this.loading = false;
        });
      }
    } else {
      this.snackbarService.validationError();
    }
  }

  countrySelected( cntry: String ): void {
    if (cntry == 'CN') {
      this.states = this.data.provincesList;
      this.stateLabelText = 'Province';
      this.zipLabelText = 'Postal Code';
    } else {
      this.states = this.data.unitedStatesList;
      this.stateLabelText = 'State';
      this.zipLabelText = 'ZIP';
    }

    //this.state.reset();
  }
}
