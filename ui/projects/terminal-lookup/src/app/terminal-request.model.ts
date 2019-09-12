
export class TerminalRequest {
  country: String;
  state: String;
  city: String;
  zip: String;

  constructor ( private point?: any) {
    this.country = this.point.country || "";
    this.state = this.point.state || "";
    this.city = this.point.city || "";
    this.zip = this.point.zip || "";
  }
}