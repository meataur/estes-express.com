import { ServiceResponse } from 'account';
import { Terminal } from 'common';

export class TerminalList {
  state: String;
  terminals: Terminal[];

  constructor () {
    this.state = "";
    this.terminals = [];
  }
}