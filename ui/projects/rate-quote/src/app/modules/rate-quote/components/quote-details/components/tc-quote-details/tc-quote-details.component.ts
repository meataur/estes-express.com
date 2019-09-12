import { Component, OnInit, Input } from '@angular/core';
import { Point, RateQuote, RoleEnum, TermsEnum, ServiceLevelEnum } from '../../../../models';
import { Address, DialogService, UtilService } from 'common';
import { PointDetailsModalComponent } from '../../../point-details-modal/point-details-modal.component';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { QuoteDetails } from '../../quote-details.model';
import { TimePipe } from '../../../../pipes/display-time.pipe';
import { RateQuoteService } from '../../../../services/rate-quote.service';

@Component({
  selector: 'app-tc-quote-details',
  templateUrl: './tc-quote-details.component.html',
  styleUrls: ['./tc-quote-details.component.scss']
})
export class TcQuoteDetailsComponent extends QuoteDetails implements OnInit {
  formattedAccount$: Observable<string>;
  foodWarehouse$: Observable<string>;

  constructor(
    private utilService: UtilService,
    private dialog: DialogService,
    private timePipe: TimePipe,
    private rateQuoteService: RateQuoteService
  ) {
    super();
  }

  ngOnInit() {
    // this.formattedAccount$ = this.utilService
    //   .getAccountInfo(this.quote.accountCode)
    //   .pipe(map(res => `${res.name} - ${res.accountNumber}`));
    this.foodWarehouse$ = this.rateQuoteService.getFoodWarehouses().pipe(
      map(res => {
        const result = res.find(f => +f.id === this.quote.foodWarehouseId);
        return result ? result.name : ``;
      })
    );
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

  getDeliveryDate() {
    if (this.quote.service.id === ServiceLevelEnum.VolumeAndTruckloadBasic) {
      return `TBD**`;
    }

    return this.quote.delivery.date;
  }

  getDeliveryTime() {
    if (this.quote.service.id === ServiceLevelEnum.VolumeAndTruckloadBasic) {
      return `TBD**`;
    }

    return this.timePipe.transform(this.quote.delivery.time);
  }
}
