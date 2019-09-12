import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ImageRetrievalRequest, DocumentInfo } from '../models';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { MyEstesGlobalHandlers } from 'common';
import { catchError } from 'rxjs/operators';

@Injectable()
export class DocumentRetrievalService {
  constructor(private http: HttpClient) {}

  sendDocumentRetrievalRequest(payload: ImageRetrievalRequest): Observable<DocumentInfo | any> {
    return this.http.post<DocumentInfo>(
      `${environment.serviceApiUrl}/myestes/DocumentRetrieval/v1.0/doc`,
      payload
    ).pipe(
      catchError(MyEstesGlobalHandlers.handleErrors)
    );
  }
}
