import { Component, OnInit, Input } from '@angular/core';
import { RateQuote } from '../../models';
import { DialogService, AuthService } from 'common';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-quote-drawer',
  templateUrl: './quote-drawer.component.html',
  styleUrls: ['./quote-drawer.component.scss']
})
export class QuoteDrawerComponent implements OnInit {
  @Input() rateQuote: RateQuote;
  @Input() formattedAccount$: Observable<string>;

  constructor(
    public auth: AuthService
  ) {}

  ngOnInit() {}

  hasContactInformation(): boolean {
    if (this.rateQuote.contactName || this.rateQuote.contactPhone || this.rateQuote.contactEmail) {
      return true;
    }

    return false;
  }
}
