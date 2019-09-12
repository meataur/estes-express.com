import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HeaderModule } from 'header';
import { AppRoutingModule } from './routing/app-routing.module';
import { AccountModule } from 'account';
import { AuthInterceptorService, SharedModule } from 'common';
import { QuickLinksModule } from 'common';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppComponent } from './app.component';
import { ErrorsComponent } from './errors.component';
import { environment } from '../environments/environment';
import 'hammerjs';

@NgModule({
  declarations: [AppComponent, ErrorsComponent],
  imports: [
    BrowserAnimationsModule,
    AppRoutingModule,
    HeaderModule.forRoot(environment),
    NgbModule,
    HttpClientModule,
    SharedModule.forRoot(environment),
    AccountModule.forRoot(environment),
    QuickLinksModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
