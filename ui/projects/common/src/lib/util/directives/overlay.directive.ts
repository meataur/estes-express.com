import {
  Directive,
  ElementRef,
  Input,
  OnChanges,
  SimpleChanges
} from '@angular/core';
import { OverlayService, ProgressRef } from '../services/overlay.service';

@Directive({
  selector: '[libOverlay]',
  providers: [OverlayService]
})
export class OverlayDirective implements OnChanges {
  // tslint:disable-next-line:no-input-rename
  @Input('libOverlay') show = false;
  progressRef: ProgressRef;

  constructor(private el: ElementRef, private overlayService: OverlayService) {}

  ngOnChanges(changes: SimpleChanges) {
    setTimeout(() => {
      const show = changes.show.currentValue;

      if (show) {
        this.progressRef = this.overlayService.showProgress(this.el);
      } else {
        this.overlayService.detach(this.progressRef);
      }
    });
  }
}
