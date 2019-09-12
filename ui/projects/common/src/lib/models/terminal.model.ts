import { Address } from '../modules/components/models/address.interface';

export class Terminal {
  abbr: String;
  address: Address;
  displayOptions: String; //'A', 'B', or 'C'
  email: String;
  expanded: boolean;
  fax: String;
  id: String;
  lat: number;
  lng: number;
  loading: boolean;
  manager: String;
  managerEmail: String;
  name: String;
  nationalMap: String;
  phone: String;
  serviceArea: String;
  serviceMap: String;

  constructor(public term?: any) {
    this.abbr = term.abbr || term.alphaCode || "";
    if (term && !term.address) {
      term.address = {
        streetAddress: term.streetaddress || "",
        streetAddress2: term.streetAddress2 || "",
        city: term.city || "",
        country: term.country || "",
        state: term.state || "",
        zip: term.zip || "",
        zip4: term.zip4 || ""
      };
    }
    this.address = term.address || {};
    this.displayOptions = term.displayOptions || "";
    this.email = term.email || "";
    this.expanded = term.expanded || false;
    this.fax = term.fax || "";
    this.id = term.id || term.terminal || "";
    this.loading = term.loading || false;
    this.manager = term.manager || "";
    this.managerEmail = term.managerEmail || "";
    this.name = term.name || term.terminalName || "";
    if (!term.displayOptions.includes('C')) {
      this.nationalMap = term.nationalMap || "";
    }
    this.phone = term.phone || "";
    this.serviceArea = term.serviceArea || "";
    if (!term.displayOptions && (!term.address.country.trim() || term.address.country === 'US')) {
      this.serviceMap = term.serviceMap || "";
    }
    this.term = null;
  }
  deserialize() {

  }
}