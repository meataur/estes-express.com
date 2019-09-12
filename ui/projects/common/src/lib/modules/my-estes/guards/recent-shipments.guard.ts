import { Injectable } from '@angular/core';
import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  Router
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { AuthService } from '../../auth/services/auth.service';
import { UtilService } from '../../../util/services/util.service';
import { switchMap, map, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class RecentShipmentsGuard implements CanActivate {
  constructor(
    private util: UtilService,
    private router: Router,
    private authService: AuthService
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> | Promise<boolean> | boolean {
    const accountNumber = route.paramMap.get('accountNumber');

    if (this.authService.isLocalAccount) {
      return this.util.getProfileInfo().pipe(
        map(next => {
          if (next.accountCode === accountNumber) {
            return true;
          } else {
            return this.navigateHome();
          }
        }),
        catchError(err => this.catchObsError())
      );
    } else {
      return this.util.isSubAccountOf(accountNumber).pipe(
        map(next => {
          if (!next) {
            return this.navigateHome();
          } else {
            return true;
          }
        }),
        catchError(err => this.catchObsError())
      );
    }
  }

  private navigateHome(): false {
    this.router.navigate(['/myestes']);
    return false;
  }

  private catchObsError(): Observable<boolean> {
    return of(this.navigateHome());
  }
}
