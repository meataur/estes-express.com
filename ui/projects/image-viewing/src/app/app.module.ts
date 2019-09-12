import { BrowserModule } from '@angular/platform-browser';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppComponent } from './app.component';
import { ImageViewingComponent } from './components/image-viewing/image-viewing.component';
import { AppRoutingModule } from './routing/app-routing-module';
import { SharedModule } from 'common';
import { QuickLinksModule } from 'common';
import { HeaderModule } from 'header';
import { AccountModule } from 'account';
import { MaterialModule } from './modules/material/material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { ImageFetchComponent } from './components/image-fetch/image-fetch.component';
import { ImageViewingService } from './services/image-viewing.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { environment } from '../environments/environment';
import { EmailDialogComponent } from './components/email-dialog/email-dialog.component';
import 'hammerjs';

@NgModule({
  declarations: [AppComponent, ImageViewingComponent, ImageFetchComponent, EmailDialogComponent],
  imports: [
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    SharedModule.forRoot(environment),
    QuickLinksModule.forRoot(),
    AccountModule.forRoot(environment),
    HeaderModule.forRoot(environment),
    MaterialModule,
    ReactiveFormsModule
  ],
  providers: [ImageViewingService],
  bootstrap: [AppComponent],
  entryComponents: [EmailDialogComponent]
})
export class AppModule {}
