import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class ScopeGuard implements CanActivate {
  constructor(private router: Router, private authService: AuthService) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> | Promise<boolean> | boolean {
    const scope = route.data['scope'] as string;
    return this.isInScopeForUser(scope);
  }

  private isInScopeForUser(scope: string): boolean {
    const authorized = this.authService.isInScope(scope);

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
