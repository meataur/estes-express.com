import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'lib-button-link',
  templateUrl: './button-link.component.html',
  styleUrls: ['./button-link.component.scss']
})
export class ButtonLinkComponent implements OnInit {
  @Input() text: string;
  /**
   * @description Optional font awesome icon identifer.
   */
  @Input() faIcon: string;
  @Output() clicked = new EventEmitter<boolean>();

  constructor() {}

  ngOnInit() {}

  onClick() {
    this.clicked.next(true);
  }

  getClasses() {
    return {
      fa: true,
      [this.faIcon]: true
    };
  }
}
