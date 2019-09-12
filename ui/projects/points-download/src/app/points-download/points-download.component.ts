import { AfterViewInit, Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PointsDownloadService } from '../services/points-download.service';
import { Observable, Subscription } from 'rxjs';
import {
  validateFormFields,
  FormService,
  patternValidator,
  RegEx,
  MessageConstants,
  SnackbarService,
  FeedbackTypes,
  MyEstesValidators
} from 'common';
import { HttpErrorResponse } from '@angular/common/http';
import { LeftNavigationService, PromoService } from 'common';

@Component({
  selector: 'app-points-download',
  templateUrl: './points-download.component.html',
  styleUrls: ['./points-download.component.scss']
})
export class PointsDownloadComponent implements OnInit, OnDestroy, AfterViewInit {
  loading = false;
  formGroup: FormGroup;
  states$: Observable<Array<string>>;
  criteriaSub: Subscription;
  formSubmissionSub: Subscription;
  getErrorMessage: (FormControl) => string;
  errorMessage: [FeedbackTypes, string];

  constructor(
    private fb: FormBuilder,
    private pdService: PointsDownloadService,
    private formService: FormService,
    private snackbarService: SnackbarService,
    private leftNavigationService: LeftNavigationService,
    private promoService: PromoService,
  ) {
    this.getErrorMessage = this.formService.getErrorMessage;
  }

  ngOnDestroy() {
    if (this.criteriaSub) {
      this.criteriaSub.unsubscribe();
    }
    if (this.formSubmissionSub) {
      this.formSubmissionSub.unsubscribe();
    }
  }
  ngAfterViewInit() {
    this.leftNavigationService.setNavigation(`Ship`, `/ship`);
    this.promoService.setAppId('points-download');
    this.promoService.setAppState('any');
  }

  ngOnInit() {
    this.formGroup = this.fb.group({
      email: [
        '',
        [MyEstesValidators.required, patternValidator(RegEx.email, MessageConstants.invalidEmail)]
      ],
      fileFormat: ['', MyEstesValidators.required],
      criteria: ['', MyEstesValidators.required],
      sort: ['zip', MyEstesValidators.required],
      state: ['']
    });

    this.criteriaSub = this.criteria.valueChanges.subscribe(val => {
      switch ((val as String).toLowerCase()) {
        case 'us':
        case 'cn':
          this.states$ = this.pdService.getStates(val);
          this.state.reset('');
          this.state.setValidators(MyEstesValidators.required);
          break;
        default:
          this.state.reset('');
          this.state.setValidators([]);
          this.state.updateValueAndValidity();
          break;
      }
    });

    this.leftNavigationService.setNavigation(`Ship`, `/ship`);
    
  }

  get email() {
    return this.formGroup.controls['email'];
  }
  get fileFormat() {
    return this.formGroup.controls['fileFormat'];
  }
  get criteria() {
    return this.formGroup.controls['criteria'];
  }
  get sort() {
    return this.formGroup.controls['sort'];
  }
  get state() {
    return this.formGroup.controls['state'];
  }

  onSubmit() {
    this.errorMessage = null;
    if (this.formGroup.valid) {
      this.loading = true;
      this.formSubmissionSub = this.pdService
        .downloadPoints(
          this.criteria.value,
          this.email.value,
          this.fileFormat.value,
          this.sort.value,
          this.state.value
        )
        .subscribe(
          res => {
            this.snackbarService.success(res.message);
          },
          err => {
            if (err) {
              this.errorMessage = ['error', err];
            }
            this.loading = false;
          },
          () => (this.loading = false)
        );
    } else {
      validateFormFields(this.formGroup);
      this.snackbarService.validationError();
    }
  }
}
