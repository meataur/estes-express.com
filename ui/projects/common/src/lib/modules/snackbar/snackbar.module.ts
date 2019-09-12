import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatSnackBarModule } from '@angular/material';
import { SnackbarMessageComponent } from './components/snackbar-message/snackbar-message.component';
import { ModuleWithProviders } from '@angular/compiler/src/core';
import { SnackbarService } from './services/snackbar.service';

@NgModule({
  imports: [CommonModule, MatSnackBarModule],
  declarations: [SnackbarMessageComponent],
  entryComponents: [SnackbarMessageComponent]
})
export class SnackbarModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: SnackbarModule,
      providers: [SnackbarService]
    };
  }
}
