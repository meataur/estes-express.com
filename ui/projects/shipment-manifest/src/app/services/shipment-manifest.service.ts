import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {
  ShipmentManifestResponse,
  PaginatedRequestPayload,
  SubAccountsResponse,
  SubAccount,
  EmailRequestPayload
} from '../models';
import { Observable } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { MyEstesGlobalHandlers } from 'common';

@Injectable()
export class ShipmentManifestService {
  constructor(private http: HttpClient) {}

  getShipmentManifest(payload: PaginatedRequestPayload): Observable<ShipmentManifestResponse> {
    const { pageSize, pageNum, ...body } = payload;

    // tslint:disable-next-line:max-line-length
    return this.http.post<ShipmentManifestResponse>(
      `${environment.serviceApiUrl}/myestes/ShipmentManifest/v1.0/view?page=${
        payload.pageNum
      }&size=${payload.pageSize}`,
      body
    ).pipe(catchError(MyEstesGlobalHandlers.handleErrors));
  }

  emailShipmentManifest(payload: EmailRequestPayload): Observable<any> {
    return this.http.post<any>(
      `${environment.serviceApiUrl}/myestes/ShipmentManifest/v1.0/email`,
      payload
    ).pipe(catchError(MyEstesGlobalHandlers.handleErrors));
  }
}
