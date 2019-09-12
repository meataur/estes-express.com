import { Component, OnInit, Input } from '@angular/core';
import { RateQuote } from '../../../../models';
import { NextStepsChildComponent } from '../../models/quote-details-child-component.interface';
import { RateQuoteService } from '../../../../services/rate-quote.service';
import { DialogService } from 'common';
import { DisclosuresModalComponent } from '../../../disclosures-modal/disclosures-modal.component';

@Component({
  selector: 'app-non-guaranteed-next-steps',
  templateUrl: './non-guaranteed-next-steps.component.html',
  styleUrls: ['./non-guaranteed-next-steps.component.scss']
})
export class NonGuaranteedNextStepsComponent implements OnInit, NextStepsChildComponent {
  @Input() quote: RateQuote;

  constructor(private rateQuoteService: RateQuoteService, private dialog: DialogService) {}

  ngOnInit() {}

  openDisclosuresModal() {
    this.dialog.prompt(DisclosuresModalComponent, this.quote);
  }

  createBol() {
    this.rateQuoteService.navigateToBillOfLading(this.quote.quoteID);
  }

  requestPickup() {
    this.rateQuoteService.navigateToPickupRequest(this.quote);
  }
}
