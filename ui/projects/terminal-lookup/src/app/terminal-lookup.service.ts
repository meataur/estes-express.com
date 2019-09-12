import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders
} from "@angular/common/http";
import { catchError, map } from "rxjs/operators";
import { throwError } from "rxjs/internal/observable/throwError";
import { TerminalRequest } from "./terminal-request.model";
import { TerminalPointRequest } from "./terminal-point-request.model";
import { Terminal } from "common";
import { TerminalListResponse } from "./terminal-list-response.model";
import { TerminalList } from "./terminal-list.model";
// import { AuthenticationService } from 'account';
import { ServiceResponse } from "common";
import { Point } from "common";
import { environment } from "../environments/environment";

@Injectable({
  providedIn: "root"
})
export class TerminalLookupService {
  searchCountry: String;
  searchState: String;
  private baseTerminalURL: String;
  private basePointsURL: String;
  private commonURL: String;
  private httpOptions: {};

  constructor(private httpClient: HttpClient) {
    this.baseTerminalURL = `${
      environment.serviceApiUrl
    }/myestes/TerminalList/v1.0/`;
    this.basePointsURL = `${environment.serviceApiUrl}/myestes/Points/v1.0/`;
    this.commonURL = `${environment.serviceApiUrl}/common/v1.0/`;
    this.httpOptions = {
      headers: new HttpHeaders({
        "Content-Type": "application/json"
      })
    };
  }

  /**
   * get lists of terminals that service either an entire country, or state (province). The returned terminal
   * lists are organized by state
   * @param request is the TerminalRequest that includes the state and country to search by
   */
  getTerminalsByCountryState(
    request: TerminalPointRequest
  ): Observable<TerminalListResponse> {
    const url = this.baseTerminalURL + "searchTerminal";

    this.searchCountry = request.country;
    this.searchState = request.state;
    return this.httpClient
      .post<TerminalListResponse>(
        url,
        JSON.stringify(request),
        this.httpOptions
      )
      .pipe(
        map(res => {
          let terminalSearchArea = request.state || request.country;
          return TerminalLookupService.transformTerminals(
            res,
            terminalSearchArea,
            "state"
          );
        }),
        catchError(TerminalLookupService.handleErrors)
      );
  }
  /**
   * emails a list of terminals to the users input email adderess
   * @param request the TerminalRequest data including country, state, and email
   */
  emailTerminalsByCountryState(request): Observable<ServiceResponse> {
    const url = this.baseTerminalURL + "email";

    return this.httpClient
      .post<ServiceResponse>(url, JSON.stringify(request), this.httpOptions)
      .pipe(catchError(TerminalLookupService.handleErrors));
  }

  getTerminalCoordinates(): Observable<any> {
    // tslint:disable-next-line:max-line-length
    const url = `${
      environment.cmsUrl
    }/api/content/render/false/query/+contentType:Terminal%20+(conhost:8a7d5e23-da1e-420a-b4f0-471e7da8ea2d%20conhost:SYSTEM_HOST)%20+languageId:1%20+deleted:false%20+working:true/orderby/Terminal.terminalCode/limit/0`;
    return this.httpClient
      .get<any>(url, {
        headers: new HttpHeaders({
          "X-Skip-Interceptor": ""
        })
      })
      .pipe(catchError(TerminalLookupService.handleErrors));
  }
  /**
   * getTerminalById fetches ther terminal data associated with a specific terminal ID
   * @param id the specific terminal ID
   */
  getTerminalById(id: String): Observable<Terminal> {
    const url = this.commonURL + "terminal/" + id;

    return this.httpClient
      .get<Terminal>(url)

      .pipe(
        map(res => {
          return new Terminal(res);
        }),
        catchError(TerminalLookupService.handleErrors)
      );
  }
  /**
   * getTerminalsByPoint returns the terminals that service a specific point (city, country, state, zip)
   * @param point
   */
  getTerminalsByPoint(point: Point): Observable<TerminalListResponse> {
    const url = this.basePointsURL + "terminal";

    return this.httpClient
      .post<TerminalListResponse>(url, JSON.stringify(point), this.httpOptions)
      .pipe(
        map(res => {
          let terminalSearchArea =
            point.city + ", " + point.state + " " + point.zip;
          return TerminalLookupService.transformTerminals(
            res,
            terminalSearchArea,
            "point"
          );
        }),
        catchError(TerminalLookupService.handleErrors)
      );
  }
  /**
   * emailCoverageByTerminal emails a list of next day coverage points for the terminal that services the specified point (city, country, state, zip)
   * @param point
   */
  emailCoverageByTerminal(
    terminalPoint: TerminalPointRequest
  ): Observable<TerminalListResponse> {
    const url = this.basePointsURL + "coverage";

    return this.httpClient
      .post<TerminalListResponse>(
        url,
        JSON.stringify(terminalPoint),
        this.httpOptions
      )
      .pipe(catchError(TerminalLookupService.handleErrors));
  }
  /**
   * Transforms returned terminal info into a consistent TerminalListResponse data model.
   * Needed because the Estes Point service and TerminalList service return two different terminal models
   * @param res is the terminal response from either the Estes TerminalList service or the Points service.
   * @param terminalSearchArea the search area string used to display the results
   * @param terminalSearchType the type of search made, which determins wether terminal details are included on the object
   */
  static transformTerminals(res, terminalSearchArea, terminalSearchType) {
    if (res.error) throw new Error(res.error);

    let terminalLists = [];
    if (terminalSearchType == "state") {
      res.forEach(stateTerms => {
        let terminalList = new TerminalList();
        terminalList.state = stateTerms.state;
        stateTerms.terminals.forEach(term => {
          terminalList.terminals.push(new Terminal(term));
        });

        terminalLists.push(terminalList);
      });
    } else {
      let terminalList = new TerminalList();
      terminalList.terminals.push(new Terminal(res));
      terminalLists.push(terminalList);
    }
    let terminalListResponse = new TerminalListResponse(
      terminalLists,
      terminalSearchArea,
      terminalSearchType
    );
    return terminalListResponse;
  }

  static handleErrors(error: HttpErrorResponse) {
    let errMsgs = [];
    if (error.error instanceof Array) {
      error.error.forEach(e => {
        if (e.message) {
          errMsgs.push(e.message);
        }
      });
    } else if (error.error && error.error.message) {
      errMsgs.push(error.error.message);
    } else if (error.message) {
      errMsgs.push(error.message);
    } else {
      errMsgs.push("Something bad happened. Error not recognized.");
    }
    return throwError(errMsgs);
  }
}
