import {
  Component,
  OnInit,
  forwardRef,
  OnDestroy,
  Input,
  OnChanges,
  SimpleChanges
} from '@angular/core';
import {
  ControlValueAccessor,
  NG_VALUE_ACCESSOR,
  FormGroup,
  FormBuilder,
  NG_VALIDATORS,
  FormControl,
  NgForm,
  FormGroupDirective,
  AbstractControl
} from '@angular/forms';
import { Point } from '../../models';
import { PointLookupService, validateFormFields } from 'common';
import { merge, Subject, Observable, of } from 'rxjs';
import { debounceTime, distinctUntilChanged, takeUntil, share } from 'rxjs/operators';
import { MatAutocompleteSelectedEvent, ErrorStateMatcher } from '@angular/material';

// See https://stackoverflow.com/a/51606362
class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    return control.parent.touched && control.parent.hasError('invalidPoint');
  }
}

export function validatePoint(c: FormControl) {
  const err = {
    invalidPoint: {
      message: 'Please enter a valid point.'
    }
  };

  if (c.validator) {
    const validator = c.validator({} as AbstractControl);
    if (validator && validator.required) {
      return !(c.value instanceof Point) ? err : null;
    }
  }

  return;
}

@Component({
  selector: 'app-point-control',
  templateUrl: './point-control.component.html',
  styleUrls: ['./point-control.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => PointControlComponent),
      multi: true
    },
    {
      provide: NG_VALIDATORS,
      useValue: validatePoint,
      multi: true
    }
  ]
})
export class PointControlComponent implements ControlValueAccessor, OnInit, OnDestroy, OnChanges {
  /**
   * @description Used to trigger validation of inner formGroup.
   */
  @Input() touched?: boolean;
  private _point: Point;
  get point() {
    return this._point;
  }
  set point(obj) {
    this.propagateChange(obj);
    this.validPoint.setValue(obj);
    this._point = obj;
  }
  matcher = new MyErrorStateMatcher();
  formGroup: FormGroup;
  stop$ = new Subject<boolean>();
  filterOptions$: Observable<Array<Point>>;
  selectedOption = false;

  constructor(private fb: FormBuilder, private pointLookup: PointLookupService) {}

  ngOnInit() {
    this.initForm();
    this.initFormWatchers();
  }

  ngOnDestroy() {
    this.stop$.next(true);
    this.stop$.unsubscribe();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.touched && changes.touched.currentValue) {
      validateFormFields(this.formGroup);
    }
  }

  propagateChange = (_: any) => {};
  writeValue(obj: any): void {
    this.tryPrepopulatePoint(obj);
  }
  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }
  registerOnTouched(fn: any): void {}
  setDisabledState?(isDisabled: boolean): void {}

  initForm() {
    this.formGroup = this.fb.group(
      {
        country: ['US'],
        city: [''],
        state: [''],
        zip: [''],
        /**
         * This form control will be set to the value of the point.  Used
         * to reflect the validity of the control within the formGroup in the template.
         */
        validPoint: [null]
      },
      { validator: this.checkPoint }
    );
  }

  checkPoint(group: FormGroup) {
    const validPoint = group.controls.validPoint.value;
    return !validPoint ? { invalidPoint: true } : null;
  }

  onOptionSelected(event: MatAutocompleteSelectedEvent) {

    this.selectedOption = true;

    const point = event.option.value as Point;
    this.state.setValue(point.state, { emitEvent: false });
    this.city.setValue(point.city, { emitEvent: false });
    this.zip.setValue(point.zip, { emitEvent: false });
    point.country = this.country.value;

    const newPoint = new Point();
    newPoint.country = point.country;
    newPoint.city = point.city;
    newPoint.state = point.state;
    newPoint.zip = point.zip;

    this.point = newPoint;
  }

  clear() {
    this.city.reset('', { emitEvent: false });
    this.state.reset('', { emitEvent: false });
    this.zip.reset('', { emitEvent: false });
    this.country.setValue('US');
    this.filterOptions$ = of([]);

    this.point = null;
  }

  get zipText() {
    return this.country.value === 'CN' || this.country.value === 'MX' ? 'Postal Code' : 'ZIP Code';
  }
  get stateText() {
    return this.country.value === 'CN' ? 'Province' : 'State';
  }

  initFormWatchers() {
    merge(this.city.valueChanges, this.state.valueChanges, this.zip.valueChanges)
      .pipe(
        debounceTime(200),
        distinctUntilChanged(),
        takeUntil(this.stop$)
      )
      .subscribe(next => {
        // Clear out the value of the control, as we must first validate the input.
        // this.point = null;


        if (!this.selectedOption) {
          const point = new Point();
          point.country = this.country.value;
          point.city = this.city.value;
          point.state = this.state.value;
          point.zip = this.zip.value;

          this.tryGetPoints(point);
        } else {
          this.selectedOption = false;
        }
      });
  }

  private tryGetPoints(point: Point) {
    if (point.country && point.city && point.state && point.zip) {
      this.tryPrepopulatePoint(point);
    } else {
      this.point = null;
      this.filterOptions$ = this.pointLookup.lookupPoints(point).pipe(share());
    }
  }

  private tryPrepopulatePoint(obj: any) {
    if (
      obj instanceof Point ||
      (!!obj && !!(typeof obj === 'object' && obj.country && obj.city && obj.state && obj.zip))
    ) {
      const point = new Point();
      point.country = obj.country;
      point.city = obj.city;
      point.state = obj.state;
      point.zip = obj.zip;

      this.pointLookup.lookupPoints(point).subscribe(next => {
        let foundPoint = null;

        next.forEach(incPoint => {
          console.log(incPoint);
          console.log(point);
          if (
            (point.country.toUpperCase() === 'MX' &&
             point.state.toUpperCase() === 'MX' &&
             point.city.toUpperCase().includes(incPoint.city.toUpperCase()) &&
             point.zip.toUpperCase() === incPoint.zip.toUpperCase()
            ) ||
            (
            point.city.toUpperCase() === incPoint.city.toUpperCase() &&
            point.state.toUpperCase() === incPoint.state.toUpperCase() &&
            point.zip.toUpperCase() === incPoint.zip.toUpperCase())
          ) {
            this.country.setValue(point.country, { emitEvent: false });
            this.city.setValue(incPoint.city, { emitEvent: false });
            this.state.setValue(incPoint.state, { emitEvent: false });
            this.zip.setValue(incPoint.zip, { emitEvent: false });

            this.filterOptions$ = of([]);

            foundPoint = point;
          }
        });
        this.point = foundPoint;
      });
    } else {
      this.point = null;
    }
  }

  get country() {
    return this.formGroup.controls['country'];
  }
  get city() {
    return this.formGroup.controls['city'];
  }
  get state() {
    return this.formGroup.controls['state'];
  }
  get zip() {
    return this.formGroup.controls['zip'];
  }
  get validPoint() {
    return this.formGroup.controls['validPoint'];
  }
}
