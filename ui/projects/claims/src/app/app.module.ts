import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HeaderModule } from 'header';
import { AppRoutingModule } from './routing/app-routing.module';
import { AccountModule } from 'account';
import { AuthInterceptorService } from 'common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppComponent } from './app.component';
import { ClaimsInquiryComponent } from './claims-inquiry/claims-inquiry.component';

import { SharedModule } from 'common';
import { QuickLinksModule } from 'common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ClaimsService } from './claims.service';


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
import { FileClaimComponent } from './file-claim/file-claim.component';
import { ClaimsComponent } from './claims.component';
import { environment } from '../environments/environment';

@NgModule({
  declarations: [
    AppComponent,
    ClaimsInquiryComponent,
    FileClaimComponent,
    ClaimsComponent
  ],
  imports: [
    BrowserAnimationsModule,
    AppRoutingModule,
    HeaderModule.forRoot(environment),
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    AccountModule.forRoot(environment),
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
    MatTreeModule,
  ],
  providers: [ClaimsService],
  bootstrap: [AppComponent]
})

export class AppModule { }
