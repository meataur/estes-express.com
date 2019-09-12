import { Component, OnInit } from '@angular/core';
import { HeaderService } from './header.service';

@Component({
	selector: 'lib-footer',
	templateUrl: './footer.component.html'
})
export class FooterComponent implements OnInit {

	public shippingMenu: Array<any>;
	public companyMenu: Array<any>;
	public supportMenu: Array<any>;
	public currentYear: number;

	constructor(private headerService: HeaderService) {
		this.shippingMenu = [];
		this.companyMenu = [];
		this.supportMenu = [];
	}

	ngOnInit() {
		const self = this;
		this.currentYear = new Date().getFullYear() || 2019;
		this.headerService.getNavigation('/menus/shipping').subscribe(
			data => {
				self.shippingMenu = (Array.isArray(data)) ? data : [];
			},
			error => {
				// console.warn('Get shipping menu failed');
			}
		);
		this.headerService.getNavigation('/menus/company').subscribe(
			data => {
				self.companyMenu = (Array.isArray(data)) ? data : [];
			},
			error => {
				// console.warn('Get company menu failed');
			}
		);
		this.headerService.getNavigation('/menus/support').subscribe(
			data => {
				self.supportMenu = (Array.isArray(data)) ? data : [];
			},
			error => {
				// console.warn('Get support menu failed');
			}
		);
	}

}
