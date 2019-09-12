import { Injectable, Inject, EventEmitter, Output } from '@angular/core';
import { Observable, of, Subject, BehaviorSubject } from 'rxjs';
import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { switchMap, catchError, tap, map } from 'rxjs/operators';
import { timer } from 'rxjs';
import { AuthResponse, AuthActionEnum, Login } from '../models';
import { Router } from '@angular/router';
import { APP_CONFIG } from '../../../app.config';
import { AppConfig } from '../../../models/app-config.interface';
import { UserRoleEnum } from '../../../models/user-role.enum';
import { UtilService } from '../../../util/services/util.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly AUTH_TOKEN = 'AuthToken';
  private readonly COMPANY_NAME = 'CompanyName';
  private readonly ACCESS_COOKIE = 'eatkn';
  private readonly SCOPE_COOKIE = 'escp';
  private readonly SESSION_COOKIE = 'eses';
  private readonly ACCOUNT_COOKIE = 'esact';
  private readonly ACCOUNT_CODE_COOKIE = 'esacc';
  private readonly COMPANY_NAME_COOKIE = 'escnc';

  counter = 0;

  private resettingToken = false;
  private readonly CHECK_AUTH_TIMEOUT = 1000 * 60 * 10; // Every 10 minutes
  private authActionSource = new BehaviorSubject<AuthActionEnum>(AuthActionEnum.Logout);

  authAction$ = this.authActionSource.asObservable();

  @Output() authStateSet: EventEmitter<string> = new EventEmitter();

  constructor(
    private http: HttpClient,
    private router: Router,
    private utilService: UtilService,
    @Inject(APP_CONFIG) private appConfig: AppConfig
  ) {
    this.initSessionLogout();
    const checkAuth = timer(1000, this.CHECK_AUTH_TIMEOUT);
    const authTimer = checkAuth.subscribe(val => {
      const value = sessionStorage && sessionStorage.getItem(this.AUTH_TOKEN);
      const accessToken = this.getCookie(this.ACCESS_COOKIE);
      const applicationScope = this.getCookie(this.SCOPE_COOKIE);
      const sessionId = this.getCookie(this.SESSION_COOKIE);
      const accountType = this.getCookie(this.ACCOUNT_COOKIE);
      const accountCode = this.getCookie(this.ACCOUNT_CODE_COOKIE);
      let authToken = new AuthResponse();
      // console.log(`AuthService.timer:`, val);
      if (value) {
        authToken = JSON.parse(value);
      } else if (accessToken && applicationScope && sessionId && accountType && accountCode) {
        authToken.access_token = accessToken;
      }
      if (authToken && authToken.hasOwnProperty('access_token')) {
        // console.log(`AuthService.timer token:`, authToken);
        this.resetAuthToken(authToken.access_token).subscribe(
          (res: AuthResponse) => {
            // console.log(`AuthService.timer resetAuthToken success:`, res);
            if (!res.access_token) {
              res.access_token = authToken.access_token;
            }
            this.setAuthToken(res);
            this.setAuthState('authenticated');
          },
          err => {
            // console.warn('AuthService.timer resetAuthToken failed:', err);
            this.clearData();
            this.setAuthState('unauthenticated');
          }
        );
      } else {
        // console.error('AuthService.timer no authToken');
        this.setAuthState('unauthenticated');
      }
    });
  }

  setAuthState(authState: string) {
    this.counter++;
    if ('authenticated' === authState || 'unauthenticated' === authState) {
      // console.log('AuthService.setAuthState(' + authState + ')');

      switch (authState) {
        case 'authenticated':
          this.authActionSource.next(AuthActionEnum.Login);
          break;
        case 'unauthenticated':
          this.authActionSource.next(AuthActionEnum.Logout);
          break;
      }

      this.authStateSet.emit(authState);
    }
  }

  get isLoggedIn() {
    return this.getAuthToken() !== null;
  }

  get isLocalAccount() {
    const authToken = this.getAuthToken();
    return authToken !== null
      ? authToken.accountType.toUpperCase() === 'L'
        ? true
        : false
      : false;
  }

  get isNationalAccount() {
    const authToken = this.getAuthToken();
    return authToken !== null
      ? authToken.accountType.toUpperCase() === 'N'
        ? true
        : false
      : false;
  }

  get isGroupAccount() {
    const authToken = this.getAuthToken();
    return authToken !== null
      ? authToken.accountType.toUpperCase() === 'W'
        ? true
        : false
      : false;
  }

  get is91Account() {
    const authToken = this.getAuthToken();
    return authToken !== null
      ? authToken.accountType.toUpperCase() === 'G'
        ? true
        : false
      : false;
  }

  get isAdmin() {
    const isAdmin = this.isInScope('ADMINSUBS') || this.isInScope('ADMIN');
    return isAdmin;
  }

  get accountCode() {
    const authToken = this.getAuthToken();

    return authToken ? authToken.accountCode : '';
  }

  isInScope(appName: string | string[]): boolean {
    if (!this.isLoggedIn) return true;

    const authToken = this.getAuthToken();
    if (authToken === null) return false;

    if (typeof appName === 'string') {
      return authToken.scope.toUpperCase().includes(appName.toUpperCase()) ? true : false;
    } else if (appName instanceof Array) {
      let inScope = false;
      appName.forEach(name => {
        if (authToken.scope.toUpperCase().includes(name.toUpperCase())) {
          inScope = true;
        }
      });
      return inScope;
    }
  }

  login(username: string, password: string): Observable<boolean> {
    // tslint:disable-next-line:max-line-length
    const username_encoded = encodeURIComponent(username);
    const password_encoded = encodeURIComponent(password);
    const authURL = `${
      this.appConfig.authUrl
    }/token?username=${username_encoded}&password=${password_encoded}&grant_type=password&client_id=MY_ESTES`;
    return this.http.get<AuthResponse>(authURL).pipe(
      switchMap((res: AuthResponse) => {
        this.setAuthToken(res);
        this.setAuthState('authenticated');
        this.authActionSource.next(AuthActionEnum.Login);
        return of(true);
      }),
      catchError((err: any, caught: any) => of(false))
    );
  }

  loginByToken(token: string): Observable<boolean> {
    // tslint:disable-next-line:max-line-length
    const token_encoded = encodeURIComponent(token);

    const checkTokenUrl = `${this.appConfig.authUrl}/check_token?token=${token_encoded}`;

    return this.http.get<AuthResponse>(checkTokenUrl).pipe(
      switchMap((res: AuthResponse) => {
        res.access_token = token_encoded;
        if (Array.isArray(res.scope)) {
          res.scope = res.scope.join(' ');
        }
        this.setAuthToken(res);
        this.setAuthState('authenticated');
        this.authActionSource.next(AuthActionEnum.Login);
        return of(true);
      }),
      catchError((err: any, caught: any) => of(false))
    );
  }

  clearUserSession(returnUrl?: string) {
    // console.log('AuthService.clearUserSession');
    const storageDate: Date = new Date();
    this.clearData();
    //console.log('localStorage[logout]:', storageDate.getTime().toString());
    localStorage.setItem('logout', storageDate.getTime().toString());
    this.authActionSource.next(AuthActionEnum.Logout);

    if (returnUrl) {
      this.router.navigate(['/login'], { queryParams: { returnUrl: returnUrl } });
    } else {
      this.router.navigate(['/login']);
    }
  }

  logout(returnUrl?: string): Observable<any> {
    const auth = this.getAuthToken();
    if (auth) {
      const authURL = `${this.appConfig.authUrl}/`;
      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        Accept: 'application/json'
      });
      const params = 'remove?token=' + auth.access_token;
      return this.http.get(authURL + params, { headers: headers }).pipe(
        tap(() => {
          this.clearUserSession(returnUrl);
        })
      );
    }
  }

  getAuthToken(): AuthResponse | null {
    const accessToken = this.getCookie(this.ACCESS_COOKIE);
    const applicationScope = this.getCookie(this.SCOPE_COOKIE);
    const sessionId = this.getCookie(this.SESSION_COOKIE);
    const accountType = this.getCookie(this.ACCOUNT_COOKIE);
    const accountCode = this.getCookie(this.ACCOUNT_CODE_COOKIE);
    // console.log('getAuthToken.access_token: ' + accessToken);
    // console.log('getAuthToken.scope: ' + applicationScope);
    // console.log('getAuthToken.session: ' + sessionId);
    // console.log('getAuthToken.accountType: ' + accountType);
    const value = sessionStorage && sessionStorage.getItem(this.AUTH_TOKEN);
    if (value) {
      return JSON.parse(value);
    } else if (accessToken && applicationScope && sessionId && accountType && accountCode) {
      const authToken = new AuthResponse();
      authToken.access_token = accessToken;
      authToken.scope = applicationScope;
      authToken.session = sessionId;
      authToken.accountType = accountType;
      authToken.accountCode = accountCode;
      if (!this.resettingToken) {
        this.resetAuthToken(accessToken).subscribe(
          (res: AuthResponse) => {
            // console.log(`AuthService.resetAuthToken success: `, res);
            if (!res.access_token) {
              res.access_token = accessToken;
            }
            this.setAuthToken(res);
            this.resettingToken = false;
            this.setAuthState('authenticated');
          },
          err => {
            // console.warn('AuthService.resetAuthToken failed: ', err);
            this.resettingToken = false;
            this.clearData();
            this.setAuthState('unauthenticated');
            // window.location.reload();
          }
        );
      }
      return authToken;
    }
    return null;
  }

  getCompanyName(): Observable<string> {
    if (this.isLoggedIn) {
      const sessionValue = sessionStorage && sessionStorage.getItem(this.COMPANY_NAME);
      const cookieValue = this.getCookie(this.COMPANY_NAME_COOKIE);
      if (sessionValue) {
        return of(JSON.parse(sessionValue));
      } else if (cookieValue) {
        return of(cookieValue);
      } else {
        return this.utilService.getAccountInfo(this.accountCode).pipe(
          map(data => {
            if (data && data.name) {
              this.setCompanyName(data.name);
              return data.name;
            }
          })
        );
      }
    } else {
      return of(null);
    }
  }

  getUserRole(): UserRoleEnum | null {
    if (this.isLoggedIn) {
      const token = this.getAuthToken();
      switch (token.accountType.toUpperCase()) {
        case 'L':
          return UserRoleEnum.Local;
        case 'N':
          return UserRoleEnum.National;
        case 'G':
          return UserRoleEnum.WebGroup;
        case 'W':
          return UserRoleEnum.Group;
        default:
          return null;
      }
    }
  }

  private setAuthToken(value: AuthResponse) {
    const token = JSON.stringify(value);
    // console.log(`setAuthToken: `, value);
    sessionStorage.setItem(this.AUTH_TOKEN, JSON.stringify(value));
    if (value.access_token) {
      this.setCookie(this.ACCESS_COOKIE, value.access_token, 7);
    }
    if (value.scope) {
      this.setCookie(this.SCOPE_COOKIE, value.scope, 7);
    }
    if (value.session) {
      this.setCookie(this.SESSION_COOKIE, value.session, 7);
    }
    if (value.accountType) {
      this.setCookie(this.ACCOUNT_COOKIE, value.accountType, 7);
    }
    if (value.accountCode) {
      this.setCookie(this.ACCOUNT_CODE_COOKIE, value.accountCode, 7);
    }
  }

  private setCompanyName(value) {
    if (value) {
      sessionStorage.setItem(this.COMPANY_NAME, JSON.stringify(value));
      this.setCookie(this.COMPANY_NAME_COOKIE, value, 7);
    }
  }

  clearData() {
    // console.log('AuthService.clearData');
    sessionStorage.removeItem(this.AUTH_TOKEN);
    sessionStorage.removeItem(this.COMPANY_NAME);
    this.deleteCookie(this.ACCESS_COOKIE);
    this.deleteCookie(this.SCOPE_COOKIE);
    this.deleteCookie(this.SESSION_COOKIE);
    this.deleteCookie(this.ACCOUNT_COOKIE);
    this.deleteCookie(this.ACCOUNT_CODE_COOKIE);
    this.deleteCookie(this.COMPANY_NAME_COOKIE);
  }

  deleteCookie(name) {
    this.setCookie(name, '', -1);
  }

  private getCookie(name: string) {
    const ca: Array<string> = document.cookie.split(';');
    const caLen: number = ca.length;
    const cookieName = `${name}=`;
    let c: string;
    for (let i = 0; i < caLen; i += 1) {
      c = ca[i].replace(/^\s+/g, '');
      if (c.indexOf(cookieName) === 0) {
        return c.substring(cookieName.length, c.length);
      }
    }
    return '';
  }

  private setCookie(name: string, value: string, expireDays: number, path: string = '/') {
    const d: Date = new Date();
    d.setTime(d.getTime() + expireDays * 24 * 60 * 60 * 1000);
    const expires = `expires=${d.toUTCString()}`;
    const cpath = path ? `; path=${path}` : '';
    document.cookie = `${name}=${value}; ${expires}${cpath}`;
  }

  private resetAuthToken(token: string): Observable<AuthResponse> {
    const resetURL = `${this.appConfig.authUrl}/check_token?token=${token}`;
    this.resettingToken = true;
    return this.http.get<AuthResponse>(resetURL).pipe(
      map(res => {
        if (Array.isArray(res.scope)) {
          res.scope = res.scope.join(' ');
        }
        return res;
      })
    );
  }

  clearSessionStorage(event) {
    if (!event) {
      event = window.event;
    } // ie suq
    if (!event.newValue) {
      return;
    }
    // console.log('AuthService.clearSessionStorage', event);
    if (event.key === 'logout') {
      sessionStorage.removeItem('AuthToken');
      sessionStorage.removeItem(this.COMPANY_NAME);
      localStorage.removeItem('logout');
      window.location.reload();
    }
  }

  private initSessionLogout() {
    // console.log(`initSessionLogout`);
    // localStorage.removeItem('logout');
    if (window.addEventListener) {
      window.addEventListener('storage', this.clearSessionStorage, false);
    } else {
      (window as any).attachEvent('onstorage', this.clearSessionStorage);
    }
  }
}
