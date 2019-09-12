import { TestBed } from '@angular/core/testing';
import { HttpErrorResponse } from '@angular/common/http';
import { of, defer } from 'rxjs';
import { ForgotPasswordService } from './forgot-password.service';
import { ForgotPasswordRequest } from './forgot-password.model';

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
//TODO remove f from fdescribe
fdescribe('ForgotPasswordService', () => {
  let injector: TestBed;
  let service: ForgotPasswordService;
  let httpClientSpy: { post: jasmine.Spy };
  let forgotPasswordRequest: ForgotPasswordRequest;

  beforeEach(() => {
    httpClientSpy = jasmine.createSpyObj('HttpClient', ['post']);
    service = new ForgotPasswordService(<any> httpClientSpy);
    forgotPasswordRequest = new ForgotPasswordRequest();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return expected response and HttpClient called once', () => {
    const expectedResponse = { errorCode: '', message: 'Success!' };
   
    httpClientSpy.post.and.returnValue(asyncData(expectedResponse));
   
    service.email(forgotPasswordRequest).subscribe(
      data => expect(data).toEqual(expectedResponse, 'expected successful response'),
      fail
    );
    expect(httpClientSpy.post.calls.count()).toBe(1, 'one call');
  });

  it('should return an error when the server returns a 404', () => {
    const errorResponse = new HttpErrorResponse({
      status: 404, statusText: 'Not Found'
    });
   
    httpClientSpy.post.and.returnValue(asyncError(errorResponse));
   
    service.email(forgotPasswordRequest).subscribe(
      data => fail('expected an error, not data'),
      error  => expect(error[0]).toContain('404 Not Found')
    );
  });

  
});
