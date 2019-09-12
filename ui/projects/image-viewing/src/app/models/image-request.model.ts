export class ImageRequest {
  documentType: 'DR' | 'BOL' | 'WR';
  faxInfo: {
    attention: string;
    companyName: string;
    faxNumber: string;
  };
  requestType: 'V' | 'F';
  searchTerm: string;
  searchType: 'F' | 'B' | 'P';
}
