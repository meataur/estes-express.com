import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { APP_CONFIG } from '../../../app.config';

@Injectable()
export class AppService {
  constructor(private http: HttpClient, @Inject(APP_CONFIG) private appConfig) {}

  // https://qa.estesinternal.com/api/common/v1.0/swagger-ui.html#/app-controller/getAppAvailabilityUsingGET
  appAvailable(appName: string): Observable<boolean> {
    return this.http.get<boolean>(`${this.appConfig.serviceApiUrl}/common/v1.0/appAvailable/${appName}`);
  }
}
