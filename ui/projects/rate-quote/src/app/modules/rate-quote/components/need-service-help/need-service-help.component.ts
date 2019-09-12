import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-need-service-help',
  templateUrl: './need-service-help.component.html',
  styleUrls: ['./need-service-help.component.scss']
})
export class NeedServiceHelpComponent implements OnInit {
  tcSubject = encodeURIComponent(`My Estes time-critical inquiry`);
  vtlSubject = encodeURIComponent(`Volume and Truckload Rate Quote Help Request`);

  constructor() {}

  ngOnInit() {}
}
