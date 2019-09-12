import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';
import { QuoteDetailsComponent } from './modules/rate-quote/components/quote-details/quote-details.component';
import { ExitRateQuoteModalComponent } from './modules/rate-quote/components/exit-rate-quote-modal/exit-rate-quote-modal.component';

@NgModule({
  declarations: [
    AppComponent,
    QuoteDetailsComponent,
    ExitRateQuoteModalComponent
  ],
  imports: [
    BrowserModule,
    ServiceWorkerModule.register('/ngsw-worker.js', { enabled: environment.production })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
