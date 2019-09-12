import { Component, OnInit, AfterViewInit } from '@angular/core';
import { LeftNavigationService, PromoService, AuthService } from 'common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements AfterViewInit {
  private isAuthenticated: boolean = false;
  constructor(
    private leftNavigationService: LeftNavigationService,
    private promoService: PromoService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngAfterViewInit() {
    this.leftNavigationService.setNavigation('Ship', '/ship');
    this.promoService.setAppId('rate-quote');
    this.promoService.setAppState('any');
    this.isAuthenticated = this.authService.isLoggedIn;

    this.authService.authStateSet.subscribe((authState: string) => {
      this.promoService.setAppState(authState);
      if (this.isAuthenticated && authState === 'unauthenticated') {
        this.router.navigate(['login']);
      }
      this.isAuthenticated = 'authenticated' === authState ? true : false;
    });
  }
}
