import { Injectable } from '@angular/core';
import { FormGroup, FormBuilder, FormArray } from '@angular/forms';
import { BehaviorSubject, Observable, Subject, merge } from 'rxjs';
import {
  RequestorInformation,
  PickupInformation,
  RoleEnum,
  BolSection,
  BolSectionService,
  BillOfLading,
  PickupDetailInfoForm,
  PickupDetailInfo
} from '../models';
import { tap, takeUntil, filter, take } from 'rxjs/operators';
import { BolService } from './bol.service';
import { DialogService } from 'common';
import { AccountInfo } from 'common';
import { formatDate } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class PickupRequestFormService extends BolSectionService {
  // private hasPickupRequest = false;
  private stop$ = new Subject<boolean>();
  private generatePickupRequestSource: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(
    false
  );
  private pickupAccessorialSelectedSource: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(
    false
  );
  private accountInfoSource: BehaviorSubject<AccountInfo> = new BehaviorSubject<AccountInfo>(null);

  private roleSelectedSource: BehaviorSubject<
    [RoleEnum | null, string | null]
  > = new BehaviorSubject<[RoleEnum | null, string | null]>([null, null]);

  private pickupDetailInfoForm: BehaviorSubject<FormGroup | undefined> = new BehaviorSubject(
    this.fb.group(new PickupDetailInfoForm(new PickupDetailInfo()))
  );

  pickupDetailInfoForm$: Observable<FormGroup> = this.pickupDetailInfoForm.asObservable().pipe(
    tap(fg => {
      this.stop$.next(true);

      const pickupRequest = fg.controls['pickupRequest'];

      pickupRequest.valueChanges.pipe(takeUntil(this.stop$)).subscribe(val => {
        if (val === false) {
          if (fg.dirty) {
            this.dialog
              .confirm(
                `Are you sure?`,
                `If you proceed, all of your progress for this pickup section will be deleted.`
              )
              .subscribe(next => {
                if (next === true) {
                  this.generatePickupRequestSource.next(val);
                  this.onRoleChanged(null, null);
                  this.onAccountInfoChanged(null);
                  this.resetForm();
                } else {
                  pickupRequest.setValue(true, { emitEvent: false });
                }
              });
          } else {
            this.generatePickupRequestSource.next(val);
            this.onAccountInfoChanged(null);
            this.resetForm();
          }
        } else if (val === true) {
          this.generatePickupRequestSource.next(true);
        }
      });

      this.generatePickupRequestSource.next(pickupRequest.value === true ? true : false);

      if (this.bolService.modified !== true) {
        fg.valueChanges
          .pipe(
            filter(() => fg.dirty === true),
            take(1),
            takeUntil(merge(this.stop$, this.bolService.stopModified$))
          )
          .subscribe(() => {
            // console.log(`Form modified: Pickup Request`);
            this.bolService.setModified();
          });
      }
    })
  );
  generatePickupRequest$: Observable<boolean> = this.generatePickupRequestSource.asObservable();
  pickupAccessorialSelected$: Observable<
    boolean
  > = this.pickupAccessorialSelectedSource.asObservable();
  roleSelected$: Observable<
    [RoleEnum | null, string | null]
  > = this.roleSelectedSource.asObservable();

  // accountInfo$ = this.accountInfoSource.asObservable();

  constructor(
    private fb: FormBuilder,
    private bolService: BolService,
    private dialog: DialogService
  ) {
    super();
  }

  resetForm(p: PickupDetailInfo = new PickupDetailInfo()) {
    const newForm = this.fb.group(new PickupDetailInfoForm(p));

    // this.hasPickupRequest = this.isPopulatedPickupRequest(p);

    this.pickupDetailInfoForm.next(newForm);
  }

  onPickupAccessorialSelectedChange(isSelected: boolean) {
    this.pickupAccessorialSelectedSource.next(isSelected);
  }

  onRoleChanged(role: RoleEnum | null, accountCode: string | null = null) {
    this.roleSelectedSource.next([role, accountCode]);
  }

  onAccountInfoChanged(account: AccountInfo) {
    this.accountInfoSource.next(account);
  }

  valid(): boolean {
    return this.generatePickupRequestSource.getValue()
      ? this.isValid(this.pickupDetailInfoForm.getValue())
      : true;
  }

  isPopulatedPickupRequest(p: PickupDetailInfo) {
    // Omitting rejected, because it always defaults to true.
    return !!(
      p.accepted ||
      p.accountCode ||
      p.completed ||
      p.email ||
      p.endTime ||
      p.food ||
      p.freeze ||
      p.hookOrDrop ||
      p.liftgateRequired ||
      p.name ||
      p.noStackPallet ||
      p.oversize ||
      p.phone ||
      p.phoneExt ||
      p.pickupDate ||
      p.poision ||
      p.role ||
      p.specialInstruction ||
      p.startTime
    );
  }

  populateModel(bol: BillOfLading) {
    const form = this.pickupDetailInfoForm.getValue();
    const hasPickupRequest = form.controls['pickupRequest'].value;

    // accepted = new FormControl(false);
    // accountCode = new FormControl('');
    // completed = new FormControl(false);
    // email = new FormControl('');
    // endTime = new FormControl('');
    // food = new FormControl(false);
    // freeze = new FormControl(false);
    // hookOrDrop = new FormControl(false);
    // liftgateRequired = new FormControl(false);
    // name = new FormControl('');
    // noStackPallet = new FormControl(false);
    // oversize = new FormControl(false);
    // phone = new FormControl('');
    // phoneExt = new FormControl('');
    // pickupDate = new FormControl('');
    // poison = new FormControl(false);
    // rejected = new FormControl(false);
    // role = new FormControl('');
    // specialInstruction = new FormControl('');
    // availableByHour = new FormControl('8');
    // availableByMinutes = new FormControl('00');
    // availableByAmPm = new FormControl('AM');
    // closesByHour = new FormControl('5');
    // closesByMinutes = new FormControl('00');
    // closesByAmPm = new FormControl('PM');

    if (hasPickupRequest) {
      bol.generalInfo.pickupRequest = true;
      bol.pickupDetailInfo.accepted = form.controls['accepted'].value;
      bol.pickupDetailInfo.accountCode = form.controls['accountCode'].value;
      bol.pickupDetailInfo.completed = form.controls['completed'].value;
      bol.pickupDetailInfo.email = form.controls['email'].value;
      bol.pickupDetailInfo.food = form.controls['food'].value;
      bol.pickupDetailInfo.freeze = form.controls['freeze'].value;
      bol.pickupDetailInfo.hookOrDrop = form.controls['hookOrDrop'].value;
      bol.pickupDetailInfo.liftgateRequired = form.controls['liftgateRequired'].value;
      bol.pickupDetailInfo.name = form.controls['name'].value;
      bol.pickupDetailInfo.noStackPallet = form.controls['noStackPallet'].value;
      bol.pickupDetailInfo.oversize = form.controls['oversize'].value;
      bol.pickupDetailInfo.phone = form.controls['phone'].value;
      bol.pickupDetailInfo.phoneExt = form.controls['phoneExt'].value;
      try {
        bol.pickupDetailInfo.pickupDate = formatDate(
          form.controls['pickupDate'].value,
          'MM/dd/yyyy',
          'en-US'
        );
      } catch (err) {
        console.warn(`Pickup date formatting failed.`);
      }
      bol.pickupDetailInfo.poision = form.controls['poison'].value;
      bol.pickupDetailInfo.rejected = form.controls['rejected'].value;
      bol.pickupDetailInfo.role = form.controls['role'].value || null;
      bol.pickupDetailInfo.specialInstruction = form.controls['specialInstruction'].value;

      const availableByHour = form.controls['availableByHour'].value;
      const availableByMinutes = form.controls['availableByMinutes'].value;
      const availableByAmPm = form.controls['availableByAmPm'].value;
      const closesByHour = form.controls['closesByHour'].value;
      const closesByMinutes = form.controls['closesByMinutes'].value;
      const closesByAmPm = form.controls['closesByAmPm'].value;

      bol.pickupDetailInfo.startTime = `${availableByHour}:${availableByMinutes} ${availableByAmPm}`;
      bol.pickupDetailInfo.endTime = `${closesByHour}:${closesByMinutes} ${closesByAmPm}`;
    } else {
      bol.generalInfo.pickupRequest = false;
      bol.pickupDetailInfo = null;
    }
  }
}
