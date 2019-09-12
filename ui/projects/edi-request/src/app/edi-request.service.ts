import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError, map } from 'rxjs/operators';
import { throwError } from 'rxjs/internal/observable/throwError';
import { EdiRequest } from './models/edi-request.model';
import { BillType } from './models/bill-type.model';
import { HeaderType } from './models/header-type.model';
import { ServiceResponse, MyEstesGlobalHandlers } from 'common';
import { environment } from '../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class EdiRequestService {

  private baseURL: String;
  private httpOptions: {};

  constructor( private httpClient: HttpClient ) {
    this.baseURL = `${environment.serviceApiUrl}/myestes/EDIRequest/v1.0/`;

    this.httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
   }

   getBillTypes(): Observable<BillType[]> {
     const url = this.baseURL + 'getBillTypes';
     return this.httpClient.get<BillType[]>(url)
      .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      );
   }

   getHeaderTypes(): Observable<HeaderType[]> {
    const url = this.baseURL + 'getHeaderTypes';
    return this.httpClient.get<HeaderType[]>(url)
     .pipe(
       catchError(MyEstesGlobalHandlers.handleErrors)
     );
  }


  save(request: EdiRequest): Observable<ServiceResponse> {
    const url = this.baseURL + 'save';
    return this.httpClient.post<ServiceResponse>(url, request, this.httpOptions)
      .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      );
  }
}
