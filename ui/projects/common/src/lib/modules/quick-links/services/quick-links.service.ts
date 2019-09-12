import { Injectable, Inject, EventEmitter, Output } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { QuickLink, ServiceResponse } from '../models';
import { Observable, Subject, forkJoin, BehaviorSubject } from 'rxjs';
import { tap, switchMap } from 'rxjs/operators';
import { AppConfig } from '../../../models/app-config.interface';
import { APP_CONFIG } from '../../../app.config';

@Injectable()
export class QuickLinksService {

  constructor(private http: HttpClient, @Inject(APP_CONFIG) private appConfig: AppConfig) {
  }

  private availableLinksSource = new Subject<Array<QuickLink>>();
  private addedLinksSource = new Subject<Array<QuickLink>>();

  availableLinks$ = this.availableLinksSource.asObservable();
  addedLinks$ = this.addedLinksSource.asObservable();

  getQuickLinks(): Observable<Array<QuickLink>> {
    return this.http
      .get<Array<QuickLink>>(`${this.appConfig.serviceApiUrl}/myestes/QuickLinks/v1.0/links`)
      .pipe(tap(next => this.addedLinksSource.next(next)));
  }

  getAvailableLinks(): Observable<Array<QuickLink>> {
    return this.http
      .get<Array<QuickLink>>(`${this.appConfig.serviceApiUrl}/myestes/QuickLinks/v1.0/links/list`)
      .pipe(tap(next => this.availableLinksSource.next(next)));
  }

  resetLinks(): Observable<ServiceResponse> {
    return this.http
      .get<ServiceResponse>(`${this.appConfig.serviceApiUrl}/myestes/QuickLinks/v1.0/links/reset`)
      .pipe(tap(() => forkJoin(this.getQuickLinks(), this.getAvailableLinks()).subscribe()));
  }

  addLink(appCategory: string, appName: string): Observable<Array<QuickLink>> {
    return this.http
      .post<Array<QuickLink>>(
        `${
          this.appConfig.serviceApiUrl
        }/myestes/QuickLinks/v1.0/links?appCategory=${appCategory}&appName=${appName}`,
        {}
      )
      .pipe(tap(() => forkJoin(this.getQuickLinks(), this.getAvailableLinks()).subscribe()));
  }

  removeLink(appCategory: string, appName: string): Observable<ServiceResponse> {
    return this.http
      .delete<ServiceResponse>(
        `${
          this.appConfig.serviceApiUrl
        }/myestes/QuickLinks/v1.0/links?appCategory=${appCategory}&appName=${appName}`
      )
      .pipe(tap(() => forkJoin(this.getQuickLinks(), this.getAvailableLinks()).subscribe()));
  }
}
