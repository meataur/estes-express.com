import { Component, OnInit, Input } from '@angular/core';
import { RateQuote, RateSummary } from '../../models';
import { RateQuoteService } from '../../services/rate-quote.service';
import { Observable } from 'rxjs';
import { environment } from '../../../../../environments/environment';

@Component({
  selector: 'app-quote-reference-list',
  templateUrl: './quote-reference-list.component.html',
  styleUrls: ['./quote-reference-list.component.scss']
})
export class QuoteReferenceListComponent implements OnInit {
  @Input() quote: RateQuote;
  rateQuoteReferences$: Observable<Array<RateSummary>>;

  constructor(private rateQuoteService: RateQuoteService) {}

  ngOnInit() {
    this.rateQuoteReferences$ = this.rateQuoteService.getReferenceRateQuotes(
      this.quote.quoteRefNum,
      this.quote.quoteID
    );
  }

  scrollTo() {
    location.hash = '#' + this.quote.quoteID;
  }
}
