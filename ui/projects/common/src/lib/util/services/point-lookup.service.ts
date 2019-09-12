import { Injectable, Inject } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs/internal/observable/throwError';
import { Point } from '../../models/point.model';
import { APP_CONFIG } from '../../app.config';
import { AppConfig } from '../../models/app-config.interface';
import { Terminal } from '../../models/terminal.model';

@Injectable({
  providedIn: 'root'
})
export class PointLookupService {
  private basePointsURL: String;
  private httpOptions: {};

  constructor(private httpClient: HttpClient, @Inject(APP_CONFIG) private appConfig: AppConfig) {
    this.basePointsURL = `${appConfig.serviceApiUrl}/common/v1.0/points/`;
    this.httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
  }

  /**
   * lookupPoints returns a list of 10 or less points suggestions based on a partial point input
   * @param point is the partial point to be suggested upon
   */
  lookupPoints(point: Point, maxRows?: number): Observable<Point[]> {
    const url = this.basePointsURL + 'lookup';
    // let newPnt = new Point(point);
    const req = {
      point: point,
      maxRows: maxRows || 5
    };

    return this.httpClient
      .post<Point[]>(url, JSON.stringify(req), this.httpOptions)
      .pipe(catchError(PointLookupService.handleErrors));
  }

  getTerminalByPoint(point: Point): Observable<Terminal> {
    const url = `${this.appConfig.serviceApiUrl}/myestes/Points/v1.0/terminal`;

    return this.httpClient.post<Terminal>(url, JSON.stringify(point), this.httpOptions);
  }

  static handleErrors(error: HttpErrorResponse) {
    let errMsgs = [];
    if (error.error instanceof Array) {
      error.error.forEach(e => {
        if (e.message) {
          errMsgs.push(e.message);
        }
      });
    } else if (error.error && error.error.message) {
      errMsgs.push(error.error.message);
    } else if (error.message) {
      errMsgs.push(error.message);
    } else {
      errMsgs.push('Something bad happened. Error not recognized.');
    }
    return throwError(errMsgs);
  }
}
