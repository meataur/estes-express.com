import { Component, OnInit, Input } from '@angular/core';
import { RateQuote } from '../../models';

@Component({
  selector: 'app-quote-side-panel',
  templateUrl: './quote-side-panel.component.html',
  styleUrls: ['./quote-side-panel.component.scss']
})
export class QuoteSidePanelComponent implements OnInit {

  @Input() quote: RateQuote;

  constructor() { }

  ngOnInit() {
  }

}
