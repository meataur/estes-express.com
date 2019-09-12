import { Component, OnInit, OnDestroy, OnChanges, SimpleChanges } from '@angular/core';
import { FormGroup, Validators } from '@angular/forms';
import { FormService, Masks, MyEstesValidators } from 'common';
import { BolDetailsFormService } from '../../services/bol-details-form.service';
import { BolSourceFormService } from '../../services/bol-source-form.service';
import { Subscription, merge } from 'rxjs';
import { DocumentTypeEnum, BolSection, Draft, BillOfLading } from '../../models';
import { EmailAndFaxNotificationFormService } from '../../services/email-fax-notification-form.service';

@Component({
  selector: 'app-bol-details',
  templateUrl: './bol-details.component.html',
  styleUrls: ['./bol-details.component.scss']
})
export class BolDetailsComponent extends BolSection implements OnInit, OnDestroy, OnChanges {
  vicsBol = false;
  formGroup: FormGroup;
  formSub: Subscription;
  bolSourceFormSub: Subscription;
  notificationFormSub: Subscription;
  proNumberMask = Masks.pronumber;

  constructor(
    public formService: FormService,
    private bolDetailsFormSerice: BolDetailsFormService,
    private bolSourceFormService: BolSourceFormService,
    private notificationFormService: EmailAndFaxNotificationFormService
  ) {
    super();
  }

  ngOnInit() {
    this.formSub = this.bolDetailsFormSerice.bolDetailsForm$.subscribe(fg => {
      this.formGroup = fg;
      /**
       * To fix dp2-4500
       */
      if ( !!this.draft ) {
        if (!!this.draft.generalInfo.proNumber === false) {
          this.autoAssignPro.setValue(this.draft.generalInfo.assignProNumber);
        }

      }
    });

    this.bolSourceFormSub = this.bolSourceFormService.documentTypeChanged$.subscribe(next => {
      if (next === DocumentTypeEnum.V) {
        this.vicsBol = true;
      } else {
        this.vicsBol = false;
        this.masterBol.reset(null);
        this.masterBolNumber.reset('');
        this.masterBolNumber.clearValidators();
      }
      this.masterBolNumber.updateValueAndValidity();

    });

    this.notificationFormSub = this.notificationFormService.proNumberResolution.subscribe(next => {
      const autoAssignPro = next[0];
      const proNumber = next[1];

      this.autoAssignPro.setValue(autoAssignPro, { emitEvent: false });
      this.reservedPro.setValue(proNumber, { emitEvent: false });
    });
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.draft && changes.draft.currentValue) {
      this.bolDetailsFormSerice.resetForm((changes.draft.currentValue as BillOfLading).generalInfo);
    }
  }

  ngOnDestroy() {
    if (this.formSub) {
      this.formSub.unsubscribe();
    }
    if (this.bolSourceFormSub) {
      this.bolSourceFormSub.unsubscribe();
    }
    if (this.notificationFormSub) {
      this.notificationFormSub.unsubscribe();
    }
  }

  get bolReferenceNumber() {
    return this.formGroup.controls['bolReferenceNumber'];
  }
  get bolDate() {
    return this.formGroup.controls['bolDate'];
  }
  get autoAssignPro() {
    return this.formGroup.controls['autoAssignPro'];
  }
  get reservedPro() {
    return this.formGroup.controls['reservedPro'];
  }
  get masterBol() {
    return this.formGroup.controls['masterBol'];
  }
  get masterBolNumber() {
    return this.formGroup.controls['masterBolNumber'];
  }
  get today() {
    return new Date();
  }
  get tomorrow() {
    const tomorrow = new Date(this.today);
    tomorrow.setDate(tomorrow.getDate() + 1);

    return tomorrow;
  }
  get thirtyDaysAway() {
    const thirtyDaysAway = new Date(this.today);
    thirtyDaysAway.setDate(thirtyDaysAway.getDate() + 30);

    return thirtyDaysAway;
  }
}
