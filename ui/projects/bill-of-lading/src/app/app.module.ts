import { AppRoutingModule } from './routing/app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SharedModule } from 'common';
import { HeaderModule } from 'header';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { AccountModule } from 'account';
import { NgModule } from '@angular/core';
import 'hammerjs';
import { MaterialModule } from './modules/material/material.module';
import { environment } from '../environments/environment';
import { QuickLinksModule } from 'common';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserAnimationsModule,
    AppRoutingModule,
    QuickLinksModule.forRoot(),
    SharedModule.forRoot(environment),
    AccountModule.forRoot(environment),
    HeaderModule.forRoot(environment),
    HttpClientModule,
    ReactiveFormsModule,
    MaterialModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
