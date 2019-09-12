import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError, map } from 'rxjs/operators';
import { throwError } from 'rxjs/internal/observable/throwError';
import { TransitTimeCalculatorResponse } from './transit-time-calculator-response.model';
import { TransitTimeCalculatorRequest } from './transit-time-calculator-request.model';
import { ServiceResponse, MyEstesGlobalHandlers } from 'common';
import { environment } from '../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class TransitTimeCalculatorService {

  private baseURL: String;
  private httpOptions: {};

  constructor( private httpClient: HttpClient ) {
    this.baseURL = `${environment.serviceApiUrl}/myestes/TransitTimeCalculator/v1.0/`;
    this.httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
   }

  /**
   * calculate transit time between origin and destination.
   * @param request includes the origin point and a list of destination points, up to 10
   */
  calculate(request: TransitTimeCalculatorRequest): Observable<TransitTimeCalculatorResponse> {
    const url = this.baseURL + 'calculate';

    return this.httpClient.post<TransitTimeCalculatorResponse>(url, request, this.httpOptions)
      .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      );
  }

}
