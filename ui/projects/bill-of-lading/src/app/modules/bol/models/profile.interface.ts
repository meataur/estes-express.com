import { StatusEnum } from './status.enum';

export interface Profile {
  firstName: string;
  lastName: string;
  username: string;
  email: string;
  phone: string;
  companyName: string;
  accountType: string;
  accountCode: string;
  createdDate: string;
  status: StatusEnum;
  preference: string;
}
