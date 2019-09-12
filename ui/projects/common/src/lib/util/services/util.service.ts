import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { SubAccountsResponse, SubAccount } from '../../models/sub-account.interface';
import { Observable, of, throwError } from 'rxjs';
import { map, catchError, tap, shareReplay } from 'rxjs/operators';
import { AccountInfo } from '../../models';
import { APP_CONFIG } from '../../app.config';
import { AppConfig } from '../../models/app-config.interface';
import { PackageType } from '../models';
import { ProfileDTO } from '../../models/profile.interface';
import { CountryEnum } from '../../models/country.enum';
import { Terminal } from '../../models/terminal.model';

@Injectable()
export class UtilService {
  classes$: Observable<Array<string>>;
  packageTypes$: Observable<Array<PackageType>>;

  constructor(private http: HttpClient, @Inject(APP_CONFIG) private appConfig: AppConfig) {}

  getProfileInfo(): Observable<ProfileDTO> {
    return this.http.get<ProfileDTO>(
      `${this.appConfig.serviceApiUrl}/myestes/common/v1.0/customer/getProfileInfo`
    );
  }

  getAccountInfo(accountCode: string): Observable<AccountInfo> {
    return this.http
      .get<AccountInfo>(
        `${this.appConfig.serviceApiUrl}/myestes/common/v1.0/customer/getAccountInfo/${accountCode}`
      )
      .pipe(
        map(res => {
          if (res && res.address) {
            res.address.country =
              res.address.country ||
              (Object.keys(CountryEnum).find(
                c => CountryEnum[c] === CountryEnum.US
              ) as CountryEnum);
          }
          return res;
        })
      );
  }

  isSubAccountOf(selectedAccount: string): Observable<boolean> {
    return this.http.get<boolean>(
      `${
        this.appConfig.serviceApiUrl
      }/myestes/common/v1.0/isSubAccountOf?selectedAccount=${selectedAccount}`
    );
  }

  getPaginatedSubAccounts(
    page: number,
    size: number,
    sort: 'name' | 'address' | 'city' | 'state' | 'zip' | 'acct' | string,
    order: 'asc' | 'desc' | string,
    searchTerm?: string
  ): Observable<SubAccountsResponse> {
    let queryString = `?page=${page}&size=${size}&sort=${sort}&order=${order}&searchBy=city,state,zip,streetAddress,streetAddress2,name,accountNumber`;
    if (searchTerm) queryString = queryString + `&searchTerm=${searchTerm}`;
    return this.http
      .get<SubAccountsResponse>(
        `${this.appConfig.serviceApiUrl}/myestes/common/v1.0/sub_accounts${queryString}`
      )
      .pipe(
        map(next => {
          next.content = next.content.filter(value => value.accountNumber.trim());
          return next;
        })
      );
  }

  getStates(country: string): Observable<Array<string>> {
    return this.http
      .get<Array<string>>(`${this.appConfig.serviceApiUrl}/common/v1.0/states/${country}`)
      .pipe(map(states => states.sort()));
  }

  getClasses(): Observable<Array<string>> {
    if (!this.classes$) {
      this.classes$ = this.http
        .get<Array<string>>(`${this.appConfig.serviceApiUrl}/common/v1.0/commodity/classes`)
        .pipe(
          shareReplay(1)
        );
    }

    return this.classes$;
  }

  getPackageTypes(): Observable<Array<PackageType>> {
    if (!this.packageTypes$) {
      this.packageTypes$ = this.http
        .get<Array<PackageType>>(
          `${this.appConfig.serviceApiUrl}/common/v1.0/commodity/packageTypes`
        )
        .pipe(
          shareReplay(1)
        );
    }

    return this.packageTypes$;
  }

  getSubAccounts(searchTerm?: string): Observable<SubAccountsResponse> {
    return this.getPaginatedSubAccounts(1, 100, 'accountCode', 'asc', searchTerm || null);
  }

  getSubAccountsByAccountNumber(searchTerm): Observable<SubAccountsResponse> {
    let queryString = `?page=1&size=100&sort=accountNumber&order=asc&searchBy=accountNumber&searchTerm=${searchTerm}`;
    return this.http
      .get<SubAccountsResponse>(
        `${this.appConfig.serviceApiUrl}/myestes/common/v1.0/sub_accounts${queryString}`
      )
      .pipe(
        map(next => {
          next.content = next.content.filter(value => value.accountNumber.trim());
          return next;
        })
      );
  }

  validateCityStateZip(city: string, state: string, zip: string): Observable<boolean> {
    return this.http
      .get<boolean>(
        `${
          this.appConfig.serviceApiUrl
        }/common/v1.0/validate/city_state_zip?city=${city}&state=${state}&zip=${zip}`
      )
      .pipe(
        catchError(err => {
          if (err instanceof HttpErrorResponse) {
            if (err.status === 400) {
              return of(false);
            } else {
              return throwError(err);
            }
          }
          return throwError(err);
        })
      );
  }

