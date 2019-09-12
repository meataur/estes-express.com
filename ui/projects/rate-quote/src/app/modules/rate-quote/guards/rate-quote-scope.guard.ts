import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from 'common';

@Injectable({
  providedIn: 'root'
})
export class RateQuoteScopeGuard implements CanActivate {
  constructor(private router: Router, private authService: AuthService) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> | Promise<boolean> | boolean {
    return this.isInScopeForUser();
  }

  private isInScopeForUser(): boolean {
    const authorized =
      this.authService.isLoggedIn &&
      !!(
        this.authService.isInScope('TIMECRIT') ||
        this.authService.isInScope('LTLRATEQOT') ||
        this.authService.isInScope('VTLQUOTE')
      );

    if (authorized) {
      return true;
    }

    if (this.authService.isLoggedIn) {
      this.router.navigate(['/forbidden']);
      return false;
    }

    this.router.navigate(['/login']);
    return false;
  }
}
