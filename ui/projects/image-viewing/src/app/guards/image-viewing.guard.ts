import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthService } from 'common';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ImageViewingGuard implements CanActivate {
  constructor(private router: Router, private authService: AuthService) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const url: string = state.url;
    return this.isAuthenticated(url);
  }

  isAuthenticated(url: string): boolean {
    if (this.authService.isLoggedIn) {
      return true;
    }

    const myUrl = `${environment.appBaseUrl}/document-retrieval/`;
    if (window) {
      window.open(myUrl, '_self');
    }
    return false;
  }
}
