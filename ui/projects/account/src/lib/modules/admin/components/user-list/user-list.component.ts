import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { DialogService, FormService, FeedbackTypes, AuthService, SnackbarService } from 'common';
import { AdminService } from '../../services/admin.service';
import { FormBuilder, FormGroup, AbstractControl } from '@angular/forms';
import { Subscription, merge } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { MatTableDataSource, MatPaginator, MatSort, MatDialog } from '@angular/material';
import { Profile } from '../../models/profile.interface';
import { startWith, switchMap, map } from 'rxjs/operators';
import { StatusEnum } from '../../models';
import { ResetPasswordModalComponent } from '../reset-password-modal/reset-password-modal.component';
import { UserAccessModalComponent } from '../user-access-modal/user-access-modal.component';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'lib-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit, OnDestroy {
  pageSize = 25;
  loading = false;
  formGroup: FormGroup;
  formSubmissionSub: Subscription;
  feedback: [FeedbackTypes, string];
  loggedInUsername: string;
  dataSource: MatTableDataSource<Profile> = new MatTableDataSource();
  displayedColumns: string[];
  tableSub: Subscription;
  totalElements: number;
  statusEnabled: StatusEnum = StatusEnum.ENABLED;
  statusDisabled: StatusEnum = StatusEnum.DISABLED;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    private dialogService: DialogService,
    private adminService: AdminService,
    private fb: FormBuilder,
    private auth: AuthService,
    private route: ActivatedRoute,
    private snackbarService: SnackbarService,
    public formService: FormService,
    public dialog: MatDialog
  ) {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const username = params.get('username');
      if (username) {
        this.openEditUserAccessModal(username);
      }
    });
    this.formGroup = this.fb.group({
      firstName: [''],
      lastName: [''],
      username: ['']
    });
    this.displayedColumns = ['username', 'firstName', 'lastName', 'email', 'createdDate', 'resetPassword', 'actions'];
    this.loggedInUsername = this.auth.getAuthToken().username;
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.sort.direction = 'asc';
    this.sort.active = 'username';
    this.paginator.pageSize = this.pageSize;
    this.tableSub = merge(this.sort.sortChange, this.paginator.page)
    .pipe(
      startWith({}),
      switchMap(() => {
        this.loading = true;
        if (!this.sort.direction) {
          this.sort.active = 'username';
          this.sort.direction = 'asc';
        }
        return this.adminService
        .getUsers(
          this.firstName.value,
          this.lastName.value,
          this.sort.direction,
          this.paginator.pageIndex+1,
          this.paginator.pageSize,
          this.sort.active,
          this.username.value
        );
      })
    ).subscribe(
      res => {
        console.log('merge result ', res);
        this.totalElements = res.totalElements;
        this.dataSource = new MatTableDataSource(res.content);
        this.loading = false
      },
      err => {
        this.feedback = ['error', err];
        this.loading = false;
      });

  }

  get firstName(): AbstractControl {
    return this.formGroup.controls['firstName'];
  }
  get lastName(): AbstractControl {
    return this.formGroup.controls['lastName'];
  }
  get username(): AbstractControl {
    return this.formGroup.controls['username'];
  }

  ngOnDestroy() {}

  openResetPasswordModal(username: string) {
    this.dialog.open(ResetPasswordModalComponent, {
			width: '550px',
			panelClass: ['estes-modal', 'estes-app'],
			data: {
				username: username
			}
		});
  }

  openEditUserAccessModal(username: string) {
    this.dialog.open(UserAccessModalComponent, {
      width: '850px',
			panelClass: ['estes-modal', 'estes-app'],
			data: {
				username: username
			}
    });
  }

  changeStatus(el: Profile, status: StatusEnum) {
    const title = (status === this.statusEnabled) ? `Enable ${el.username}` : `Disable ${el.username}`;
    const message = (status === this.statusEnabled) ? `Are you sure you want to enable this user?` : `Are you sure you want to disable this user?`;
    this.dialogService.confirm(title, message).subscribe(res => {
      if (res) {
        this.adminService.changeUserStatus(el.username, status).subscribe(res => {
          el.status = status;
        }, err => {
          this.snackbarService.error(err);
        })
      }
    })

  }

  onSubmit() {
    this.feedback = null;
    if (this.formGroup.valid) {
      this.loading = true;
      this.sort.active = 'username';
      this.sort.direction = 'asc';

      this.adminService
        .getUsers(
          this.firstName.value,
          this.lastName.value,
          this.sort.direction,
          1,
          this.pageSize,
          this.sort.active,
          this.username.value
        )
        .subscribe(
          res => {
            this.dataSource = new MatTableDataSource(res.content);
            this.totalElements = res.totalElements;
          },
          err => {
            this.loading = false;
            this.feedback = ['error', err];
          },
          () => (this.loading = false)
        );
    }
  }
}
