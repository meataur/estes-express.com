import 'hammerjs';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { SharedModule } from 'common';
import { QuickLinksModule } from 'common';
import { AccountModule } from 'account';
import { HeaderModule } from 'header';
import { DocumentRetrievalComponent } from './components/document-retrieval/document-retrieval.component';
import { AppRoutingModule } from './routing/app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { DocumentRetrievalService } from './services/document-retrieval.service';
import { MaterialModule } from './modules/material/material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { environment } from '../environments/environment';

@NgModule({
  declarations: [AppComponent, DocumentRetrievalComponent],
  imports: [
    BrowserAnimationsModule,
    AppRoutingModule,
    SharedModule.forRoot(environment),
    QuickLinksModule.forRoot(),
    AccountModule.forRoot(environment),
    HeaderModule.forRoot(environment),
    HttpClientModule,
    MaterialModule,
    ReactiveFormsModule
  ],
  providers: [DocumentRetrievalService],
  bootstrap: [AppComponent]
})
export class AppModule {}
