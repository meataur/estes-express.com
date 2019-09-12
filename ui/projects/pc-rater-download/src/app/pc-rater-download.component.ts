import { AfterViewInit, Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
// import { PointsDownloadService } from '../services/points-download.service';
import { Subscription, merge } from 'rxjs';
import {
  FormService,
  patternValidator,
  validateFormFields,
  RegEx,
  MessageConstants,
  Masks,
  AuthService,
  SnackbarService,
  DialogService,
  UtilService } from 'common';

import { startWith, switchMap } from 'rxjs/operators';

import { PcRaterDownloadService } from './pc-rater-download.service';
import { ActivatedRoute } from '@angular/router';
import { FeedbackTypes } from '../../../common/src/public_api';
import { environment } from '../environments/environment';

@Component({
  selector: 'app-pc-rater-download',
  templateUrl: './pc-rater-download.component.html',
  styleUrls: ['./pc-rater-download.component.scss']
})
export class PcRaterDownloadComponent implements OnInit {

  formGroup: FormGroup;

  proMask = Masks.pronumber;
  phoneMask = Masks.phone;
  proNumberSub: Subscription;

  errorMessages: [string, any];
  successMessage: [FeedbackTypes, string];
  loading: boolean;
  isAuthenticatedGroup: boolean;
  downloadUrl: string;
  downloadMessage: string;

  accountNumberTooltipHtml = `<p>Be sure to enter the correct account information, failure to do so may result in downloading the incorrect version of the rating software. If you do not know your account number <a href="/myestes/pc-rater-download/request-account-number" target="_blank">click here</a>.</p>`;


  constructor(
    private fb: FormBuilder,
    private pcRaterDownloadService: PcRaterDownloadService,
    private route: ActivatedRoute,
    private authService: AuthService,
    private utilService: UtilService,
    private snackbarService: SnackbarService,
    private dialogService: DialogService,
    public formService: FormService,
  ) { }

  ngOnInit() {

    this.formGroup = this.fb.group({
      accountCode: ['', [Validators.required, Validators.maxLength(7), Validators.minLength(7)]],
      contactName: ['', [Validators.required, Validators.maxLength(100)]],
      companyName: ['', [Validators.required, Validators.maxLength(50)]],
      customerAddress: ['', [Validators.required, Validators.maxLength(50)]],
      customerCity: ['', [Validators.required, Validators.maxLength(50)]],
      customerPhone: ['', Validators.required],
      customerState: ['', [Validators.required, Validators.maxLength(2)]],
      customerZip: ['', [Validators.required, Validators.maxLength(6)]],
      email: ['', [Validators.required, patternValidator(RegEx.email, MessageConstants.invalidEmail)]],
      emailReceptive: [true],
      extension: ['', Validators.maxLength(5)],
      salespersonReceptive: [true],
      z4: ['', [Validators.maxLength(4)]]
    });

    
    this.isAuthenticatedGroup = this.authService.isLoggedIn && !this.authService.isLocalAccount;

    

  }

  ngOnDestroy() {

  }

  get accountCode() {
    return this.formGroup.controls['accountCode'];
  }
  get contactName() {
    return this.formGroup.controls['contactName'];
  }
  get companyName() {
    return this.formGroup.controls['companyName'];
  }
  get customerAddress() {
    return this.formGroup.controls['customerAddress'];
  }
  get customerCity() {
    return this.formGroup.controls['customerCity'];
  }
  get customerPhone() {
    return this.formGroup.controls['customerPhone'];
  }
  get customerState() {
    return this.formGroup.controls['customerState'];
  }
  get customerZip() {
    return this.formGroup.controls['customerZip'];
  }
  get email() {
    return this.formGroup.controls['email'];
  }
  get emailReceptive() {
    return this.formGroup.controls['emailReceptive'];
  }
  get extension() {
    return this.formGroup.controls['extension'];
  }
  get salespersonReceptive() {
    return this.formGroup.controls['salespersonReceptive'];
  }
  get z4() {
    return this.formGroup.controls['z4'];
  }

  openInfoModal(title: string, html: string) {
    this.dialogService.info(title, html);
  }

  onSubmit() {
    if (this.formGroup.valid) {
      this.loading = true;
      this.pcRaterDownloadService.submitRequest(this.formGroup.getRawValue())
      .subscribe(res => {
        if (res.link) {
          this.successMessage = ['info', 'The files for this version of the Rating Software are contained in one self-extracting executable.  Download the file, find it in Windows Explorer and double-click it.  The software will install automatically.'];
          this.snackbarService.success('');
          this.downloadUrl = environment.serviceBaseUrl + res.link;
          this.downloadMessage = res.message;
          window.scrollTo({ top: 0, behavior: 'smooth' });
        } else {
          this.errorMessages = ['error', [`We are unable to determine the download your account type requires, please email us at <a href="mailto:ratedisk@estes-express.com">ratedisk@estes-express.com</a>.`]];
        }
        this.loading = false;
       
      }, err => {
        this.errorMessages = ['error', err];
        this.loading = false;
      });
    } else {
      validateFormFields(this.formGroup);
      this.snackbarService.validationError();
    }
  }

}

