import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError, map } from 'rxjs/operators';
import { throwError } from 'rxjs/internal/observable/throwError';
import { ReportType } from './report-type.model';
import { Report } from './report.model';
import { ServiceResponse, MyEstesGlobalHandlers } from 'common';
import { environment } from '../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class OnlineReportingService {

  private baseURL: String;
  private httpOptions: {};

  constructor( private httpClient: HttpClient ) {
    this.baseURL = `${environment.serviceApiUrl}/myestes/OnlineReporting/v1.0/`;

    this.httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
   }

  /**
   * getReports gets list of reports for s given user
   */
  getReports(): Observable<Report[]> {
    const url = this.baseURL + 'reports';

    return this.httpClient.get<Report[]>(url)
      .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      );
  }
  /**
   * getReportTypes fetches the types of reports available to a given user
   */
  getReportTypes(): Observable<ReportType[]> {
    const url = this.baseURL + 'types';

    return this.httpClient.get<ReportType[]>(url)

      .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      );
  }
  /**
   * deleteReport deletes the specified report
   * 
   */

  deleteReport(scheduleId: string): Observable<ServiceResponse> {
    const url = this.baseURL + 'report/' + scheduleId;

    return this.httpClient.delete<ServiceResponse>(url)
      .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      );
  }

  createReport(report: Report): Observable<ServiceResponse> {
    const url = this.baseURL + 'report';
    return this.httpClient.post<ServiceResponse>(url, report, this.httpOptions)
      .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      );
  }

  updateReport(report: Report): Observable<ServiceResponse> {
    const url = this.baseURL + 'report';
    return this.httpClient.put<ServiceResponse>(url, report, this.httpOptions)
      .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      );
  }

  getReportById(scheduleId: number): Observable<Report> {
    const url = this.baseURL + `report/${scheduleId}`;
    return this.httpClient.get<Report>(url)
      .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      );
  }
}
