import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './routing/app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { MaterialModule } from './modules/material.module';
import { SharedModule } from 'common';
import { QuickLinksModule } from 'common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { environment } from '../environments/environment';
import { AccountModule } from 'account';
import { HeaderModule } from 'header';
import { RateQuoteTypeService } from './modules/rate-quote/services/rate-quote-type.service';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    MaterialModule,
    SharedModule.forRoot(environment),
    QuickLinksModule.forRoot(),
    AccountModule.forRoot(environment),
    HeaderModule.forRoot(environment),
    ReactiveFormsModule,
    FormsModule
  ],
  providers: [RateQuoteTypeService],
  bootstrap: [AppComponent]
})
export class AppModule {}
