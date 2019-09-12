import { Component, OnInit, Input } from '@angular/core';
import { RateQuote } from '../../models';
import { Observable } from 'rxjs';
import { RateQuoteService } from '../../services/rate-quote.service';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-guaranteed-option-message',
  templateUrl: './guaranteed-option-message.component.html',
  styleUrls: ['./guaranteed-option-message.component.scss']
})
export class GuaranteedOptionMessageComponent implements OnInit {
  message$: Observable<string>;

  @Input() quote: RateQuote;

  constructor(private rateQuoteService: RateQuoteService) {}

  ngOnInit() {
    if (this.quote.guaranteed) {
      this.message$ = this.rateQuoteService.getServiceLevels().pipe(
        map(res => {
          const s = res.find(sl => sl.id === this.quote.service.id);
          if (s) {
            return `Please write or type ${s.text} on your BOL (including the driver's copy).`;
          }
          return ``;
        })
      );
    }
  }
}
