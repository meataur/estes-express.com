import { FormArray, FormControl, Validators, FormGroup, FormBuilder } from '@angular/forms';
import { MyEstesValidators, patternValidator, RegEx, MessageConstants } from 'common';
import { RateQuoteType } from './rate-quote-type-form.model';
import { Commodity } from './commodity.model';
import { CommodityPricing } from './commodity-pricing.model';
import { disableDebugTools } from '@angular/platform-browser';

export class CommodityItemForm {
  description = new FormControl('');
  length = new FormControl('');
  width = new FormControl('');
  height = new FormControl('');
  pieceType = new FormControl('');
  pieces = new FormControl('');
  shipClass = new FormControl('');
  weight = new FormControl('');

  constructor(c?: Commodity) {
    if (c) {
      if (c.description) {
        this.description.setValue(c.description);
      }
      if (c.dimensions) {
        if (c.dimensions.height) {
          this.height.setValue(c.dimensions.height);
        }
        if (c.dimensions.width) {
          this.width.setValue(c.dimensions.width);
        }
        if (c.dimensions.length) {
          this.length.setValue(c.dimensions.length);
        }
      }
      if (c.pieces) {
        this.pieces.setValue(c.pieces);
      }
      if (c.pieceType) {
        this.pieceType.setValue(c.pieceType);
      }
      if (c.shipClass) {
        this.shipClass.setValue(c.shipClass.toString());
      }
      if (c.weight) {
        this.weight.setValue(c.weight);
      }
    }
  }

  static initEstimateValidators(fg: FormGroup) {
    const pieces = fg.controls['pieces'];
    const pieceType = fg.controls['pieceType'];
    const length = fg.controls['length'];
    const width = fg.controls['width'];
    const height = fg.controls['height'];
    const weight = fg.controls['weight'];
    const shipClass = fg.controls['shipClass'];
    const description = fg.controls['description'];

    weight.setValidators([
      MyEstesValidators.required,
      Validators.maxLength(7),
      patternValidator(RegEx.numberGreaterThanZero, MessageConstants.invalidNumberGreaterThanZero)
    ]);
    shipClass.setValidators([MyEstesValidators.required]);
    description.setValidators([Validators.maxLength(50)]);

    pieces.reset('', { emitEvent: false });
    pieceType.reset('', { emitEvent: false });
    length.reset('', { emitEvent: false });
    width.reset('', { emitEvent: false });
    height.reset('', { emitEvent: false });

    pieces.disable({ emitEvent: false });
    pieceType.disable({ emitEvent: false });
    length.disable({ emitEvent: false });
    width.disable({ emitEvent: false });
    height.disable({ emitEvent: false });

    pieces.updateValueAndValidity();
    pieceType.updateValueAndValidity();
    length.updateValueAndValidity();
    width.updateValueAndValidity();
    height.updateValueAndValidity();
    weight.updateValueAndValidity();
    shipClass.updateValueAndValidity();
    description.updateValueAndValidity();
  }

  static setFormValidators(
    form: FormGroup,
    vtlOnly: boolean,
    ltlOnly: boolean,
    isRateEstimate: boolean
  ) {
    if (isRateEstimate) {
      CommodityItemForm.initEstimateValidators(form);
    } else {
      const pieces = form.controls['pieces'];
      const pieceType = form.controls['pieceType'];
      const length = form.controls['length'];
      const width = form.controls['width'];
      const height = form.controls['height'];
      const weight = form.controls['weight'];
      const shipClass = form.controls['shipClass'];
      const description = form.controls['description'];

      pieces.enable({ emitEvent: false });
      pieceType.enable({ emitEvent: false });
      length.enable({ emitEvent: false });
      width.enable({ emitEvent: false });
      height.enable({ emitEvent: false });
      shipClass.enable({ emitEvent: false });

      pieces.setValidators([
        MyEstesValidators.required,
        Validators.maxLength(6),
        patternValidator(RegEx.numberGreaterThanZero, MessageConstants.invalidNumberGreaterThanZero)
      ]);
      pieceType.setValidators([MyEstesValidators.required]);
      length.setValidators([
        MyEstesValidators.required,
        Validators.maxLength(3),
        patternValidator(RegEx.numberGreaterThanZero, MessageConstants.invalidNumberGreaterThanZero)
      ]);
      width.setValidators([
        MyEstesValidators.required,
        Validators.maxLength(3),
        patternValidator(RegEx.numberGreaterThanZero, MessageConstants.invalidNumberGreaterThanZero)
      ]);
      height.setValidators([
        MyEstesValidators.required,
        Validators.maxLength(3),
        patternValidator(RegEx.numberGreaterThanZero, MessageConstants.invalidNumberGreaterThanZero)
      ]);
      weight.setValidators([
        MyEstesValidators.required,
        Validators.maxLength(7),
        patternValidator(RegEx.numberGreaterThanZero, MessageConstants.invalidNumberGreaterThanZero)
      ]);
      shipClass.setValidators([MyEstesValidators.required]);
      description.setValidators([Validators.maxLength(50)]);

      if (ltlOnly) {
        pieces.reset('', { emitEvent: false });
        pieceType.reset('', { emitEvent: false });
        length.reset('', { emitEvent: false });
        width.reset('', { emitEvent: false });
        height.reset('', { emitEvent: false });

        pieces.disable({ emitEvent: false });
        pieceType.disable({ emitEvent: false });
        length.disable({ emitEvent: false });
        width.disable({ emitEvent: false });
        height.disable({ emitEvent: false });
      } else if (vtlOnly) {
        shipClass.clearValidators();
      }

      pieces.updateValueAndValidity();
      pieceType.updateValueAndValidity();
      length.updateValueAndValidity();
      width.updateValueAndValidity();
      height.updateValueAndValidity();
      weight.updateValueAndValidity();
      shipClass.updateValueAndValidity();
      description.updateValueAndValidity();
    }
  }
}

