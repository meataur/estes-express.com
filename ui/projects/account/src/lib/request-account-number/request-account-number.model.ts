export class RequestInfoRequest {
  addresses: string;
  company: string;
  email: string;
  name: string;
  phone: string;
  parentCompany: string;
  source: string;

  constructor() {
    this.addresses = '';
    this.company = '';
    this.email = '';
    this.name = '';
    this.phone = '';
    this.parentCompany = '';
    this.source = '';
  }
}
