import { InvoiceServiceResponse } from './invoice-service-response.interface';
import { AgingDetail } from './aging-detail.model';
export class InvoiceSearchResult {
  error: InvoiceServiceResponse;
  result: AgingDetail;
}