import { Component, OnInit, Input } from '@angular/core';
import { Observable } from 'rxjs';
import { CmsAlertService } from '../services/cms-alert.service';
import { Router } from '@angular/router';

@Component({
	selector: 'lib-cms-alert',
	templateUrl: './cms-alert.component.html'
})
export class CmsAlertComponent implements OnInit {
  @Input() appName: string;
  public alerts: any[];
  public routesToHide = ['/login', '/sign-up', '/request-account-number', '/profile', '/admin/users', '/admin/create-user'];

	constructor(private cmsAlertService: CmsAlertService, public route: Router) {}

	ngOnInit() {
    const self = this;
    if (this.appName) {
      self.cmsAlertService.getAlerts(this.appName).subscribe(
        (data: any) => {
          if (data && data.contentlets && Array.isArray(data.contentlets)) {
            this.alerts = data.contentlets;
          }
        },
        err => {
          console.error('Error calling service: CmsAlertService.getAlerts()', '\n', err);
        }
      );
    }
  }

  dismiss(index: number) {
    this.alerts.splice(index, 1);
  }
}
