import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router, Params } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from 'common';

@Injectable({
  providedIn: 'root'
})
export class RateEstimateGuard implements CanActivate {
  constructor(private router: Router, private authService: AuthService) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> | Promise<boolean> | boolean {
    return this.isAuthorized(route.queryParams);
  }

  private isAuthorized(queryParams?: Params): boolean {
    if (this.authService.isLoggedIn) {
      this.router.navigate(['../rate-quote/create/'], { queryParams: queryParams });
      return false;
    } else {
      return true;
    }
  }
}
