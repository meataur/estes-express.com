import { ServiceResponse } from 'account';
import { TerminalList } from './terminal-list.model';

export class TerminalListResponse {
  error: ServiceResponse;
  searchArea: String;
  searchType: String; // state or point
  terminalLists: TerminalList[];

  constructor (public _terminalList?: TerminalList[], public _searchArea?: String, public _searchType?: String) {
    this.searchType = _searchType || "";
    this.searchArea = _searchArea || "";
    this.terminalLists = _terminalList || [];
  }
}