import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { ForgotPasswordRequest } from './forgot-password.model';
import { catchError } from 'rxjs/operators';
import { HttpHeaders } from '@angular/common/http';
import { ServiceResponse } from '../service-response.model';
import { MyEstesGlobalHandlers } from 'common';
import { APP_CONFIG } from '../models/app.config';


@Injectable({
  providedIn: 'root'
})
export class ForgotPasswordService {
  private baseURL: string;
  private httpOptions: {};

  constructor(private httpClient: HttpClient, @Inject(APP_CONFIG) private appConfig) {
    this.baseURL = `${this.appConfig.serviceApiUrl}/myestes/RecoverPassword/v1.0/`;
    this.httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
  }

  /**
   * email sends a POST request to the retrieve password email service.
   * @param request is the request data sent to the retrieve password service containing either username or email
   */
  email(request: ForgotPasswordRequest): Observable<ServiceResponse> {
    const url = this.baseURL + 'email';

    return this.httpClient
      .post<ServiceResponse>(url, JSON.stringify(request), this.httpOptions)
      .pipe(catchError(MyEstesGlobalHandlers.handleErrors));
  }
}
