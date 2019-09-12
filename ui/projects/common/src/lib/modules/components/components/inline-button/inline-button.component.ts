import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'lib-inline-button',
  templateUrl: './inline-button.component.html',
  styleUrls: ['./inline-button.component.scss']
})
export class InlineButtonComponent implements OnInit {
  @Input() faIcon: string;

  constructor() {}

  ngOnInit() {}
}
