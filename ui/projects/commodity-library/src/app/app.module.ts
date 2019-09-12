import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './routing/app-routing.module';
import { CommodityComponent } from './components/commodity/commodity.component';
import { SharedModule } from 'common';
import { QuickLinksModule } from 'common';
import { AccountModule } from 'account';
import { HeaderModule } from 'header';
import { environment } from '../environments/environment';
import { ReactiveFormsModule } from '@angular/forms';
import { CommodityService } from './services/commodity.service';
import { HttpClientModule } from '@angular/common/http';
import { CommodityListComponent } from './components/commodity-list/commodity-list.component';
import { MaterialModule } from './modules/material.module';

@NgModule({
  declarations: [
    AppComponent,
    CommodityComponent,
    CommodityListComponent
  ],
  imports: [
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    MaterialModule,
    QuickLinksModule.forRoot(),
    SharedModule.forRoot(environment),
    AccountModule.forRoot(environment),
    HeaderModule.forRoot(environment),
    ReactiveFormsModule
  ],
  providers: [CommodityService],
  bootstrap: [AppComponent]
})
export class AppModule { }
