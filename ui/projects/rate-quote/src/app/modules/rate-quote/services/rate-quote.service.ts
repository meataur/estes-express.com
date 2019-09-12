import { Injectable } from "@angular/core";
import { Observable, Subject } from "rxjs";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import {
  RateQuote,
  RateQuoteType,
  FoodWarehouse,
  RatingRequest,
  EmailRequest,
  ServiceResponse,
  ContactRequest,
  BookingRequest,
  RateSummary,
  RateSearch,
  Page,
  ServiceLevel
} from "../models";
import { switchMap, tap, map, shareReplay } from "rxjs/operators";
import { formatDate } from "@angular/common";
import { environment } from "../../../../environments/environment";
import { RateQuoteUtils } from "./rate-quote-utils";

@Injectable({
  providedIn: "root"
})
export class RateQuoteService {
  quoteSelected = false;
  /**
   * @description This flag is used to bypass the onbeforeunload event logic that prompts the
   * user to confirm navigating way from the page, under proper circumstances.
   */
  mailToInProgress = false;
  foodWarehouses$: Observable<Array<FoodWarehouse>>;
  serviceLevels$: Observable<Array<ServiceLevel>>;

  constructor(private http: HttpClient) {}

  isLtlOnly(apps: Array<"VTL" | "TC" | "LTL">) {
    return apps.length === 1 && apps[0].toUpperCase() === "LTL";
  }

  navigateToBillOfLading(quoteId: string) {
    window.location.href = `${
      environment.appBaseUrl
    }/bill-of-lading/bol/create/quote/${quoteId}`;
  }

  navigateToPickupRequest(quote: RateQuote) {
    const pieces = quote.commodities.reduce((accum, curr) => {
      accum += curr.commodity.pieces;
      return accum;
    }, 0);
    const weight = quote.commodities
      .filter(c => c.deficitRate === false)
      .reduce((accum, curr) => {
        accum += curr.commodity.weight;
        return accum;
      }, 0);
    const availableBy = RateQuoteUtils.extractHoursMinutesAmPm(
      quote.pickupAvail
    );
    const closesBy = RateQuoteUtils.extractHoursMinutesAmPm(quote.pickupClose);

    const queryParams = `?dzip=${quote.dest.zip}&name=${
      quote.contactName
    }&phone=${quote.contactPhone}&ext=${quote.contactPhoneExt}&email=${
      quote.contactEmail
    }&city=${quote.origin.city}&state=${quote.origin.state}&zip=${
      quote.origin.zip
    }&shipmentService=${
      quote.guaranteed ? "GUARANTEED" : ""
    }&pieces=${pieces}&weight=${weight}&pickupDate=${
      quote.pickupDate
    }&availableByHour=${availableBy[0]}&availableByMinutes=${
      availableBy[1]
    }&availableByAmPm=${availableBy[2]}&closesByHour=${
      closesBy[0]
    }&closesByMinutes=${closesBy[1]}&closesByAmPm=${closesBy[2]}&noStack=${
      quote.stackable ? false : true
    }`;

    window.location.href = `${
      environment.appBaseUrl
    }/pickup-request${queryParams}`;
  }

  getReferenceRateQuotes(
    referenceNumber: string,
    rateQuoteId: string
  ): Observable<Array<RateSummary>> {
    return this.http
      .get<Array<RateSummary>>(
        `${
          environment.serviceApiUrl
        }/myestes/Rating/v4.0/reference/${referenceNumber}`
      )
      .pipe(
        map(res => {
          return res.filter(r => r.quoteID !== rateQuoteId);
        })
      );
  }

  getServiceLevels(): Observable<Array<ServiceLevel>> {
    if (!this.serviceLevels$) {
      this.serviceLevels$ = this.http
        .get<Array<ServiceLevel>>(
          `${environment.serviceApiUrl}/myestes/Rating/v4.0/serviceLevels`
        )
        .pipe(shareReplay(1));
    }

    return this.serviceLevels$;
  }

