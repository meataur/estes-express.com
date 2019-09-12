import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AccountModule } from 'account';
import { RouterTestingModule } from '@angular/router/testing';
import { of, throwError } from 'rxjs';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { ForgotPasswordComponent } from './forgot-password.component';
import { ForgotPasswordService } from './forgot-password.service';

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

fdescribe('ForgotPasswordComponent', () => {
  let component: ForgotPasswordComponent;
  let fixture: ComponentFixture<ForgotPasswordComponent>;
  let postForgotPasswordSpy: any;
  let testResponse: any;
  let forgotPasswordService: any;


  beforeEach(async(() => {
    testResponse = {          
      errorCode: "",
      message: "Success!"};

    forgotPasswordService = jasmine.createSpyObj('ForgotPasswordService', ['email']);
    postForgotPasswordSpy = forgotPasswordService.email.and.returnValue(of(testResponse));

    TestBed.configureTestingModule({
      declarations: [ ForgotPasswordComponent ],
      providers: [{ provide: ForgotPasswordService, useValue: forgotPasswordService }],
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
        NgbModule,
        RouterTestingModule.withRoutes([])
        ]

    })
    .compileComponents();
  }));

  beforeEach(() => {

    fixture = TestBed.createComponent(ForgotPasswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should define a new ForgotPasswordRequest model', () => {
    expect(component.forgotPswdRequest).toBeDefined();
  });

  it('should call submit function on submit button click', async(() => {
    spyOn(component, 'submit');
  
    let button = fixture.debugElement.nativeElement.querySelector('button[type="submit"]');
    button.click();
  
    fixture.whenStable().then(() => {
      expect(component.submit).toHaveBeenCalled();
    })
  }));

  it('should call ForgotPasswordService.email on submit(true)', () => {
    component.submit(true);

    fixture.detectChanges();
    expect(postForgotPasswordSpy.calls.any()).toBe(true, 'email called');
  });

  it('should set submitted and successMessage on successful submit', () =>  {
    component.submit(true);

    fixture.detectChanges();
    expect(component.submitted).toBeTruthy();
    expect(component.successMessage).toBe('Success!');
  });
  it('should set errorMessage on unsuccessful submit', fakeAsync(() =>  {
    postForgotPasswordSpy.and.returnValue(throwError(['Something terrible happened!']));
    component.submit(true);

    fixture.detectChanges(); // onInit()
    // sync spy errors immediately after init
   
    tick(); // flush the component's setTimeout()
   
    fixture.detectChanges(); // update errorMessage within setTimeout()

    expect(component.submitted).not.toBeTruthy();
    expect(component.successMessage).not.toBeTruthy();
    expect(component.errorMessages[0]).toBe('Something terrible happened!');
  }));
});
