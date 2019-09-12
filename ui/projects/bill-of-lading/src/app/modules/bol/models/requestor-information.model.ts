import { RoleEnum } from './role.enum';

export class RequestorInformation {
  contactName: string;
  phone: string;
  phoneExt: string;
  email: string;
  role: RoleEnum;
  accountCode: string;

  constructor() {

  }
}
