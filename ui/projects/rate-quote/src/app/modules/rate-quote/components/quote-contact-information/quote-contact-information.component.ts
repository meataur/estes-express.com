import { Component, OnInit, Input } from '@angular/core';
import { RateQuote } from '../../models';
import { AuthService } from 'common';

@Component({
  selector: 'app-quote-contact-information',
  templateUrl: './quote-contact-information.component.html',
  styleUrls: ['./quote-contact-information.component.scss']
})
export class QuoteContactInformationComponent implements OnInit {
  @Input() quote: RateQuote;

  constructor(public auth: AuthService) {}

  ngOnInit() {}
}
