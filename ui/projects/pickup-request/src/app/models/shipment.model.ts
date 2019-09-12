import { Commodity } from './commodity.model';
import { ShipmentOption } from './shipment.option.model';
import { Notification } from './notification.model';

export class Shipment {
  requestNumber: string;
  commodity: Commodity;
  shipmentOption: ShipmentOption;
  shipmentService: string;
  notification: Notification;
}