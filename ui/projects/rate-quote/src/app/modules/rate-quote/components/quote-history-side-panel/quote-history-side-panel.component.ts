import { Component, OnInit, Input } from '@angular/core';
import { RateQuote } from '../../models';
import { DialogService } from 'common';
import { DisclosuresModalComponent } from '../disclosures-modal/disclosures-modal.component';

@Component({
  selector: 'app-quote-history-side-panel',
  templateUrl: './quote-history-side-panel.component.html',
  styleUrls: ['./quote-history-side-panel.component.scss']
})
export class QuoteHistorySidePanelComponent implements OnInit {
  @Input() quote: RateQuote;

  constructor(private dialog: DialogService) {}

  ngOnInit() {}

  openDisclosuresModal() {
    this.dialog.prompt(DisclosuresModalComponent, this.quote);
  }
}
