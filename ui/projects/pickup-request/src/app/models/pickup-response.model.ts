import { Pickup } from './pickup.model';

export class PickupResponse {
  content: Pickup[];
  first: boolean;
  last: boolean;
  number: number;
  numberOfElements: number;
  size: number;
  totalElements: number;
  totalPages: number;
}