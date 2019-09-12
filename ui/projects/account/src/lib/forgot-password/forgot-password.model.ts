export class ForgotPasswordRequest {
  searchCriteria: string; //user input username or email address
  selectionType: string;  //can be one of two strings, 'userName' or 'email'

  constructor() {
    this.searchCriteria = '';
    this.selectionType = 'userName';
  }
}
