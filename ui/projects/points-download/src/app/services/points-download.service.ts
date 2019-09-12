import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { MyEstesGlobalHandlers } from 'common';

type FileFormat = 'xls' | 'csv' | 'txt';
type FileSort = 'CTY' | 'ZIP';
type Criteria = '*' | '2' | '3' | '5' | 'US' | 'CN' | 'MX';

@Injectable({
  providedIn: 'root'
})
export class PointsDownloadService {
  baseUrl = environment.serviceApiUrl;
  constructor(private _http: HttpClient) {}

  getStates(country: string): Observable<Array<string>> {
    return this._http
      .get<Array<string>>(`${this.baseUrl}/common/v1.0/states/${country}`)
      .pipe(map(states => states.sort()));
  }

  downloadPoints(
    criteria: Criteria,
    email: string,
    fileFormat: FileFormat,
    fileSort: FileSort,
    state: string
  ): Observable<any> {
    const body = {
      criteria: criteria,
      email: email,
      fileFormat: fileFormat,
      fileSort: fileSort,
      state: state
    };

    return this._http.post(`${this.baseUrl}/myestes/Points/v1.0/download`, body).pipe(catchError(MyEstesGlobalHandlers.handleErrors));
  }
}
