export class AgingDetailRequest {
  bucket: number;
  direction: string;
  maxRows: number;
  page: number;
  sort: string;

  constructor() {
    this.bucket = 0;
    this.direction = '';
    this.maxRows = 25;
    this.page = 1;
    this.sort = '';
  }
}