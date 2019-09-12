import { Component, OnInit, Inject, OnDestroy } from '@angular/core';
import {
  RateQuote,
  CommodityItemForm,
  CommodityPricingForm,
  ContactRequest,
  EmailRequest,
  ServiceResponse
} from '../../models';
import { MAT_DIALOG_DATA, MatDialogRef, ErrorStateMatcher } from '@angular/material';
import { RateQuoteService } from '../../services/rate-quote.service';
import {
  FormBuilder,
  FormGroup,
  FormArray,
  FormControl,
  FormGroupDirective,
  NgForm,
  Validators
} from '@angular/forms';
import {
  UtilService,
  SnackbarService,
  FormService,
  MyEstesValidators,
  patternValidator,
  RegEx,
  MessageConstants,
  validateFormFields,
  FeedbackTypes,
  Masks,
  MyEstesFormatters,
  AddressSelectorDialogService,
  AuthService,
  DialogService,
  dateValidator
} from 'common';
import { CommodityFormModel } from 'projects/commodity-library/src/app/models/commodity-form.model';
import { formatDate } from '@angular/common';
import { Subject } from 'rxjs';
import { takeUntil, switchMap } from 'rxjs/operators';
import { CommodityPricing } from '../../models/commodity-pricing.model';
import { HttpErrorResponse } from '@angular/common/http';

// See https://stackoverflow.com/a/51606362
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    return control.parent.hasError('invalidPickupRange');
  }
}

@Component({
  selector: 'app-ltl-contact-me-modal',
  templateUrl: './ltl-contact-me-modal.component.html',
  styleUrls: ['./ltl-contact-me-modal.component.scss']
})
export class LtlContactMeModalComponent implements OnInit, OnDestroy {
  stop$ = new Subject<boolean>();
  formGroup: FormGroup;
  feedback: [FeedbackTypes, string | string[]];
  mask = Masks.phone;
  matcher = new MyErrorStateMatcher();
  loading: boolean;

  constructor(
    private dialogRef: MatDialogRef<LtlContactMeModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: RateQuote,
    private rateQuoteService: RateQuoteService,
    private fb: FormBuilder,
    private utilService: UtilService,
    private snackbar: SnackbarService,
    public formService: FormService,
    private addressDialog: AddressSelectorDialogService,
    public auth: AuthService,
    private dialog: DialogService
  ) {}

  ngOnInit() {
    this.data.commodities = this.data.commodities.filter(c => c.deficitRate === false);
    this.initForm();
    this.dialogRef.disableClose = true;
    this.dialogRef
      .backdropClick()
      .pipe(takeUntil(this.stop$))
      .subscribe(next => {
        this.confirmClose();
      });
  }

  ngOnDestroy() {
    this.stop$.next(true);
    this.stop$.unsubscribe();
  }

  cancel() {
    this.confirmClose();
  }

  private confirmClose() {
    if (this.formGroup.dirty) {
      this.dialog
        .confirm(
          `Are you sure?`,
          `You have changes on the form.  If you close, you will lose your progress.`
        )
        .subscribe(confirm => {
          if (confirm) {
            this.dialogRef.close();
          }
        });
    } else {
      this.dialogRef.close();
    }
  }

  onSubmit() {
    if (this.formGroup.valid) {
      const contact = new ContactRequest();
      contact.comments = this.comments.value;
      contact.contactEmail = this.contactEmail.value;
      contact.contactName = this.contactName.value;
      contact.contactPhone = this.contactPhone.value;
      contact.contactPhoneExt = this.contactExtension.value;
      try {
        contact.pickupDate = formatDate(this.pickupDate.value, 'MM/dd/yyyy', 'en-US');
      } catch (err) {
        console.warn(`Pickup date formatting failed.`);
      }
      const availableByHour = this.availableByHour.value;
      const availableByMinutes = this.availableByMinutes.value;
      const availableByAmPm = this.availableByAmPm.value;
      const closesByHour = this.closesByHour.value;
      const closesByMinutes = this.closesByMinutes.value;
      const closesByAmPm = this.closesByAmPm.value;

      contact.pickupAvail = `${availableByHour}:${availableByMinutes} ${availableByAmPm}`;
      contact.pickupClose = `${closesByHour}:${closesByMinutes} ${closesByAmPm}`;

      contact.quoteId = this.data.quoteID;
      contact.quoteRefNum = +this.data.quoteRefNum;
      contact.stackable = this.stackable.value;

      for (let cf of this.commodities.controls as Array<FormGroup>) {
        const description = cf.controls['description'].value;
        const length = cf.controls['length'].value;
        const width = cf.controls['width'].value;
        const height = cf.controls['height'].value;
        const pieceType = cf.controls['pieceType'].value;
        const pieces = cf.controls['pieces'].value;
        const shipClass = cf.controls['shipClass'].value;
        const weight = cf.controls['weight'].value;
        const rate = cf.controls['rate'].value;
        const charge = cf.controls['charge'].value;

        const cp = new CommodityPricing();
        cp.charge = charge;
        cp.rate = rate;
        cp.commodity.description = description;
        cp.commodity.dimensions = {
          height: height,
          width: width,
          length: length
        };
        cp.commodity.pieceType = pieceType;
        cp.commodity.pieces = pieces;
        cp.commodity.shipClass = shipClass;
        cp.commodity.weight = weight;

        contact.commodities.push(cp);
      }

      this.loading = true;
      this.rateQuoteService
        .selectQuote(this.data.quoteID)
        .pipe(switchMap(() => this.rateQuoteService.ltlContactMe(contact)))
        .subscribe(
          next => {
            let msg = `Contact Me request sent successfully.`;
            if (next.message) {
              msg = next.message;
            }
            this.snackbar.success(msg);
            this.dialogRef.close(true);
          },
          err => {
            this.loading = false;
            let errMsg:
              | string
              | string[] = `An error occurred while submitting the form.  Please try again.`;
            if (err instanceof HttpErrorResponse) {
              if (err.error && Array.isArray(err.error) && err.error.length) {
                errMsg = (err.error as Array<ServiceResponse>).reduce((accum, curr) => {
                  if (curr.message) {
                    accum.push(curr.message);
                  }

                  return accum;
                }, []);
              }
            }
            this.feedback = ['error', errMsg];
          },
          () => (this.loading = false)
        );
    } else {
      validateFormFields(this.formGroup);
    }
  }

