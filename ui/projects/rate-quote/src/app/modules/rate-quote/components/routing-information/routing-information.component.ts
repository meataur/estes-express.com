import { Component, OnInit, OnDestroy, SimpleChanges, OnChanges, ViewChild } from '@angular/core';
import { RateQuoteFormSection, RateQuote } from '../../models';
import { RoutingInformationService } from '../../services/routing-information.service';
import { takeUntil, tap, debounceTime, distinctUntilChanged, map } from 'rxjs/operators';
import { FormService, PointLookupService, Point, AuthService } from 'common';
import { merge, Observable, of, empty, forkJoin, EMPTY, Subscription } from 'rxjs';
import { MatAutocompleteSelectedEvent, MatAutocomplete, MatOption } from '@angular/material';
import { ValidatorFn, AbstractControl } from '@angular/forms';
import { Point as RateQuotePoint } from '../../models/point.model';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-routing-information',
  templateUrl: './routing-information.component.html',
  styleUrls: ['./routing-information.component.scss']
})
export class RoutingInformationComponent extends RateQuoteFormSection
  implements OnInit, OnDestroy, OnChanges {
  originOptions$: Observable<Array<Point>> = of([]);
  destOptions$: Observable<Array<Point>> = of([]);
  paramSub: Subscription;
  @ViewChild('originZipAuto') originAutoComplete: MatAutocomplete;

  constructor(
    private routingService: RoutingInformationService,
    public formService: FormService,
    private pointsLookup: PointLookupService,
    protected auth: AuthService,
    private route: ActivatedRoute
  ) {
    super(auth);
  }

  ngOnInit() {
    super.ngOnInit();
    this.paramSub = this.route.queryParams.subscribe(params => {
        this.resetFormWithPointsFromParams(
          params['dzip'] || '',
          params['dcity'] || '',
          params['dstate'] || '',
          params['dcountry'] || '',
          params['ozip'] || '',
          params['ocity'] || '',
          params['ostate'] || '',
          params['ocountry'] || '');
    });
  }

  ngOnDestroy() {
    if (this.paramSub) {
      this.paramSub.unsubscribe();
    }
    super.ngOnDestroy();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.quote && changes.quote.currentValue) {
      const quote: RateQuote = changes.quote.currentValue;
      this.routingService.resetForm(quote.origin, quote.dest);
    }
  }

  // Invoked by parent class in ngOnInit()
  initForm() {
    this.routingService.formModel$.pipe(takeUntil(this.stop$)).subscribe(form => {
      this.formGroup = form;
    });
  }

  onOriginOptionSelected(event: MatAutocompleteSelectedEvent) {
    const point = event.option.value as Point;
    this.originState.setValue(point, { emitEvent: false });
    this.originCity.setValue(point, { emitEvent: false });
    this.originZip.setValue(point, { emitEvent: false });
  }

  onDestOptionSelected(event: MatAutocompleteSelectedEvent) {
    const point = event.option.value as Point;
    this.destState.setValue(point, { emitEvent: false });
    this.destCity.setValue(point, { emitEvent: false });
    this.destZip.setValue(point, { emitEvent: false });
  }

  private resetFormWithPointsFromParams(destZip: string, destCity: string, destState: string, destCountry: string, originZip: string, originCity: string, originState: string, originCountry: string) {
    const destPoint = new Point();
    destPoint.zip = destZip;
    destPoint.city = destCity;
    destPoint.state = destState;
    destPoint.country = destCountry;

    const originPoint = new Point();
    originPoint.zip = originZip;
    originPoint.city = originCity;
    originPoint.state = originState;
    originPoint.country = originCountry;

    this.destination.setValue(destPoint, { emitEvent: false });
    this.origin.setValue(originPoint, { emitEvent: false });



  }

  get originCity() {
    return this.formGroup.controls['originCity'];
  }
  get originCountry() {
    return this.formGroup.controls['originCountry'];
  }
  get originState() {
    return this.formGroup.controls['originState'];
  }
  get originZip() {
    return this.formGroup.controls['originZip'];
  }
  get destCity() {
    return this.formGroup.controls['destCity'];
  }
  get destCountry() {
    return this.formGroup.controls['destCountry'];
  }
  get destState() {
    return this.formGroup.controls['destState'];
  }
  get destZip() {
    return this.formGroup.controls['destZip'];
  }
  get destZipText() {
    return this.destCountry.value === 'CA' || this.destCountry.value === 'MX'
      ? 'Postal Code'
      : 'ZIP Code';
  }
  get originZipText() {
    return this.originCountry.value === 'CA' || this.originCountry.value === 'MX'
      ? 'Postal Code'
      : 'ZIP Code';
  }
  get destStateText() {
    return this.destCountry.value === 'CA' ? 'Province' : 'State';
  }
  get originStateText() {
    return this.originCountry.value === 'CA' ? 'Province' : 'State';
  }
  get origin() {
    return this.formGroup.controls['origin'];
  }
  get destination() {
    return this.formGroup.controls['destination'];
  }
}
