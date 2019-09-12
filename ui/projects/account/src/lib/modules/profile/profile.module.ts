import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProfileService } from './services/profile.service';
import { SharedModule, DialogModule } from 'common';
import { EditUsernameComponent } from './components/edit-username/edit-username.component';
import { EditPasswordComponent } from './components/edit-password/edit-password.component';
import { ProfileComponent } from './components/profile/profile.component';
import { LoginDialogComponent } from './components/login-dialog/login-dialog.component';
import { HttpClientModule } from '@angular/common/http';
import { EditEmailAddressComponent } from './components/edit-email-address/edit-email-address.component';
import { ProfileRoutingModule } from './profile-routing.module';
import { MaterialModule } from './modules/material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { ModuleWithProviders } from '@angular/compiler/src/core';
import { AppConfig } from '../../models/app-config.interface';
import { APP_CONFIG } from '../../models/app.config';

@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
    ReactiveFormsModule,
    ProfileRoutingModule,
    SharedModule,
    MaterialModule
  ],
  declarations: [
    EditUsernameComponent,
    EditPasswordComponent,
    ProfileComponent,
    LoginDialogComponent,
    EditEmailAddressComponent
  ],
  entryComponents: [
    LoginDialogComponent,
    EditEmailAddressComponent,
    EditUsernameComponent,
    EditPasswordComponent
  ],
  providers: [ProfileService]
})
export class ProfileModule {}
