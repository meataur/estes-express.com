import { Injectable } from '@angular/core';
import {
  RateQuoteFormSectionService,
  AccessorialForm,
  Accessorial,
  RatingRequest,
  RateQuote
} from '../models';
import { FormBuilder } from '@angular/forms';
import { Observable, Subject, BehaviorSubject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/environment';
import { SelectionModel } from '@angular/cdk/collections';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AccessorialsService extends RateQuoteFormSectionService {
  private accessorialSelection: BehaviorSubject<
    SelectionModel<Accessorial> | undefined
  > = new BehaviorSubject(new SelectionModel<Accessorial>(true, []));
  accessorialsForm$: Observable<
    SelectionModel<Accessorial>
  > = this.accessorialSelection.asObservable();

  initValueChangeWatchers() {}
  initFormState() {}
  valid(): boolean {
    return true;
  }

  constructor(private http: HttpClient, fb: FormBuilder) {
    super(fb);
  }

  getAccessorials(): Observable<Array<Accessorial>> {
    return this.http.get<Array<Accessorial>>(
      `${environment.serviceApiUrl}/myestes/Rating/v4.0/accessorials`
    );
  }

  resetForm(q?: RateQuote) {
    const newAccessorialSelection = new SelectionModel<Accessorial>(true, []);
    for (let a of q ? q.accessorials : []) {
      newAccessorialSelection.toggle(a.accessorial);
    }

    this.accessorialSelection.next(newAccessorialSelection);
  }

  populateModel(ratingRequest: RatingRequest) {
    const selected = this.accessorialSelection.getValue().selected;

    ratingRequest.accessorials = [];

    if (selected.length) {
      ratingRequest.accessorials = selected;
    }
  }
}
