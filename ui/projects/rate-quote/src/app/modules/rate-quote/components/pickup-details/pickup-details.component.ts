import { Component, OnInit, OnDestroy, SimpleChanges, OnChanges } from '@angular/core';
import { RateQuoteFormSection, RateQuoteType, PickupDetailsForm } from '../../models';
import { PickupDetailsService } from '../../services/pickup-details.service';
import { takeUntil } from 'rxjs/operators';
import { FormService, AuthService } from 'common';
import { RateQuoteTypeService } from '../../services/rate-quote-type.service';
import { RateQuoteService } from '../../services/rate-quote.service';
import { NgForm, FormGroupDirective, FormControl } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material';

// See https://stackoverflow.com/a/51606362
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    return control.parent.hasError('invalidPickupRange');
  }
}

@Component({
  selector: 'app-pickup-details',
  templateUrl: './pickup-details.component.html',
  styleUrls: ['./pickup-details.component.scss']
})
export class PickupDetailsComponent extends RateQuoteFormSection
  implements OnInit, OnDestroy, OnChanges {
  matcher = new MyErrorStateMatcher();
  ltlOnly: boolean;

  // myFilter = (d: Date): boolean => {
  //   const day = d.getDay();
  //   // Prevent Saturday and Sunday from being selected.
  //   return day !== 0 && day !== 6;
  // }

  constructor(
    private pickupDetailsService: PickupDetailsService,
    public formService: FormService,
    private rateQuoteType: RateQuoteTypeService,
    private rateQuoteService: RateQuoteService,
    protected auth: AuthService
  ) {
    super(auth);
  }

  ngOnInit() {
    super.ngOnInit();

    this.rateQuoteType.rateQuoteType$.pipe(takeUntil(this.stop$)).subscribe(next => {
      this.intializeFormState(next);
    });
  }

  ngOnDestroy() {
    super.ngOnDestroy();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.quote && changes.quote.currentValue) {
      this.pickupDetailsService.resetForm(changes.quote.currentValue);
    }
  }

  // Invoked by parent class in ngOnInit()
  initForm() {
    this.pickupDetailsService.formModel$.pipe(takeUntil(this.stop$)).subscribe(form => {
      this.formGroup = form;
    });
  }

  private intializeFormState(rateQuoteType: RateQuoteType) {
    this.ltlOnly = this.rateQuoteService.isLTLOnly(rateQuoteType);
    PickupDetailsForm.initValidators(this.formGroup, this.ltlOnly);
  }

  get pickupDate() {
    return this.formGroup.controls['pickupDate'];
  }
  get availableByHour() {
    return this.formGroup.controls['availableByHour'];
  }
  get availableByMinutes() {
    return this.formGroup.controls['availableByMinutes'];
  }
  get availableByAmPm() {
    return this.formGroup.controls['availableByAmPm'];
  }
  get closesByHour() {
    return this.formGroup.controls['closesByHour'];
  }
  get closesByMinutes() {
    return this.formGroup.controls['closesByMinutes'];
  }
  get closesByAmPm() {
    return this.formGroup.controls['closesByAmPm'];
  }
  get today() {
    return new Date();
  }
  get thirtyDaysAway() {
    const thirtyDaysAway = new Date(this.today);
    thirtyDaysAway.setDate(thirtyDaysAway.getDate() + 30);

    return thirtyDaysAway;
  }
}
