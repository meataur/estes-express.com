import { Component, OnInit, Input } from '@angular/core';
import { RateQuote } from '../../models';
import { environment } from '../../../../../environments/environment';

@Component({
  selector: 'app-quote-options',
  templateUrl: './quote-options.component.html',
  styleUrls: ['./quote-options.component.scss']
})
export class QuoteOptionsComponent implements OnInit {
  @Input() quote: RateQuote;

  constructor() {}

  ngOnInit() {}

  startNewQuote() {
    window.location.href = `${environment.appBaseUrl}/rate-quote/rate-quote/create`;
  }
}
