import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { QuickLink } from '../../models';
import { HttpErrorResponse } from '@angular/common/http';
import { FeedbackTypes } from '../../../components/models';
import { tap } from 'rxjs/operators';
import { SnackbarService } from '../../../snackbar/public_api';
import { AuthService } from '../../../auth/public_api';
import { QuickLinksService } from '../../services/quick-links.service';

@Component({
  selector: 'lib-manage-quick-links',
  templateUrl: './manage-quick-links.component.html',
  styleUrls: ['./manage-quick-links.component.scss']
})
export class ManageQuickLinksComponent implements OnInit {
  feedback: [FeedbackTypes, string];
  availableLinks$: Observable<Array<QuickLink>>;
  addedLinks$: Observable<Array<QuickLink>>;
  loadingAvailableLinks = true;
  loadingAddedLinks = true;

  constructor(
    public linkService: QuickLinksService,
    private snackbarService: SnackbarService,
    public auth: AuthService
  ) {}

  ngOnInit() {
    this.availableLinks$ = this.linkService.availableLinks$.pipe(
      tap(next => (this.loadingAvailableLinks = false))
    );
    this.addedLinks$ = this.linkService.addedLinks$.pipe(
      tap(next => (this.loadingAddedLinks = false))
    );
    this.linkService.getAvailableLinks().subscribe();
    this.linkService.getQuickLinks().subscribe();
  }

  resetDefaults() {
    this.feedback = null;
    this.loadingAddedLinks = true;
    this.loadingAvailableLinks = true;
    this.linkService.resetLinks().subscribe(
      next => {
        this.snackbarService.success('Links reset to default successfully.');
      },
      err => {
        this.loadingAddedLinks = false;
        this.loadingAvailableLinks = false;
        if (err instanceof HttpErrorResponse) {
          if (err.status === 400) {
            this.snackbarService.error(err.error[0].message);
          } else if (err.status === 500) {
            this.snackbarService.error(`An unexpected error occurred.  Please try again.`);
          }
        }
      }
    );
  }

  removeLink(appCategory: string, appName: string) {
    this.feedback = null;
    this.loadingAddedLinks = true;
    this.loadingAvailableLinks = true;
    this.linkService.removeLink(appCategory, appName).subscribe(
      next => {
        this.snackbarService.success('Link removed successfully.');
      },
      err => {
        this.loadingAddedLinks = false;
        this.loadingAvailableLinks = false;
        if (err instanceof HttpErrorResponse) {
          if (err.status === 400) {
            this.snackbarService.error(err.error[0].message);
          } else if (err.status === 500) {
            this.snackbarService.error(`An unexpected error occurred.  Please try again.`);
          }
        }
      }
    );
  }

  addLink(appCategory: string, appName: string) {
    this.feedback = null;
    this.linkService.addLink(appCategory, appName).subscribe(
      next => {
        this.snackbarService.success('Link added successfully.');
      },
      err => {
        if (err instanceof HttpErrorResponse) {
          if (err.status === 400) {
            this.snackbarService.error(err.error[0].message);
          } else if (err.status === 500) {
            this.snackbarService.error(`An unexpected error occurred.  Please try again.`);
          }
        }
      }
    );
  }
}
