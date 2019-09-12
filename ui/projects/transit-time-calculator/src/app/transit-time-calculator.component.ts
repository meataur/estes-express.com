import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { DatePipe } from '@angular/common';
import { of, Observable, Subscription } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { debounceTime } from 'rxjs/operators';
import { FormControl, FormGroup, FormBuilder, FormArray, FormGroupDirective } from '@angular/forms';
import { FormService, Point, PointLookupService, SnackbarService, MyEstesValidators, FeedbackTypes, validateFormFields } from 'common';
import { TransitTimeCalculatorResponse } from './transit-time-calculator-response.model';
import { TransitTimeCalculatorService } from './transit-time-calculator.service';
import { TransitTimeCalculatorRequest } from './transit-time-calculator-request.model';
import { LeftNavigationService, PromoService, UtilService, Terminal, dateValidator } from 'common';

@Component({
  selector: 'app-transit-time-calculator',
  templateUrl: './transit-time-calculator.component.html',
  styleUrls: ['./transit-time-calculator.component.scss']
})
export class TransitTimeCalculatorComponent implements OnInit, OnDestroy {
  @ViewChild(FormGroupDirective) myForm;

  formGroup: FormGroup;
  originPointSuggestions: Observable<Point[]>;
  destinationPointSuggestions: Observable<Point[]>[];

  datePipe: DatePipe;
  loading: boolean;
  results: TransitTimeCalculatorResponse;
  calculateSub: Subscription;
  paramSub: Subscription;
  terminalSub: Subscription;
  params: any;
  errorMessages: [FeedbackTypes, string];
  requestCopy: TransitTimeCalculatorRequest;

  countries: any[];

  constructor(
    public formService: FormService,
    private fb: FormBuilder,
    private pointLookupService: PointLookupService,
    private transitTimeCalculatorService: TransitTimeCalculatorService,
    public leftNavigationService: LeftNavigationService,
    private route: ActivatedRoute,
    public promoService: PromoService,
    private utilService: UtilService,
    private snackbar: SnackbarService
  ) {
    this.countries = [
      {viewValue: 'United States', value: 'US'},
      {viewValue: 'Canada', value: 'CN'},
      {viewValue: 'Mexico', value: 'MX'}
    ];
   }

  ngOnInit() {
    this.datePipe = new DatePipe('en-US');
    this.results = null;
    this.initFormGroup();

    this.leftNavigationService.setNavigation(`Ship`, `/ship`);
    this.promoService.setAppId('transit-time-calculator');
    this.promoService.setAppState('any');
    
    this.originPointSuggestions = null;
    this.destinationPointSuggestions = [];

    this.params = {};

    this.paramSub = this.route.queryParams.subscribe(params => {
      let ozip = params['ozip'];
      let dzip = params['dzip'];
      this.params = {
        ozip: ozip,
        dzip: dzip
      };
      this.loadPointsFromParams();
   });
  }

  ngOnDestroy() {
    if (this.paramSub) {
      this.paramSub.unsubscribe();
    }
    if (this.terminalSub) {
      this.terminalSub.unsubscribe();
    }
    if (this.calculateSub) {
      this.calculateSub.unsubscribe();
    }
  }

  resetTransitForm() {
    this.myForm.resetForm();
    this.initFormGroup();
    this.results = null;
    this.errorMessages = null;
    this.originPointSuggestions = of();
    this.destinationPointSuggestions = [of()];
  }

  onSubmit() {
    this.errorMessages = null;
    if (this.formGroup.valid) {
      this.loading = true;
      let req: TransitTimeCalculatorRequest;
      req = this.formatRequest(this.formGroup.getRawValue());
      this.calculateSub = this.transitTimeCalculatorService.calculate(req)
      .subscribe(data => {
        this.results = data;
        this.requestCopy = req;
        this.loading = false;
        this.fetchTerminalMaps();
        this.snackbar.success(`See the search results in the table below.`);
      },
      err => {
        this.loading = false;
        this.errorMessages = ['error', err];
        this.snackbar.error(`No terminals found matching your search criteria.`);
      });
    } else {
       validateFormFields(this.formGroup);
       this.snackbar.validationError();
    }
  }

  fetchTerminalMaps() {
    if (this.results.originTerminal && this.results.originTerminal.id) {
      this.terminalSub = this.utilService.getTerminalById(this.results.originTerminal.id).subscribe(terminalInfo => {
          if (terminalInfo && terminalInfo.nationalMap) {
            this.results.originTerminal.nationalMap = terminalInfo.nationalMap;
          }
          if (terminalInfo && terminalInfo.serviceMap) {
            this.results.originTerminal.serviceMap = terminalInfo.serviceMap;
          }
      });
    }
  }

