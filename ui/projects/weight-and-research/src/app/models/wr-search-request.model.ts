export class WRSearchRequest {
  accountNumber: string;
  endDate: string; // YYYYMMDD
  searchBy: 'Date Range' | 'PRO' | 'Other';
  searchTerm: Array<string>; // Comma-delimited
  startDate: string; // YYYYMMDD
}
