import {
  Component,
  OnInit,
  ViewChildren,
  QueryList,
  ElementRef,
  ViewChild,
  AfterViewInit,
  OnDestroy,
  OnChanges,
  SimpleChanges
} from '@angular/core';
import { FormGroup, FormBuilder, FormArray, Validators, AbstractControl } from '@angular/forms';
import {
  FormService,
  Masks,
  patternValidator,
  RegEx,
  MessageConstants,
  MyEstesValidators,
  DialogService
} from 'common';
import { CommodityComponent } from '../commodity/commodity.component';
import { goodsTypes } from './goods-types';
import { classTypes } from './class-types';
import { takeUntil } from 'rxjs/operators';
import { fromEvent, Subscription, Subject } from 'rxjs';
import { CommodityFormService } from '../../services/commodity-form.service';
import { BolSourceFormService } from '../../services/bol-source-form.service';
import {
  DocumentTypeEnum,
  BolSection,
  BillOfLading,
  Commodity,
  CommodityLibraryViewModel,
  CommodityInformationForm,
  CommodityInformation
} from '../../models';
import { environment } from '../../../../../environments/environment';
import { CommodityLibraryModalComponent } from '../commodity-library-modal/commodity-library-modal.component';
import { QuoteDetailsFormService } from '../../services/quote-details.service';

export const hasRequiredField = (abstractControl: AbstractControl): boolean => {
  if (abstractControl.validator) {
    const validator = abstractControl.validator({} as AbstractControl);
    if (validator && validator.required) {
      return true;
    }
  }
  if (abstractControl['controls']) {
    for (const controlName in abstractControl['controls']) {
      if (abstractControl['controls'][controlName]) {
        if (hasRequiredField(abstractControl['controls'][controlName])) {
          return true;
        }
      }
    }
  }
  return false;
};

