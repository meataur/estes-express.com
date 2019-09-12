import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OverlayDirective } from './directives/overlay.directive';
import { OverlayComponent } from './components/overlay/overlay.component';
import { DynamicOverlay, DynamicOverlayContainer } from './services/overlay.service';
import { MatProgressSpinnerModule } from '@angular/material';
import { DisplayDatePipe } from './pipes/display-date.pipe';
import { DisplayFilenamePipe } from './pipes/display-filename.pipe';
import { PhonePipe } from './pipes/phone.pipe';

@NgModule({
  imports: [CommonModule, MatProgressSpinnerModule],
  declarations: [
    OverlayDirective,
    OverlayComponent,
    DisplayDatePipe,
    DisplayFilenamePipe,
    PhonePipe
  ],
  entryComponents: [OverlayComponent],
  exports: [OverlayDirective, DisplayDatePipe, DisplayFilenamePipe, PhonePipe],
  providers: [DynamicOverlay, DynamicOverlayContainer]
})
export class UtilModule {}
