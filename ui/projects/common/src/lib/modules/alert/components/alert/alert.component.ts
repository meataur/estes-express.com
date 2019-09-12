import { Component, OnInit, Output } from '@angular/core';
import { AlertService } from '../../services/alert.service';
import {
  trigger,
  state,
  style,
  transition,
  animate
} from '@angular/animations';
import { EventEmitter } from '@angular/core';

const ANIMATION_TIMINGS = '400ms cubic-bezier(0.25, 0.8, 0.25, 1)';

@Component({
  selector: 'lib-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.scss'],
  animations: [
    trigger('slideContent', [
      state('void', style({ transform: 'translate3d(0, -100%, 0)' })),
      state('enter', style({ transform: 'none' })),
      state('leave', style({ transform: 'translate3d(0,-100%, 0' })),
      transition('* => *', animate(ANIMATION_TIMINGS))
    ])
  ]
})
export class AlertComponent implements OnInit {
  @Output() close: EventEmitter<null> = new EventEmitter();
  show = true;
  animationState: 'void' | 'enter' | 'leave' = 'enter';

  constructor(private alertService: AlertService) {}

  ngOnInit() {
    // this.alertService.alert$.subscribe(next => {
    //   // this.show = true;
    // });
  }

  closeAlert() {
    this.animationState = 'leave';
    this.close.emit(null);
  }
}
