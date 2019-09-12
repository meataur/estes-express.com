import { UserInformation } from './user-information.model';
import { Party } from './party.model';
import { PickupInformation } from './pickup-information.model';
import { Shipment } from './shipment.model';
import { PartyNotification } from './party-notification.model';

export class PickupRequest {
  user: UserInformation;
  shipper: Party;
  pickupInfo: PickupInformation;
  shipmentInfo: Shipment[];
  partyNotificationList: PartyNotification[];
  bolId: number;
}