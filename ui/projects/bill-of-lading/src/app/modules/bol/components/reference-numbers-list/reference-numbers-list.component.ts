import { Component, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { FormGroup, FormArray, FormControl } from '@angular/forms';
import { MatSlideToggleChange } from '@angular/material';
import { ReferenceNumbersFormService } from '../../services/reference-numbers.service';
import { Subscription } from 'rxjs';
import { DialogService } from 'common';
import { BolSection, BillOfLading } from '../../models';

@Component({
  selector: 'app-reference-numbers-list',
  templateUrl: './reference-numbers-list.component.html',
  styleUrls: ['./reference-numbers-list.component.scss']
})
export class ReferenceNumbersListComponent extends BolSection implements OnInit, OnChanges {
  maxReferenceNumbers: number;
  showForm = true;
  formSub: Subscription;
  formGroup: FormGroup;
  referenceNumbers: FormArray;

  constructor(
    private referenceNumbersService: ReferenceNumbersFormService,
    private dialog: DialogService
  ) {
    super();
  }

  ngOnInit() {
    this.maxReferenceNumbers = this.referenceNumbersService.MAX_REFERENCE_COUNT;
    this.formSub = this.referenceNumbersService.referenceNumbersForm$.subscribe(
      commodityInformationForm => {
        this.formGroup = commodityInformationForm;
        this.referenceNumbers = commodityInformationForm.controls['referenceNumbers'] as FormArray;
        // this.formGroup.markAsPristine();
      }
    );

    // if (this.referenceNumbers.length === 0) {
    //   this.referenceNumbersService.addReferenceNumber(
    //     this.referenceNumbersService.INITIAL_REFERENCE_NUMBER_COUNT
    //   );
    // }
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.draft && changes.draft.currentValue) {
      this.referenceNumbersService.resetForm(
        (changes.draft.currentValue as BillOfLading).references
      );
    }
  }

  get totalWeight() {
    return this.referenceNumbers.controls.reduce((prev, curr) => {
      const weight = +(curr as FormGroup).controls['weight'].value;
      if (!isNaN(weight)) {
        prev += weight;
      }
      return prev;
    }, 0);
  }

  addReferenceNumber() {
    this.referenceNumbersService.addReferenceNumber();
  }

  deleteReferenceNumber(index: number) {
    this.referenceNumbersService.deleteReferenceNumber(index);
  }

  get hasReferenceNumbers(): FormControl {
    return this.formGroup.controls['hasReferenceNumbers'] as FormControl;
  }
}
