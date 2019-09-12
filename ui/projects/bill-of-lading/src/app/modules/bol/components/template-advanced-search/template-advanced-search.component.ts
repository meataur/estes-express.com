import { Component, OnInit, Input, EventEmitter, Output, OnDestroy } from '@angular/core';
import { trigger, state, animate, style, transition } from '@angular/animations';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { FormService, validateFormFields, MyEstesValidators, Masks, dateValidator } from 'common';
import { TemplateFilterEnum } from '../../models';
import { TextMaskConfig } from 'angular2-text-mask';

@Component({
  selector: 'app-template-advanced-search',
  templateUrl: './template-advanced-search.component.html',
  styleUrls: ['./template-advanced-search.component.scss'],
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
export class TemplateAdvancedSearchComponent implements OnInit, OnDestroy {
  private filterSub: Subscription;
  private proNumberMask = Masks.pronumber;

  formGroup: FormGroup;
  maskConfig: TextMaskConfig;
  filterOptions = Object.keys(TemplateFilterEnum).map(key => ({
    value: key,
    text: TemplateFilterEnum[key]
  }));
  @Input() expand: boolean;
  @Output() filterChange: EventEmitter<[TemplateFilterEnum, string | string[]]> = new EventEmitter<
    [TemplateFilterEnum, string | string[]]
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
      switch (TemplateFilterEnum[val]) {
        case TemplateFilterEnum.SHOW_ALL:
          this.startDate.clearValidators();
          this.endDate.clearValidators();
          this.contains.clearValidators();
          this.startDate.reset();
          this.endDate.reset();
          this.contains.reset();
          break;
        case TemplateFilterEnum.BOL_DATE_RANGE:
          this.contains.clearValidators();
          this.contains.reset();
          this.startDate.setValidators([Validators.required, dateValidator()]);
          this.endDate.setValidators([Validators.required, dateValidator()]);
          break;
        case TemplateFilterEnum.BOL_NUMBER:
        case TemplateFilterEnum.CONSIGNEE:
        case TemplateFilterEnum.SHIPPER:
        case TemplateFilterEnum.TEMPLATE_NAME:
          this.startDate.clearValidators();
          this.endDate.clearValidators();
          this.startDate.reset();
          this.endDate.reset();
          this.contains.setValidators([Validators.required, Validators.maxLength(255)]);
          this.maskConfig = { mask: false, guide: false };
          break;
        case TemplateFilterEnum.PRO_NUMBER:
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

  private getFilterValue(): [TemplateFilterEnum, string | string[] | null] {
    const filter = this.filter.value;
    let filterVal: string | string[] | null = null;

    switch (TemplateFilterEnum[filter]) {
      case TemplateFilterEnum.SHOW_ALL:
        filterVal = null;
        break;
      case TemplateFilterEnum.BOL_DATE_RANGE:
        filterVal = [
          this.formService.formatDate(this.startDate.value),
          this.formService.formatDate(this.endDate.value)
        ];
        break;
      case TemplateFilterEnum.BOL_NUMBER:
      case TemplateFilterEnum.CONSIGNEE:
      case TemplateFilterEnum.SHIPPER:
      case TemplateFilterEnum.PRO_NUMBER:
      case TemplateFilterEnum.TEMPLATE_NAME:
        filterVal = this.contains.value || '';
        break;
    }

    return [filter, filterVal];
  }

  get showContains() {
    return TemplateFilterEnum[this.filter.value] !== TemplateFilterEnum.SHOW_ALL &&
      TemplateFilterEnum[this.filter.value] !== TemplateFilterEnum.BOL_DATE_RANGE
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
