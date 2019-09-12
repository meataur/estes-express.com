import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AlertComponent } from './components/alert/alert.component';
import { AlertService } from './services/alert.service';

@NgModule({
  imports: [CommonModule],
  declarations: [AlertComponent],
  exports: [AlertComponent],
  entryComponents: [AlertComponent]
})
export class AlertModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: AlertModule,
      providers: [AlertService]
    };
  }
}