  validateProNumber(proNumber: string): Observable<boolean> {
    return this.http.get<boolean>(
      `${this.appConfig.serviceApiUrl}/common/v1.0/validate/pro?proNumber=${proNumber}`
    );
  }

  extractTextAreaLineItems(val: string): Array<string> {
    return val.split(/\r?\n/g);
  }

  /**
   * getTerminalById fetches ther terminal data associated with a specific terminal ID
   * @param id the specific terminal ID
   */
  getTerminalById(id: String): Observable<Terminal> {
    const url = `${this.appConfig.serviceApiUrl}/common/v1.0/terminal/${id}`;
    return this.http.get<Terminal>(url).pipe(
      map(res => {
        return new Terminal(res);
      }),
      catchError(err => {
        if (err instanceof HttpErrorResponse) {
          if (err.status === 400) {
            return of(null);
          } else {
            return throwError(err);
          }
        }
        return throwError(err);
      })
    );
  }

  /**
   * @description Determines whether a date/time is past the current date/time (now).
   * @param expirationDate MM/dd/yyyy
   * @param expirationTime hh:mm:ss
   */
  isDateExpired(expirationDate: string, expirationTime: string): boolean {
    if (expirationDate && expirationTime) {
      try {
        const expiration = new Date(`${expirationDate} ${expirationTime}`);
        const now = new Date();

        return now > expiration;
      } catch (err) {
        return false;
      }
    }

    return false;
  }

  /**
   * @description Open multiple images in a new tab & show only one link for multipages.
   * @param imageDetails are the images to be opened
   */

  openImagesInNewTab(imagePaths) {
    let myString = ``;
    for (let i = 0; i < imagePaths.length; i++) {
      myString += `<div>
      <a href="${i + 1}"></a>
      <strong class="title">Page ${i + 1} of ${imagePaths.length}</strong><br />
      <div class="no_print">
          <div onclick="printPage()"><i class="fas fa-print fa-fw"></i></div>
          <div onclick="rotateImage('image${i + 1}')"><i class="fas fa-redo-alt fa-fw"></i></div>
          <div onclick="zoomImage('image${i +
            1}', 0.9)"><i class="fas fa-minus fa-fw"></i></div>
          <div onclick="zoomImage('image${i +
            1}', 1.1)"><i class="fas fa-plus fa-fw"></i></div>
      </div>
      <br />
      <img src="${imagePaths[i]}" width="630" name="image${i + 1}">
      <br /><br />
      </div>`;
    }

    const html = `
  <!DOCTYPE html>
  <html>
    <head>
      <meta name="viewport" content="width=device-width, initial-scale=1">
    </head>
    <style>
    @media print {
      .no_print {
        display: none;
      }
    }

    .title {
      display: inherit;
      margin-bottom: 1rem;
      font-family: Roboto, "Helvetica Neue", Helvetica, Arial, sans-serif;      text-decoration: none;
    }

    img {
      margin-left: auto;
      margin-right: auto;
      display: block;
    }

    .no_print {
      margin-bottom: 1rem;
      width: 14rem;
    }

    .no_print>div {
      float: right;
      width: fit-content;
      color: #D81E05;
      margin-right: 0.6rem;
      font-size: 0.9rem;
      font-family: Roboto, "Helvetica Neue", Helvetica, Arial, sans-serif;      text-decoration: none;
      padding: 0.8rem;
      border: rgba(0, 0, 0, 0.12) 1px solid;
      border-radius: 5rem;

      -webkit-user-select: none;
      -moz-user-select: none;
      -ms-user-select: none;
    }
    .no_print>div:hover {
      background: #f5f5f5;
    }
    </style>
    <body>
      ${myString}
    </body>
    <script src="https://use.fontawesome.com/releases/v5.9.0/js/all.js" data-auto-replace-svg="nest"></script>
    <script type="application/javascript">
    function zoomImage(imageOrImageName, factor) {
      const image =
        typeof imageOrImageName === 'string'
          ? document[imageOrImageName]
          : imageOrImageName;
      const currentWidth = image.width;
      const currentHeight = image.height;
      const width = currentWidth * factor;
      const height = currentHeight * factor;
      this.resizeImage(image, width, height);
    }

    function rotateImage(imageOrImageName) {
      const image = typeof imageOrImageName === 'string'
      ? document[imageOrImageName]
      : imageOrImageName;

      rotate+=90;
      rotate = rotate%360;

      var rt = rotate.toString() + 'deg';

      image.style.transform = 'rotate(' + rt + ')';
      
    }

    var rotate = 0;

    function resizeImage(imageOrImageName, width, height) {
      const image =
        typeof imageOrImageName === 'string'
          ? document[imageOrImageName]
          : imageOrImageName;
      image.width = width;
      image.height = height;
    }

    function printPage() {
      window.print();
    }
    </script>
  </html>
  `;

    const wnd = window.open("about:blank", "_blank");
    wnd.document.write(html);
    wnd.document.close();
  }

}


