/// <reference types="@types/googlemaps" />
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HeaderModule } from 'header';
import { AppRoutingModule } from './routing/app-routing.module';
import { AccountModule } from 'account';
// import { RequestAdditionalInfoModule } from 'request-additional-info';
// import { QuickLinksModule } from 'quick-links';
import { AuthInterceptorService } from 'common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppComponent } from './app.component';
import { TerminalLookupComponent } from './terminal-lookup/terminal-lookup.component';
import { EmailTerminalDialogComponent } from './email-dialogs/email-terminal-dialog.component';
import { EmailCoverageDialogComponent } from './email-dialogs/email-coverage-dialog.component';
import { SharedModule } from 'common';
import { QuickLinksModule } from 'common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import 'hammerjs';
import {
  MatAutocompleteModule,
  MatButtonModule,
  MatButtonToggleModule,
  MatCardModule,
  MatCheckboxModule,
  MatDatepickerModule,
  MatDialogModule,
  MatDividerModule,
  MatExpansionModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatNativeDateModule,
  MatPaginatorModule,
  MatProgressBarModule,
  MatProgressSpinnerModule,
  MatRadioModule,
  MatRippleModule,
  MatSelectModule,
  MatSortModule,
  MatTableModule,
  MatTabsModule,
  MatTreeModule
} from '@angular/material';
import { environment } from '../environments/environment';

@NgModule({
  declarations: [
    AppComponent,
    TerminalLookupComponent,
    EmailTerminalDialogComponent,
    EmailCoverageDialogComponent
  ],
  imports: [
    BrowserAnimationsModule,
    AppRoutingModule,
    HeaderModule.forRoot(environment),
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserModule,
    AccountModule.forRoot(environment),
    // RequestAdditionalInfoModule,
    // QuickLinksModule,
    SharedModule.forRoot(environment),
    QuickLinksModule.forRoot(),
    NgbModule,
    MatAutocompleteModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCardModule,
    MatCheckboxModule,
    MatDatepickerModule,
    MatDialogModule,
    MatDividerModule,
    MatExpansionModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatNativeDateModule,
    MatPaginatorModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatRadioModule,
    MatRippleModule,
    MatSelectModule,
    MatSortModule,
    MatTabsModule,
    MatTableModule,
    MatTreeModule
  ],
  bootstrap: [AppComponent],
  entryComponents: [EmailTerminalDialogComponent, EmailCoverageDialogComponent]
})
export class AppModule {}
