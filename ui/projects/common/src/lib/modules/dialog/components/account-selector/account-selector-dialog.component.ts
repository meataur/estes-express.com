import { Component, Inject, OnInit, ViewChild, OnDestroy, AfterViewInit } from '@angular/core';
import {
  MAT_DIALOG_DATA,
  MatDialogRef,
  MatTableDataSource,
  MatPaginator,
  MatSort
} from '@angular/material';
import { FormControl } from '@angular/forms';
import { debounceTime, startWith, map, switchMap, catchError } from 'rxjs/operators';
import { SubAccount } from '../../../../models/sub-account.interface';
import { UtilService } from '../../../../util/services/util.service';
import { Subscription, merge, of } from 'rxjs';

@Component({
  selector: 'account-selector-dialog',
  templateUrl: './account-selector-dialog.component.html',
  styleUrls: []
})
export class AccountSelectorDialogComponent implements OnInit, OnDestroy, AfterViewInit {
  public subAccounts = new MatTableDataSource<SubAccount>();
  public displayedColumns: string[];
  public totalSubAccounts: number;
  public search: FormControl;
  public tableSub: Subscription;
  public loading: boolean;
  public pageSize: number = 25;
  public errorMessages: [string, string];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    public dialogRef: MatDialogRef<AccountSelectorDialogComponent>,
    public utilService: UtilService
  ) {
    this.displayedColumns = ['accountNumber', 'name', 'streetAddress', 'city', 'state', 'zip'];
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  selectAccount(accountNum: string): void {
    this.dialogRef.close(accountNum);
  }

  ngOnInit() {
    this.search = new FormControl();

    this.paginator.pageSize = this.pageSize;
    this.sort.active = 'accountCode';
    this.sort.direction = 'asc';
    this.subAccounts.sort = this.sort;
    this.totalSubAccounts = 0;

    this.tableSub = merge(
      this.sort.sortChange,
      this.paginator.page,
      this.search.valueChanges.pipe(
        debounceTime(500),
        map(val => {
          this.paginator.pageIndex = 0;
          return val;
        })
      )
    )
      .pipe(
        startWith({}),
        switchMap(() => {
          this.loading = true;
          this.errorMessages = null;
          if (!this.sort.direction) {
            this.sort.direction = 'asc';
            this.sort.active = 'accountCode';
          }
          return this.utilService.getPaginatedSubAccounts(
            this.paginator.pageIndex + 1,
            this.paginator.pageSize,
            this.sort.active,
            this.sort.direction,
            this.search.value || null
          );
        }),
        map(data => {
          this.totalSubAccounts = data.totalElements;
          return data.content;
        }),
        catchError(err => {
          this.errorMessages = ['error', err.message];
          return of([]);
        })
      )
      .subscribe(data => {
        this.loading = false;
        this.subAccounts.data = data;
      });
  }

  ngAfterViewInit() {
    this.subAccounts.sort = this.sort;
    setTimeout(() => (this.paginator.length = this.totalSubAccounts));
  }

  ngOnDestroy() {
    this.tableSub.unsubscribe();
  }
}
