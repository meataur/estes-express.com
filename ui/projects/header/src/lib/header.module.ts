import { NgModule, ModuleWithProviders } from '@angular/core';
import { HeaderComponent } from './header.component';
import { FooterComponent } from './footer.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { DialogModule } from 'common';
import { RouterModule } from '@angular/router';
import { AppConfig } from './models/app-config.interface';
import { APP_CONFIG } from './models/app.config';

@NgModule({
  imports: [CommonModule, NgbModule.forRoot(), DialogModule.forRoot(), RouterModule],
  declarations: [HeaderComponent, FooterComponent],
  exports: [HeaderComponent, FooterComponent]
})
export class HeaderModule {
  static forRoot(config: AppConfig): ModuleWithProviders {
    return {
      ngModule: HeaderModule,
      providers: [
        {
          provide: APP_CONFIG,
          useValue: config
        }
      ]
    };
  }
}
