import { Component, OnInit, OnDestroy, OnChanges, SimpleChanges } from '@angular/core';
import { FormGroup, Validators } from '@angular/forms';
import { MatSlideToggleChange } from '@angular/material';
import { ShippingLabelsFormService } from '../../services/shipping-labels-form.service';
import { Subscription, Subject } from 'rxjs';
import { FormService, patternValidator, RegEx, MessageConstants, DialogService } from 'common';
import { takeUntil } from 'rxjs/operators';
import { BolSection, BillOfLading } from '../../models';

@Component({
  selector: 'app-shipping-labels',
  templateUrl: './shipping-labels.component.html',
  styleUrls: ['./shipping-labels.component.scss']
})
export class ShippingLabelsComponent extends BolSection implements OnInit, OnDestroy, OnChanges {
  private stop$ = new Subject<boolean>();
  showForm = false;
  formGroup: FormGroup;
  formSub: Subscription;
  labelPositions: Array<string>;

  constructor(
    private shippingLabelsFormService: ShippingLabelsFormService,
    public formService: FormService,
    private dialog: DialogService
  ) {
    super();
  }

  ngOnInit() {
    this.formSub = this.shippingLabelsFormService.shippingLabelForm$.subscribe(formGroup => {
      this.stop$.next(true);
      this.formGroup = formGroup;

      this.labelType.valueChanges.pipe(takeUntil(this.stop$)).subscribe(next => {
        switch (+next) {
          case 0:
            this.startLabel.disable();
            this.numberOfLabel.disable();
            break;
          case 1:
            this.labelPositions = ['1'];
            this.startLabel.setValue('1');
            this.startLabel.disable();
            break;
          case 2:
            this.labelPositions = ['1', '2'];
            break;
          case 4:
            this.labelPositions = ['1', '2', '3', '4'];
            break;
          case 6:
            this.labelPositions = ['1', '2', '3', '4', '5', '6'];
            break;
        }

        if (+next !== 1 && +next !== 0) {
          this.startLabel.enable();
          if (this.labelPositions.indexOf(this.startLabel.value) === -1) {
            this.startLabel.reset(null);
          }
        }
        if (+next !== 0) {
          this.numberOfLabel.enable();
        }
      });

      this.labelType.updateValueAndValidity();
    });
  }

  ngOnDestroy() {
    this.stop$.next(true);
    this.stop$.unsubscribe();
    if (this.formSub) {
      this.formSub.unsubscribe();
    }
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.draft && changes.draft.currentValue) {
      this.shippingLabelsFormService.resetForm(
        (changes.draft.currentValue as BillOfLading).shippingLabel
      );
    }
  }

  get labelType() {
    return this.formGroup.controls['labelType'];
  }
  get startLabel() {
    return this.formGroup.controls['startLabel'];
  }
  get numberOfLabel() {
    return this.formGroup.controls['numberOfLabel'];
  }
  get hasShippingLabel() {
    return this.formGroup.controls['hasShippingLabel'];
  }
}
