import { Component, AfterViewInit } from '@angular/core';
import { LeftNavigationService, PromoService } from 'common';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements AfterViewInit {

  constructor(
    private leftNavigationService: LeftNavigationService,
    private promoService: PromoService
  ) {}

  ngAfterViewInit() {
    this.leftNavigationService.setNavigation('Ship', '/ship');
    this.promoService.setAppId('bill-of-lading');
    this.promoService.setAppState('any');
  }

}
