export class TerminalPointRequest {
  city: String;
  country: String;
  email: String;
  fileFormat: String;
  fileSort: String;
  state: String;
  zip: String;
  constructor( private _country?: String, private _state?: String ) {
    this.city = '';
    this.country = this._country || '';
    this.email = '';
    this.fileFormat = '';
    this.fileSort = '';
    this.state = this._state || '';
    this.zip = '';
  }
}
