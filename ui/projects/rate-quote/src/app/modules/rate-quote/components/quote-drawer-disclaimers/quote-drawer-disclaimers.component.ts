import { Component, OnInit, Input } from '@angular/core';
import { DialogService } from 'common';
import { RateQuote, ServiceLevelEnum } from '../../models';
import { DisclosuresModalComponent } from '../disclosures-modal/disclosures-modal.component';
import { environment } from '../../../../../environments/environment';

@Component({
  selector: 'app-quote-drawer-disclaimers',
  templateUrl: './quote-drawer-disclaimers.component.html',
  styleUrls: ['./quote-drawer-disclaimers.component.scss']
})
export class QuoteDrawerDisclaimersComponent implements OnInit {
  pickupRequestUrl = `${environment.appBaseUrl}/pickup-request`;
  @Input() quote: RateQuote;

  constructor(private dialog: DialogService) {}

  ngOnInit() {}

  openDisclosuresModal() {
    this.dialog.prompt(DisclosuresModalComponent, this.quote);
  }

  showVTLBasicDisclaimer() {
    return this.quote.service.id === ServiceLevelEnum.VolumeAndTruckloadBasic;
  }
}
