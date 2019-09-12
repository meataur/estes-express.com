import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError, map } from 'rxjs/operators';
import { throwError } from 'rxjs/internal/observable/throwError';
import { AgingSummary } from './models/aging-summary.model';
import { AgingDetailRequest } from './models/aging-detail-request.model';
import { AgingDetailResponse } from './models/aging-detail-response.model';
import { AgingDetail } from './models/aging-detail.model';
import { InvoiceSearchRequest } from './models/invoice-search-request.model';
import { InvoiceSearchResult } from './models/invoice-search-result.model';
import { InvoiceServiceResponse } from './models/invoice-service-response.interface';
import { ImageRequest } from './models/image-request.model';
import { Image } from './models/image.model';
import { PaymentReason } from './models/payment-reason.model';
import { PaymentLimits } from './models/payment-limits.model';
import { Payment } from './models/payment.model';
import { PaymentServiceResponse } from './models/payment-service-response.model';
import { StatementDetail } from './models/statement-detail.model';
import { AgingEmailRequest } from './models/aging-email-request.model';
import { ServiceResponse, MyEstesGlobalHandlers } from 'common';
import { environment } from '../environments/environment';
import { SelectionModel } from '@angular/cdk/collections';

@Injectable({
  providedIn: 'root'
})
export class InvoiceInquiryService {

  private baseURL: String;
  private httpOptions: {};
  private agingDetailsSelections: AgingDetail[];

  constructor( private httpClient: HttpClient ) {
    this.baseURL = `${environment.serviceApiUrl}/myestes/InvoiceInquiry/v1.0/`;

    this.httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
   }

   getAgingSummary(): Observable<AgingSummary> {
     const url = this.baseURL + 'summary';
     return this.httpClient.get<AgingSummary>(url)
      .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      );
   }

  getInvoicesByAgeBucket(request: AgingDetailRequest): Observable<AgingDetailResponse> {
    const url = this.baseURL + 'agedetail';
    return this.httpClient.post<AgingDetailResponse>(url, request, this.httpOptions)
      .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      );
  }

  searchInvoices(request: InvoiceSearchRequest): Observable<InvoiceSearchResult[]> {
    const url = this.baseURL + 'search';
    return this.httpClient.post<InvoiceSearchResult[]>(url, request, this.httpOptions)
      .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      );
  }

  getStatement(statementNumber: string) {
    const url = this.baseURL + `statement?sort=PRO&stmt=${statementNumber}`;
    return this.httpClient.get<StatementDetail[]>(url)
      .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      );
  }

  fetchImage(pro: string): Observable<Image> {
    const url = this.baseURL + 'images';
    let imageRequest = new ImageRequest();
    imageRequest.pro = pro;
    imageRequest.showBol = imageRequest.showDr = true;
    return this.httpClient.post<Image>(url, imageRequest, this.httpOptions)
      .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      );
  }

  getPaymentReasons() {
    const url = this.baseURL + 'paymentReasons';
    return this.httpClient.get<PaymentReason[]>(url)
      .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      )
  }
  
  getPaymentLimits() {
    const url = this.baseURL + 'paymentLimits';
    return this.httpClient.get<PaymentLimits>(url)
      .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      )
  }

  verifyPayment(payments: Payment[]): Observable<PaymentServiceResponse> {
    const url = this.baseURL + 'verifyPayment';
    return this.httpClient.post<PaymentServiceResponse>(url, payments, this.httpOptions)
      .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      );
  }

  finalizePayment(id: string, action: string) {
    const url = this.baseURL + `pay/${action}/${id}`;
    return this.httpClient.get<PaymentServiceResponse>(url)
      .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      );
  }

  email(req: AgingEmailRequest): Observable<ServiceResponse> {
    const url = this.baseURL + 'agingEmail';
    return this.httpClient.post<ServiceResponse>(url, req, this.httpOptions)
      .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      );
  }

  hasSpecialInstructions(): Observable<string> {
    const url = this.baseURL + 'customer/hasInstructions';
    return this.httpClient.get<string>(url)
      .pipe(
        catchError(MyEstesGlobalHandlers.handleErrors)
      );
  }

  setSelectedAgingDetails(selections: AgingDetail[]) {
    this.agingDetailsSelections = selections;
  }

  getSelectedAgingDetails(): AgingDetail[] {
    return this.agingDetailsSelections;
  }
}
