import { Component, OnInit, Input, EventEmitter, Output, OnDestroy } from '@angular/core';
import { trigger, state, animate, style, transition } from '@angular/animations';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { FormService, validateFormFields, MyEstesValidators, Masks, dateValidator } from 'common';
import { formatDate } from '@angular/common';
import { TextMaskConfig } from 'angular2-text-mask';

@Component({
  selector: 'app-draft-advanced-search',
  templateUrl: './draft-advanced-search.component.html',
  styleUrls: ['./draft-advanced-search.component.scss'],
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
export class DraftAdvancedSearchComponent implements OnInit, OnDestroy {
  private filterSub: Subscription;
  private proNumberMask = Masks.pronumber;
  maskConfig: TextMaskConfig;

  formGroup: FormGroup;
  filterOptions = Object.keys(DraftFilterEnum).map(key => ({
    value: key,
    text: DraftFilterEnum[key]
  }));
  @Input() expand: boolean;
  @Output() filterChange: EventEmitter<[DraftFilterEnum, string | string[]]> = new EventEmitter<
    [DraftFilterEnum, string | string[]]
  >();
  @Output() closed: EventEmitter<boolean> = new EventEmitter<boolean>();

  constructor(private fb: FormBuilder, public formService: FormService) {}

  ngOnInit() {
    this.maskConfig = { mask: false, guide: false };

    this.formGroup = this.fb.group({
      filter: ['SHOW_ALL', MyEstesValidators.required],
      contains: [''],
      startDate: ['', dateValidator()],
      endDate: ['', dateValidator()]
    });

    this.filterSub = this.filter.valueChanges.subscribe(val => {
      switch (DraftFilterEnum[val]) {
        case DraftFilterEnum.SHOW_ALL:
          this.startDate.clearValidators();
          this.endDate.clearValidators();
          this.contains.clearValidators();
          this.startDate.reset();
          this.endDate.reset();
          this.contains.reset();
          break;
        case DraftFilterEnum.BOL_DATE_RANGE:
          this.contains.clearValidators();
          this.contains.reset();
          this.startDate.setValidators([Validators.required, dateValidator()]);
          this.endDate.setValidators([Validators.required, dateValidator()]);
          break;
        case DraftFilterEnum.BOL_NUMBER:
        case DraftFilterEnum.CONSIGNEE:
        case DraftFilterEnum.SHIPPER:
          this.startDate.clearValidators();
          this.endDate.clearValidators();
          this.startDate.reset();
          this.endDate.reset();
          this.contains.setValidators([Validators.required, Validators.maxLength(255)]);
          this.maskConfig = { mask: false, guide: false };
          break;
        case DraftFilterEnum.PRO_NUMBER:
          this.startDate.clearValidators();
          this.endDate.clearValidators();
          this.startDate.reset();
          this.endDate.reset();
          this.contains.setValidators([Validators.required, Validators.maxLength(255)]);
          this.maskConfig = { mask: this.proNumberMask, guide: false };
          break;
      }
    });
  }

  ngOnDestroy() {
    if (this.filterSub) {
      this.filterSub.unsubscribe();
    }
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
      filter: 'SHOW_ALL'
    });
    this.filterChange.next([this.filter.value, null]);
    this.close();
  }

  close() {
    this.expand = false;
    this.closed.next(true);
  }

  private getFilterValue(): [DraftFilterEnum, string | string[] | null] {
    const filter = this.filter.value;
    let filterVal: string | string[] | null = null;

    switch (DraftFilterEnum[filter]) {
      case DraftFilterEnum.SHOW_ALL:
        filterVal = null;
        break;
      case DraftFilterEnum.BOL_DATE_RANGE:
        filterVal = [
          this.formService.formatDate(this.startDate.value),
          this.formService.formatDate(this.endDate.value)
        ];
        break;
      case DraftFilterEnum.BOL_NUMBER:
      case DraftFilterEnum.CONSIGNEE:
      case DraftFilterEnum.SHIPPER:
      case DraftFilterEnum.PRO_NUMBER:
        filterVal = this.contains.value || '';
        break;
    }

    return [filter, filterVal];
  }

  get showContains() {
    return DraftFilterEnum[this.filter.value] !== DraftFilterEnum.SHOW_ALL &&
      DraftFilterEnum[this.filter.value] !== DraftFilterEnum.BOL_DATE_RANGE
      ? true
      : false;
  }
  get filter() {
    return this.formGroup.controls['filter'];
  }
  get contains() {
    return this.formGroup.controls['contains'];
  }
  get startDate() {
    return this.formGroup.controls['startDate'];
  }
  get endDate() {
    return this.formGroup.controls['endDate'];
  }
  get today() {
    return new Date();
  }
}

export enum DraftFilterEnum {
  SHOW_ALL = 'Show All',
  BOL_NUMBER = 'BOL Reference Number',
  BOL_DATE_RANGE = 'BOL Date Range',
  PRO_NUMBER = 'PRO Number',
  SHIPPER = 'Shipper',
  CONSIGNEE = 'Consignee'
}
