import { AddressBookComponent } from './address-book.component';
import { AppComponent } from './app.component';

import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HeaderModule } from 'header';
import { AppRoutingModule } from './routing/app-routing.module';
import { AccountModule } from 'account';
import { SharedModule } from 'common';
import { QuickLinksModule } from 'common';
// import { RequestAdditionalInfoModule } from 'request-additional-info';
// import { QuickLinksModule } from 'quick-links';
import { AuthInterceptorService } from 'common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AddressDialogComponent } from './dialog-components/address-details/address-dialog.component';
import { DeleteAddressDialogComponent } from './dialog-components/delete-address/delete-address-dialog.component';
import 'hammerjs';
import {
  MatAutocompleteModule,
  MatButtonModule,
  MatButtonToggleModule,
  MatCardModule,
  MatCheckboxModule,
  MatDialogModule,
  MatDividerModule,
  MatExpansionModule,
  MatFormFieldModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatPaginatorModule,
  MatProgressSpinnerModule,
  MatRadioModule,
  MatRippleModule,
  MatSelectModule,
  MatSortModule,
  MatTableModule,
  MatTreeModule
} from '@angular/material';
import { UploadAddressBookComponent } from './upload-address-book/upload-address-book.component';
import { environment } from '../environments/environment';

@NgModule({
  declarations: [
    AppComponent,
    AddressBookComponent,
    AddressDialogComponent,
    DeleteAddressDialogComponent,
    UploadAddressBookComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserAnimationsModule,
    HeaderModule.forRoot(environment),
    NgbModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    AccountModule.forRoot(environment),
    // RequestAdditionalInfoModule,
    // QuickLinksModule,
    MatAutocompleteModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCardModule,
    MatCheckboxModule,
    MatDialogModule,
    MatDividerModule,
    MatExpansionModule,
    MatFormFieldModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatPaginatorModule,
    MatProgressSpinnerModule,
    MatRadioModule,
    MatRippleModule,
    MatSelectModule,
    MatSortModule,
    MatTableModule,
    MatTreeModule,
    SharedModule.forRoot(environment),
    QuickLinksModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent],
  entryComponents: [AddressDialogComponent, DeleteAddressDialogComponent]
})
export class AppModule {}
