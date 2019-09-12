import { Component, OnInit } from '@angular/core';
import { LeftNavigationService, PromoService } from 'common';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'common';

@Component({
  selector: 'app-root',
  templateUrl: './errors.component.html'
})
export class ErrorsComponent implements OnInit {
  public code: string;
  public title: string;
  public message: string;
  public routeData: any;

  constructor(
    public activatedRoute: ActivatedRoute,
    public leftNavigationService: LeftNavigationService,
    public promoService: PromoService,
    public route: Router,
    public authService: AuthService
  ) {
    this.code = '404';
    this.title = 'Not Found';
    this.message = 'The content you requested is forbidden.';
  }

  ngOnInit() {
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.code = data.code;
      switch (data.code) {
        case '400':
          this.title = 'Bad Request';
          this.message = 'Your request was malformed or unrecognized by the server.';
          break;
        case '401':
          this.title = 'Unauthorized';
          this.message = 'You are not authorized to view the content you requested.';
          break;
        case '403':
          this.title = 'Forbidden';
          this.message = 'The content you requested is forbidden.';
          break;
        case '500':
          this.title = 'Internal Server Error';
          this.message =
            'The server experienced an error and could not retrieve the content you requested.';
          break;
        case '404':
        default:
          this.title = 'Not Found';
          this.message = 'The content you requested could not be found.';
          break;
      }
      document.title = 'My Estes: ' + this.title;
    });
    
    this.authService.authStateSet.subscribe((authState: string) => {
      
      this.promoService.setAppId('errors');
      this.promoService.setAppState(authState);
    });
  }
}
