import { Component, OnInit, Input } from '@angular/core';
import { RateQuote, ServiceLevelEnum } from '../../models';
import { AuthService } from 'common';

@Component({
  selector: 'app-quote-commodities',
  templateUrl: './quote-commodities.component.html',
  styleUrls: ['./quote-commodities.component.scss']
})
export class QuoteCommoditiesComponent implements OnInit {
  @Input() quote: RateQuote;

  get showDimensions() {
    return this.quote.app.toUpperCase() !== 'LTL' && this.auth.isLoggedIn;
  }

  get showRateChargse() {
    return this.quote.service.id === ServiceLevelEnum.LTLStandardTransit;
  }

  get showPieces() {
    return this.quote.app !== 'LTL';
  }

  constructor(private auth: AuthService) {}

  ngOnInit() {}
}
