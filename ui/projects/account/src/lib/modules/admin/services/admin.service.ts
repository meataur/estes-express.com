import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ServiceResponse, User, Page, App, Password } from '../models';
import { APP_CONFIG } from '../../../models/app.config';
import { AppConfig } from '../../../models/app-config.interface';
import { catchError } from 'rxjs/operators';
import { MyEstesGlobalHandlers } from 'common';

// @Injectable()
export class AdminService {
  constructor(private http: HttpClient, @Inject(APP_CONFIG) private appConfig: AppConfig) {}

  createUser(user: User): Observable<ServiceResponse> {
    return this.http.post<ServiceResponse>(
      `${this.appConfig.serviceApiUrl}/myestes/Profile/v1.0/users`,
      user
    ).pipe(
      catchError(MyEstesGlobalHandlers.handleErrors)
    );;
  }

  getUsers(
    firstName: string,
    lastName: string,
    order: string,
    page: number,
    size: number,
    // tslint:disable-next-line:max-line-length
    sort: string,
    username: string,
    q: string = ''
  ): Observable<Page> {
    const username_encoded = username;
    return this.http.get<Page>(
      `${
        this.appConfig.serviceApiUrl
        // tslint:disable-next-line:max-line-length
      }/myestes/Profile/v1.0/users?firstName=${firstName}&lastName=${lastName}&order=${order}&page=${page}&q=${q}&size=${size}&sort=${sort}&username=${username_encoded}`
    ).pipe(
      catchError(MyEstesGlobalHandlers.handleErrors)
    );
  }

  getAuthorizedApps(username: string): Observable<Array<App>> {
    const username_encoded = username;

    return this.http.get<Array<App>>(
      `${
        this.appConfig.serviceApiUrl
      }/myestes/Profile/v1.0/users/authorized_apps?username=${username_encoded}`
    ).pipe(
      catchError(MyEstesGlobalHandlers.handleErrors)
    );
  }

  getBlockedApps(username: string): Observable<Array<App>> {
    const username_encoded = username;

    return this.http.get<Array<App>>(
      `${this.appConfig.serviceApiUrl}/myestes/Profile/v1.0/users/blocked_apps?username=${username_encoded}`
    ).pipe(
      catchError(MyEstesGlobalHandlers.handleErrors)
    );
  }

  blockApps(username: string, apps: Array<string>): Observable<ServiceResponse> {
    // tslint:disable-next-line:max-line-length
    const username_encoded = username;

    return this.http.post<ServiceResponse>(
      `${
        this.appConfig.serviceApiUrl
      }/myestes/Profile/v1.0/users/blocked_apps?username=${username_encoded}`,
      apps
    ).pipe(
      catchError(MyEstesGlobalHandlers.handleErrors)
    );
  }

  unblockApps(username: string, apps: Array<string>): Observable<ServiceResponse> {
    // tslint:disable-next-line:max-line-length
    const username_encoded = username;

    return this.http.request<ServiceResponse>(
      `delete`,
      `${
        this.appConfig.serviceApiUrl
      }/myestes/Profile/v1.0/users/blocked_apps?username=${username_encoded}`,
      { body: apps }
    ).pipe(
      catchError(MyEstesGlobalHandlers.handleErrors)
    );
  }

  unblockAllApps(username: string): Observable<ServiceResponse> {
    // tslint:disable-next-line:max-line-length
    const username_encoded = username;

    return this.http.delete<ServiceResponse>(
      `${
        this.appConfig.serviceApiUrl
      }/myestes/Profile/v1.0/users/blocked_apps/all?username=${username_encoded}`
    ).pipe(
      catchError(MyEstesGlobalHandlers.handleErrors)
    );
  }

  changeUserPassword(username: string, password: Password): Observable<ServiceResponse> {
    // tslint:disable-next-line:max-line-length
    const username_encoded = username;

    return this.http.post<ServiceResponse>(
      `${
        this.appConfig.serviceApiUrl
      }/myestes/Profile/v1.0/users/change_password?username=${username_encoded}`,
      password
    ).pipe(
      catchError(MyEstesGlobalHandlers.handleErrors)
    );
  }

  changeUserStatus(username: string, status: 'ENABLED' | 'DISABLED'): Observable<ServiceResponse> {
    // tslint:disable-next-line:max-line-length
    const username_encoded = username;

    return this.http.post<ServiceResponse>(
      `${
        this.appConfig.serviceApiUrl
      }/myestes/Profile/v1.0/users/change_status?status=${status}&username=${username_encoded}`,
      {}
    ).pipe(
      catchError(MyEstesGlobalHandlers.handleErrors)
    );
  }
}
