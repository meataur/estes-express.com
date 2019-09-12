import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError, map } from 'rxjs/operators';
import { throwError } from 'rxjs/internal/observable/throwError';
import { ServiceResponse, MyEstesGlobalHandlers } from 'common';
import { environment } from '../environments/environment';

import { PickupResponse } from './models/pickup-response.model';
import { PickupRequest } from './models/pickup-request.model';
import { PickupCalendar } from './models/pickup-calendar.model';
import { Commodity } from './models/commodity.model';
import { UserInformation } from './models/user-information.model';
import { Party } from './models/party.model';


@Injectable({
  providedIn: 'root'
})
export class PickupRequestService {

  private baseURL: String;
  private httpOptions: {};

  constructor( private httpClient: HttpClient ) {
    this.baseURL = `${environment.serviceApiUrl}/myestes/PickupRequest/v1.0/`;

    this.httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
   }


  getPickupHistory(page: number, size: number, sort: string, order: string): Observable<PickupResponse> {
    const url = this.baseURL + `pickup?page=${page}&size=${size}&sort=${sort}&order=${order}`;
    return this.httpClient.get<PickupResponse>(url)
      .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      );
  }

  getAvailablePickupDates(): Observable<PickupCalendar[]> {
    const url = this.baseURL + 'available_pickup_dates';
    return this.httpClient.get<PickupCalendar[]>(url)
      .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      );
  }

  getCommodities(size: number): Observable<Commodity[]> {
    const url = this.baseURL + 'commodities?size=' + size;
    return this.httpClient.get<Commodity[]>(url)
      .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      );
  }
  getShippers(size: number): Observable<Party[]> {
    const url = this.baseURL + 'shippers?size=' + size;
    return this.httpClient.get<Party[]>(url)
      .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      );
  }
  getUsers(size: number): Observable<UserInformation[]> {
    const url = this.baseURL + 'users?size=' + size;
    return this.httpClient.get<UserInformation[]>(url)
      .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      );
  }

  validatePickupDate(date: string): Observable<boolean> {
    const url = this.baseURL + 'validate/pickup_date' + '?pickupDate=' + date;
    return this.httpClient.get<boolean>(url)
      .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      );
  }

  createPickupRequest(req: PickupRequest): Observable<PickupRequest> {
    const url = this.baseURL + 'pickup';
    return this.httpClient.post<PickupRequest>(url, req, this.httpOptions)
      .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      );
  }
}
