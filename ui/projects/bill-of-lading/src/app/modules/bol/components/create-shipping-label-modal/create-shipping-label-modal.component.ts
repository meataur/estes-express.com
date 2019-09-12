import { Component, OnInit, Inject, OnDestroy } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ShippingLabelForm, ShippingLabel } from '../../models';
import { FormService, validateFormFields, SnackbarService } from 'common';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { Bol } from '../../models/bol.interface';
import { BolService } from '../../services/bol.service';

@Component({
  selector: 'app-create-shipping-label-modal',
  templateUrl: './create-shipping-label-modal.component.html',
  styleUrls: ['./create-shipping-label-modal.component.scss']
})
export class CreateShippingLabelModalComponent implements OnInit, OnDestroy {
  private stop$ = new Subject<boolean>();
  formGroup: FormGroup;
  labelPositions: Array<string>;
  loadingShippingLabel: boolean;

  constructor(
    private dialogRef: MatDialogRef<CreateShippingLabelModalComponent>,
    @Inject(MAT_DIALOG_DATA) private data: Bol,
    private fb: FormBuilder,
    private bolService: BolService,
    private snackbar: SnackbarService,
    public formService: FormService
  ) {}

  ngOnInit() {
    const shippingLabel = new ShippingLabel();
    this.formGroup = this.fb.group(new ShippingLabelForm(shippingLabel));
    this.labelType.setValue('6');

    this.labelType.valueChanges.pipe(takeUntil(this.stop$)).subscribe(next => {
      switch (+next) {
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

      if (+next !== 1) {
        this.startLabel.enable();
        this.startLabel.reset(null);
      }
    });

    this.labelType.updateValueAndValidity();
  }

  ngOnDestroy() {
    this.stop$.next(true);
    this.stop$.unsubscribe();
  }

  onSubmit() {
    if (this.formGroup.valid) {
      this.loadingShippingLabel = true;

      const payload = new ShippingLabel();
      payload.labelType = this.labelType.value;
      payload.numberOfLabel = this.numberOfLabel.value;
      payload.startLabel = this.startLabel.value;

      this.bolService
        .createShippingLabel(this.data.bolId.toString(), payload)
        .pipe(takeUntil(this.stop$))
        .subscribe(
          next => {
            this.snackbar.success('Shipping Labels created successfully.');
            this.dialogRef.close(true);
          },
          err => {
            this.loadingShippingLabel = false;
            this.snackbar.error(
              `An error occurred while retrieving the Shipping Labels.  Please try again.`
            );
          },
          () => (this.loadingShippingLabel = false)
        );
    } else {
      validateFormFields(this.formGroup);
    }
  }

  private open(url: string) {
    if (url) {
      const win = window.open(url, '_blank');
      win.focus();
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
}
