import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { environment } from '../../environments/environment';
import { AuthService } from 'common';

@Injectable({
  providedIn: 'root'
})
export class DocumentRetrievalGuard implements CanActivate {
  constructor(private router: Router, private authService: AuthService) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const url: string = state.url;
    return this.isAuthenticated(url);
  }

  isAuthenticated(url: string): boolean {
    if (this.authService.isLoggedIn) {
      const myUrl = `${environment.appBaseUrl}/image-viewing/`;
      // console.log(`document retrieval guard myUrl: ${myUrl}`);
      // console.log(myUrl);
      // this.router.navigateByUrl(myUrl);
      if (window) {
        window.open(myUrl, '_self');
      }
      return false;
    }

    return true;
  }
}
