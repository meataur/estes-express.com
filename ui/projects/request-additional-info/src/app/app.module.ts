import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { RequestAdditionalInfoComponent } from './request-additional-info.component';
import { AppRoutingModule } from './routing/app-routing.module';
import { HeaderModule } from 'header';
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
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { RequestAdditionalInfoService } from './request-additional-info.service';
import { HttpClientModule } from '@angular/common/http';
import { AlertModule, SharedModule, AuthModule } from 'common';
import { QuickLinksModule } from 'common';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { environment } from '../environments/environment';
import { AccountModule } from 'account';

@NgModule({
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  declarations: [AppComponent, RequestAdditionalInfoComponent],
  imports: [
    HttpClientModule,
    AppRoutingModule,
    SharedModule.forRoot(environment),
    QuickLinksModule.forRoot(),
    AccountModule.forRoot(environment),
    HeaderModule.forRoot(environment),
    ReactiveFormsModule,
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
    BrowserAnimationsModule
  ],
  providers: [RequestAdditionalInfoService],
  bootstrap: [AppComponent]
})
export class AppModule {}