  getQuoteHistory(
    rateSearch: RateSearch,
    filterOptions: {
      order: "asc" | "desc";
      sort: "QUOTENUM" | "TIMST" | "SVCLVL" | "ORGZIP" | "DESZIP" | "NETCHG";
      page: number;
      size: number;
    }
  ): Observable<Page<RateSummary>> {
    return this.http
      .post<Page<RateSummary>>(
        `${environment.serviceApiUrl}/myestes/Rating/v4.0/search?order=${
          filterOptions.order
        }&sort=${filterOptions.sort}&size=${filterOptions.size}&page=${
          filterOptions.page
        }`,
        rateSearch
      )
      .pipe(
        map(res => {
          res.content = res.content.map(r => {
            try {
              r.quoteDate = formatDate(
                new Date(r.quoteDate),
                "MM/dd/yyyy",
                "en-US"
              );
            } catch (err) {
              console.warn(`Unable to format quote date.`);
            }

            return r;
          });

          return res;
        })
      );
  }

  reserveShipment(bookingRequest: BookingRequest): Observable<ServiceResponse> {
    return this.http.post<ServiceResponse>(
      `${environment.serviceApiUrl}/myestes/Rating/v4.0/book`,
      bookingRequest
    );
  }

  /**
   *
   * @description Submit either an 'Email Me' or 'Contact Me' request to services.
   */
  email(req: EmailRequest): Observable<ServiceResponse> {
    return this.http.post<ServiceResponse>(
      `${environment.serviceApiUrl}/myestes/Rating/v4.0/email`,
      req
    );
  }

  selectQuote(quoteId: string): Observable<ServiceResponse> {
    return this.http
      .get<ServiceResponse>(
        `${environment.serviceApiUrl}/myestes/Rating/v4.0/select/${quoteId}`
      )
      .pipe(tap(() => (this.quoteSelected = true)));
  }
  /**
   * Get Pdf
   * @param quoteId
   */

  getQuotePdf(quoteId: string): Observable<any> {
    let headers = new HttpHeaders();
    headers = headers.set("Accept", "application/pdf");
    return this.http.get(
      `${environment.serviceApiUrl}/myestes/Rating/v4.0/print/${quoteId}`,
      { responseType: "blob", headers: headers }
    );
  }

  ltlContactMe(contact: ContactRequest): Observable<ServiceResponse> {
    return this.http.post<ServiceResponse>(
      `${environment.serviceApiUrl}/myestes/Rating/v4.0/adjust`,
      contact
    );
  }

  requestQuote(ratingRequest: RatingRequest): Observable<Array<RateQuote>> {
    return this.http.post<Array<RateQuote>>(
      `${environment.serviceApiUrl}/myestes/Rating/v4.0/requestQuote`,
      ratingRequest
    );
  }

  getQuoteById(quoteId: string): Observable<RateQuote> {
    return this.http.get<RateQuote>(
      `${environment.serviceApiUrl}/myestes/Rating/v4.0/quote/${quoteId}`
    );
  }

  getFoodWarehouses(): Observable<Array<FoodWarehouse>> {
    if (!this.foodWarehouses$) {
      this.foodWarehouses$ = this.http
        .get<Array<FoodWarehouse>>(
          `${environment.serviceApiUrl}/myestes/Rating/v4.0/foodWarehouses`
        )
        .pipe(shareReplay(1));
    }

    return this.foodWarehouses$;
  }

  isLTLOnly(rateQuoteType: RateQuoteType): boolean {
    if (
      rateQuoteType.ltl &&
      (!rateQuoteType.timeCritical && !rateQuoteType.volumeAndTruckload)
    ) {
      return true;
    }

    return false;
  }

  isVTLOnly(rateQuoteType: RateQuoteType): boolean {
    if (
      rateQuoteType.volumeAndTruckload &&
      (!rateQuoteType.timeCritical && !rateQuoteType.ltl)
    ) {
      return true;
    }

    return false;
  }

  isTCOnly(rateQuoteType: RateQuoteType): boolean {
    if (
      rateQuoteType.timeCritical &&
      (!rateQuoteType.volumeAndTruckload && !rateQuoteType.ltl)
    ) {
      return true;
    }

    return false;
  }

  isLTLandVCOnly(rateQuoteType: RateQuoteType): boolean {
    if (
      rateQuoteType.volumeAndTruckload &&
      rateQuoteType.ltl &&
      !rateQuoteType.timeCritical
    ) {
      return true;
    }

    return false;
  }
}
