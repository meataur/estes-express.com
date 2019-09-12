import { Point } from 'common';
export class TransitTimeCalculatorRequest {
  destinationPoints: {
    point: Point,
    shipmentDate: string
  }[];
  originpoint: Point;
}