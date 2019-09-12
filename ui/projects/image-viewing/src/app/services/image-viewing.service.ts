import { Injectable } from '@angular/core';
import {
  ImageRequest,
  ImageResult,
  ImageDetailsRequest,
  Image,
  ImageType,
  EmailImagesRequest
} from '../models';
import { ServiceResponse, MyEstesGlobalHandlers } from 'common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ImageStatusResponse } from '../models/image-status-response.interface';
import { environment } from '../../environments/environment';
import { catchError } from 'rxjs/operators';

@Injectable()
export class ImageViewingService {
  httpOptions: any;

  constructor(private http: HttpClient) {
    this.httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
  }

  getImages(payload: ImageRequest): Observable<Array<ImageResult>> {
    return this.http.post<Array<ImageResult>>(
      `${environment.serviceApiUrl}/myestes/ImageViewing/v1.0/image/request`,
      payload
    );
  }

  getImageDetails(payload: ImageDetailsRequest): Observable<Array<Image>> {
    const qs = `?docType=${payload.docType}&key1=${payload.key1}&key2=${
      payload.key2
    }&key3=${payload.key3}&key4=${payload.key4}&key5=${payload.key5}`;

    return this.http.get<Array<Image>>(
      `${environment.serviceApiUrl}/myestes/ImageViewing/v1.0/image/view${qs}`
    );
  }

  getImageTypes(): Observable<Array<ImageType>> {
    return this.http.get<Array<ImageType>>(
      `${environment.serviceApiUrl}/myestes/ImageViewing/v1.0/image/types`
    );
  }

  emailImages(request: EmailImagesRequest): Observable<ServiceResponse> {
    const url = `${environment.serviceApiUrl}/myestes/ImageViewing/v1.0/image/email`;
    return this.http.post(url, JSON.stringify(request), this.httpOptions).pipe(
      catchError(MyEstesGlobalHandlers.handleErrors)
    );
  }

  getImageStatus(
    docType: string,
    requestNumber: string,
    searchData: string
  ): Observable<ImageStatusResponse> {
    const qs = `?docType=${docType}&requestNumber=${requestNumber}&searchData=${searchData}`;

    return this.http.get<ImageStatusResponse>(
      `${environment.serviceApiUrl}/myestes/ImageViewing/v1.0/image/status${qs}`
    );
  }
}
