import { Injectable, EventEmitter, Output, Inject } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { APP_CONFIG } from '../../../app.config';
import { AppConfig } from '../../../models/app-config.interface';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class LeftNavigationService {
  private baseURL: string;
  private leftNavSource = new BehaviorSubject<[string, Array<any>]>([null, null]);

  leftNav$ = this.leftNavSource.asObservable();

  constructor(private http: HttpClient, @Inject(APP_CONFIG) private appConfig: AppConfig) {
    this.baseURL = appConfig.cmsUrl;
  }

  setNavigation(title: string, menu: string) {
    this.getNavigation(title, menu);
  }

  private getNavigation(menuTitle: string, menu: string) {
    const url = this.baseURL + '/rest/navigation?menu=' + menu;
    this.http
      .get<Array<any>>(url)
      .pipe(
        map(res => {
          for (const item of res) {
            item.classStr =
              item.link === window.location.href ? 'list-group-item active' : 'list-group-item';
          }
          return res;
        })
      )
      .subscribe(next => {
        this.leftNavSource.next([menuTitle, next]);
      });
  }
}
