import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { BookAddress } from '../../models';
import { FormBuilder, FormGroup } from '@angular/forms';
import { BolService } from '../../services/bol.service';
import { validateFormFields, SnackbarService } from 'common';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-add-address-modal',
  templateUrl: './add-address-modal.component.html',
  styleUrls: ['./add-address-modal.component.scss']
})
export class AddAddressModalComponent implements OnInit {
  loading = false;
  formGroup: FormGroup;

  constructor(
    public dialogRef: MatDialogRef<AddAddressModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: BookAddress,
    private fb: FormBuilder,
    private bolService: BolService,
    private snackbarService: SnackbarService
  ) {}

  ngOnInit() {
    this.formGroup = this.fb.group({
      shipper: [false],
      thirdParty: [false],
      consignee: [false],
      cod: [false]
    }, { validator: this.atLeastOneChecked });
  }

  atLeastOneChecked(group: FormGroup) {
    const shipperChecked = group.controls['shipper'].value === true;
    const thirdPartyChecked = group.controls['thirdParty'].value === true;
    const consigneeChecked = group.controls['consignee'].value === true;
    const codChecked = group.controls['cod'].value === true;

    const atLeastOneChecked = !!(
      shipperChecked ||
      thirdPartyChecked ||
      consigneeChecked ||
      codChecked
    );

    return atLeastOneChecked ? null : { atLeastOneChecked: true };
  }

  get shipper() {
    return this.formGroup.controls['shipper'];
  }
  get thirdParty() {
    return this.formGroup.controls['thirdParty'];
  }
  get consignee() {
    return this.formGroup.controls['consignee'];
  }
  get cod() {
    return this.formGroup.controls['cod'];
  }

  onNoClick() {
    this.dialogRef.close();
  }

  onSubmit() {
    if (this.formGroup.valid) {
      const ba: BookAddress = { ...this.data };
      ba.cod = this.cod.value;
      ba.consignee = this.consignee.value;
      ba.thirdParty = this.thirdParty.value;
      ba.shipper = this.shipper.value;

      this.bolService.addAddress(ba).subscribe(
        next => {
          this.snackbarService.success(next.message);
          this.dialogRef.close();
        },
        err => {
          this.loading = false;
          let msg = `An unexpected error has occurred.  Please try again.`;
          if (err instanceof HttpErrorResponse) {
            if (err.status === 400) {
              msg = err.error[0].message;
            } else if (err.status === 500) {
              msg = err.error.message;
            }
          }
          this.snackbarService.error(msg);
        },
        () => (this.loading = false)
      );
    } else {
      validateFormFields(this.formGroup);
    }
  }
}
