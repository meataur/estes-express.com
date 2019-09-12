export class CustomerInfoResponse {
  phoneNumber: string;
  name: string;
  email: string;
  error: string;

  constructor() {
    this.error = null;
    this.email = null;
    this.name = null;
    this.phoneNumber = null;
  }
}