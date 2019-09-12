import {
  Component,
  OnInit,
  Input,
  OnChanges,
  SimpleChanges
} from '@angular/core';
import { FeedbackTypes } from '../../models';

@Component({
  selector: 'lib-feedback',
  templateUrl: './feedback.component.html',
  styleUrls: ['./feedback.component.scss']
})
export class FeedbackComponent implements OnInit, OnChanges {
  @Input() message: [FeedbackTypes, string | string[]];
  /**
   * @description Toggles icon on/off.  Shows only when the message is single string only (not list).
   */
  @Input() useIcon = false;

  constructor() {}

  get hasMultipleMessages() { return this.message && Array.isArray(this.message[1]) ? true : false; }

  ngOnInit() {}

  ngOnChanges(changes: SimpleChanges) {
  }

  getIconClasses() {
    return {
      fas: true,
      'mr-2': true,
      'fa-check-circle': this.message[0] === 'success',
      'fa-info-circle': this.message[0] === 'info',
      'fa-exclamation-circle': this.message[0] === 'error'
    };
  }

  getClasses() {
    return {
      alert: true,
      'alert-success': this.message[0] === 'success',
      'alert-info': this.message[0] === 'info',
      'alert-danger': this.message[0] === 'error'
    };
  }
}
