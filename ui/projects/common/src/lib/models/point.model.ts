export class Point {
  city: string;
  country: string;
  state: string;
  zip: string;

  constructor(point?: any) {
    if (point) {
      this.city = point.city || '';
      this.country = point.country || '';
      this.state = point.state || '';
      this.zip = point.zip || '';
    } else {
      this.city = '';
      this.country = '';
      this.state = '';
      this.zip = '';
    }
  }
}
