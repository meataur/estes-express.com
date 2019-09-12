import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './routing/app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { MaterialModule } from './modules/material.module';
import { SharedModule } from 'common';
import { QuickLinksModule } from 'common';
import { AccountModule } from 'account';
import { HeaderModule } from 'header';
import { ReactiveFormsModule } from '@angular/forms';
import { environment } from '../environments/environment';
import { WeightAndResearchComponent } from './components/weight-and-research/weight-and-research.component';
import { WeightAndResearchService } from './services/weight-and-research.service';
import { ImageFetchComponent } from './components/image-fetch/image-fetch.component';
import { GetEmailModalComponent } from './components/get-email-modal/get-email-modal.component';
import 'hammerjs';

@NgModule({
  declarations: [
    AppComponent,
    WeightAndResearchComponent,
    ImageFetchComponent,
    GetEmailModalComponent
  ],
  imports: [
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    MaterialModule,
    SharedModule.forRoot(environment),
    QuickLinksModule.forRoot(),
    AccountModule.forRoot(environment),
    HeaderModule.forRoot(environment),
    ReactiveFormsModule
  ],
  providers: [WeightAndResearchService],
  entryComponents: [GetEmailModalComponent],
  bootstrap: [AppComponent]
})
export class AppModule {}
