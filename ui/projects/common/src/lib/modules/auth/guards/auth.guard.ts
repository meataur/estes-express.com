import { Injectable } from '@angular/core';
import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  Router,
  Params
} from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router, private authService: AuthService) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    const url: string = state.url;
    return this.isAuthenticated(url, route.queryParams);
  }

  isAuthenticated(url: string, queryParams?: Params): boolean {
    if (this.authService.isLoggedIn) {
      return true;
    }

    this.router.navigate(['/login'], { queryParams: queryParams });
    return false;
  }
}
