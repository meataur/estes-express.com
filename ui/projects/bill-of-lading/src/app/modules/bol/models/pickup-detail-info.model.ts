import { RoleEnum } from './role.enum';

export class PickupDetailInfo {
  accepted: boolean;
  accountCode: string;
  completed: boolean;
  email: string;
  endTime: string;
  food: boolean;
  freeze: boolean;
  hookOrDrop: boolean;
  liftgateRequired: boolean;
  name: string;
  noStackPallet: boolean;
  oversize: boolean;
  phone: string;
  phoneExt: string;
  pickupDate: string;
  poision: boolean;
  rejected: boolean;
  role: RoleEnum;
  specialInstruction: string;
  startTime: string;
}
