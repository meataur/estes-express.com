import { Component, OnInit, Inject } from '@angular/core';
import { MAT_SNACK_BAR_DATA } from '@angular/material';

@Component({
  selector: 'lib-snackbar-message',
  template: '<i [ngClass]="getClasses()"></i>&nbsp;&nbsp;{{message}}',
  styleUrls: ['./snackbar-message.component.scss']
})
export class SnackbarMessageComponent implements OnInit {
  constructor(
    @Inject(MAT_SNACK_BAR_DATA)
    public data: any
  ) {}

  ngOnInit() {}

  getClasses() {
    return {
      fas: true,
      'fa-check-circle': this.data.type === 'success',
      'fa-info-circle': this.data.type === 'info',
      'fa-exclamation-circle': this.data.type === 'error'
    };
  }

  get message(): string {
    switch (this.data.type) {
      case 'success':
        return `Success! ${this.data.message}`;
      case 'info':
        return `${this.data.message}`;
      case 'error':
        return `ERROR: ${this.data.message}`;
    }
  }
}
