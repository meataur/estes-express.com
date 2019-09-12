import { Component, OnInit, Input } from '@angular/core';
import { RateQuote, Point, TermsEnum, RoleEnum } from '../../../../models';
import { Address, DialogService, UtilService } from 'common';
import { PointDetailsModalComponent } from '../../../point-details-modal/point-details-modal.component';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { QuoteDetails } from '../../quote-details.model';

@Component({
  selector: 'app-ltl-quote-details',
  templateUrl: './ltl-quote-details.component.html',
  styleUrls: ['./ltl-quote-details.component.scss']
})
export class LtlQuoteDetailsComponent extends QuoteDetails implements OnInit {
  formattedAccount$: Observable<string>;

  constructor(private utilService: UtilService, private dialog: DialogService) {
    super();
  }

  ngOnInit() {
    // this.formattedAccount$ = this.utilService
    //   .getAccountInfo(this.quote.accountCode)
    //   .pipe(map(res => `${res.name} - ${res.accountNumber}`));
  }

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
