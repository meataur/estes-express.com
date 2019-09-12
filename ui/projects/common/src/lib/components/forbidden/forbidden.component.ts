import { Component, OnInit, Inject } from '@angular/core';
import { APP_CONFIG } from '../../app.config';
import { AppConfig } from '../../models/app-config.interface';

@Component({
  selector: 'lib-forbidden',
  templateUrl: './forbidden.component.html',
  styleUrls: ['./forbidden.component.scss']
})
export class ForbiddenComponent implements OnInit {
  private welcomePageUrl: string;

  constructor(@Inject(APP_CONFIG) private appConfig: AppConfig) { }

  ngOnInit() {
    this.welcomePageUrl = `${this.appConfig.appBaseUrl}/home`;
  }

  navigateToWelcomePage() {
    window.location.href = this.welcomePageUrl;
  }

}
