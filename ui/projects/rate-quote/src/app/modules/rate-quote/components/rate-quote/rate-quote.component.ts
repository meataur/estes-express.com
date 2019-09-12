import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-rate-quote',
  templateUrl: './rate-quote.component.html',
  styleUrls: ['./rate-quote.component.scss']
})
export class RateQuoteComponent implements OnInit {
  navLinks = [
    {
      label: 'Create Rate Quote',
      path: 'create'
    },
    {
      label: 'Rate Quote History',
      path: 'history'
    }
  ];

  constructor() { }

  ngOnInit() {
  }

}
