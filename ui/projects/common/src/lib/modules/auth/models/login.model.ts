export class Login {
  grantType: string;
  clientId: string;
  userName: string;
  password: string;

  constructor() {
    this.grantType = 'password';
    this.clientId = 'MY_ESTES';
    this.userName = '';
    this.password = '';
  }
}
