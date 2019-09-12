import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError, map } from 'rxjs/operators';
import { throwError } from 'rxjs/internal/observable/throwError';
import { BookAddress } from './book-address.model';
import { AddressSearch } from './address-search.model';
import { ServiceResponse, MyEstesGlobalHandlers } from 'common';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AddressBookService {

  private baseURL: String;
  private httpOptions: {};

  constructor( private httpClient: HttpClient ) {
    this.baseURL = `${environment.serviceApiUrl}/myestes/AddressBook/v1.0/`;
    this.httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':'application/json'
      })
    };
   }

   getAddressList(): Observable<BookAddress[]> {
     const url = this.baseURL + 'show';
     return this.httpClient.get<BookAddress[]>(url)
       .pipe(
         catchError(MyEstesGlobalHandlers.handleErrors)
       )
   }

   search(addressSearch: AddressSearch): Observable<BookAddress[]> {
    const url = this.baseURL + 'search';
    return this.httpClient.post<BookAddress[]>(url, addressSearch, this.httpOptions)
      .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      )
    }

   addAddress(bookAddress: BookAddress): Observable<ServiceResponse> {
     const url = this.baseURL + 'do/add';
     return this.httpClient.post<ServiceResponse>(url, bookAddress, this.httpOptions)
     .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      );
   }

   updateAddress(bookAddress: BookAddress): Observable<ServiceResponse> {
    const url = this.baseURL + 'do/update';
    return this.httpClient.post<ServiceResponse>(url, bookAddress, this.httpOptions)
    .pipe(
       catchError(MyEstesGlobalHandlers.handleErrors)
     );
  }

  deleteAddress(bookAddress: BookAddress): Observable<ServiceResponse> {
    const url = this.baseURL + 'do/delete';
    return this.httpClient.post<ServiceResponse>(url, bookAddress, this.httpOptions)
    .pipe(
       catchError(MyEstesGlobalHandlers.handleErrors)
     );
  }

  upload(formData: FormData, operation: string): Observable<ServiceResponse> {
    const url = this.baseURL + 'upload/' + operation;
    return this.httpClient.post<ServiceResponse>(url, formData, {
      headers: new HttpHeaders({
        'Remove-Content-Type': '' //use this empty string value will allow browser to autoset this header
      })
    })
    .pipe(
      catchError(MyEstesGlobalHandlers.handleErrors)
    );
  }
}
