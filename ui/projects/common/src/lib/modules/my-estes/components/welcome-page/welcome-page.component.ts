import { Component, OnInit, ViewChild, OnDestroy, Inject } from '@angular/core';
import { UtilService } from '../../../../util/services/util.service';
import { SubAccount } from '../../../../models/sub-account.interface';
import { MatSort, Sort, MatPaginator, MatTableDataSource } from '@angular/material';
import { merge, of, Subscription } from 'rxjs';
import { startWith, switchMap, map, catchError } from 'rxjs/operators';
import { FormBuilder, FormGroup, AbstractControl, Validators } from '@angular/forms';
import { FeedbackTypes } from '../../../components/models';
import { validateFormFields } from '../../../../util/validate-form-fields';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { APP_CONFIG } from '../../../../app.config';
import { AppConfig } from '../../../../models/app-config.interface';
import { AuthService } from '../../../auth/services/auth.service';

@Component({
  selector: 'lib-welcome-page',
  templateUrl: './welcome-page.component.html',
  styleUrls: ['./welcome-page.component.scss']
})
export class WelcomePageComponent implements OnInit, OnDestroy {
  formGroup: FormGroup;
  displayedColumns = ['accountNumber', 'name', 'city', 'state', 'zip'];
  results: MatTableDataSource<SubAccount> = new MatTableDataSource();
  tableSub: Subscription;
  totalSubAccounts: number;
  pageSize: number = 25;
  accountSearchSub: Subscription;
  feedback: [FeedbackTypes, string];
  loading = false;
  loadingSubmit = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    @Inject(APP_CONFIG) public appConfig: AppConfig,
    private utilService: UtilService,
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService
  ) {}

  onSubmit() {
    this.feedback = null;
    if (this.formGroup.valid) {
      this.loading = this.loadingSubmit = true;
      if (this.accountSearchSub) {
        this.accountSearchSub.unsubscribe();
      }
      this.utilService.isSubAccountOf(this.accountLookup.value).subscribe(
        next => {
          if (next) {
            this.router.navigate(['../myestes/recent-shipments', this.accountLookup.value]);
          } else {
            this.feedback = ['error', 'Account number not found.'];
          }
        },
        err => {
          this.loading = this.loadingSubmit = false;
          let msg = `An unexpected error has occurred.  Please try again.`;
          if (err instanceof HttpErrorResponse) {
            if (err.error.errorCode === 'ERROR' && err.error.message) {
              msg = err.error.message;
            }
          }
          this.feedback = ['error', msg];
        },
        () => (this.loading = this.loadingSubmit = false)
      );
    } else {
      validateFormFields(this.formGroup);
    }
  }

  get accountLookup(): AbstractControl {
    return this.formGroup.controls['accountLookup'];
  }

  ngOnInit() {
    this.sort.active = 'accountNumber';
    this.sort.direction = 'asc';
    this.paginator.pageSize = this.pageSize;
    this.results.sort = this.sort;
    this.totalSubAccounts = 0;
    this.formGroup = this.fb.group({
      accountLookup: ['', Validators.required]
    });

    this.tableSub = merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.loading = true;
          if (!this.sort.direction || !this.sort.active) {
            this.sort.active = 'accountNumber';
            this.sort.direction = 'asc';
          }
          return this.utilService.getPaginatedSubAccounts(this.paginator.pageIndex+1, this.paginator.pageSize, this.sort.active, this.sort.direction)
        }),
        map(data => {
          this.totalSubAccounts = data.totalElements;
          return data.content;
        }),
      catchError((err) => {
        return of([]);
      })
    )
    .subscribe(data => {
      this.loading = false;
      this.results.data = data;
    });
    this.authService.authStateSet.subscribe((authState: string) => {
      if ('unauthenticated' === authState) {
        this.router.navigate(['login']);
	    }
    });
  }

  ngOnDestroy() {
    if (this.tableSub) {
      this.tableSub.unsubscribe();
    }
    if (this.accountSearchSub) {
      this.accountSearchSub.unsubscribe();
    }
  }
}
