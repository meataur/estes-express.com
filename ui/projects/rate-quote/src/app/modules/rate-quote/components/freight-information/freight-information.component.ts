import { Component, OnInit, OnDestroy, SimpleChanges, OnChanges } from '@angular/core';
import { FreightInformationService } from '../../services/freight-information.service';
import { RateQuoteFormSection, RateQuoteType, FreightInfoForm, FoodWarehouse } from '../../models';
import { takeUntil } from 'rxjs/operators';
import { FormService, AuthService, DialogService } from 'common';
import { RateQuoteTypeService } from '../../services/rate-quote-type.service';
import { RequestorForm } from '../../models/requestor-form.model';
import { Observable } from 'rxjs';
import { RateQuoteService } from '../../services/rate-quote.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AdditionalCargoLiabilityModalComponent } from '../additional-cargo-liability-modal/additional-cargo-liability-modal.component';
import { environment } from '../../../../../environments/environment';

@Component({
  selector: 'app-freight-information',
  templateUrl: './freight-information.component.html',
  styleUrls: ['./freight-information.component.scss']
})
export class FreightInformationComponent extends RateQuoteFormSection
  implements OnInit, OnDestroy, OnChanges {
  readonly rulesTariffUrl = `${environment.cmsUrl}/resources/fees-and-surcharges/rules-tariff`;

  ltlOnly: boolean;
  ltlAndTcOnly: boolean;
  tcOnly: boolean;
  foodWarehouse$: Observable<Array<FoodWarehouse>>;

  constructor(
    private freightService: FreightInformationService,
    public formService: FormService,
    private rateQuoteTypeService: RateQuoteTypeService,
    private rateQuoteService: RateQuoteService,
    protected auth: AuthService,
    private dialog: DialogService
  ) {
    super(auth);
  }

  ngOnInit() {
    super.ngOnInit();

    this.foodWarehouse$ = this.rateQuoteService.getFoodWarehouses();
  }

  ngOnDestroy() {
    super.ngOnDestroy();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.quote && changes.quote.currentValue) {
      this.freightService.resetForm(changes.quote.currentValue);
    }
  }

  openAdditionalLibilityCoverageModal() {
    this.dialog.prompt(AdditionalCargoLiabilityModalComponent, null);
  }

  // Invoked by parent class in ngOnInit()
  initForm() {
    this.freightService.formModel$.pipe(takeUntil(this.stop$)).subscribe(form => {
      this.formGroup = form;

      FreightInfoForm.initValidators(
        this.formGroup,
        this.ltlOnly,
        this.ltlAndTcOnly,
        this.tcOnly,
        this.isRateEstimate
      );
    });

    this.rateQuoteTypeService.rateQuoteType$.pipe(takeUntil(this.stop$)).subscribe(next => {
      this.intializeFormState(next);
    });
  }

  private intializeFormState(rateQuoteType: RateQuoteType) {
    this.ltlOnly = rateQuoteType.isLTLOnly;
    this.ltlAndTcOnly = rateQuoteType.isLTLandTCOnly;
    this.tcOnly = rateQuoteType.isTCOnly;

    FreightInfoForm.initValidators(
      this.formGroup,
      rateQuoteType.isLTLOnly,
      rateQuoteType.isLTLandTCOnly,
      rateQuoteType.isTCOnly,
      this.isRateEstimate
    );
  }

  get showLinearFeet() {
    return !(this.ltlAndTcOnly || this.ltlOnly || this.tcOnly || this.isRateEstimate);
  }

  get declaredValue() {
    return this.formGroup.controls['declaredValue'];
  }
  get declaredValueWaived() {
    return this.formGroup.controls['declaredValueWaived'];
  }
  get deliversAtFoodWarehouse() {
    return this.formGroup.controls['deliversAtFoodWarehouse'];
  }
  get foodWarehouse() {
    return this.formGroup.controls['foodWarehouse'];
  }
  get stackable() {
    return this.formGroup.controls['stackable'];
  }
  get linearFeet() {
    return this.formGroup.controls['linearFeet'];
  }
  get comments() {
    return this.formGroup.controls['comments'];
  }
}
