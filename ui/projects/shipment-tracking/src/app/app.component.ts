import { Component } from '@angular/core';
import { ActivatedRoute, RoutesRecognized, Router } from '@angular/router';

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html'
})
export class AppComponent {
	title = 'Shipment Tracking';

	constructor(
		public route: Router,
		public activatedRoute: ActivatedRoute) {

	}

}
