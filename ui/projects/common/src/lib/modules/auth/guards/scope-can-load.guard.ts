import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, CanLoad, Route, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class ScopeCanLoadGuard implements CanLoad {
  constructor(private authService: AuthService, private router: Router) {}

  canLoad(route: Route): Observable<boolean> | Promise<boolean> | boolean {
    const scope = route.data['scope'] as string;
    return this.isInScopeForUser(scope);
  }

  private isInScopeForUser(scope: string): boolean {
    const authorized = this.authService.isInScope(scope);

    if (authorized) {
      return true;
    }

    if (this.authService.isLoggedIn) {
      this.router.navigate(['/myestes']);
      return false;
    }

    this.router.navigate(['/login']);
    return false;
  }
}
