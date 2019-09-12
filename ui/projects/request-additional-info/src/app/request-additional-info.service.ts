import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { RequestAdditionalInfoRequest } from './models/request-additional-info-request.model';
import { ServiceResponse } from 'account';
import { MyEstesGlobalHandlers } from 'common';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RequestAdditionalInfoService {
  private baseURL: any;
  private httpOptions: {};

  constructor( private httpClient: HttpClient ) {
    this.baseURL = `${environment.serviceApiUrl}/myestes/RequestInfo/v1.0/`;

    this.httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
  }
  // /**
  //  * getCustomerInfo calls estes sercice to retrieve customer info based on authentication token
  //  */
  // getCustomerInfo (): Observable<CustomerInfoResponse> {
  //   const url = this.baseURL + 'getCustomerInfo';
  //   return this.httpClient.get<CustomerInfoResponse>(url)
  //     .pipe(
  //       catchError(RequestAdditionalInfoService.handleErrors)
  //     );
  // }

  getProblemTypes(pro: string): Observable<Array<string>> {
    const url = this.baseURL + `getProblemTypes`;
    return this.httpClient.post<Array<string>>(url, { otPro: pro }, this.httpOptions)
      .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      )
  }
  /**
   * submitRequest submits request additional info to estes service
   * @param request object containing form data
   */
  submitRequest (request: RequestAdditionalInfoRequest): Observable<ServiceResponse> {
    const url = this.baseURL + 'submitRequest';
    return this.httpClient.post<ServiceResponse>(url, request, this.httpOptions)
      .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      );
  }
}