  get originpoint() {
    return this.formGroup.get(['originpoint']) as FormGroup;
  }
  get originCity() {
    return this.formGroup.get(['originpoint', 'city']);
  }
  get originCountry() {
    return this.formGroup.get(['originpoint', 'country']);
  }
  get originState() {
    return this.formGroup.get(['originpoint', 'state']);
  }
  get originZip() {
    return this.formGroup.get(['originpoint', 'zip']);
  }
  get destinationPoints(): FormArray {
    return this.formGroup.get('destinationPoints') as FormArray;
  }

  initFormGroup() {
    this.formGroup = this.fb.group({
      destinationPoints: this.fb.array([
        this.fb.group({
          point: this.fb.group({
            city: ['', MyEstesValidators.required],
            country: ['', MyEstesValidators.required],
            state: ['', MyEstesValidators.required],
            zip: ['', MyEstesValidators.required]
          }),
          shipmentDate: new FormControl('', [dateValidator()])
        })
      ]),
      originpoint: this.fb.group(
        {
          city: ['', MyEstesValidators.required],
          country: ['', MyEstesValidators.required],
          state: ['', MyEstesValidators.required],
          zip: ['', MyEstesValidators.required]
        }
      )
    });
    (this.formGroup.get('destinationPoints') as FormArray).at(0).get('point.city').setValue('');
    (this.formGroup.get('destinationPoints') as FormArray).at(0).get('point.country').setValue('');
    (this.formGroup.get('destinationPoints') as FormArray).at(0).get('point.state').setValue('');
    (this.formGroup.get('destinationPoints') as FormArray).at(0).get('point.zip').setValue('');
    this.formGroup.get('originpoint.city').setValue('');
    this.formGroup.get('originpoint.country').setValue('');
    this.formGroup.get('originpoint.state').setValue('');
    this.formGroup.get('originpoint.zip').setValue('');
    this.loadPointsFromParams();
  }

  addDestination() {
    if (this.destinationPoints.length <= 10) {
      this.destinationPoints.push(
        new FormGroup({
          point: new FormGroup({
            city: new FormControl(''),
            country: new FormControl(''),
            state: new FormControl(''),
            zip: new FormControl(''),
          }),
          shipmentDate: new FormControl('', [dateValidator()])
      }));
    }
  }

  removeDestination(index: number) {
    if (index > 0) {
      this.destinationPoints.removeAt(index);
    }
  }

  formatRequest(form) {
    for (let obj of form.destinationPoints) {
      if (obj.shipmentDate) {
         let date = obj.shipmentDate;
         if (date) {
          let stringDate = this.datePipe.transform(date, 'MM/dd/yyyy');
           obj.shipmentDate = stringDate;
         }
      }
   }
   return form;
  }

  reloadOriginSuggestions(fg: any, event: any) {
    if (event.target.value) {
      this.originPointSuggestions = this.pointLookupService
        .lookupPoints(fg.getRawValue())
        .pipe(debounceTime(100));
    } else {
      this.originPointSuggestions = of([]).pipe(debounceTime(100));
    }
  }

  reloadDestinationSuggestions(fg: any, event: any, index: number) {
    if (event.target.value) {
      this.destinationPointSuggestions[index] = this.pointLookupService
        .lookupPoints(fg.getRawValue())
        .pipe(debounceTime(100));
    } else {
      this.destinationPointSuggestions[index] = of([]).pipe(debounceTime(100));
    }
  }

  /**
   * Autofills the origin or destination point fields based on the query string params included in the url.
   */
  loadPointsFromParams() {
    if (this.params && this.formGroup) {
      for (let key in this.params) {
        let point = new Point({zip: this.params[key]});
        this.pointLookupService.lookupPoints(point).subscribe(data => {
          if (data && data[0]) {
            if (key === 'ozip') {
              this.autofillPoint(data[0], this.originpoint);

            } else if (key === 'dzip') {
              this.autofillPoint(data[0], this.destinationPoints.controls[0].get('point') as FormGroup);
            }
          }
        });
      }
    }
  }

  /**
   * autofillPoint sets the request based on the point selected from the autoselector
   * @param point is the selected point from the autoselect list
   */
  autofillPoint(point: Point, fg: FormGroup) {
    fg.controls['city'].patchValue(point.city);
    fg.controls['state'].patchValue(point.state);
    fg.controls['zip'].patchValue(point.zip);
    if (point.country.trim()) fg.controls['country'].patchValue(point.country);
    if (point.country === '  ') fg.controls['country'].patchValue('US');
  }

}
