import { Component, OnInit, Inject } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { FeedbackTypes, SnackbarService } from 'common';
import { AdminService } from '../../services/admin.service';
import { App } from '../../models/app.interface';
import { tap, switchMap } from 'rxjs/operators';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';


@Component({
  selector: 'lib-user-access-modal',
  templateUrl: './user-access-modal.component.html',
  styleUrls: ['./user-access-modal.scss']
})
export class UserAccessModalComponent implements OnInit {
  feedback: [FeedbackTypes, string];
  username: string;
  authorizedApps$: Observable<Array<App>>;
  blockedApps$: Observable<Array<App>>;
  loadingAuthorizedApps: boolean;
  loadingBlockedApps: boolean;


  constructor( 
    public dialogRef: MatDialogRef<UserAccessModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private adminService: AdminService, private snackbarService: SnackbarService) {}

  ngOnInit() {
    this.username = this.data.username;

    this.loadApps();
  }

  loadApps() {
    this.loadingAuthorizedApps = true;
    this.loadingBlockedApps = true;
    this.authorizedApps$ = this.adminService.getAuthorizedApps(this.username).pipe(tap(next => this.loadingAuthorizedApps = false));
    this.blockedApps$ = this.adminService.getBlockedApps(this.username).pipe(tap(next => this.loadingBlockedApps = false));
  }

  blockApp(appName: string) {
    this.feedback = null;
    this.adminService.blockApps(this.username, [appName]).subscribe(
      next => {
        this.snackbarService.success(next.message);
        this.loadApps();
      },
      err => {
        this.feedback = ['error', err];
      }
    );
  }

  unblockApp(appName: string) {
    this.feedback = null;
    this.adminService.unblockApps(this.username, [appName]).subscribe(
      next => {
        this.snackbarService.success(next.message);
        this.loadApps();
      },
      err => {
        this.feedback = ['error', err];
      }
    );
  }

  onNoClick() {
    this.dialogRef.close();
  }

}
