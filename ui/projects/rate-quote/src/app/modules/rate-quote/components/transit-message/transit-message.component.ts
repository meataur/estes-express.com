import { Component, OnInit, Input } from '@angular/core';
import { RateQuote } from '../../models';

@Component({
  selector: 'app-transit-message',
  templateUrl: './transit-message.component.html',
  styleUrls: ['./transit-message.component.scss']
})
export class TransitMessageComponent implements OnInit {
  @Input() quote: RateQuote;

  constructor() {}

  ngOnInit() {}
}
