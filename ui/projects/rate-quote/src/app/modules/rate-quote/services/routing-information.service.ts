import { Injectable } from '@angular/core';
import {
  RateQuoteFormSectionService,
  RoutingInfoForm,
  RatingRequest,
  Point,
  RateQuote
} from '../models';
import { FormBuilder } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class RoutingInformationService extends RateQuoteFormSectionService {
  initValueChangeWatchers() {}
  initFormState() {}
  valid(): boolean {
    return this.isValid(this.formModelSource.getValue());
  }

  constructor(fb: FormBuilder) {
    super(fb);
    super.initFormSource(RoutingInfoForm);
  }

  resetForm(originPoint?: Point, destPoint?: Point) {
    const newForm = this.fb.group(new RoutingInfoForm(originPoint, destPoint));

    this.formModelSource.next(newForm);
  }

  populateModel(ratingRequest: RatingRequest) {
    const form = this.formModelSource.getValue().getRawValue();

    ratingRequest.dest = form.destination;
    ratingRequest.origin = form.origin;
  }
}
