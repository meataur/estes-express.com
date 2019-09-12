import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HeaderModule } from 'header';
import { AppRoutingModule } from './routing/app-routing.module';
import { AccountModule } from 'account';
import { AuthInterceptorService, SharedModule } from 'common';
import { QuickLinksModule } from 'common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppComponent } from './app.component';
import { ShipmentTrackingComponent } from './shipment-tracking.component';
import 'hammerjs';
import {
  MatCardModule,
  MatCheckboxModule,
  MatDialogModule,
  MatDividerModule,
  MatExpansionModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatPaginatorModule,
  MatProgressSpinnerModule,
  MatSelectModule,
  MatSortModule,
  MatTableModule,
  MatTreeModule
} from '@angular/material';
import { EmailDialogComponent } from './components/email-dialog/email-dialog.component';
import { ImageFetchWeightAndResearchComponent } from './components/image-fetch-weight-and-research/image-fetch-weight-and-research.component';
import { environment } from '../environments/environment';

@NgModule({
  declarations: [AppComponent, ShipmentTrackingComponent, EmailDialogComponent, ImageFetchWeightAndResearchComponent],
  imports: [
    BrowserAnimationsModule,
    AppRoutingModule,
    HeaderModule.forRoot(environment),
    NgbModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    SharedModule.forRoot(environment),
    QuickLinksModule.forRoot(),
    AccountModule.forRoot(environment),
    // RequestAdditionalInfoModule,
    MatCardModule,
    MatCheckboxModule,
    MatDialogModule,
    MatDividerModule,
    MatExpansionModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatPaginatorModule,
    MatProgressSpinnerModule,
    MatSelectModule,
    MatSortModule,
    MatTableModule,
    MatTreeModule
  ],
  entryComponents: [EmailDialogComponent],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
