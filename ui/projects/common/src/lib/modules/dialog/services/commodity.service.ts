import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { APP_CONFIG } from '../../../app.config';
import { AppConfig } from '../../../models/app-config.interface';
import { Commodity } from '../models';

@Injectable()
export class CommodityService {
  constructor(private http: HttpClient, @Inject(APP_CONFIG) private appConfig: AppConfig) {}

  getCommodities(): Observable<Array<Commodity>> {
    return this.http.get<Array<Commodity>>(
      `${this.appConfig.serviceApiUrl}/myestes/CommodityLibrary/v1.0/commodities`
    );
  }
}
