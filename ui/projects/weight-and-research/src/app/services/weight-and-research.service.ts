import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { WREmailRequest, WRSearchRequest, WRCertificate, WREmailResponse } from '../models';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { MyEstesGlobalHandlers } from 'common';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class WeightAndResearchService {
  constructor(private http: HttpClient) {}

  getImageList(wrSearchRequest: WRSearchRequest): Observable<Array<WRCertificate>> {
    return this.http.post<Array<WRCertificate>>(
      `${environment.serviceApiUrl}/myestes/ImageViewing/v1.0/wr/search`,
      wrSearchRequest
    );
  }

  getDocumentDetails(proNumbers: Array<string>): Observable<Array<WRCertificate>> {
    return this.http.post<Array<WRCertificate>>(
      `${environment.serviceApiUrl}/myestes/ImageViewing/v1.0/wr/documentDetails`,
      proNumbers
    );
  }

  sendEmail(wrEmailRequest: WREmailRequest) {
    // const options = { responseType: 'text' as 'json' };
    return this.http.post<WREmailResponse>(
      `${environment.serviceApiUrl}/myestes/ImageViewing/v1.0/wr/getEmail`,
      wrEmailRequest
    ).pipe(
      catchError(MyEstesGlobalHandlers.handleErrors)
    );
  }

  downloadDocument(fileName: string) {
    let headers = new HttpHeaders();
    headers = headers.set('Accept', 'application/pdf');
    // const options = { responseType: 'blob', headers: headers };
    return this.http.get(
      `${environment.serviceApiUrl}/myestes/ImageViewing/v1.0/wr/download?fileName=${fileName}`,
      { responseType: 'blob', headers: headers }
    );
  }
}
