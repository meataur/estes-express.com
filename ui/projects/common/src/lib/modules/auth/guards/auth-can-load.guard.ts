import { CanLoad, Route, Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthCanLoadGuard implements CanLoad {
  constructor(private auth: AuthService, private router: Router) {}

  canLoad(route: Route): Observable<boolean> | Promise<boolean> | boolean {
    return this.isAuthenticated(route.path);
  }

  private isAuthenticated(url: string): boolean {
    if (this.auth.isLoggedIn) {
      return true;
    }

    this.router.navigate(['/login']);
    return false;
  }
}
