import { AppComponent } from './app.component';
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HeaderModule } from 'header';
import { AppRoutingModule } from './routing/app-routing.module';
import { AccountModule } from 'account';
import { SharedModule } from 'common';
import { QuickLinksModule } from 'common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { InvoiceInquiryComponent } from './invoice-inquiry.component';
import {CurrencyPipe} from '@angular/common'
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
  MatSlideToggleModule,
  MatSortModule,
  MatTabsModule,
  MatTableModule,
  MatTreeModule
} from '@angular/material';
import { environment } from '../environments/environment';
import { StatementDetailsComponent } from './statement-details/statement-details.component';
import { PayInvoicesComponent } from './pay-invoices/pay-invoices.component';
import { ImageFetchComponent } from './image-fetch/image-fetch.component';
import { EmailDialogComponent } from './email-dialog/email-dialog.component';
import { SpecialInstructionsComponent } from './special-instructions/special-instructions.component';


@NgModule({
  declarations: [
    AppComponent,
    InvoiceInquiryComponent,
    StatementDetailsComponent,
    PayInvoicesComponent,
    ImageFetchComponent,
    EmailDialogComponent,
    SpecialInstructionsComponent
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
    MatAutocompleteModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCardModule,
    MatCheckboxModule,
    MatDatepickerModule,
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
    MatSlideToggleModule,
    MatSortModule,
    MatTableModule,
    MatTabsModule,
    MatTreeModule,
    SharedModule.forRoot(environment),
    QuickLinksModule.forRoot()
  ],
  bootstrap: [AppComponent],
  entryComponents: [EmailDialogComponent],
  providers: [CurrencyPipe]
})
export class AppModule {}
