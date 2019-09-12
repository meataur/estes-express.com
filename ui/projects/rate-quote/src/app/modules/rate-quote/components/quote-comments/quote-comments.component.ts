import { Component, OnInit, Input } from '@angular/core';
import { RateQuote } from '../../models';

@Component({
  selector: 'app-quote-comments',
  templateUrl: './quote-comments.component.html',
  styleUrls: ['./quote-comments.component.scss']
})
export class QuoteCommentsComponent implements OnInit {
  @Input() quote: RateQuote;

  constructor() {}

  ngOnInit() {}
}
