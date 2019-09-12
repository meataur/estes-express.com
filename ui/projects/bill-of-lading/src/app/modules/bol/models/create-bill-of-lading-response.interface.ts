import { Bol } from "./bol.interface";
import { BolServiceResponse } from "./bol-service-response.interface";
import { PickupRequest } from "./pickup-request.interface";

export interface CreateBillOfLadingResponse {
  bol: Bol;
  pickupErrors: Array<BolServiceResponse>;
  pickupRequest: PickupRequest;
}
