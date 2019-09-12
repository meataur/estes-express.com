export class ShipmentTrackingRequest {
	session: string;
	type: string;
	search: string;
	sort: string;
	direction: string;
	rowsPerPage: string;
	page: string;
	totalPages: string;

	constructor() {
		this.session = '';
		this.type = 'PRO';
		this.search = null;
		this.sort = 'Date';
		this.direction = 'ASCEND';
		this.rowsPerPage = '25';
		this.page = '1';
		this.totalPages = '1';
	}
}
