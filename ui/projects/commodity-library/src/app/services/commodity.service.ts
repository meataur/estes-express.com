import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { Commodity, ServiceResponse } from '../models';
import { MyEstesGlobalHandlers } from 'common';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CommodityService {
  private baseUrl = `${environment.serviceApiUrl}`;

  constructor(private http: HttpClient) {}

  getCommodities(): Observable<Array<Commodity>> {
    return this.http.get<Array<Commodity>>(
      `${this.baseUrl}/myestes/CommodityLibrary/v1.0/commodities`
    ).pipe(
      catchError(MyEstesGlobalHandlers.handleErrors)
    );
  }

  getCommodity(commodityId: string): Observable<Commodity> {
    return this.http.get<Commodity>(
      `${this.baseUrl}/myestes/CommodityLibrary/v1.0/commodity/${commodityId}`
    ).pipe(
      catchError(MyEstesGlobalHandlers.handleErrors)
    );
  }

  deleteCommodity(commodityId: string): Observable<ServiceResponse> {
    return this.http.delete<ServiceResponse>(
      `${this.baseUrl}/myestes/CommodityLibrary/v1.0/commodity/${commodityId}`
    ).pipe(
      catchError(MyEstesGlobalHandlers.handleErrors)
    );
  }

  createCommodity(commodity: Commodity): Observable<ServiceResponse> {
    return this.http.post<ServiceResponse>(
      `${this.baseUrl}/myestes/CommodityLibrary/v1.0/commodity`,
      commodity
    ).pipe(
      catchError(MyEstesGlobalHandlers.handleErrors)
    );
  }

  editCommodity(commodity: Commodity): Observable<ServiceResponse> {
    return this.http.put<ServiceResponse>(
      `${this.baseUrl}/myestes/CommodityLibrary/v1.0/commodity`,
      commodity
    ).pipe(
      catchError(MyEstesGlobalHandlers.handleErrors)
    );
  }
}
