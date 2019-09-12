import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { SignupRequest } from './signup-request.model';
import { catchError } from 'rxjs/operators';
import { HttpHeaders } from '@angular/common/http';
import { ServiceResponse } from '../service-response.model';
import { MyEstesGlobalHandlers } from 'common';
import { APP_CONFIG } from '../models/app.config';

@Injectable({
  providedIn: 'root'
})
export class SignupService {
  private baseURL: any;
  private httpOptions: {};

  constructor(private httpClient: HttpClient, @Inject(APP_CONFIG) private appConfig) {
    this.baseURL = `${this.appConfig.serviceApiUrl}/myestes/MyEstesSignup/v1.0/`;
    this.httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
  }

  /**
   *signup submits signup to estes service
   * @param request object containing signup form data
   */
  signup(request: SignupRequest): Observable<ServiceResponse> {
    const url = this.baseURL + 'signup';
    return this.httpClient
      .post<ServiceResponse>(url, JSON.stringify(request), this.httpOptions)
      .pipe(catchError(MyEstesGlobalHandlers.handleErrors));
  }
}
