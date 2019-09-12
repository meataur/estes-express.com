import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {
  Profile,
  ServiceResponse,
  Password,
  EmailPreference,
  EmailAddress,
  Username
} from '../models';
import { Observable } from 'rxjs';
import { APP_CONFIG } from '../../../models/app.config';
import { AppConfig } from '../../../models/app-config.interface';
import { catchError } from 'rxjs/operators';
import { MyEstesGlobalHandlers } from 'common';

@Injectable()
export class ProfileService {
  constructor(private http: HttpClient, @Inject(APP_CONFIG) private appConfig: AppConfig) {}

  // https://qa.estesinternal.com/api/myestes/common/v1.0/swagger-ui.html#/
  // getAccountInfo(): Observable<Profile> {
  //   return this.http.get<Profile>(
  //     `${this.appConfig.serviceApiUrl}/myestes/common/v1.0/customer/getProfileInfo`
  //   );
  // }

  // https://qa.estesinternal.com/api/myestes/Profile/v1.0/swagger-ui.html#/profile-controller
  changeEmail(email: EmailAddress): Observable<ServiceResponse> {
    return this.http.post<ServiceResponse>(
      `${this.appConfig.serviceApiUrl}/myestes/Profile/v1.0/change_email`,
      email
    );
  }

  // https://qa.estesinternal.com/api/myestes/Profile/v1.0/swagger-ui.html#/profile-controller
  changeEmailPreference(emailPref: EmailPreference): Observable<ServiceResponse> {
    return this.http.post<ServiceResponse>(
      `${this.appConfig.serviceApiUrl}/myestes/Profile/v1.0/change_email_preference`,
      emailPref
    );
  }

  // https://qa.estesinternal.com/api/myestes/Profile/v1.0/swagger-ui.html#/profile-controller
  changePassword(password: Password): Observable<ServiceResponse> {
    return this.http.post<ServiceResponse>(
      `${this.appConfig.serviceApiUrl}/myestes/Profile/v1.0/change_password`,
      password
    );
  }

  // https://qa.estesinternal.com/api/myestes/Profile/v1.0/swagger-ui.html#/profile-controller
  changeUsername(username: Username): Observable<ServiceResponse> {
    return this.http.post<ServiceResponse>(
      `${this.appConfig.serviceApiUrl}/myestes/Profile/v1.0/change_username`,
      username
    ).pipe(catchError(MyEstesGlobalHandlers.handleErrors));
  }
}
