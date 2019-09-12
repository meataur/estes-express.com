import { Address } from '../../../modules/components/models/address.interface';

export class BookAddress {
  address: Address;
  cod: boolean;
  company: string;
  consignee: boolean;
  email: string;
  fax: string;
  firstName: string;
  lastName: string;
  locationNumber: string;
  phone: string;
  phoneExt: string;
  seq: number;
  shipper: boolean;
  thirdParty: boolean;
  user: string;

  constructor(public data?: any) {
    this.address = data.address || {};
    this.cod = data.cod || false;
    this.company = data.company || '';
    this.consignee = data.consignee || false;
    this.email = data.email || '';
    this.fax = data.fax || '';
    this.firstName = data.firstName || '';
    this.lastName = data.lastName || '';
    this.locationNumber = data.locationNumber || '';
    this.phone = data.phone || '';
    this.phoneExt = data.phoneExt || '';
    this.seq = data.seq || null;
    this.shipper = data.shipper || false;
    this.thirdParty = data.thirdParty || false;
    this.user = data.user || '';
  }
}