/**
 * @description This class is used just for the LTL Contact Me modal.  It differs
 * from the CommodityItemForm class in that the rate and charge properties from
 * CommodityPricing are included so that the values can be carried over and associated
 * to the right commodity when the /adjust call is made.
 */
export class CommodityPricingForm {
  description = new FormControl('');
  length = new FormControl('');
  width = new FormControl('');
  height = new FormControl('');
  pieceType = new FormControl('');
  pieces = new FormControl('');
  shipClass = new FormControl('');
  weight = new FormControl('');
  rate = new FormControl('');
  charge = new FormControl('');

  constructor(c: CommodityPricing) {
    if (c.commodity.description) {
      this.description.setValue(c.commodity.description);
    }
    if (c.commodity.dimensions) {
      if (c.commodity.dimensions.height) {
        this.height.setValue(c.commodity.dimensions.height);
      }
      if (c.commodity.dimensions.width) {
        this.width.setValue(c.commodity.dimensions.width);
      }
      if (c.commodity.dimensions.length) {
        this.length.setValue(c.commodity.dimensions.length);
      }
    }
    if (c.commodity.pieces) {
      this.pieces.setValue(c.commodity.pieces);
    }
    if (c.commodity.pieceType) {
      this.pieceType.setValue(c.commodity.pieceType);
    }
    if (c.commodity.shipClass) {
      this.shipClass.setValue(c.commodity.shipClass.toString());
    }
    if (c.commodity.weight) {
      this.weight.setValue(c.commodity.weight);
    }
    if (c.charge) {
      this.charge.setValue(c.charge.toString());
    }
    if (c.rate) {
      this.rate.setValue(c.rate);
    }

    this.shipClass.disable();

    // Charge and rate should NOT be manipulated by the user or outside forces.  It is part of the FormGroup
    // just to carry the values over.
    this.charge.disable();
    this.rate.disable();

    this.pieces.setValidators([
      MyEstesValidators.required,
      Validators.maxLength(6),
      patternValidator(RegEx.numberGreaterThanZero, MessageConstants.invalidNumberGreaterThanZero)
    ]);
    this.pieceType.setValidators([MyEstesValidators.required]);
    this.length.setValidators([
      MyEstesValidators.required,
      Validators.maxLength(3),
      patternValidator(RegEx.numberGreaterThanZero, MessageConstants.invalidNumberGreaterThanZero)
    ]);
    this.width.setValidators([
      MyEstesValidators.required,
      Validators.maxLength(3),
      patternValidator(RegEx.numberGreaterThanZero, MessageConstants.invalidNumberGreaterThanZero)
    ]);
    this.height.setValidators([
      MyEstesValidators.required,
      Validators.maxLength(3),
      patternValidator(RegEx.numberGreaterThanZero, MessageConstants.invalidNumberGreaterThanZero)
    ]);
    this.weight.setValidators([
      MyEstesValidators.required,
      Validators.maxLength(7),
      patternValidator(RegEx.numberGreaterThanZero, MessageConstants.invalidNumberGreaterThanZero)
    ]);
    this.shipClass.setValidators([MyEstesValidators.required]);
    this.description.setValidators([Validators.maxLength(50)]);
  }
}

export class CommodityForm {
  commodities = new FormArray([]);

  constructor(commodities?: Array<CommodityPricing>, fb?: FormBuilder) {
    if (commodities && fb) {
      for (let c of commodities) {
        const fg = fb.group(new CommodityItemForm(c.commodity));
        this.commodities.push(fg);
      }
    }
  }
}
