export class EmailStatusRequest {
	session:  string;
	pros: any[];
	addresses: string;

	constructor() {
		this.session = null;
		this.pros = [];
		this.addresses = null;
	}
}
