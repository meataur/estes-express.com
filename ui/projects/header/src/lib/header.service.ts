import { Injectable, Inject } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AppConfig } from './models/app-config.interface';
import { APP_CONFIG } from './models/app.config';

@Injectable({
  providedIn: 'root'
})
export class HeaderService {
  private baseURL: String;

  constructor(private http: HttpClient, @Inject(APP_CONFIG) private appConfig: AppConfig) {
    this.baseURL = `${appConfig.cmsUrl}`;
  }

  getNavigation(menu: string): Observable<Array<string>> {
    let url = this.baseURL + '/rest/navigation';
    if (menu) {
      url += '?menu=' + menu;
    }
    return this.http.get<Array<string>>(url);
  }

  getFooter(): Observable<Array<string>> {
    const url = this.baseURL + '/utils/footer';
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'text/html'
      })
    };
    return this.http.get<Array<string>>(url, httpOptions);
  }
}
