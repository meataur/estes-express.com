import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './routing/app-routing.module';

import { TransitTimeCalculatorComponent } from './transit-time-calculator.component';
import { HeaderModule } from 'header';
import { SharedModule } from 'common';
import { QuickLinksModule } from 'common';
import { AuthInterceptorService } from 'common';
import { AccountModule } from 'account';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import 'hammerjs';

import {
  MatAutocompleteModule,
  MatButtonModule,
  MatButtonToggleModule,
  MatCardModule,
  MatDatepickerModule,
  MatInputModule,
  MatNativeDateModule,
  MatProgressSpinnerModule,
  MatSelectModule
} from '@angular/material';
import { environment } from '../environments/environment';

@NgModule({
  declarations: [
    AppComponent,
    TransitTimeCalculatorComponent
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
    MatAutocompleteModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCardModule,
    MatDatepickerModule,
    MatInputModule,
    MatNativeDateModule,
    MatProgressSpinnerModule,
    MatSelectModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
