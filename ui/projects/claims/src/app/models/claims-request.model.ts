export class ClaimsRequest {
  accountNumber: string; //If logged in as grouped account have to specify sub account from sub account service
  endDate: string; //If searching by Date the end of the range – yyyymmdd
  numbers: string[];
  searchBy: string; //Search with field – Date Range/PRO Number/Your Reference Number/Estes Claim Number
  startDate: string; //If searching by Date the start of the range – yyyymmdd
  status: string; //If searching with Date Range have to specify status–All/Open/Paid/Declined
}