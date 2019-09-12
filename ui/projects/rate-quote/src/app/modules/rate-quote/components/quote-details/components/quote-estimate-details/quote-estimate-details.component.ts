import { Component, OnInit, Input } from '@angular/core';
import { RoleEnum, TermsEnum, Point, RateQuote } from '../../../../models';
import { Address, DialogService } from 'common';
import { PointDetailsModalComponent } from '../../../point-details-modal/point-details-modal.component';
import { QuoteDetails } from '../../quote-details.model';

@Component({
  selector: 'app-quote-estimate-details',
  templateUrl: './quote-estimate-details.component.html',
  styleUrls: ['./quote-estimate-details.component.scss']
})
export class QuoteEstimateDetailsComponent extends QuoteDetails implements OnInit {
  constructor(private dialog: DialogService) {
    super();
  }

  ngOnInit() {}

  openTerminalInformation(p: Point) {
    this.dialog.prompt(PointDetailsModalComponent, p);
  }

  generateAddressFromPoint(p: Point): Address {
    const a = new Address();

    a.country = p.country;
    a.city = p.city;
    a.state = p.state;
    a.zip = p.zip;

    return a;
  }

  getRole(role: string) {
    return RoleEnum[role];
  }

  getTerms(terms: string) {
    return TermsEnum[terms];
  }
}
