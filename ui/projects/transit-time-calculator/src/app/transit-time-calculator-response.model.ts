import { Terminal } from 'common';
import { DestinationTerminal } from './destination-terminal.model';
export class TransitTimeCalculatorResponse {
  originTerminal: Terminal;
  destinationTerminals: DestinationTerminal[]
}