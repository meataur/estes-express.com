import { NgModule, ModuleWithProviders } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AccountRoutingModule } from './account-routing.module';
import { LoginComponent } from './login/login.component';
import { TextMaskModule } from 'angular2-text-mask';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { SignupComponent } from './signup/signup.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ProfileComponent } from './profile/profile.component';
import { RequestAccountNumberComponent } from './request-account-number/request-account-number.component';
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
import { SharedModule } from 'common';
import { QuickLinksModule } from 'common';
import { CommonModule } from '@angular/common';
import { SignupService } from './signup/signup.service';
import { AppConfig } from './models/app-config.interface';
import { APP_CONFIG } from './models/app.config';

@NgModule({
  imports: [
    CommonModule,
    AccountRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    // BrowserAnimationsModule,
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
    TextMaskModule,
    NgbModule,
    SharedModule,
    QuickLinksModule.forRoot()
  ],
  declarations: [
    LoginComponent,
    ForgotPasswordComponent,
    SignupComponent,
    ProfileComponent,
    RequestAccountNumberComponent
  ],
  exports: [
    LoginComponent,
    ForgotPasswordComponent,
    SignupComponent,
    ProfileComponent,
    RequestAccountNumberComponent
  ]
})
export class AccountModule {
  static forRoot(config: AppConfig): ModuleWithProviders {
    return {
      ngModule: AccountModule,
      providers: [
        {
          provide: APP_CONFIG,
          useValue: config
        }
      ]
    };
  }
}
