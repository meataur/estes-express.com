import { Component, OnInit } from '@angular/core';
import { formatDate } from '@angular/common';
import { environment } from '../../../../../environments/environment';

@Component({
  selector: 'app-rate-estimate-intro',
  templateUrl: './rate-estimate-intro.component.html',
  styleUrls: ['./rate-estimate-intro.component.scss']
})
export class RateEstimateIntroComponent implements OnInit {
  formattedToday = formatDate(new Date(), 'MM/dd/yyyy', 'en-US');
  terminalLookupUrl = `${environment.appBaseUrl}/terminal-lookup`;

  constructor() {}

  ngOnInit() {}
}
