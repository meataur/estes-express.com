export class ProfileDTO {
  firstName: string;
  lastName: string;
  username: string;
  email: string;
  phone: string;
  companyName: string;
  accountType: string;
  accountCode: string;
  createdDate: string;
  status: 'ENABLED' | 'DISABLED';
  preference: string;
}
