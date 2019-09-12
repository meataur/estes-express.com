import { Injectable } from "@angular/core";
import {
  CommodityForm,
  RateQuoteFormSectionService,
  CommodityItemForm,
  Commodity,
  RatingRequest,
  RateQuote
} from "../models";
import { FormBuilder, FormArray, FormGroup } from "@angular/forms";
import { BehaviorSubject } from "rxjs";
import { RateQuoteTypeService } from "./rate-quote-type.service";

@Injectable({
  providedIn: "root"
})
export class CommoditiesService extends RateQuoteFormSectionService {
  readonly MAX_COMMODITIES = 30;
  private changeDetectSource = new BehaviorSubject<boolean>(true);
  changeDetect$ = this.changeDetectSource.asObservable();
  initValueChangeWatchers() {}
  initFormState(fg: FormGroup) {
    const commodities = fg.controls["commodities"] as FormArray;
    if (!commodities.controls.length) {
      this.addCommodity();
    }
  }
  valid(): boolean {
    let valid = this.isValid(this.formModelSource.getValue());

    let rqt = this.rateQuoteTypeService.getRateQuoteType();
    const isNotVTL = rqt.volumeAndTruckload === false;
    const isNotTC = rqt.timeCritical === false;

    if (isNotVTL && isNotTC) {
      const shipmentWeightValidity = this.validateShipmentWeight(11999);
      valid = valid && shipmentWeightValidity;
    } else if (isNotVTL && !isNotTC) {
      const shipmentWeightValidity = this.validateShipmentWeight(42500);
      valid = valid && shipmentWeightValidity;
    }
    this.changeDetectSource.next(true);
    return valid;
  }

  validateShipmentWeight(maxWeight: number): boolean {
    const currentForm = this.formModelSource.getValue();
    const currentCommodities = currentForm.get("commodities") as FormArray;
    const w = currentCommodities.controls.reduce((prev, curr) => {
      const weight = +(curr as FormGroup).controls["weight"].value;
      if (!isNaN(weight)) {
        prev += weight;
      }
      return prev;
    }, 0);
    return w <= maxWeight;
  }

  constructor(
    fb: FormBuilder,
    private rateQuoteTypeService: RateQuoteTypeService
  ) {
    super(fb);
    super.initFormSource(CommodityForm);
  }

  addCommodity(c?: Commodity) {
    const currentForm = this.formModelSource.getValue();
    const currentCommodities = currentForm.get("commodities") as FormArray;

    if (currentCommodities.length < this.MAX_COMMODITIES) {
      currentCommodities.push(this.fb.group(new CommodityItemForm(c)));
      this.formModelSource.next(currentForm);
    }
  }

  deleteCommodity(index: number) {
    const currentTeam = this.formModelSource.getValue();
    const currentCommodities = currentTeam.get("commodities") as FormArray;

    if (currentCommodities.length > 1) {
      currentCommodities.removeAt(index);
      this.formModelSource.next(currentTeam);
    }
  }

  resetForm(q?: RateQuote) {
    const newForm = this.fb.group(
      new CommodityForm(q ? q.commodities : [], this.fb)
    );

    this.formModelSource.next(newForm);
  }

  populateModel(ratingRequest: RatingRequest, isRateEstimate = false) {
    const form = this.formModelSource.getValue().getRawValue();

    ratingRequest.commodities = [];
    const commodities = form.commodities as Array<any>;

    if (commodities.length) {
      if (isRateEstimate) {
        for (const c of commodities) {
          const commodity = new Commodity();
          commodity.description = c.description;
          commodity.shipClass = c.shipClass;
          commodity.weight = c.weight;
          delete commodity.dimensions;

          ratingRequest.commodities.push(commodity);
        }

        return;
      }

      for (const c of commodities) {
        const commodity = new Commodity();
        commodity.description = c.description;
        commodity.dimensions.height = c.height;
        commodity.dimensions.length = c.length;
        commodity.pieceType = c.pieceType;
        commodity.pieces = c.pieces;
        commodity.shipClass = c.shipClass;
        commodity.weight = c.weight;
        commodity.dimensions.width = c.width;

        ratingRequest.commodities.push(commodity);
      }
    }
  }
}
