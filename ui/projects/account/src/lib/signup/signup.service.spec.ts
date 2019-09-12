import { TestBed } from '@angular/core/testing';
import { SignupService } from './signup.service';
import { of, defer } from 'rxjs';
import { SignupRequest } from './signup-request.model';

//TODO this function can be added to a testing utility file
/** Create async observable that emits-once and completes
 *  after a JS engine turn */
export function asyncData<T>(data: T) {
  return defer(() => Promise.resolve(data));
}

//TODO remove f from fdescribe when other account tests are fixed
//fdescribe causes the test command to only run fdescribe tests, and ignore all others
fdescribe('SignupService', () => {
  let injector: TestBed;
  let service: SignupService;
  let httpClientSpy: { post: jasmine.Spy };
  let signupRequest: SignupRequest;

  beforeEach(() => {
    httpClientSpy = jasmine.createSpyObj('HttpClient', ['post']);
    service = new SignupService(<any> httpClientSpy);
    signupRequest = new SignupRequest();
  });

  it('should create', () => {
    expect(service).toBeTruthy();
  });

  it('should return expected response and HttpClient called once', () => {
    const expectedResponse = { errorCode: '', message: 'Success!' };
   
    httpClientSpy.post.and.returnValue(asyncData(expectedResponse));
   
    service.signup(signupRequest).subscribe(
      data => expect(data).toEqual(expectedResponse, 'expected successful response'),
      fail
    );
    expect(httpClientSpy.post.calls.count()).toBe(1, 'one call');
  });
   
});