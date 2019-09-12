import { Injectable, Inject } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs/internal/observable/throwError';
import { RequestInfoRequest } from './request-account-number.model';
import { ServiceResponse } from '../service-response.model';
import { APP_CONFIG } from '../models/app.config';

@Injectable({
  providedIn: 'root'
})
export class RequestAccountNumberService {
  constructor(private httpClient: HttpClient, @Inject(APP_CONFIG) private appConfig) {}

  static handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // console.error('An error occurred:', error.error.message);
    } else {
      // console.error(`Backend returned code ${error.status}, ` + `body was: ${error.error}`);
    }
    return throwError(error.message);
  }

  requestAccountNumber(infoRequest: RequestInfoRequest): Observable<ServiceResponse> {
    const url = `${this.appConfig.serviceApiUrl}/myestes/AccountNumberRequest/v1.0/req`;
    return this.httpClient
      .post<ServiceResponse>(url, JSON.stringify(infoRequest))
      .pipe(catchError(RequestAccountNumberService.handleError));
  }
}
