import { Injectable, OnDestroy } from "@angular/core";
import { Observable, Subject, BehaviorSubject, throwError } from "rxjs";
import {
  Profile,
  ServiceResponse,
  BookAddress,
  Page,
  BillOfLading,
  BolServiceResponse,
  CreateBillOfLadingResponse,
  BolDocument,
  ShippingLabel,
  TemplateFilterEnum,
  Template,
  Draft,
  CommodityLibraryViewModel
} from "../models";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { environment } from "../../../../environments/environment";
import { DraftFilterEnum } from "../components/draft-advanced-search/draft-advanced-search.component";
import { Bol } from "../models/bol.interface";
import { RateQuote } from "../models/rate-quote.interface";
import { map, catchError, mapTo } from "rxjs/operators";
import { MyEstesGlobalHandlers } from "common";

@Injectable({
  providedIn: "root"
})
export class BolService implements OnDestroy {
  stopModified$ = new Subject<boolean>();
  baseUrl: string;
  pickupErrors: any[] = [];
  pickupRequestNumber: string = '';
  private _modified: boolean;
  get modified(): boolean {
    return this._modified;
  }

  setModified() {
    // console.log(`form was modified!`);
    this._modified = true;
    this.stopModified$.next(true);
  }

  resetFormState() {
    this._modified = false;
  }

  constructor(private http: HttpClient) {
    this.baseUrl = `${environment.serviceApiUrl}`;
  }

  getCommodities(): Observable<Array<CommodityLibraryViewModel>> {
    return this.http.get<Array<CommodityLibraryViewModel>>(
      `${this.baseUrl}/myestes/CommodityLibrary/v1.0/commodities`
    );
  }

  saveBillOfLading(bol: BillOfLading): Observable<BolServiceResponse> {
    return this.http.post<BolServiceResponse>(
      `${this.baseUrl}/myestes/BillOfLading/v1.0/draft`,
      bol
    );
  }

  saveTemplate(
    bol: BillOfLading,
    templateName: string
  ): Observable<BolServiceResponse> {
    return this.http.post<BolServiceResponse>(
      `${
        this.baseUrl
      }/myestes/BillOfLading/v1.0/template?template=${templateName}`,
      bol
    );
  }

  getQuoteById(quoteId: string): Observable<RateQuote> {
    return this.http
      .get<RateQuote>(
        `${environment.serviceApiUrl}/myestes/Rating/v4.0/quote/${quoteId}`
      )
      .pipe(
        map(q => {
          if (q) {
            if (!!(q.origin.country === "MX")) {
              q.origin.country = "";
              q.origin.city = "";
              q.origin.state = "";
              q.origin.zip = "";
              q.hasMexicoPoint = true;
            }
            if (!!(q.dest.country === "MX")) {
              q.dest.country = "";
              q.dest.city = "";
              q.dest.state = "";
              q.dest.zip = "";
              q.hasMexicoPoint = true;
            }
          }
          q.commodities = q.commodities.filter(c => c.deficitRate === false);
          return q;
        })
      );
  }

  addAddress(bookAddress: BookAddress): Observable<ServiceResponse> {
    const url = this.baseUrl + "/myestes/AddressBook/v1.0/do/add";
    return this.http.post<ServiceResponse>(url, bookAddress);
  }

  createBillOfLading(
    bol: BillOfLading
  ): Observable<CreateBillOfLadingResponse> {
    return this.http.post<CreateBillOfLadingResponse>(
      `${this.baseUrl}/myestes/BillOfLading/v1.0/bol`,
      bol
    );
  }

  getDrafts(
    options: {
      page: number;
      size: number;
      sort: string;
      direction: string;
    },
    filterOptions?: {
      bolEndDate: string;
      bolNumber: string;
      bolStartDate: string;
      consignee: string;
      filterBy: string;
      proNumber: string;
      shipper: string;
    }
  ): Observable<Page<Draft>> {
    let queryString = `?sort=${options.sort}&page=${options.page}&size=${
      options.size
    }&order=${options.direction}`;

    let filterBy: string;
    if (filterOptions && filterOptions.filterBy) {
      filterBy = `&filterBy=${filterOptions.filterBy}`;

      switch (DraftFilterEnum[filterOptions.filterBy] as DraftFilterEnum) {
        case DraftFilterEnum.BOL_DATE_RANGE:
          filterBy += `&bolStartDate=${filterOptions.bolStartDate}&bolEndDate=${
            filterOptions.bolEndDate
          }`;
          break;
        case DraftFilterEnum.BOL_NUMBER:
          filterBy += `&bolNumber=${filterOptions.bolNumber}`;
          break;
        case DraftFilterEnum.CONSIGNEE:
          filterBy += `&consignee=${filterOptions.consignee}`;
          break;
        case DraftFilterEnum.SHIPPER:
          filterBy += `&shipper=${filterOptions.shipper}`;
          break;
        case DraftFilterEnum.PRO_NUMBER:
          filterBy += `&proNumber=${filterOptions.proNumber}`;
          break;
      }

      queryString += filterBy;
    }

    return this.http.get<Page<Draft>>(
      `${this.baseUrl}/myestes/BillOfLading/v1.0/draft${queryString}`
    );
  }

