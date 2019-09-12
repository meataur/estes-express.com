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
import { OnlineReportingComponent } from './online-reporting.component';
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
  MatSortModule,
  MatTabsModule,
  MatTableModule,
  MatTreeModule
} from '@angular/material';
import { CreateReportComponent } from './create-report.component';
import { environment } from '../environments/environment';


@NgModule({
  declarations: [
    AppComponent,
    OnlineReportingComponent,
    CreateReportComponent
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
    MatSortModule,
    MatTableModule,
    MatTabsModule,
    MatTreeModule,
    SharedModule.forRoot(environment),
    QuickLinksModule.forRoot()
  ],
  bootstrap: [AppComponent],
  entryComponents: []
})
export class AppModule {}
