import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RecentShipment, ServiceResponse } from '../models';
import { APP_CONFIG } from '../../../app.config';
import { AppConfig } from '../../../models/app-config.interface';

@Injectable()
export class MyestesService {
  constructor(private http: HttpClient, @Inject(APP_CONFIG) private appConfig: AppConfig) {}

  getRecentShipments(account: string, party: 'T' | 'S' | 'C'): Observable<Array<RecentShipment>> {
    return this.http.get<Array<RecentShipment>>(
      `${this.appConfig.serviceApiUrl}/myestes/RecentShipments/v1.0/recentShipments?account=${account}&party=${party}`
    );
  }

  getDefaultParty(): Observable<'T' | 'S' | 'C'> {
    const options = { responseType: 'text' as 'json' };
    return this.http.get<'T' | 'S' | 'C'>(
      `${this.appConfig.serviceApiUrl}/myestes/RecentShipments/v1.0/defaultParty`,
      options
    );
  }

  setDefaultParty(party: 'T' | 'S' | 'C'): Observable<ServiceResponse> {
    return this.http.post<ServiceResponse>(
      `${this.appConfig.serviceApiUrl}/myestes/RecentShipments/v1.0/defaultParty?party=${party}`,
      {}
    );
  }
}
