import { Injectable, OnDestroy } from '@angular/core';
import { FormGroup, FormBuilder, FormArray } from '@angular/forms';
import { BehaviorSubject, Observable, Subject, merge } from 'rxjs';
import {
  EmailAndFaxNotificationForm,
  EmailAndFaxNotification,
  BolSection,
  BolSectionService,
  BillOfLading,
  PickupDetailInfo
} from '../models';
import { tap, filter, take, takeUntil } from 'rxjs/operators';
import { BolService } from './bol.service';

@Injectable({
  providedIn: 'root'
})
export class EmailAndFaxNotificationFormService extends BolSectionService implements OnDestroy {
  private stop$ = new Subject<boolean>();
  private proNumberResolutionSource = new Subject<[boolean, string]>();
  private emailFaxForm: BehaviorSubject<FormGroup | undefined> = new BehaviorSubject(
    this.fb.group(
      new EmailAndFaxNotificationForm(new EmailAndFaxNotification(), new PickupDetailInfo())
    )
  );

  emailFaxForm$: Observable<FormGroup> = this.emailFaxForm.asObservable().pipe(
    tap(fg => {
      const hasEmail = fg.controls['hasEmail'];
      const hasFax = fg.controls['hasFax'];

      const consigneeEmail = fg.controls['consigneeEmail'];
      const otherEmail = fg.controls['otherEmail'];
      const shipperEmail = fg.controls['shipperEmail'];
      const thirdPartyEmail = fg.controls['thirdPartyEmail'];
      const consigneeEmailBolUpdate = fg.controls['consigneeEmailBolUpdate'];
      const otherEmailBolUpdate = fg.controls['otherEmailBolUpdate'];
      const shipperEmailBolUpdate = fg.controls['shipperEmailBolUpdate'];
      const thirdPartyEmailBolUpdate = fg.controls['thirdPartyEmailBolUpdate'];
      const consigneeEmailTrackingUpdate = fg.controls['consigneeEmailTrackingUpdate'];
      const otherEmailTrackingUpdate = fg.controls['otherEmailTrackingUpdate'];
      const shipperEmailTrackingUpdate = fg.controls['shipperEmailTrackingUpdate'];
      const thirdPartyEmailTrackingUpdate = fg.controls['thirdPartyEmailTrackingUpdate'];
      const consigneeEmailPickupNotice = fg.controls['consigneeEmailPickupNotice'];
      const otherEmailPickupNotice = fg.controls['otherEmailPickupNotice'];
      const shipperEmailPickupNotice = fg.controls['shipperEmailPickupNotice'];
      const thirdPartyEmailPickupNotice = fg.controls['thirdPartyEmailPickupNotice'];
      const consigneeEmailShippingLabel = fg.controls['consigneeEmailShippingLabel'];
      const otherEmailShippingLabel = fg.controls['otherEmailShippingLabel'];
      const shipperEmailShippingLabel = fg.controls['shipperEmailShippingLabel'];
      const thirdPartyEmailShippingLabel = fg.controls['thirdPartyEmailShippingLabel'];
      const consigneeFaxBolUpdate = fg.controls['consigneeFaxBolUpdate'];
      const otherFaxBolUpdate = fg.controls['otherFaxBolUpdate'];
      const shipperFaxBolUpdate = fg.controls['shipperFaxBolUpdate'];
      const thirdPartyFaxBolUpdate = fg.controls['thirdPartyFaxBolUpdate'];
      const consigneeFax = fg.controls['consigneeFax'];
      const otherFax = fg.controls['otherFax'];
      const shipperFax = fg.controls['shipperFax'];
      const thirdPartyFax = fg.controls['thirdPartyFax'];

      hasEmail.valueChanges.pipe(takeUntil(this.stop$)).subscribe(next => {
        if (next !== true) {
          consigneeEmail.setValue('');
          otherEmail.setValue('');
          shipperEmail.setValue('');
          thirdPartyEmail.setValue('');
          consigneeEmailBolUpdate.setValue(false);
          otherEmailBolUpdate.setValue(false);
          shipperEmailBolUpdate.setValue(false);
          thirdPartyEmailBolUpdate.setValue(false);
          consigneeEmailTrackingUpdate.setValue(false);
          otherEmailTrackingUpdate.setValue(false);
          shipperEmailTrackingUpdate.setValue(false);
          thirdPartyEmailTrackingUpdate.setValue(false);
          consigneeEmailPickupNotice.setValue(false);
          otherEmailPickupNotice.setValue(false);
          shipperEmailPickupNotice.setValue(false);
          thirdPartyEmailPickupNotice.setValue(false);
          consigneeEmailShippingLabel.setValue(false);
          otherEmailShippingLabel.setValue(false);
          shipperEmailShippingLabel.setValue(false);
          thirdPartyEmailShippingLabel.setValue(false);
          consigneeFaxBolUpdate.setValue(false);
          otherFaxBolUpdate.setValue(false);
          shipperFaxBolUpdate.setValue(false);
          thirdPartyFaxBolUpdate.setValue(false);
        }
      });

      hasFax.valueChanges.pipe(takeUntil(this.stop$)).subscribe(next => {
        if (next !== true) {
          consigneeFax.setValue('');
          shipperFax.setValue('');
          thirdPartyFax.setValue('');
          otherFax.setValue('');
        }
      });

      merge(consigneeFax.valueChanges, shipperFax.valueChanges, thirdPartyFax.valueChanges, otherFax.valueChanges).subscribe(val => {
        consigneeFaxBolUpdate.setValue(consigneeFax.value ? true : false);
        otherFaxBolUpdate.setValue(otherFax.value ? true : false);
        shipperFaxBolUpdate.setValue(shipperFax.value ? true : false);
        thirdPartyFaxBolUpdate.setValue(thirdPartyFax.value ? true : false);
      });

      if (this.bolService.modified !== true) {
        fg.valueChanges
          .pipe(
            filter(() => fg.dirty === true),
            take(1),
            takeUntil(merge(this.stop$, this.bolService.stopModified$))
          )
          .subscribe(() => {
            // console.log(`Form modified: Email and Fax Notifications`);
            this.bolService.setModified();
          });
      }
    })
  );

