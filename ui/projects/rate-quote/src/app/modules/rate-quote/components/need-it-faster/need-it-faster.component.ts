import { Component, OnInit, Input } from '@angular/core';
import { RateQuote } from '../../models';
import { AuthService, PackageType, UtilService } from 'common';
import { formatDate } from '@angular/common';
import { RateQuoteService } from '../../services/rate-quote.service';

@Component({
  selector: 'app-need-it-faster',
  templateUrl: './need-it-faster.component.html',
  styleUrls: ['./need-it-faster.component.scss']
})
export class NeedItFasterComponent implements OnInit {
  @Input() isRateEstimate = false;
  @Input() quote: RateQuote;
  subject: string;
  body: string;

  constructor(private auth: AuthService, private rateQuoteService: RateQuoteService) {}

  ngOnInit() {
    if (this.isRateEstimate) {
      this.initRateEstimateNeedItFaster();
    } else {
      this.initAuthenticatedNeedItFaster();
    }
  }
  mailTo() {
    // Setting this boolean so that, if the onbeforeunload event fires for the page, it can bypass
    // the confirmation message to navigate away.
    this.rateQuoteService.mailToInProgress = true;
    window.location.href = `mailto:solutions@estes-express.com?subject=${this.subject}&body=${
      this.body
    }`;
  }

  private initAuthenticatedNeedItFaster() {
    this.subject = encodeURIComponent(`My Estes: Need It Delivered Faster?`);
    this.body = encodeURIComponent(`Dear Estes Solution Center,

We would like more information regarding the time-critical services available for the quote we just entered into My Estes. We look forward to hearing from you!

Company Name: ${this.quote.accountName}
Profile Name: ${this.auth.getAuthToken().username}
Date: ${formatDate(new Date(), 'MM/dd/yyyy', 'en-US')}`);
  }

  private initRateEstimateNeedItFaster() {
    this.subject = encodeURIComponent(`Need It Delivered Faster?`);
    this.body = encodeURIComponent(`Dear Estes Solution Center,

We would like more information regarding the time-critical services available for the following shipment:

Contact Name:
Company Name:
Contact Phone Number/Ext:
Estes Account Number (if applicable):
Potential Pickup Date:
Requested Delivery Date/Time:
Class:
Number of Pieces:
Piece Type (pallet, box, etc.):
Total Weight (pounds):
Dimensions (Length/Width/Height in inches):

Thank you for your time and attention. We look forward to hearing from you.
`);
  }
}
