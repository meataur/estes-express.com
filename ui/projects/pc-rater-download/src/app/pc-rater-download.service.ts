import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { PcRaterDownloadRequest } from './models/pc-rater-download-request.model';
import { PcRaterServiceResponse } from './models/pc-rater-service-response.model';
import { ServiceResponse } from 'account';
import { MyEstesGlobalHandlers } from 'common';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PcRaterDownloadService {
  private baseURL: any;
  private httpOptions: {};

  constructor( private httpClient: HttpClient ) {
    this.baseURL = `${environment.serviceApiUrl}/myestes/PCRaterDownload/v1.0/`;

    this.httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
  }
  /**
   * submitRequest submits pcraterdownload request and returns download link
   * @param request object containing form data
   */
  submitRequest (request: PcRaterDownloadRequest): Observable<PcRaterServiceResponse> {
    const url = this.baseURL + 'pcraterlink';
    return this.httpClient.post<PcRaterServiceResponse>(url, request, this.httpOptions)
      .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      );
  }
}

