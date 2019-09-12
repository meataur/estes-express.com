import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs/internal/observable/throwError';
import { ClaimsRequest } from './models/claims-request.model';
import { ProInfo } from './models/pro-info.model';
import { ClaimantResponse } from './models/claimant-response.model';
import { Claim } from './models/claim.model';
import { ServiceResponse, MyEstesGlobalHandlers } from 'common';
import { BehaviorSubject } from 'rxjs';
import { environment } from '../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class ClaimsService {

  private baseURL: String;
  private httpOptions: {};

  private claimSource = new BehaviorSubject(new ClaimsRequest());
  currentClaim = this.claimSource.asObservable();

  private claimSearchResultsSource = new BehaviorSubject([]);
  currentClaimSearchResults = this.claimSearchResultsSource.asObservable();

  constructor( private httpClient: HttpClient ) {
    this.baseURL = `${environment.serviceApiUrl}/myestes/Claims/v1.0/`;
    this.httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
   }

  /**
   * get lists of claims by date, status, number.
   * @param request is the ClaimsRequest that includes query data
   */
  getClaims(request: ClaimsRequest): Observable<Array<Claim>> {
    const url = this.baseURL + 'getClaims';

    return this.httpClient.post<Array<Claim>>(url, request, this.httpOptions)
      .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      );
  }

  getProInfo(pro: string, accountNumber: string): Observable<ProInfo> {
    const url = this.baseURL + 'getProInfo';

    return this.httpClient.post<ProInfo>(url, {
      accountNumber: accountNumber,
      otpro: pro
    }, this.httpOptions)
    .pipe(
      catchError(MyEstesGlobalHandlers.handleErrors)
    );
  }

  getClaimantInfo(): Observable<ClaimantResponse> {
    const url = this.baseURL + 'getClaimantInfo';

    return this.httpClient.get<ClaimantResponse>(url)
    .pipe(
      catchError(MyEstesGlobalHandlers.handleErrors)
    );
  }

  hasEnteredClaim(pro: string, accountNumber: string): Observable<ServiceResponse> {
    const url = this.baseURL + 'hasEnteredClaim';

    return this.httpClient.post<ServiceResponse>(url, {
      accountNumber: accountNumber,
      otpro: pro
    }, this.httpOptions)
    .pipe(
      catchError(MyEstesGlobalHandlers.handleErrors)
    );
  }

  submitClaim(claim: FormData): Observable<ServiceResponse> {
    const url = this.baseURL + 'submitClaim';
    return this.httpClient.post<ServiceResponse>(url, claim, {
      headers: new HttpHeaders({
        'Remove-Content-Type': '' //use this empty string value will allow browser to autoset this header
      })
    })
    .pipe(
      catchError(MyEstesGlobalHandlers.handleErrors)
    );
  }
}
