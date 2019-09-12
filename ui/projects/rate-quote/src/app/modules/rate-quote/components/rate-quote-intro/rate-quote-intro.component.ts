import { Component, OnInit } from '@angular/core';
import { AuthService, UtilService } from 'common';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-rate-quote-intro',
  templateUrl: './rate-quote-intro.component.html',
  styleUrls: ['./rate-quote-intro.component.scss']
})
export class RateQuoteIntroComponent implements OnInit {
  accountName$: Observable<string>;

  constructor(private auth: AuthService, private utilService: UtilService) {}

  ngOnInit() {
    this.getAccountName();
  }

  private getAccountName(): Observable<string> {
    if (this.auth.isLoggedIn) {
      const token = this.auth.getAuthToken();
      this.accountName$ = this.utilService
        .getAccountInfo(token.accountCode)
        .pipe(map(res => res.name));
    } else {
      return of('');
    }
  }

  get today() {
    return new Date();
  }
}
