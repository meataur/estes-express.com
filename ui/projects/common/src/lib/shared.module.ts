import { NgModule, InjectionToken } from '@angular/core';
import { AlertModule } from './modules/alert/alert.module';
import { MatFormFieldModule, MatSnackBarModule, MatCardModule } from '@angular/material';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule, PlatformLocation, APP_BASE_HREF } from '@angular/common';
import { FormService } from './util/services/form.service';
import { DisplayDatePipe } from './util/pipes/display-date.pipe';
import { DisplayFilenamePipe } from './util/pipes/display-filename.pipe';
import { PhonePipe } from './util/pipes/phone.pipe';
import { ComponentsModule } from './modules/components/components.module';
import { SnackbarModule } from './modules/snackbar/snackbar.module';
import { TextMaskModule } from 'angular2-text-mask';
import { UtilService } from './util/services/util.service';
// import { QuickLinksModule } from './modules/quick-links/quick-links.module';
import { LeftNavigationModule } from './modules/left-navigation/left-navigation.module';
import { PromoModule } from './modules/promo/promo.module';
import { CmsAlertModule } from './modules/cms-alert/cms-alert.module';
import { AuthModule } from './modules/auth/auth.module';
import { UtilModule } from './util/util.module';
import { DialogModule } from './modules/dialog/dialog.module';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptorService } from './modules/auth/services/auth-interceptor.service';
import { ModuleWithProviders } from '@angular/compiler/src/core';
import { APP_CONFIG } from './app.config';
import { AppConfig } from './models/app-config.interface';
import { ForbiddenComponent } from './components/forbidden/forbidden.component';
import { SharedRoutingModule } from './shared-routing.module';

export function getBaseHref(platformLocation: PlatformLocation): string {
  return platformLocation.getBaseHrefFromDOM();
}

@NgModule({
  schemas: [],
  imports: [
    CommonModule,
    SharedRoutingModule,
    LeftNavigationModule,
    PromoModule,
    CmsAlertModule,
    AlertModule,
    AuthModule,
    SnackbarModule,
    DialogModule.forRoot(),
    MatFormFieldModule,
    MatCardModule,
    ReactiveFormsModule,
    ComponentsModule,
    TextMaskModule,
    UtilModule
  ],
  exports: [
    AlertModule,
    AuthModule,
    DialogModule,
    DisplayDatePipe,
    DisplayFilenamePipe,
    PhonePipe,
    ComponentsModule,
    TextMaskModule,
    LeftNavigationModule,
    PromoModule,
    CmsAlertModule,
    UtilModule,
    SnackbarModule
  ],
  declarations: [ForbiddenComponent],
  providers: [
    FormService,
    UtilService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptorService,
      multi: true
    }
  ]
})
export class SharedModule {
  static forRoot(config: AppConfig): ModuleWithProviders {
    return {
      ngModule: SharedModule,
      providers: [
        {
          provide: APP_CONFIG,
          useValue: config
        },
        {
          provide: APP_BASE_HREF,
          useFactory: getBaseHref,
          deps: [PlatformLocation]
      }
      ]
    };
  }
}
