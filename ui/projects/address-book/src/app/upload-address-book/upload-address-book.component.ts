import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormControl, Validators, FormGroup, FormBuilder } from '@angular/forms';
import { FormService, SnackbarService, MyEstesValidators } from 'common';
import { AddressBookService } from '../address-book.service';

@Component({
  selector: 'app-upload-address-book',
  templateUrl: './upload-address-book.component.html',
  styleUrls: ['./upload-address-book.component.scss']
})
export class UploadAddressBookComponent implements OnInit {

  maxFileSize: number;
  formData: FormData;
  formGroup: FormGroup;
  submitLoading: boolean;
  errorMessages: string[];
  acceptedFileTypes: string;


  constructor(
    public formService: FormService,
    private fb: FormBuilder,
    public addressBookService: AddressBookService,
    public snackbarService: SnackbarService,
    public router : Router) { }

  ngOnInit() {
    this.formGroup = this.fb.group({
      addressFile: [null, [MyEstesValidators.required]],
      uploadType: [null, [MyEstesValidators.required]]
    });
    this.formData = new FormData();
    this.acceptedFileTypes = ".csv";
    this.maxFileSize = 10485760;
  }

  get addressFile() {
    return this.formGroup.controls['addressFile'];
  }
  get uploadType() {
    return this.formGroup.controls['uploadType'];
  }

  validateFileSize(event, formField) {
    if (event.target.files && event.target.files[0]) {
      let file: File = event.target.files[0];
      let fileExt = file.name.split('.').pop();
      if (file.size > this.maxFileSize) {
        formField.reset();
        formField.setErrors({'maxSize': 'File exceeds size limit of 10MB'});
      } else if (!this.acceptedFileTypes.includes(fileExt)) {
        formField.reset();
        formField.setErrors({'fileType': 'Not an accepted file type.'});
      } else {
        formField.setErrors();
        this.formData.append('file', event.target.files[0]);
      }
    }
  }

  onSubmit() {
    if (!this.formGroup.invalid) {
      this.submitLoading = true;
      this.addressBookService.upload(this.formData, this.uploadType.value)
      .subscribe(data => {
        this.submitLoading = false;
        this.snackbarService.success('Your address file successfully uploaded.');
        this.errorMessages = null;
        this.router.navigate(['/']);
      },
      err => {
        this.submitLoading = false;
        this.errorMessages = ['error', err]
      })
    } else {
      this.snackbarService.validationError();
    }
  }

}
