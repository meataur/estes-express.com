import { Component, OnInit, ViewChild, OnDestroy, OnChanges, SimpleChanges } from '@angular/core';
import { RateQuoteFormSection } from '../../models/rate-quote-form-section.model';
import { RateQuoteTypeService } from '../../services/rate-quote-type.service';
import { takeUntil } from 'rxjs/operators';
import { AuthService } from 'common';
import { RateQuote } from '../../models';

@Component({
  selector: 'app-rate-quote-type',
  templateUrl: './rate-quote-type.component.html',
  styleUrls: ['./rate-quote-type.component.scss']
})
export class RateQuoteTypeComponent extends RateQuoteFormSection
  implements OnInit, OnDestroy, OnChanges {
  showApplicationAccessFeedback = false;

  constructor(private rateQuoteTypeService: RateQuoteTypeService, protected auth: AuthService) {
    super(auth);
  }

  ngOnInit() {
    super.ngOnInit();
  }

  ngOnDestroy() {
    super.ngOnDestroy();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.quote && changes.quote.currentValue) {
      const quote: RateQuote = changes.quote.currentValue;
      this.rateQuoteTypeService.resetForm(quote.selectedApps);
    }
  }

  // Invoked by parent class in ngOnInit()
  initForm() {
    this.rateQuoteTypeService.formModel$.pipe(takeUntil(this.stop$)).subscribe(form => {
      this.formGroup = form;

      this.initFormScope();
    });
  }

  get ltl() {
    return this.formGroup.controls['ltl'];
  }
  get volumeAndTruckload() {
    return this.formGroup.controls['volumeAndTruckload'];
  }
  get timeCritical() {
    return this.formGroup.controls['timeCritical'];
  }

  /**
   * Check for the user's access to different quote types in the form; disable as necessary.
   */
  private initFormScope() {
    const hasLtl = this.auth.isInScope('LTLRATEQOT');
    const hasTc = this.auth.isInScope('TIMECRIT');
    const hasVtl = this.auth.isInScope('VTLQUOTE');

    if (!hasLtl) {
      this.ltl.setValue(false);
      this.ltl.disable({ emitEvent: false });
      this.showApplicationAccessFeedback = true;
    }

    if (!hasTc) {
      this.timeCritical.setValue(false);
      this.timeCritical.disable({ emitEvent: false });
      this.showApplicationAccessFeedback = true;
    }

    if (!hasVtl) {
      this.volumeAndTruckload.setValue(false);
      this.volumeAndTruckload.disable({ emitEvent: false });
      this.showApplicationAccessFeedback = true;
    }
  }
}