@Component({
  selector: 'app-commodity-information',
  templateUrl: './commodity-information.component.html',
  styleUrls: ['./commodity-information.component.scss']
})
export class CommodityInformationComponent extends BolSection
  implements OnInit, OnDestroy, OnChanges {
  readonly CMS_URL = environment.cmsUrl;
  readonly MAX_COMMODITIES = 30;
  private stop$ = new Subject<boolean>();
  formGroup: FormGroup;
  commodities: FormArray;
  commodityFormSub: Subscription;
  phoneMask = Masks.phone;
  bolSourceFormSub: Subscription;

  constructor(
    public formService: FormService,
    private commodityFormService: CommodityFormService,
    private dialog: DialogService,
    private quoteFormService: QuoteDetailsFormService
  ) {
    super();
  }

  ngOnInit() {
    this.commodityFormSub = this.commodityFormService.commodityForm$.subscribe(
      commodityInformationForm => {
        this.formGroup = commodityInformationForm;
        this.commodities = commodityInformationForm.controls['commodityList'] as FormArray;
        this.onHazmatChanged(null);
      }
    );

    this.quoteFormService.rateQuote$.pipe(takeUntil(this.stop$)).subscribe(next => {
      if (next) {
        let incCommodityList: Array<Commodity> = [];

        if (next.commodities && next.commodities.length) {
          incCommodityList = next.commodities.reduce((accum, curr) => {
            const c = new Commodity();

            c.goodsType = { code: curr.commodity.pieceType, description: null };
            c.description = curr.commodity.description;
            c.goodsWeight = curr.commodity.weight.toString();
            c.goodsUnit = curr.commodity.pieces.toString();
            c.shipmentClass = { code: curr.commodity.shipClass.toString(), description: null };
            accum.push(c);
            return accum;
          }, []);
        }

        const commodityInfo = new CommodityInformation();
        commodityInfo.commodityList = incCommodityList;
        commodityInfo.contactName = this.contactName.value;
        commodityInfo.phone = this.phone.value;
        commodityInfo.phoneExt = this.phoneExt.value;
        commodityInfo.specialIns = this.specialIns.value;
        commodityInfo.totalCube = this.totalCube.value;

        this.commodityFormService.resetForm(commodityInfo);
      }
    });
  }

  ngOnDestroy() {
    if (this.commodityFormSub) {
      this.commodityFormSub.unsubscribe();
    }
    if (this.bolSourceFormSub) {
      this.bolSourceFormSub.unsubscribe();
    }
    this.stop$.next(true);
    this.stop$.unsubscribe();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.draft && changes.draft.currentValue) {
      this.commodityFormService.resetForm(
        (changes.draft.currentValue as BillOfLading).commodityInfo
      );
    }
  }

  openCommodityLibrary() {
    this.dialog
      .prompt(CommodityLibraryModalComponent, null, 'estes-app')
      .subscribe((next: CommodityLibraryViewModel) => {
        if (next) {
          const c = new Commodity();
          c.hazmat = next.hazmat.toUpperCase() === 'Y' ? true : false;
          c.goodsUnit = next.goodsQuantity.toString();
          c.goodsType = { code: next.goodsType, description: '' };
          c.description = next.description;
          c.goodsWeight = next.weight.toString();
          c.nmfc = next.nmfc;
          c.nmfcExt = next.nmfcSub;
          c.shipmentClass = { code: next.shipClass, description: '' };

          let populated = false;
          for (let commodityForm of this.commodityList.controls) {
            if (this.isBlankCommodityForm(commodityForm as FormGroup)) {
              this.populateCommodityForm(commodityForm as FormGroup, c);
              populated = true;
              break;
            }
          }

          if (!populated) {
            this.addCommodity(c);
          }
        }
      });
  }
  private populateCommodityForm(fg: FormGroup, c: Commodity) {
    const commodityId = fg.controls['commodityId'];
    const hazmat = fg.controls['hazmat'];
    const goodsUnit = fg.controls['goodsUnit'];
    const goodsType = fg.controls['goodsType'];
    const goodsWeight = fg.controls['goodsWeight'];
    const shipmentClass = fg.controls['shipmentClass'];
    const numberOfPackage = fg.controls['numberOfPackage'];
    const packageType = fg.controls['packageType'];
    const nmfc = fg.controls['nmfc'];
    const nmfcExt = fg.controls['nmfcExt'];
    const description = fg.controls['description'];

    if (c.commodityId) {
      commodityId.setValue(c.commodityId);
    }
    if (c.hazmat) {
      hazmat.setValue(c.hazmat);
      description.setValidators([MyEstesValidators.required, Validators.maxLength(512)]);
    }
    if (c.goodsUnit) {
      goodsUnit.setValue(c.goodsUnit);
    }
    if (c.goodsType) {
      goodsType.setValue(c.goodsType);
    }
    if (c.goodsWeight) {
      goodsWeight.setValue(c.goodsWeight);
    }
    if (c.shipmentClass) {
      shipmentClass.setValue(c.shipmentClass);
    }
    if (c.numberOfPackage) {
      numberOfPackage.setValue(c.numberOfPackage);
    }
    if (c.packageType) {
      packageType.setValue(c.packageType);
    }
    if (c.nmfc) {
      nmfc.setValue(c.nmfc);
    }
    if (c.nmfcExt) {
      nmfcExt.setValue(c.nmfcExt);
    }
    if (c.description) {
      description.setValue(c.description);
    }
  }

  private isBlankCommodityForm(fg: FormGroup) {
    const commodityId = fg.controls['commodityId'].value;
    const hazmat = fg.controls['hazmat'].value;
    const goodsUnit = fg.controls['goodsUnit'].value;
    const goodsType = fg.controls['goodsType'].value;
    const goodsWeight = fg.controls['goodsWeight'].value;
    const shipmentClass = fg.controls['shipmentClass'].value;
    const numberOfPackage = fg.controls['numberOfPackage'].value;
    const packageType = fg.controls['packageType'].value;
    const nmfc = fg.controls['nmfc'].value;
    const nmfcExt = fg.controls['nmfcExt'].value;
    const description = fg.controls['description'].value;

    return !(
      commodityId ||
      hazmat ||
      goodsUnit ||
      goodsType ||
      goodsWeight ||
      shipmentClass ||
      numberOfPackage ||
      packageType ||
      nmfc ||
      nmfcExt ||
      description
    );
  }

  get commodityList() {
    return this.formGroup.controls['commodityList'] as FormArray;
  }
  get commodityCount() {
    return this.commodityList.length;
  }
  get shipmentWeight() {
    return this.commodityList.controls.reduce((prev, curr) => {
      const weight = +(curr as FormGroup).controls['goodsWeight'].value;
      if (!isNaN(weight)) {
        prev += weight;
      }
      return prev;
    }, 0);
  }
  get contactName() {
    return this.formGroup.controls['contactName'];
  }
  get phone() {
    return this.formGroup.controls['phone'];
  }
  get phoneExt() {
    return this.formGroup.controls['phoneExt'];
  }
  get totalCube() {
    return this.formGroup.controls['totalCube'];
  }
  get specialIns() {
    return this.formGroup.controls['specialIns'];
  }

  onHazmatChanged(isHazmat: boolean | null) {
    let hasHazmat = isHazmat ? isHazmat.valueOf() : false;
    if (!hasHazmat) {
      for (const fg of this.commodityList.controls) {
        const hazmat = (fg as FormGroup).controls['hazmat'].value;
        if (!!hazmat) {
          hasHazmat = true;
        }
      }
    }

    if (hasHazmat) {
      this.contactName.setValidators([MyEstesValidators.required, Validators.maxLength(30)]);
      this.phone.setValidators([
        MyEstesValidators.required,
        patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone)
      ]);
    } else {
      this.contactName.setValidators([Validators.maxLength(30)]);
      this.phone.setValidators([
        patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone)
      ]);
    }

    this.contactName.updateValueAndValidity();
    this.phone.updateValueAndValidity();
  }

  addCommodity(c?: Commodity) {
    this.commodityFormService.addCommodity(c);
  }

  deleteCommodity(index: number) {
    this.commodityFormService.deleteCommodity(index);
  }
}
