import { TestBed } from '@angular/core/testing';
import { HttpErrorResponse } from '@angular/common/http';
import { of, defer } from 'rxjs';
import { ServiceResponse } from 'common';
import { TerminalLookupService } from './terminal-lookup.service';
import { TerminalRequest } from './terminal-request.model';
import { TerminalList } from './terminal-list.model';
import { TerminalListResponse } from './terminal-list-response.model';
import { TerminalPointRequest } from './terminal-point-request.model';
import { Terminal } from 'common';
import { Address } from 'common';

//TODO this function can be added to a testing utility file
/** Create async observable that emits-once and completes
 *  after a JS engine turn */
export function asyncData<T>(data: T) {
  return defer(() => Promise.resolve(data));
}

/** Create async observable error that errors
 *  after a JS engine turn */
export function asyncError<T>(errorObject: any) {
  return defer(() => Promise.reject(errorObject));
}

fdescribe('TerminalLookupService', () => {
  let httpClientSpy: { post: jasmine.Spy, get: jasmine.Spy, delete: jasmine.Spy };
  let service: TerminalLookupService;

  let mockAddress = {
    city: 'Richmond', country: 'US', state: 'VA', streetAddress: '123 Main St', zip: '23223'
  };

  let mockTerm1 = {
      "abbr": "RIC",
      "address": mockAddress,
      "email": "terminal@estes-express.com",
      "fax": "5555555555",
      "id": "01",
      "manager": "Sherlock Holmes",
      "managerEmail": "sholmes@estes-express.com",
      "name": "Richmond",
      "maps": "map/map.pdf",
      "serviceMap": "maps/map.gif",
      "phone": "5555555555"
  };

  let mockTerm2 = {
    "alphaCode": "RIC2",
    "email": "terminal2@estes-express.com",
    "city": "Richmond",
    "country": "US",
    "fax": "6666666666",
    "terminal": "02",
    "manager": "John Watson",
    "managerEmail": "jwatson@estes-express.com",
    "terminalName": "Richmond",
    "maps": "map/map2.pdf",
    "serviceMap": "maps/map2.gif",
    "state": "VA",
    "phone": "66666666666",
    "zip": "23223"
  };

  let mockTermListServiceResponse = {
    "state": "VA", 
    "terminals": [ mockTerm1, mockTerm2 ]  
  }; 

  let mockTermTransformed1 = new Terminal(mockTerm1);

  let mockTermTransformed2 = new Terminal(mockTerm2);

  let mockTermListTransformed = new TerminalList();
  mockTermListTransformed.terminals = [mockTermTransformed1, mockTermTransformed2];
  mockTermListTransformed.state = "VA";

  beforeEach(() => {
    httpClientSpy = jasmine.createSpyObj('HttpClient', ['post', 'get', 'delete']);
    service = new TerminalLookupService(<any> httpClientSpy);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should call getTerminalsByCountryState and return expected value, HttpClient post should be called once', () => {
    const expectedResponse = new TerminalListResponse([mockTermListTransformed], "", "state");
    const request = new TerminalPointRequest();
   
    httpClientSpy.post.and.returnValue(asyncData([mockTermListServiceResponse]));
   
    service.getTerminalsByCountryState(request).subscribe(
      data => expect(data).toEqual(expectedResponse, 'expected successful response'),
      fail
    );
    expect(httpClientSpy.post.calls.count()).toBe(1, 'one call');
  });
  it('should call getTerminalsById and return expected value, HttpClient get should be called once', () => {
    const request = new TerminalPointRequest();
   
    httpClientSpy.get.and.returnValue(asyncData(mockTerm1));
   
    service.getTerminalById('001').subscribe(
      data => expect(data).toEqual(mockTermTransformed1, 'expected successful response'),
      fail
    );
    expect(httpClientSpy.get.calls.count()).toBe(1, 'one call');
  });
});
