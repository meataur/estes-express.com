import { Component, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import {
  FormService,
  mustBeTrueValidator,
  DialogService,
  UtilService,
  SnackbarService
} from 'common';
import { TosModalComponent } from '../tos-modal/tos-modal.component';
import { Subscription, Subject, Observable, of } from 'rxjs';
import { QuoteDetailsFormService } from '../../services/quote-details.service';
import { BolSection, BillOfLading } from '../../models';
import { BolService } from '../../services/bol.service';
import { tap, map, catchError } from 'rxjs/operators';
import { environment } from '../../../../../environments/environment';

@Component({
  selector: 'app-quote-details',
  templateUrl: './quote-details.component.html',
  styleUrls: ['./quote-details.component.scss']
})
export class QuoteDetailsComponent extends BolSection implements OnInit, OnChanges {
  readonly rateQuoteHistoryUrl = `${environment.appBaseUrl}/rate-quote/rate-quote/history`;
  readonly terminalLookupUrl = `${environment.appBaseUrl}/terminal-lookup`;
  stop$ = new Subject<boolean>();
  formSub: Subscription;
  formGroup: FormGroup;
  loadingQuote: boolean;
  get hasMexicoCountry(): boolean {
    return this.quoteDetailsService.hasMexicoCountry;
  }
  get hasExpiredQuote(): boolean {
    return this.quoteDetailsService.hasExpiredQuote;
  }

  constructor(
    private fb: FormBuilder,
    public formService: FormService,
    private dialogService: DialogService,
    private quoteDetailsService: QuoteDetailsFormService,
    private bolService: BolService,
    private utilService: UtilService,
    private snackbar: SnackbarService
  ) {
    super();
  }

  ngOnInit() {
    this.formSub = this.quoteDetailsService.quoteDetailsForm$.subscribe(fg => {
      this.stop$.next(true);
      this.formGroup = fg;

      this.quoteNumber.setAsyncValidators(this.validateQuoteNumberInput.bind(this));
    });
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.draft && changes.draft.currentValue) {
      this.quoteDetailsService.resetForm((changes.draft.currentValue as BillOfLading).generalInfo);
    }
  }

  openTos() {
    this.dialogService.prompt(TosModalComponent, null);
  }

  private validateQuoteNumberInput(control: AbstractControl) {
    return !!control.value
      ? this.validateQuoteId(control.value).pipe(
          map(res => {
            this.loadingQuote = false;

            const quote = res ? res : null;
            if (quote) {
              this.snackbar.success(`Rate quote retrieved successfully.`);

              if (quote.guaranteed) {
                this.guaranteed.setValue(true);
              } else {
                this.guaranteed.setValue(false);
              }
              this.quoteDetailsService.onQuoteNumberChanged(
                quote,
                this.utilService.isDateExpired(quote.expireDate, quote.expireTime)
              );

              return null;
            }
            // if (!this.utilService.isDateExpired(quote.expireDate, quote.expireTime)) {
            // } else {
            //   this.quoteDetailsService.onQuoteNumberChanged(null);
            //   return { invalidQuote: true };
            // }
          }),
          catchError(() => {
            this.loadingQuote = false;
            this.quoteDetailsService.onQuoteNumberChanged(null);
            return of({ invalidQuote: true });
          })
        )
      : of(null);
  }

  private validateQuoteId(quoteId: string): Observable<any> {
    this.loadingQuote = true;
    return this.bolService.getQuoteById(quoteId);
  }

  get quoteNumber() {
    return this.formGroup.controls['quoteNumber'];
  }
  get guaranteed() {
    return this.formGroup.controls['guaranteed'];
  }
  get tosChecked() {
    return this.formGroup.controls['tosChecked'];
  }
}
