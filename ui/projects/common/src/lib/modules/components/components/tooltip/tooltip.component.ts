import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { MatTooltip } from '@angular/material';

@Component({
  selector: 'lib-tooltip',
  templateUrl: './tooltip.component.html',
  styleUrls: ['./tooltip.component.scss']
})
export class TooltipComponent implements OnInit {
  @Input() message: string;
  @Input() class: string;

  @ViewChild('tooltip') tooltip: MatTooltip;

  constructor() {}

  ngOnInit() {}

  onClick($event) {
    // $event.stopPropagation();
    this.tooltip.toggle();
  }
}
