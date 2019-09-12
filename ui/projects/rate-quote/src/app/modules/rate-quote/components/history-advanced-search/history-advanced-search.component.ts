import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { RateSearch, ServiceLevel, validPoint } from '../../models';
import {
  FormGroup,
  FormBuilder,
  Validators,
  NgForm,
  FormGroupDirective,
  FormControl
} from '@angular/forms';
import {
  validateFormFields,
  FormService,
  PointLookupService,
  Point,
  patternValidator,
  RegEx,
  MessageConstants,
  dateValidator
} from 'common';
import {
  MatCheckboxChange,
  MatAutocompleteSelectedEvent,
  ErrorStateMatcher
} from '@angular/material';
import { formatDate } from '@angular/common';
import { Observable, of, merge, Subject, empty, EMPTY } from 'rxjs';
import { RateQuoteService } from '../../services/rate-quote.service';
import { debounceTime, distinctUntilChanged, takeUntil } from 'rxjs/operators';
import { rS } from '@angular/core/src/render3';

// See https://stackoverflow.com/a/51606362
class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    return control.invalid || control.parent.hasError('invalidMinMaxCharges');
  }
}

@Component({
  selector: 'app-history-advanced-search',
  templateUrl: './history-advanced-search.component.html',
  styleUrls: ['./history-advanced-search.component.scss'],
  animations: [
    trigger('searchExpand', [
      state(
        'collapsed, void',
        style({ height: '0px', minHeight: '0', display: 'none', border: 'none' })
      ),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('0.15s ease-in')),
      transition('collapsed <=> void', animate('0.15s ease-out'))
    ])
  ]
})
export class HistoryAdvancedSearchComponent implements OnInit {
  private stop$ = new Subject<boolean>();
  originOptions$: Observable<Array<Point>> = of([]);
  destOptions$: Observable<Array<Point>> = of([]);
  formGroup: FormGroup;
  showAdditionalOptions = false;
  serviceLevels$: Observable<Array<ServiceLevel>>;
  matcher = new MyErrorStateMatcher();

  @Input() expand: boolean;
  @Output() filterChange: EventEmitter<RateSearch | null> = new EventEmitter<RateSearch | null>();
  @Output() closed: EventEmitter<boolean> = new EventEmitter<boolean>();

  constructor(
    private fb: FormBuilder,
    public formService: FormService,
    private rateQuoteService: RateQuoteService,
    private pointsLookup: PointLookupService
  ) {}

  ngOnInit() {
    this.serviceLevels$ = this.rateQuoteService.getServiceLevels();
    this.initForm();
    this.initValueWatchers();
  }

  search() {
    if (this.formGroup.valid) {
      this.filterChange.next(this.getFilterValue());
      this.close();
    } else {
      validateFormFields(this.formGroup);
    }
  }

  clear() {
    this.formGroup.reset({
      origin: null,
      destination: null,
      fromDate: '',
      maxCharges: '',
      minCharges: '',
      serviceLevel: '',
      toDate: '',
      quoteID: '',
      accountCode: ''
    });
    this.filterChange.next(null);
  }

  close() {
    this.expand = false;
    this.closed.next(true);
  }

  toggleFilters(event: MatCheckboxChange) {
    this.showAdditionalOptions = event.checked;
  }

  private initValueWatchers() {}

  private checkMinMaxCharges(group: FormGroup) {
    const min = group.controls.minCharges.value;
    const max = group.controls.maxCharges.value;

    if (min && max) {
      if (+max < +min) {
        return { invalidMinMaxCharges: true };
      }
    }

    return null;
  }

  private getFilterValue(): RateSearch {
    const rs = new RateSearch();
    const origin = this.origin.value;
    const destination = this.destination.value;
    const fromDate = this.fromDate.value;
    const maxCharges = this.maxCharges.value;
    const minCharges = this.minCharges.value;
    const serviceLevel = this.serviceLevel.value;
    const toDate = this.toDate.value;
    const quoteID = this.quoteID.value.toUpperCase();
    const accountCode = this.accountCode.value;

    if (origin) {
      rs.origin = origin;
    }

    if (destination) {
      rs.dest = destination;
    }

    if (quoteID) {
      rs.quoteID = quoteID;
    }
    if (fromDate) {
      rs.fromDate = formatDate(fromDate, 'MM/dd/yyyy', 'en-US');
    }
    if (toDate) {
      rs.toDate = formatDate(toDate, 'MM/dd/yyyy', 'en-US');
    }
    if (serviceLevel) {
      rs.serviceLevel = serviceLevel;
    }
    if (maxCharges) {
      rs.maxCharges = maxCharges;
    }
    if (minCharges) {
      rs.minCharges = minCharges;
    }
    if (accountCode) {
      rs.accountCode = accountCode;
    }

    return rs;
  }

  private initForm() {
    this.formGroup = this.fb.group(
      {
        origin: [null],
        destination: [null],
        fromDate: ['', dateValidator()],
        maxCharges: [
          '',
          [
            Validators.maxLength(7),
            patternValidator(RegEx.numbers, MessageConstants.invalidNumbers)
          ]
        ],
        minCharges: [
          '',
          [
            Validators.maxLength(7),
            patternValidator(RegEx.numbers, MessageConstants.invalidNumbers)
          ]
        ],
        serviceLevel: [''],
        toDate: ['', dateValidator()],
        quoteID: [''],
        accountCode: ['']
      },
      { validator: this.checkMinMaxCharges }
    );
  }

  get fromDate() {
    return this.formGroup.controls['fromDate'];
  }
  get maxCharges() {
    return this.formGroup.controls['maxCharges'];
  }
  get minCharges() {
    return this.formGroup.controls['minCharges'];
  }
  get serviceLevel() {
    return this.formGroup.controls['serviceLevel'];
  }
  get toDate() {
    return this.formGroup.controls['toDate'];
  }
  get quoteID() {
    return this.formGroup.controls['quoteID'];
  }
  get accountCode() {
    return this.formGroup.controls['accountCode'];
  }
  get origin() {
    return this.formGroup.controls['origin'];
  }
  get destination() {
    return this.formGroup.controls['destination'];
  }
  get today() {
    return new Date();
  }
}