  openAddressDialog() {
    this.addressDialog.openAddressDialog().subscribe(next => {
      if (next) {
        this.contactName.setValue(`${next.firstName} ${next.lastName}`);
        this.contactPhone.setValue(MyEstesFormatters.formatPhone(next.phone));
        this.contactEmail.setValue(next.email);
        this.contactExtension.setValue(next.phoneExt);
      }
    });
  }

  checkPickupRange(group: FormGroup) {
    const availableByHour = +group.controls.availableByHour.value;
    const availableByMinutes = +group.controls.availableByMinutes.value;
    const closesByHour = +group.controls.closesByHour.value;
    const closesByMinutes = +group.controls.closesByMinutes.value;
    const availableByAmPm = group.controls.availableByAmPm.value as string;
    const closesByAmPm = group.controls.closesByAmPm.value as string;

    let validRange = true;

    /**
     * Check AM/PM first.
     */
    if (closesByAmPm.toUpperCase() === 'AM' && availableByAmPm.toUpperCase() === 'PM') {
      return { invalidPickupRange: true };
    } else if (closesByAmPm.toUpperCase() === 'PM' && availableByAmPm.toUpperCase() === 'AM') {
      return null;
    }

    /**
     * Check hours and minutes.  If hours are the same, check minutes.
     */
    if (closesByHour < availableByHour) {
      validRange = false;
    } else if (closesByHour === availableByHour) {
      if (closesByMinutes < availableByMinutes) {
        validRange = false;
      }
    }

    return validRange ? null : { invalidPickupRange: true };
  }

  private initForm() {
    this.formGroup = this.fb.group(
      {
        contactName: ['', [MyEstesValidators.required, Validators.maxLength(100)]],
        contactPhone: [
          '',
          [
            MyEstesValidators.required,
            patternValidator(RegEx.phoneNumber, MessageConstants.invalidPhone)
          ]
        ],
        contactEmail: [
          '',
          [
            MyEstesValidators.required,
            patternValidator(RegEx.email, MessageConstants.invalidEmail),
            Validators.maxLength(255)
          ]
        ],
        contactExtension: [
          '',
          [
            patternValidator(RegEx.numbers, MessageConstants.invalidNumbers),
            Validators.maxLength(7)
          ]
        ],
        comments: ['', [Validators.maxLength(100)]],
        availableByHour: ['08', [MyEstesValidators.required]],
        availableByMinutes: ['00', [MyEstesValidators.required]],
        availableByAmPm: ['AM', [MyEstesValidators.required]],
        closesByHour: ['05', [MyEstesValidators.required]],
        closesByMinutes: ['00', [MyEstesValidators.required]],
        closesByAmPm: ['PM', [MyEstesValidators.required]],
        pickupDate: ['', [MyEstesValidators.required, dateValidator()]],
        stackable: [false, [MyEstesValidators.required]],
        commodities: new FormArray([])
      },
      { validator: this.checkPickupRange }
    );

    for (let c of this.data.commodities) {
      const fg = this.fb.group(new CommodityPricingForm(c));
      this.commodities.push(fg);
    }
  }

  get stackable() {
    return this.formGroup.controls['stackable'];
  }
  get comments() {
    return this.formGroup.controls['comments'];
  }
  get contactName() {
    return this.formGroup.controls['contactName'];
  }
  get contactPhone() {
    return this.formGroup.controls['contactPhone'];
  }
  get contactExtension() {
    return this.formGroup.controls['contactExtension'];
  }
  get contactEmail() {
    return this.formGroup.controls['contactEmail'];
  }
  get pickupDate() {
    return this.formGroup.controls['pickupDate'];
  }
  get availableByHour() {
    return this.formGroup.controls['availableByHour'];
  }
  get availableByMinutes() {
    return this.formGroup.controls['availableByMinutes'];
  }
  get availableByAmPm() {
    return this.formGroup.controls['availableByAmPm'];
  }
  get closesByHour() {
    return this.formGroup.controls['closesByHour'];
  }
  get closesByMinutes() {
    return this.formGroup.controls['closesByMinutes'];
  }
  get closesByAmPm() {
    return this.formGroup.controls['closesByAmPm'];
  }
  get commodities(): FormArray {
    return this.formGroup.controls['commodities'] as FormArray;
  }
  get today() {
    return new Date();
  }
  get thirtyDaysAway() {
    const thirtyDaysAway = new Date(this.today);
    thirtyDaysAway.setDate(thirtyDaysAway.getDate() + 30);

    return thirtyDaysAway;
  }
}
