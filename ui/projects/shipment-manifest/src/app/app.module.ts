import { BrowserModule } from '@angular/platform-browser';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AppComponent } from './app.component';
import { ShipmentManifestComponent } from './components/shipment-manifest/shipment-manifest.component';
import { HeaderModule } from 'header';
import { AppRoutingModule } from './routing/app-routing.module';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ShipmentManifestService } from './services/shipment-manifest.service';
import { SharedModule, DialogModule, AuthInterceptorService } from 'common';
import { QuickLinksModule } from 'common';
import { AccountModule } from 'account';
import { ReactiveFormsModule } from '@angular/forms';
import {
  MatCardModule,
  MatCheckboxModule,
  MatSelectModule,
  MatRadioModule,
  MatTableModule,
  MatPaginatorModule,
  MatTabsModule,
  MatNativeDateModule
} from '@angular/material';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { environment } from '../environments/environment';


@NgModule({
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  declarations: [AppComponent, ShipmentManifestComponent],
  imports: [
    HttpClientModule,
    AppRoutingModule,
    ReactiveFormsModule,
    SharedModule.forRoot(environment),
    QuickLinksModule.forRoot(),
    AccountModule.forRoot(environment),
    HeaderModule.forRoot(environment),
    MatCardModule,
    MatCheckboxModule,
    MatInputModule,
    MatSelectModule,
    MatRadioModule,
    MatTableModule,
    MatPaginatorModule,
    MatTabsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    DialogModule.forRoot(),
    BrowserAnimationsModule
  ],
  providers: [
    ShipmentManifestService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptorService,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
