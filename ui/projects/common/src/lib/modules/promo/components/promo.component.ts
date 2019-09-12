import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { PromoService } from '../services/promo.service';

@Component({
	selector: 'lib-promo',
	templateUrl: './promo.component.html'
})
export class PromoComponent implements OnInit {
	public appId: string;
	public appState: string;
	public promo: any;

	constructor(private promoService: PromoService) {
		this.appId = '';
		this.appState = 'any';
		this.promo = {
			body: ''
		};
	}

	ngOnInit() {
		const self = this;
		// console.log('PromoComponent.ngOnInit()');
		this.promoService.stateSet.subscribe((appState: string) => {
			self.appState = appState;
			// console.log('PromoComponent.promoService.stateSet: ' + self.appState);
			if (self.appId) {
				self.promoService.getPromos().subscribe(
					(data: any) => {
						// console.log(data);
						let appMatch = false;
						if (data && data.hasOwnProperty('contentlets') && Array.isArray(data['contentlets'])) {
							for (const app of data['contentlets']) {
								if (!appMatch && app.authStatus === self.appState && app.application === self.appId) {
									self.promo.body = app.content;
									appMatch = true;
								}
							}
							if (appMatch === false) {
								// console.warn('PromoComponent: no applications matched');
								self.promo.body = '';
							}
						} else {
							// console.warn('PromoComponent: no data returned');
							self.promo.body = '';
						}
					},
					err => {
						// console.error('Error calling service: PromoService.getPromos()', '\n', err);
					}
				);
			} else {
				// console.warn('PromoComponent.promoService.stateSet appId not set');
			}
		});
		this.promoService.idSet.subscribe((appId: string) => {
			self.appId = appId;
			// console.log('PromoComponent.promoService.idSet: ' + self.appId);
		});
	}
}
