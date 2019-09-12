import {
  Component,
  OnInit,
  OnDestroy,
  SimpleChanges,
  OnChanges,
  ChangeDetectionStrategy
} from '@angular/core';
import { RateQuoteFormSection, RateQuoteType } from '../../models';
import { CommoditiesService } from '../../services/commodities.service';
import { takeUntil, share, shareReplay } from 'rxjs/operators';
import { FormArray, FormGroup } from '@angular/forms';
import { environment } from '../../../../../environments/environment';
import { Commodity } from '../../models';
import { DialogService, AuthService } from 'common';
import { RateQuoteTypeService } from '../../services/rate-quote-type.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-commodity-list',
  templateUrl: './commodity-list.component.html',
  styleUrls: ['./commodity-list.component.scss']
})
export class CommodityListComponent extends RateQuoteFormSection
  implements OnInit, OnDestroy, OnChanges {
  readonly CMS_URL = environment.cmsUrl;
  commodityCount = 0;
  rateQuoteType$: Observable<RateQuoteType>;

  constructor(
    private commodityService: CommoditiesService,
    private dialog: DialogService,
    public rateQuoteTypeService: RateQuoteTypeService,
    protected auth: AuthService
  ) {
    super(auth);
  }

  ngOnInit() {
    super.ngOnInit();

    this.rateQuoteType$ = this.rateQuoteTypeService.rateQuoteType$.pipe(shareReplay(1));
  }

  ngOnDestroy() {
    super.ngOnDestroy();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.quote && changes.quote.currentValue) {
      this.commodityService.resetForm(changes.quote.currentValue);
    }
  }

  // Invoked by parent class in ngOnInit()
  initForm() {
    this.commodityService.formModel$.pipe(takeUntil(this.stop$)).subscribe(form => {
      this.formGroup = form;

      this.commodityCount = (form.controls['commodities'] as FormArray).controls.length;
    });
  }

  openCommodityLibrary() {
    this.dialog.openCommodityLibrary().subscribe(next => {
      if (next) {
        const c = new Commodity();
        c.description = next.description;
        if (next.dimensions) {
          c.dimensions.height = next.dimensions.height;
          c.dimensions.length = next.dimensions.length;
          c.dimensions.width = next.dimensions.width;
        }
        c.pieces = next.goodsQuantity;
        c.pieceType = next.goodsType;
        c.shipClass = +next.shipClass;
        c.weight = next.weight;

        let populated = false;
        for (const commodityForm of (this.formGroup.controls['commodities'] as FormArray)
          .controls) {
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
    const description = fg.controls['description'];
    const length = fg.controls['length'];
    const width = fg.controls['width'];
    const height = fg.controls['height'];
    const pieceType = fg.controls['pieceType'];
    const pieces = fg.controls['pieces'];
    const shipClass = fg.controls['shipClass'];
    const weight = fg.controls['weight'];

    if (c.dimensions.length) {
      length.setValue(c.dimensions.length);
    }
    if (c.dimensions.width) {
      width.setValue(c.dimensions.width);
    }
    if (c.dimensions.height) {
      height.setValue(c.dimensions.height);
    }
    if (c.pieceType) {
      pieceType.setValue(c.pieceType);
    }
    if (c.pieces) {
      pieces.setValue(c.pieces);
    }
    if (c.shipClass) {
      shipClass.setValue(c.shipClass.toString());
    }
    if (c.weight) {
      weight.setValue(c.weight);
    }
    if (c.description) {
      description.setValue(c.description);
    }
  }

  private isBlankCommodityForm(fg: FormGroup) {
    const description = fg.controls['description'].value;
    const length = fg.controls['length'].value;
    const width = fg.controls['width'].value;
    const height = fg.controls['height'].value;
    const pieceType = fg.controls['pieceType'].value;
    const pieces = fg.controls['pieces'].value;
    const shipClass = fg.controls['shipClass'].value;
    const weight = fg.controls['weight'].value;

    return !(
      length ||
      width ||
      height ||
      pieceType ||
      pieces ||
      shipClass ||
      weight ||
      description
    );
  }

  deleteCommodity(index) {
    this.commodityService.deleteCommodity(index);
  }

  addCommodity(c?: Commodity) {
    this.commodityService.addCommodity(c);
  }

  // get commodities() {
  //   console.log(new Date().getMilliseconds());
  //   return this.formGroup.controls['commodities'] as FormArray;
  // }

  // get commodityCount() {
  //   return this.formGroup.length;
  // }

  get MAX_COMMODITIES() {
    return this.commodityService.MAX_COMMODITIES;
  }
  get shipmentWeight() {
    return (this.formGroup.controls['commodities'] as FormArray).controls.reduce((prev, curr) => {
      const weight = +(curr as FormGroup).controls['weight'].value;
      if (!isNaN(weight)) {
        prev += weight;
      }
      return prev;
    }, 0);
  }
}
