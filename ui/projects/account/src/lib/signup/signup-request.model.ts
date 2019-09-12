export class SignupRequest {
  accountCode: string;
  company: string;
  email: string;
  firstName: string;
  lastName: string;
  notify: boolean;
  password: string;
  phone: string;
  userName: string;

  constructor() {
    this.accountCode = '';
    this.company = '';
    this.email = '';
    this.firstName = '';
    this.lastName = '';
    this.notify = false;
    this.password = '';
    this.phone = '';
    this.userName = '';
  }
}
