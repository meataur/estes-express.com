import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { PromoService } from '../services/promo.service';

@Component({
  selector: 'lib-left-promo',
  templateUrl: './leftpromo.component.html'
})
export class LeftPromoComponent implements OnInit {
  public appId: string;
  public promos: any[];

  constructor(private promoService: PromoService) {
    this.appId = '';
    this.promos = [];
  }

  ngOnInit() {
    const self = this;
    // console.log('LeftPromoComponent.ngOnInit()');
    this.promoService.idSet.subscribe((appId: string) => {
      self.appId = appId;
      // console.log('LeftPromoComponent.promoService.idSet: ' + self.appId);
      if (self.appId) {
        self.promoService.getLeftPromos().subscribe(
          (data: any) => {
            // console.log(data);
            if (data && data.hasOwnProperty('contentlets') && Array.isArray(data['contentlets'])) {
              self.promos = data['contentlets'];
            } else {
              // console.warn('LeftPromoComponent: no data returned');
            }
          },
          err => {
            // console.error('Error calling service: LeftPromoService.getLeftPromos()', '\n', err);
          }
        );
      } else {
        // console.warn('LeftPromoComponent.promoService.idSet appId not set');
      }
    });
  }
}
