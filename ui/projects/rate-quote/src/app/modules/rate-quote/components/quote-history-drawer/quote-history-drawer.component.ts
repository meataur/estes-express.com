import { Component, OnInit, Input } from '@angular/core';
import { environment } from '../../../../../environments/environment';
import { GetEmailModalComponent } from '../get-email-modal/get-email-modal.component';
import { RateQuoteService } from '../../services/rate-quote.service';
import { DialogService, UtilService } from 'common';
import { RateQuote } from '../../models';
import { DisclosuresModalComponent } from '../disclosures-modal/disclosures-modal.component';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-quote-history-drawer',
  templateUrl: './quote-history-drawer.component.html',
  styleUrls: ['./quote-history-drawer.component.scss']
})
export class QuoteHistoryDrawerComponent implements OnInit {
  @Input() rateQuote: RateQuote;
  formattedAccount$: Observable<string>;

  constructor(private utilService: UtilService) {}

  ngOnInit() {
    this.formattedAccount$ = this.utilService.getAccountInfo(this.rateQuote.accountCode).pipe(
      map(res => `${res.name} - ${res.accountNumber}`),
    );
  }

  hasContactInformation(): boolean {
    if (this.rateQuote.contactName || this.rateQuote.contactPhone || this.rateQuote.contactEmail) {
      return true;
    }

    return false;
  }
}
