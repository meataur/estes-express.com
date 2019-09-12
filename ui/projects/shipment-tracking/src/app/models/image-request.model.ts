export class ImageRequest {
	user: string;
	session: string;
	pro: string;
	party: boolean;
	payor: boolean;
	bolRequestNum: string;
	drRequestNum: string;
	wrRequestNum: string;

	constructor() {
		this.user = '';
		this.session = '';
		this.pro = '';
		this.party = false;
		this.payor = false;
		this.bolRequestNum = '';
		this.drRequestNum = '';
		this.wrRequestNum = '';
	}
}
