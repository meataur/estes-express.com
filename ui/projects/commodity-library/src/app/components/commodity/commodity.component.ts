import { Component, OnInit } from '@angular/core';
import { Subscription, zip, Observable } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { CommodityActionEnum, Commodity } from '../../models';
import { FormGroup, FormBuilder } from '@angular/forms';
import { CommodityService } from '../../services/commodity.service';
import { AuthService, PromoService, LeftNavigationService, UtilService, PackageType, SnackbarService, FormService, validateFormFields, FeedbackTypes } from 'common';
import { CommodityFormModel } from '../../models/commodity-form.model';
import 'hammerjs';

@Component({
  selector: 'app-commodity',
  templateUrl: './commodity.component.html',
  styleUrls: ['./commodity.component.scss']
})
export class CommodityComponent implements OnInit {
  private routeSub: Subscription;
  private action: CommodityActionEnum;

  formGroup: FormGroup;

  classes$: Observable<Array<string>>;
  packageTypes$: Observable<Array<PackageType>>;
  loadingCommodity: boolean;
  loading: boolean;
  errorMessages: [FeedbackTypes, string];

  get componentHeaderText() {
    switch (this.action) {
      case CommodityActionEnum.Create:
        return `Add Commodity`;
      case CommodityActionEnum.Edit:
        return `Edit Commodity`;
    }
  }
  get submitButtonText() {
    switch (this.action) {
      case CommodityActionEnum.Create:
        return `Save`;
      case CommodityActionEnum.Edit:
        return `Save`;
    }
  }

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private utilService: UtilService,
    private commodityService: CommodityService,
    private snackbar: SnackbarService,
    private fb: FormBuilder,
    public formService: FormService,
    private promoService: PromoService,
	private leftNavigationService: LeftNavigationService,
	private authService: AuthService
  ) {}

  ngOnInit() {
    this.classes$ = this.utilService.getClasses();
    this.packageTypes$ = this.utilService.getPackageTypes();
    this.leftNavigationService.setNavigation(`My Estes`, `/menus/account`);
    this.promoService.setAppId('commodity-library');
    this.promoService.setAppState('authenticated');
    
    this.routeSub = zip(this.route.data, this.route.paramMap).subscribe(next => {
      const data = next[0];
      const paramMap = next[1];
      this.action = data['action'];
      this.initForm(paramMap.get('id'));
    });
 	this.authService.authStateSet.subscribe((authState: string) => {
      if ('unauthenticated' === authState) {
        this.router.navigate(['login']);
	  }
    });
  }

  onSubmit() {
    if (this.formGroup.valid) {
      switch (this.action) {
        case CommodityActionEnum.Create:
          this.createCommodity();
          break;
        case CommodityActionEnum.Edit:
          this.editCommodity();
          break;
      }
    } else {
      validateFormFields(this.formGroup);
      this.snackbar.validationError();
    }
  }

  get hazmat() {
    return this.formGroup.controls['hazmat'];
  }
  get id() {
    return this.formGroup.controls['id'];
  }
  get goodsQuantity() {
    return this.formGroup.controls['goodsQuantity'];
  }
  get goodsType() {
    return this.formGroup.controls['goodsType'];
  }
  get description() {
    return this.formGroup.controls['description'];
  }
  get weight() {
    return this.formGroup.controls['weight'];
  }
  get nmfc() {
    return this.formGroup.controls['nmfc'];
  }
  get nmfcSub() {
    return this.formGroup.controls['nmfcSub'];
  }
  get shipClass() {
    return this.formGroup.controls['shipClass'];
  }

  private createCommodity() {
    this.loading = true;
    this.commodityService.createCommodity(this.populateModel()).subscribe(
      next => {
        this.snackbar.success(`Commodity created successfully.`);
        this.router.navigate(['/']);
      },
      err => {
        this.loading = false;
        if (!err) err = `An error occurred while creating the Commodity.  Please try again.`;
        this.errorMessages = ['error', err];
      },
      () => (this.loading = false)
    );
  }

  private editCommodity() {
    this.loading = true;
    this.commodityService.editCommodity(this.populateModel()).subscribe(
      next => {
        this.snackbar.success(`Commodity edited successfully.`);
        this.router.navigate(['/']);
      },
      err => {
        this.loading = false;
        if (!err) err = `An error occurred while editing the Commodity.  Please try again.`;
        this.errorMessages = ['error', err];
      },
      () => (this.loading = false)
    );
  }

  private populateModel(): Commodity {
    const c = new Commodity();

    c.hazmat = this.hazmat.value === true ? 'Y' : 'N';
    c.id = this.id.value;
    c.goodsQuantity = this.goodsQuantity.value;
    c.goodsType = this.goodsType.value;
    c.description = this.description.value;
    c.weight = this.weight.value;
    c.nmfc = this.nmfc.value;
    c.nmfcSub = this.nmfcSub.value;
    c.shipClass = this.shipClass.value;

    return c;
  }

  private initForm(commodityId: string) {
    this.formGroup = this.fb.group(new CommodityFormModel());
    switch (this.action) {
      case CommodityActionEnum.Create:
        break;
      case CommodityActionEnum.Edit:
        this.initEditCommodityForm(commodityId);
        break;
    }
  }

  private initEditCommodityForm(commodityId: string) {
    this.loadingCommodity = true;
    this.commodityService.getCommodity(commodityId).subscribe(
      next => this.populateForm(next),
      err => {
        this.loadingCommodity = false;
        this.snackbar.error('An error occurred while retrieving the Commodity.  Please try again.');
      },
      () => (this.loadingCommodity = false)
    );
  }

  private populateForm(c: Commodity) {
    this.formGroup = this.fb.group(new CommodityFormModel(c));
  }
}
