import { Component, OnInit, OnDestroy, OnChanges, SimpleChanges } from '@angular/core';
import { RequestorService } from '../../services/requestor.service';
import { RateQuoteFormSection, RateQuoteType } from '../../models';
import { takeUntil, map } from 'rxjs/operators';
import {
  FormService,
  SnackbarService,
  UtilService,
  AuthService,
  MyEstesFormatters,
  Masks
} from 'common';
import { MatCheckboxChange } from '@angular/material';
import { AddressSelectorDialogService } from 'common';
import { forkJoin, Observable } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { RateQuoteTypeService } from '../../services/rate-quote-type.service';
import { RequestorForm } from '../../models/requestor-form.model';
import { RateQuoteService } from '../../services/rate-quote.service';

@Component({
  selector: 'app-requestor',
  templateUrl: './requestor.component.html',
  styleUrls: ['./requestor.component.scss']
})
export class RequestorComponent extends RateQuoteFormSection
  implements OnInit, OnDestroy, OnChanges {
  loadingAccountInfo = false;
  ltlOnly$: Observable<boolean> = this.rateQuote.rateQuoteType$.pipe(map(rwt => rwt.isLTLOnly));
  phoneMask = Masks.phone;
  constructor(
    private requestorService: RequestorService,
    public formService: FormService,
    private addressDialog: AddressSelectorDialogService,
    private snackbar: SnackbarService,
    private utilService: UtilService,
    protected auth: AuthService,
    private rateQuote: RateQuoteTypeService,
    private rateQuoteService: RateQuoteService
  ) {
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
      this.requestorService.resetForm(changes.quote.currentValue);
    }
  }

  // Invoked by parent class in ngOnInit()
  initForm() {
    this.requestorService.formModel$.pipe(takeUntil(this.stop$)).subscribe(form => {
      // Adding a setTimeout because of a strange behavior with this.formGroup not properly
      // resetting when it changes as it is passed from the requestorService.
      if (!this.formGroup) {
        this.formGroup = form;
      } else {
        setTimeout(
          function() {
            this.formGroup = form;
          }.bind(this)
        );
      }
    });
    this.rateQuote.rateQuoteType$.pipe(takeUntil(this.stop$)).subscribe(next => {
      this.intializeFormState(next);
    });
  }

  onAddressSourceChecked(checkbox: MatCheckboxChange) {
    if (checkbox.checked) {
      this.setAccountInfoToForm();
    }
  }

  private setAccountInfoToForm() {
    this.loadingAccountInfo = true;

    const token = this.auth.getAuthToken();
    const accountCode = token.accountCode;

    forkJoin(
      this.utilService.getProfileInfo(),
      this.utilService.getAccountInfo(accountCode)
    ).subscribe(
      next => {
        const profile = next[0];
        const account = next[1];

        this.contactName.setValue(profile.firstName + ' ' + profile.lastName);
        this.contactPhone.setValue(MyEstesFormatters.formatPhone(account.phone));
        this.contactEmail.setValue(profile.email);
      },
      err => {
        this.loadingAccountInfo = false;
        let msg = `An unexpected error has occurred.  Please try again.`;
        if (err instanceof HttpErrorResponse) {
          if (err.status === 400) {
            msg = err.error[0].message;
          } else if (err.status === 500) {
            msg = err.error.message;
          }
        }
        this.snackbar.error(msg);
      },
      () => (this.loadingAccountInfo = false)
    );
  }

  private intializeFormState(rateQuoteType: RateQuoteType) {
    RequestorForm.initValidators(this.formGroup, rateQuoteType, this.isRateEstimate);
  }

  openAddressDialog() {
    this.addressDialog.openAddressDialog().subscribe(next => {
      if (next) {
        this.contactName.setValue(`${next.firstName} ${next.lastName}`);
        this.contactPhone.setValue(MyEstesFormatters.formatPhone(next.phone));
        this.contactEmail.setValue(next.email);
        this.contactExtension.setValue(next.phoneExt);
      }
    });
  }

  get accountCode() {
    return this.formGroup.controls['accountCode'];
  }
  get contactName() {
    return this.formGroup.controls['contactName'];
  }
  get contactPhone() {
    return this.formGroup.controls['contactPhone'];
  }
  get contactEmail() {
    return this.formGroup.controls['contactEmail'];
  }
  get contactExtension() {
    return this.formGroup.controls['contactExtension'];
  }
  get role() {
    return this.formGroup.controls['role'];
  }
  get terms() {
    return this.formGroup.controls['terms'];
  }
  get discount() {
    return this.formGroup.controls['discount'];
  }
}
