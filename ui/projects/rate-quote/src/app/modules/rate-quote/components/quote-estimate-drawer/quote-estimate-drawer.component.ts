import { Component, OnInit, Input } from '@angular/core';
import { RateQuote } from '../../models';

@Component({
  selector: 'app-quote-estimate-drawer',
  templateUrl: './quote-estimate-drawer.component.html',
  styleUrls: ['./quote-estimate-drawer.component.scss']
})
export class QuoteEstimateDrawerComponent implements OnInit {
  @Input() rateQuote: RateQuote;

  constructor() {}

  ngOnInit() {}

  hasContactInformation(): boolean {
    if (this.rateQuote.contactName || this.rateQuote.contactPhone || this.rateQuote.contactEmail) {
      return true;
    }

    return false;
  }

}
