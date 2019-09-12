import { RoleEnum } from './role.enum';
import { ShipmentServiceEnum } from './shipment-service.enum';

export interface PickupRequest {
  user: {
    name: string;
    phone: string;
    phoneExt: string;
    email: string;
    role: RoleEnum;
  };
  shipper: {
    accountCode: string;
    name: string;
    company: string;
    addressLine1: string;
    addressLine2: string;
    city: string;
    state: string;
    zip: string;
    zipExt: string;
    phone: string;
    phoneExt: string;
    email: string;
  };
  consignee: {
    accountCode: string;
    name: string;
    company: string;
    addressLine1: string;
    addressLine2: string;
    city: string;
    state: string;
    zip: string;
    zipExt: string;
    phone: string;
    phoneExt: string;
    email: string;
  };
  pickupInfo: {
    pickupDate: string;
    startTime: string;
    endTime: string;
    hookOrDrop: boolean;
    liftgateRequired: boolean;
    noStackPallet: boolean;
  };
  shipmentInfo: [
    {
      requestNumber: string;
      commodity: {
        destinationZipCode: string;
        destinationZipCodeExt: string;
        totalPieces: number;
        totalWeight: number;
        totalSkids: number;
        hazmat: boolean;
        specialInstructions: string;
        referenceNumber: string;
      };
      shipmentOption: {
        freeze: boolean;
        oversize: boolean;
        food: boolean;
        poision: boolean;
      };
      shipmentService: ShipmentServiceEnum;
      notification: {
        accepted: boolean;
        completed: boolean;
        rejected: boolean;
      };
    }
  ];
  partyNotificationList: [
    {
      role: RoleEnum;
      name: string;
      email: string;
      phone: string;
      phoneExt: string;
    }
  ];
  tos: boolean;
  bolId: number;
}
