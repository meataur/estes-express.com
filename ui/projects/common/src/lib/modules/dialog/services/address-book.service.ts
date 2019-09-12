import { Injectable, Inject } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError, map } from 'rxjs/operators';
import { throwError } from 'rxjs/internal/observable/throwError';
import { BookAddress } from '../models/book-address.model';
import { APP_CONFIG } from '../../../app.config';
import { AppConfig } from '../../../models/app-config.interface';

@Injectable({
  providedIn: 'root'
})
export class AddressBookService {
  private baseURL: String;
  private httpOptions: {};

  constructor(private httpClient: HttpClient, @Inject(APP_CONFIG) appConfig: AppConfig) {
    this.baseURL = `${appConfig.serviceApiUrl}/myestes/AddressBook/v1.0/`;
    this.httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
  }

  getAddressList(): Observable<BookAddress[]> {
    const url = this.baseURL + 'show';
    return this.httpClient
      .get<BookAddress[]>(url)
      .pipe(catchError(AddressBookService.handleErrors));
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