  getHistory(
    options: {
      page: number;
      size: number;
      sort: string;
      direction: string;
    },
    filterOptions?: {
      bolEndDate: string;
      bolNumber: string;
      bolStartDate: string;
      consignee: string;
      filterBy: string;
      proNumber: string;
      shipper: string;
    }
  ): Observable<Page<Bol>> {
    let queryString = `?sort=${options.sort}&page=${options.page}&size=${
      options.size
    }&order=${options.direction}`;

    let filterBy: string;
    if (filterOptions && filterOptions.filterBy) {
      filterBy = `&filterBy=${filterOptions.filterBy}`;

      switch (DraftFilterEnum[filterOptions.filterBy] as DraftFilterEnum) {
        case DraftFilterEnum.BOL_DATE_RANGE:
          filterBy += `&bolStartDate=${filterOptions.bolStartDate}&bolEndDate=${
            filterOptions.bolEndDate
          }`;
          break;
        case DraftFilterEnum.BOL_NUMBER:
          filterBy += `&bolNumber=${filterOptions.bolNumber}`;
          break;
        case DraftFilterEnum.CONSIGNEE:
          filterBy += `&consignee=${filterOptions.consignee}`;
          break;
        case DraftFilterEnum.SHIPPER:
          filterBy += `&shipper=${filterOptions.shipper}`;
          break;
        case DraftFilterEnum.PRO_NUMBER:
          filterBy += `&proNumber=${filterOptions.proNumber}`;
          break;
      }

      queryString += filterBy;
    }

    return this.http.get<Page<Bol>>(
      `${this.baseUrl}/myestes/BillOfLading/v1.0/bol${queryString}`
    );
  }

  getTemplates(
    options: {
      page: number;
      size: number;
      sort: string;
      direction: string;
    },
    filterOptions?: {
      templateName?: string;
      bolEndDate?: string;
      bolNumber?: string;
      bolStartDate?: string;
      consignee?: string;
      filterBy: string;
      proNumber?: string;
      shipper?: string;
    }
  ): Observable<Page<Template>> {
    let queryString = `?sort=${options.sort}&page=${options.page}&size=${
      options.size
    }&order=${options.direction}`;

    let filterBy: string;
    if (filterOptions && filterOptions.filterBy) {
      filterBy = `&filterBy=${filterOptions.filterBy}`;

      switch (
        TemplateFilterEnum[filterOptions.filterBy] as TemplateFilterEnum
      ) {
        case TemplateFilterEnum.TEMPLATE_NAME:
          filterBy += `&templateName=${filterOptions.templateName}`;
          break;
        case TemplateFilterEnum.BOL_DATE_RANGE:
          filterBy += `&bolStartDate=${filterOptions.bolStartDate}&bolEndDate=${
            filterOptions.bolEndDate
          }`;
          break;
        case TemplateFilterEnum.BOL_NUMBER:
          filterBy += `&bolNumber=${filterOptions.bolNumber}`;
          break;
        case TemplateFilterEnum.CONSIGNEE:
          filterBy += `&consignee=${filterOptions.consignee}`;
          break;
        case TemplateFilterEnum.SHIPPER:
          filterBy += `&shipper=${filterOptions.shipper}`;
          break;
        case TemplateFilterEnum.PRO_NUMBER:
          filterBy += `&proNumber=${filterOptions.proNumber}`;
          break;
      }

      queryString += filterBy;
    }

    return this.http.get<Page<Template>>(
      `${this.baseUrl}/myestes/BillOfLading/v1.0/template${queryString}`
    );
  }

  getDraft(bolNumber: string) {
    return this.http.get<BillOfLading>(
      `${this.baseUrl}/myestes/BillOfLading/v1.0/draft/${bolNumber}`
    );
  }

  deleteDraft(bolNumber: string): Observable<BolServiceResponse> {
    return this.http.delete<BolServiceResponse>(
      `${this.baseUrl}/myestes/BillOfLading/v1.0/draft/${bolNumber}`
    );
  }

  deleteTemplate(templateName: string): Observable<BolServiceResponse> {
    return this.http.delete<BolServiceResponse>(
      `${this.baseUrl}/myestes/BillOfLading/v1.0/delete_template?templateName=${templateName}`
    );
  }

  cancelBillOfLading(bolId: string): Observable<BolServiceResponse> {
    return this.http
      .delete<BolServiceResponse>(
        `${this.baseUrl}/myestes/BillOfLading/v1.0/bol/${bolId}`
      )
      .pipe(catchError(MyEstesGlobalHandlers.handleErrors));
  }

  getBolById(bolId: number | string): Observable<BillOfLading> {
    return this.http.get<BillOfLading>(
      `${this.baseUrl}/myestes/BillOfLading/v1.0/bol/${bolId}`
    );
  }

  getTemplateByName(templateName: number | string): Observable<BillOfLading> {
    return this.http.get<BillOfLading>(
      `${this.baseUrl}/myestes/BillOfLading/v1.0/get_template?templateName=${templateName}`
    );
  }

  viewBolPdf(bolId: string): Observable<BolDocument> {
    return this.http.get<BolDocument>(
      `${this.baseUrl}/myestes/BillOfLading/v1.0/bol/view/${bolId}`
    );
  }

  createShippingLabel(
    bolId: string,
    shippingLabel: ShippingLabel
  ): Observable<BolDocument> {
    return this.http.post<BolDocument>(
      `${this.baseUrl}/myestes/BillOfLading/v1.0/bol/${bolId}/shipping_label`,
      shippingLabel
    );
  }

  getShippingLabel(bolId: string): Observable<BolDocument> {
    return this.http.get<BolDocument>(
      `${this.baseUrl}/myestes/BillOfLading/v1.0/bol/${bolId}/shipping_label`
    );
  }

  // getShippingLabel(b);

  ngOnDestroy() {
    this.stopModified$.next(true);
    this.stopModified$.unsubscribe();
  }
}
