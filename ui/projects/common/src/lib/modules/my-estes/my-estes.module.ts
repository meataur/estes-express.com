import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MyEstesRoutingModule } from './my-estes-routing.module';
import { WelcomePageComponent } from './components/welcome-page/welcome-page.component';
import { RecentShipmentsComponent } from './components/recent-shipments/recent-shipments.component';
import { AuthModule } from '../auth/auth.module';
import { MaterialModule } from './modules/material.module';
import { MyestesService } from './services/myestes.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
// import { ComponentsModule } from '../components/components.module';
import { SharedModule } from '../../shared.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { DialogModule } from '../dialog/dialog.module';
import { PreferenceDialogComponent } from './components/preference-dialog/preference-dialog.component';
import { SnackbarModule } from '../snackbar/snackbar.module';
import { UtilModule } from '../../util/util.module';
import { ComponentsModule } from '../components/components.module';
import { AuthInterceptorService } from '../auth/public_api';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    UtilModule,
    // SharedModule,
    ComponentsModule,
    MaterialModule,
    // AuthModule.forRoot(),
    DialogModule.forRoot(),
    SnackbarModule.forRoot(),
    MyEstesRoutingModule
    // ComponentsModule
  ],
  declarations: [WelcomePageComponent, RecentShipmentsComponent, PreferenceDialogComponent],
  entryComponents: [PreferenceDialogComponent],
  providers: [
    MyestesService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptorService,
      multi: true
    }
  ]
})
export class MyEstesModule {}