  proNumberResolution: Observable<
    [boolean, string]
  > = this.proNumberResolutionSource.asObservable();

  constructor(private fb: FormBuilder, private bolService: BolService) {
    super();
  }

  onProNumberResolved(autoAssignPro: boolean, proNumber: string) {
    this.proNumberResolutionSource.next([autoAssignPro, proNumber]);
  }

  valid(): boolean {
    return this.isValid(this.emailFaxForm.getValue());
  }

  ngOnDestroy() {
    this.stop$.next(true);
    this.stop$.unsubscribe();
  }

  resetForm(
    e: EmailAndFaxNotification = new EmailAndFaxNotification(),
    p = new PickupDetailInfo()
  ) {
    const newForm = this.fb.group(new EmailAndFaxNotificationForm(e, p));

    this.emailFaxForm.next(newForm);
  }

  populateModel(bol: BillOfLading) {
    const form = this.emailFaxForm.getValue();
    const model = bol.emailAndFaxNotification;

    // fax = new FormControl('');
    // consigneeEmail = new FormControl('');
    // otherEmail = new FormControl('');
    // shipperEmail = new FormControl('');
    // thirdPartyEmail = new FormControl('');
    // consigneeEmailBolUpdate = new FormControl(false);
    // otherEmailBolUpdate = new FormControl(false);
    // shipperEmailBolUpdate = new FormControl(false);
    // thirdPartyEmailBolUpdate = new FormControl(false);
    // consigneeEmailTrackingUpdate = new FormControl(false);
    // otherEmailTrackingUpdate = new FormControl(false);
    // shipperEmailTrackingUpdate = new FormControl(false);
    // thirdPartyEmailTrackingUpdate = new FormControl(false);
    // consigneeEmailPickupNotice = new FormControl(false);
    // otherEmailPickupNotice = new FormControl(false);
    // shipperEmailPickupNotice = new FormControl(false);
    // thirdPartyEmailPickupNotice = new FormControl(false);
    // consigneeEmailShippingLabel = new FormControl(false);
    // otherEmailShippingLabel = new FormControl(false);
    // shipperEmailShippingLabel = new FormControl(false);
    // thirdPartyEmailShippingLabel = new FormControl(false);
    // consigneeFaxBolUpdate = new FormControl(false);
    // otherFaxBolUpdate = new FormControl(false);
    // shipperFaxBolUpdate = new FormControl(false);
    // thirdPartyFaxBolUpdate = new FormControl(false);
    // consigneeFax = new FormControl('');
    // otherFax = new FormControl('');
    // shipperFax = new FormControl('');
    // thirdPartyFax = new FormControl('');
    // acceptedNotify = new FormControl(false);
    // completedNotify = new FormControl(false);
    // rejectedNotify = new FormControl(false);

    model.fax = form.controls['fax'].value;
    model.consigneeEmail = form.controls['consigneeEmail'].value;
    model.otherEmail = form.controls['otherEmail'].value;
    model.shipperEmail = form.controls['shipperEmail'].value;
    model.thirdPartyEmail = form.controls['thirdPartyEmail'].value;
    model.consigneeEmailBolUpdate = form.controls['consigneeEmailBolUpdate'].value;
    model.otherEmailBolUpdate = form.controls['otherEmailBolUpdate'].value;
    model.shipperEmailBolUpdate = form.controls['shipperEmailBolUpdate'].value;
    model.thirdPartyEmailBolUpdate = form.controls['thirdPartyEmailBolUpdate'].value;
    model.consigneeEmailTrackingUpdate = form.controls['consigneeEmailTrackingUpdate'].value;
    model.otherEmailTrackingUpdate = form.controls['otherEmailTrackingUpdate'].value;
    model.shipperEmailTrackingUpdate = form.controls['shipperEmailTrackingUpdate'].value;
    model.thirdPartyEmailTrackingUpdate = form.controls['thirdPartyEmailTrackingUpdate'].value;
    model.consigneeEmailPickupNotice = form.controls['consigneeEmailPickupNotice'].value;
    model.otherEmailPickupNotice = form.controls['otherEmailPickupNotice'].value;
    model.shipperEmailPickupNotice = form.controls['shipperEmailPickupNotice'].value;
    model.thirdPartyEmailPickupNotice = form.controls['thirdPartyEmailPickupNotice'].value;
    model.consigneeEmailShippingLabel = form.controls['consigneeEmailShippingLabel'].value;
    model.otherEmailShippingLabel = form.controls['otherEmailShippingLabel'].value;
    model.shipperEmailShippingLabel = form.controls['shipperEmailShippingLabel'].value;
    model.thirdPartyEmailShippingLabel = form.controls['thirdPartyEmailShippingLabel'].value;
    model.consigneeFaxBolUpdate = form.controls['consigneeFaxBolUpdate'].value;
    model.otherFaxBolUpdate = form.controls['otherFaxBolUpdate'].value;
    model.shipperFaxBolUpdate = form.controls['shipperFaxBolUpdate'].value;
    model.thirdPartyFaxBolUpdate = form.controls['thirdPartyFaxBolUpdate'].value;
    model.consigneeFax = form.controls['consigneeFax'].value;
    model.otherFax = form.controls['otherFax'].value;
    model.shipperFax = form.controls['shipperFax'].value;
    model.thirdPartyFax = form.controls['thirdPartyFax'].value;

    if (!!bol.pickupDetailInfo) {
      bol.pickupDetailInfo.accepted = form.controls['acceptedNotify'].value;
      bol.pickupDetailInfo.completed = form.controls['completedNotify'].value;
      bol.pickupDetailInfo.rejected = form.controls['rejectedNotify'].value;
    }
  }
}
