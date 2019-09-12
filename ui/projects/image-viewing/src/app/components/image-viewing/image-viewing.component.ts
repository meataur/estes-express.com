import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormGroupDirective } from '@angular/forms';
import { MatDialog, MatDialogConfig } from '@angular/material';

import { ImageViewingService } from '../../services/image-viewing.service';
import {
  FormService,
  SnackbarService,
  validateFormFields,
  Masks,
  RegEx,
  MessageConstants,
  FeedbackTypes,
  patternValidator,
  MyEstesValidators,
  EmailDialogData
} from 'common';
import { ImageType, ImageRequest, ImageResult } from '../../models';
import { EmailDialogComponent } from '../email-dialog/email-dialog.component';
import { Observable, Subscription } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { tap, map } from 'rxjs/operators';
import { AuthService, PromoService, LeftNavigationService, EmailDialogService } from 'common';
import { MatTableDataSource, MatSort, MatPaginator } from '@angular/material';
import { Router } from '@angular/router';
import { SelectionModel } from '@angular/cdk/collections';

@Component({
  selector: 'app-image-viewing',
  templateUrl: './image-viewing.component.html',
  styleUrls: ['./image-viewing.component.scss'],
  providers: [ImageViewingService]
})
export class ImageViewingComponent implements OnInit, OnDestroy {
  @ViewChild(FormGroupDirective) myForm;
  errorMessage: [FeedbackTypes, string];
  formSubmissionSub: Subscription;
  imageSelections: SelectionModel<ImageResult>;

  displayedColumns = ['select', 'searchData', 'pro', 'docType', 'status'];
  // results: Array<ImageResult>;
  resultsLength = 0;
  pageSize = 25;
  dataSource = new MatTableDataSource<ImageResult>();
  noData = this.dataSource.connect().pipe(map(data => data.length === 0));
  searchTypes: Array<{ text: string; value: string }> = [
    {
      text: 'PRO Number',
      value: 'F'
    },
    {
      text: 'Bill of Lading Number (LTL shipments only)',
      value: 'B'
    },
    {
      text: 'Purchase Order Number (LTL shipments only)',
      value: 'P'
    },
    {
      text: 'Interline PRO Number',
      value: 'F'
    }
  ];
  loading = false;
  formGroup: FormGroup;
  imageTypes$: Observable<Array<ImageType>>;
  mask = Masks.phone;
  showResults: boolean;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    private imageViewingService: ImageViewingService,
    private fb: FormBuilder,
    private snackbarService: SnackbarService,
    private dialog: MatDialog,
    public formService: FormService,
    private router: Router,
    private authService: AuthService,
    public leftNavigationService: LeftNavigationService,
    public promoService: PromoService,
    private emailDialogService: EmailDialogService
  ) {}

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.dataSource.sortingDataAccessor = (item, property) => {
      switch (property) {
        case 'pro':
          return `${item.key1}-${item.key2}`;
        default:
          return item[property];
      }
    };

    this.dataSource.filterPredicate = (data: ImageResult, filter: string) => {
      return (
        `${data.key1}-${data.key2}`.indexOf(filter) !== -1 ||
        data.docType.indexOf(filter) !== -1 ||
        data.searchData.indexOf(filter) !== -1
      );
    };

    this.imageSelections = new SelectionModel<ImageResult>(true, []);

    this.formGroup = this.fb.group({
      documentType: null,
      searchType: this.searchTypes[0],
      searchItems: ['', [MyEstesValidators.required]],
      viewOrFax: 'V',
      faxNumber: '',
      attention: '',
      companyName: ''
    });

    this.imageTypes$ = this.imageViewingService
      .getImageTypes()
      .pipe(tap(next => this.documentType.setValue(next[0].name)));

    this.viewOrFax.valueChanges.subscribe(next => {
      next = next.toUpperCase();
      if (next === 'F') {
        this.faxNumber.setValidators([
          MyEstesValidators.required,
          patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone)
        ]);
      } else if (next === 'V') {
        this.faxNumber.reset('');
        this.faxNumber.clearValidators();
        this.faxNumber.updateValueAndValidity();
        this.attention.reset('');
        this.companyName.reset('');
      }
    });

    this.leftNavigationService.setNavigation(`Manage`, `/manage`);
    this.promoService.setAppId('image-viewing');
    this.promoService.setAppState('authenticated');
    
    this.authService.authStateSet.subscribe((authState: string) => {
      if ('unauthenticated' === authState) {
        this.router.navigate(['login']);
      }
    });
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  ngOnDestroy() {
    if (this.formSubmissionSub) {
      this.formSubmissionSub.unsubscribe();
    }
  }

  get documentType() {
    return this.formGroup.controls['documentType'];
  }
  get viewOrFax() {
    return this.formGroup.controls['viewOrFax'];
  }
  get searchType() {
    return this.formGroup.controls['searchType'];
  }
  get searchItems() {
    return this.formGroup.controls['searchItems'];
  }
  get faxNumber() {
    return this.formGroup.controls['faxNumber'];
  }
  get attention() {
    return this.formGroup.controls['attention'];
  }
  get companyName() {
    return this.formGroup.controls['companyName'];
  }

  isSelected(row) {
    for (let s of this.imageSelections.selected) {
        if (s['searchData'] == row.searchData) {
            return true;
        }
    }
  }

  openEmailModal() {

    const config: MatDialogConfig = {
      data: {
        selections: this.imageSelections.selected
      },
      disableClose: false,
      width: '50rem',
      panelClass: ['estes-modal', 'estes-app']
    };

    this.dialog.open(EmailDialogComponent, config);
  }

  onSubmit() {
    this.errorMessage = null;
    if (this.formGroup.valid) {
      this.myForm.resetForm(this.formGroup.value);
      this.loading = true;

      if (this.documentType.value === 'WR') {
        window.location.href = `/myestes/weight-and-research?searchType=${
          this.searchType.value.value
        }&criteria=${this.searchItems.value}`;
      }

      const payload = new ImageRequest();
      payload.documentType = this.documentType.value;
      payload.searchType = this.searchType.value.value;
      payload.requestType = this.viewOrFax.value;
      payload.searchTerm = this.searchItems.value;
      payload.faxInfo = {
        attention: this.attention.value,
        companyName: this.companyName.value,
        faxNumber: this.faxNumber.value
      };

      this.imageSelections.clear();

      this.formSubmissionSub = this.imageViewingService.getImages(payload).subscribe(
        res => {
          this.paginator.firstPage();
          this.showResults = true;
          this.dataSource.data = res;
          if (res) {
            let numValidResults = 0;
            let numErrors = 0;
            for (const item of res) {
              if (item.errorMessage) {
                numErrors += 1;
              } else {
                numValidResults += 1;
              }
            }
            if (numValidResults > 0) {
              if (numErrors > 0) {
                this.snackbarService.success(
                  `Some errors were found. See the search results in the table below.`
                );
              } else {
                this.snackbarService.success(`See the status of each document in the table below.`);
              }
            } else if (numErrors > 0) {
              this.snackbarService.error(
                `No valid results returned. Check the results table below for errors.`
              );
            }
          }
        },
        err => {
          if (err instanceof HttpErrorResponse) {
            if (err.status === 400) {
              this.errorMessage = ['error', err.error[0].message];
            } else if (err.status === 500) {
              this.errorMessage = ['error', `An unexpected error occurred.  Please try again.`];
            }
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
