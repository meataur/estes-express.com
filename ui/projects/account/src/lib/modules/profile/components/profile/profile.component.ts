import { Component, OnInit, OnDestroy } from '@angular/core';
import { ProfileService } from '../../services/profile.service';
import { Profile } from '../../models';
import { Subscription } from 'rxjs';
import { DialogService, SnackbarService, LeftNavigationService, PromoService, AuthService, UtilService, ProfileDTO } from 'common';
import { LoginDialogComponent } from '../login-dialog/login-dialog.component';
import { EditEmailAddressComponent } from '../edit-email-address/edit-email-address.component';
import { EditPasswordComponent } from '../edit-password/edit-password.component';
import { EditUsernameComponent } from '../edit-username/edit-username.component';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'lib-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit, OnDestroy {
  loading = false;
  isAdmin = false;
  profile: ProfileDTO;
  sub: Subscription;
  profileItems = [
    {
      title: 'Username',
      value: '',
      action: ProfileAction.EditUsername,
      actionText: 'Change my username'
    },
    {
      title: 'Password',
      value: '**********',
      action: ProfileAction.EditPassword,
      actionText: 'Change my password'
    },
    {
      title: 'Email',
      value: '',
      action: ProfileAction.EditEmail,
      actionText: 'Change my email address'
    }
  ];

  private _adminItems = [
    {
      title: 'Create New User',
      actionText: 'Create New User',
      link: '/admin/create-user'
    },
    {
      title: 'View/Edit User',
      actionText: 'View/Edit User',
      link: '/admin'
    }
  ];

  get adminItems() {
    return this._adminItems;
  }

  constructor(
    private profileService: ProfileService,
    private dialogService: DialogService,
    private snackbarService: SnackbarService,
    private auth: AuthService,
    private router: Router,
    private utilService: UtilService,
    public leftNavigationService: LeftNavigationService,
    public promoService: PromoService,
  ) {}

  editProfile(action: ProfileAction) {
    this.dialogService.prompt(LoginDialogComponent, null).subscribe((validLogin: boolean) => {
      if (validLogin) {
        switch (action) {
          case ProfileAction.EditEmail:
            this.sub = this.dialogService
              .prompt(EditEmailAddressComponent, this.profile.email)
              .subscribe(next => {
                if (next) {
                  this.populateAccountInfo();
                }
              });
            break;
          case ProfileAction.EditPassword:
            this.sub = this.dialogService.prompt(EditPasswordComponent, null).subscribe(next => {
              if (next) {
                this.sub = this.auth.logout(this.router.routerState.snapshot.url).subscribe(
                  () => {},
                  err => {
                    this.loading = false;
                    let msg = `An unexpected error has occurred.  Please try again.`;
                    if (err instanceof HttpErrorResponse) {
                      if (err.error.errorCode === 'ERROR' && err.error.message) {
                        msg = err.error.message;
                      }
                    }
                    this.snackbarService.error(msg);
                  },
                  () => (this.loading = false)
                );
              }
            });
            break;
          case ProfileAction.EditUsername:
            this.sub = this.dialogService
              .prompt(EditUsernameComponent, this.profile.username)
              .subscribe(next => {
                if (next) {
                  this.auth.clearUserSession(this.router.routerState.snapshot.url);
                }
              });
            break;
        }
      }
    });
  }

  ngOnInit() {
    this.populateAccountInfo();
    this.isAdmin = this.auth.isAdmin;
    this.leftNavigationService.setNavigation('My Estes', '/menus/account');
    this.promoService.setAppId('profile');
    this.promoService.setAppState('authenticated');
    
  }

  ngOnDestroy() {}

  populateAccountInfo() {
    this.loading = true;
    this.sub = this.utilService.getProfileInfo().subscribe(
      next => {
        this.loading = false;
        this.profile = next;
        this.profileItems.find(i => i.action === ProfileAction.EditUsername).value = next.username;
        this.profileItems.find(i => i.action === ProfileAction.EditEmail).value = next.email;
      },
      err => {
        this.loading = false;
        let msg = `An unexpected error has occurred.  Please try again.`;
        if (err instanceof HttpErrorResponse) {
          if (err.error.errorCode === 'ERROR' && err.error.message) {
            msg = err.error.message;
          }
        }
        this.snackbarService.error(msg);
      },
      () => (this.loading = false)
    );
  }
}

export enum ProfileAction {
  EditUsername = 1,
  EditPassword,
  EditEmail
}
