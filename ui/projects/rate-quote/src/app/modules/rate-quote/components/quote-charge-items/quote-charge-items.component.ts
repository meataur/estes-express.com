import { Component, OnInit, Input } from '@angular/core';
import { RateQuote, Charge } from '../../models';
import { environment } from '../../../../../environments/environment';

@Component({
  selector: 'app-quote-charge-items',
  templateUrl: './quote-charge-items.component.html',
  styleUrls: ['./quote-charge-items.component.scss']
})
export class QuoteChargeItemsComponent implements OnInit {
  fuelSurchargeUrl = `${environment.cmsUrl}/resources/fees-and-surcharges/fuel-surcharge`;
  @Input() quote: RateQuote;

  constructor() {}

  ngOnInit() {}

  get charges(): Array<Charge> {
    return this.quote.charges.filter(c => c.show === true);
  }

  isFuelSurcharge(charge: string): boolean {
    return charge.toLowerCase().includes(`fuel surcharge`);
  }
}
