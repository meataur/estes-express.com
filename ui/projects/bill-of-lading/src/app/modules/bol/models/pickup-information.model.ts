import { PickupOptionEnum } from './pickup-option.enum';

export class PickupInformation {
  pickupDate: string;
  startTime: string;
  endTime: string;
  specialInstruction: string;
  pickupOptions: Array<PickupOptionEnum>;
  acceptedNotify: boolean;
  completedNotify: boolean;
  rejectedNotify: boolean;

  constructor() {}
}
