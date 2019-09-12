import { Component, OnInit, Input } from '@angular/core';
import { DialogService } from 'common';
import { DisclosuresModalComponent } from '../disclosures-modal/disclosures-modal.component';
import { RateQuote } from '../../models';
import { RateQuoteService } from '../../services/rate-quote.service';
import { environment } from '../../../../../environments/environment';
import { RateEstimateService } from '../../services/rate-estimate.service';

@Component({
  selector: 'app-quote-estimate-side-panel',
  templateUrl: './quote-estimate-side-panel.component.html',
  styleUrls: ['./quote-estimate-side-panel.component.scss']
})
export class QuoteEstimateSidePanelComponent implements OnInit {
  @Input() quote: RateQuote;

  constructor(
    private dialog: DialogService,
    private rateQuoteService: RateQuoteService,
    private estimateService: RateEstimateService
  ) {}

  ngOnInit() {}

  startNewQuote() {
    window.location.href = `${environment.appBaseUrl}/rate-quote/estimate`;
  }

  openDisclosuresModal() {
    this.dialog.prompt(DisclosuresModalComponent, this.quote);
  }

  createBol() {
    this.rateQuoteService.navigateToBillOfLading(this.quote.quoteID);
  }

  requestPickup() {
    this.rateQuoteService.navigateToPickupRequest(this.quote);
  }

  reviseQuote() {
    this.estimateService.reviseQuote();
  }
}
