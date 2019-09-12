import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class EmailService {
  constructor(private http: HttpClient) {}

  sendEmailRequest(url: string, body: any): Observable<any> {
    return this.http.post(url, body);
  }
}
