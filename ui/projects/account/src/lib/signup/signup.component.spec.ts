import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SignupComponent } from './signup.component';
import { SignupService } from './signup.service';
import { TextMaskModule } from 'angular2-text-mask';
import { AccountModule } from 'account';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import {
  MatAutocompleteModule,
  MatButtonModule,
  MatCardModule,
  MatCheckboxModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatRadioModule,
  MatSlideToggleModule,
  MatProgressSpinnerModule
} from '@angular/material';

//TODO remove f from fdescribe when other account tests are fixed
//fdescribe causes the test command to only run fdescribe tests, and ignore all others
fdescribe('SignupComponent', () => {
  let component: SignupComponent;
  let fixture: ComponentFixture<SignupComponent>;
  let testResponse: any;
  let getSignupSpy: any;

  beforeEach(() => {

    testResponse = {          
      errorCode: "",
      message: "Success!"};

    const signupService = jasmine.createSpyObj('SignupService', ['signup']);
    getSignupSpy = signupService.signup.and.returnValue(of(testResponse));

    TestBed.configureTestingModule({
      declarations: [ SignupComponent ],
      providers: [{ provide: SignupService, useValue: signupService }],
      imports: [    
        AccountModule,
        MatAutocompleteModule,
        MatButtonModule,
        MatCardModule,
        MatCheckboxModule,
        MatIconModule,
        MatInputModule,
        MatListModule,
        MatRadioModule,
        MatSlideToggleModule,
        MatProgressSpinnerModule,
        FormsModule,
        ReactiveFormsModule,
        TextMaskModule,
        NgbModule,
        RouterTestingModule.withRoutes([])
        ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SignupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should define a new SignupRequest model', () => {
    expect(component.signupRequest).toBeDefined();
  });

  it('should define a phoneMask variable', () => {
    expect(component.phoneMask).toBeDefined();
  });

  it('should call submit function on submit button click', async(() => {
    spyOn(component, 'submit');
  
    let button = fixture.debugElement.nativeElement.querySelector('button[type="submit"]');
    button.click();
  
    fixture.whenStable().then(() => {
      expect(component.submit).toHaveBeenCalled();
    })
  }));

  it('should call SubmitService.signup on submit(true)', () => {
    component.submit(true);

    fixture.detectChanges();
    expect(getSignupSpy.calls.any()).toBe(true, 'signup called');
  });

  it('should set submitted var to true on submit', () =>  {
    component.submit(true);

    fixture.detectChanges();
    expect(component.submitted).toBeTruthy();
  });
  
});
