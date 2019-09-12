import {
  Component,
  OnInit,
  OnDestroy,
  AfterContentInit,
  ViewChild,
  OnChanges,
  SimpleChanges
} from "@angular/core";
import {
  FormGroup,
  FormBuilder,
  Validators,
  FormControl,
  FormArray,
  FormGroupDirective,
  AbstractControl
} from "@angular/forms";
import {
  FormService,
  patternValidator,
  startEndTimeValidator,
  RegEx,
  MessageConstants,
  Masks,
  SnackbarService,
  DialogService,
  AuthService,
  UtilService,
  SubAccount,
  AddressSelectorDialogService,
  MyEstesValidators,
  MyEstesFormatters
} from "common";
import { MatCheckboxChange } from "@angular/material";
import { BolService } from "../../services/bol.service";
import { HttpErrorResponse } from "@angular/common/http";
import { RoleEnum, BolSection, BillOfLading } from "../../models";
import { PickupOptionsModalComponent } from "../pickup-options-modal/pickup-options-modal.component";
import { pickupServiceHtml } from "./service-modal-markup";
import {
  Observable,
  Subscription,
  Subject,
  empty,
  of,
  forkJoin,
  combineLatest
} from "rxjs";
import { PickupRequestFormService } from "../../services/pickup-request-form.service";
import {
  takeUntil,
  take,
  switchMap,
  tap,
  distinctUntilChanged,
  map,
  catchError,
  merge,
  startWith
} from "rxjs/operators";

@Component({
  selector: "app-pickup-request",
  templateUrl: "./pickup-request.component.html",
  styleUrls: ["./pickup-request.component.scss"]
})
export class PickupRequestComponent extends BolSection
  implements OnInit, OnDestroy, AfterContentInit, OnChanges {
  // private accountCodeSource = new Subject<string>();
  // private accountCode$ = this.accountCodeSource.asObservable();
  @ViewChild(FormGroupDirective) myForm: FormGroupDirective;
  isPickupAccessorialSelected: boolean;
  stop$ = new Subject<boolean>();
  formSub: Subscription;
  formGroup: FormGroup;
  phoneMask = Masks.phone;
  loading = false;
  showAccountCode = false;
  showAccountCodeInput = false;
  showAccountCodeDropdown = false;
  isTimeInvalid = false;
  roles: Array<{ value: string; text: string }> = Object.keys(RoleEnum).map(
    key => ({
      value: key,
      text: RoleEnum[key]
    })
  );
  pickupAccessorialsHtml = `<p>For terms and conditions regarding the pickup accessorials and services listed, please refer to our <a href="/resources/fees-and-surcharges/rules-tariff" target="_blank">EXLA 105 series tariff</a>.</p>`;
  freezeHtml = pickupServiceHtml.freeze;
  liftgateHtml = pickupServiceHtml.liftgate;
  oversizeHtml = pickupServiceHtml.oversize;
  noStackPalletsHtml = pickupServiceHtml.stackPallets;
  foodHtml = pickupServiceHtml.food;
  poisonHtml = pickupServiceHtml.poison;
  accountInfoSub: Subscription;
  timeSub: Subscription;
  loadingAccount = false;
  showAccountCodeLabel: boolean;

  constructor(
    public formService: FormService,
    private bolService: BolService,
    private snackbarService: SnackbarService,
    private dialogService: DialogService,
    private authService: AuthService,
    private utilService: UtilService,
    private pickupRequestFormService: PickupRequestFormService,
    private addressDialog: AddressSelectorDialogService,
    private auth: AuthService
  ) {
    super();
  }

  get accountCodeValid() {
    if (this.accountCode.value) {
      if (this.auth.isLocalAccount) {
        return true;
      } else {
        return this.accountCode.valid;
      }
    }
    return false;
  }

  ngOnInit() {
    this.formSub = this.pickupRequestFormService.pickupDetailInfoForm$.subscribe(
      fg => {
        this.stop$.next(true);
        this.formGroup = fg;
        this.role.valueChanges.pipe(takeUntil(this.stop$)).subscribe(next => {
          if (this.pickupRequest.value === true) {
            if (RoleEnum[next as string] === RoleEnum.SHIPPER) {
              this.showAccountCode = true;
              if (!this.auth.isLocalAccount) {
                this.accountCode.setAsyncValidators(
                  this.validateAccountCodeInput.bind(this)
                );
              }
              this.accountCode.setValidators([
                MyEstesValidators.required,
                Validators.maxLength(7),
                Validators.minLength(7)
              ]);
              if (this.authService.isLocalAccount) {
                const token = this.authService.getAuthToken();
                this.accountCode.setValue(token.accountCode);
                // this.accountCode.disable();
                this.pickupRequestFormService.onRoleChanged(
                  next,
                  this.accountCode.value
                );
              } else if (this.accountCode.valid) {
                this.pickupRequestFormService.onRoleChanged(
                  next,
                  this.accountCode.value
                );
              }
              this.accountCode.updateValueAndValidity({ emitEvent: true });
            } else {
              this.showAccountCode = false;
              this.accountCode.clearValidators();
              this.accountCode.clearAsyncValidators();
              this.pickupRequestFormService.onRoleChanged(
                next,
                this.accountCode.value
              );
            }
            this.accountCode.updateValueAndValidity();
          }
        });
        this.role.updateValueAndValidity();
        this.intitTimeSub();
      }
    );
    this.role.updateValueAndValidity();
    this.pickupRequestFormService.pickupAccessorialSelected$
      .pipe(take(1))
      .subscribe(isSelected => (this.isPickupAccessorialSelected = isSelected));

    if (this.authService.isLocalAccount) {
      this.showAccountCodeInput = true;
      this.showAccountCodeLabel = false;
    } else if (
      this.authService.isGroupAccount ||
      this.authService.is91Account ||
      this.authService.isNationalAccount
    ) {
      this.showAccountCode = true;
      this.showAccountCodeInput = false;
      this.showAccountCodeLabel = true;
    }
  }

  intitTimeSub() {
    this.timeSub = combineLatest(
      this.availableByHour.valueChanges.pipe(
        startWith(this.availableByHour.value)
      ),
      this.availableByMinutes.valueChanges.pipe(
        startWith(this.availableByMinutes.value)
      ),
      this.availableByAmPm.valueChanges.pipe(
        startWith(this.availableByAmPm.value)
      ),
      this.closesByHour.valueChanges.pipe(startWith(this.closesByHour.value)),
      this.closesByMinutes.valueChanges.pipe(
        startWith(this.closesByMinutes.value)
      ),
      this.closesByAmPm.valueChanges.pipe(startWith(this.closesByAmPm.value))
    ).subscribe(() => {
      this.isTimeInvalid = startEndTimeValidator(
        this.availableByHour,
        this.availableByMinutes,
        this.availableByAmPm,
        this.closesByHour,
        this.closesByMinutes,
        this.closesByAmPm
      );
    });
  }

  validateAccountCodeInput(control: AbstractControl) {
    // console.log(`validatingAccountCodeInput: ${control.value}`);
    return !!control.value
      ? this.validateAccountCode(control.value).pipe(
          tap(res => {
            const payload = res ? res : null;
            this.pickupRequestFormService.onAccountInfoChanged(payload);
            this.pickupRequestFormService.onRoleChanged(
              this.role.value,
              control.value
            );
          }),
          map(res => {
            this.loadingAccount = false;
            return res ? null : { emailTaken: true };
          }),
          catchError(() => {
            this.loadingAccount = false;
            this.pickupRequestFormService.onRoleChanged(null, null);
            this.pickupRequestFormService.onAccountInfoChanged(null);
            return of({ emailTaken: true });
          })
        )
      : of(null);
  }

  private validateAccountCode(accountCode: string): Observable<any> {
    this.loadingAccount = true;
    return this.utilService.getAccountInfo(accountCode);
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.draft && changes.draft.currentValue) {
      this.pickupRequestFormService.resetForm(
        (changes.draft.currentValue as BillOfLading).pickupDetailInfo
      );
    }
  }

  openAddressDialog() {
    this.addressDialog.openAddressDialog().subscribe(next => {
      if (next) {
        this.name.setValue(`${next.firstName} ${next.lastName}`);
        this.phone.setValue(MyEstesFormatters.formatPhone(next.phone));
        this.phoneExt.setValue(next.phoneExt);
        this.email.setValue(next.email);
      }
    });
  }

  onPickupAccessorialSelected() {
    // if (this.pickupAccessorials && this.pickupAccessorials.controls) {
    const atLeastOnePickupAccessorialSelected = !!(
      this.freeze.value ||
      this.liftgateRequired.value ||
      this.oversize.value
    );
    // const atLeastOnePickupAccessorialSelected = this.pickupAccessorials.controls.reduce((prev, curr) => {
    //   if (curr.value === true) {
    //     prev = curr.value;
    //   }
    //   return prev;
    // }, false);
    if (
      this.isPickupAccessorialSelected !== atLeastOnePickupAccessorialSelected
    ) {
      this.isPickupAccessorialSelected = atLeastOnePickupAccessorialSelected;
      this.pickupRequestFormService.onPickupAccessorialSelectedChange(
        this.isPickupAccessorialSelected
      );
    }
    // }
  }

  ngAfterContentInit() {}

  ngOnDestroy() {
    this.stop$.next(true);
    this.stop$.unsubscribe();
    if (this.formSub) {
      this.formSub.unsubscribe();
    }
    if (this.accountInfoSub) {
      this.accountInfoSub.unsubscribe();
    }
    if (this.timeSub) {
      this.timeSub.unsubscribe();
    }
  }

  onUseAccountInfoChanged(val: MatCheckboxChange) {
    if (val.checked) {
      this.loading = true;

      // new
      const token = this.auth.getAuthToken();
      const accountCode = token.accountCode;

      forkJoin(
        this.utilService.getProfileInfo(),
        this.utilService.getAccountInfo(accountCode)
      ).subscribe(
        next => {
          const profile = next[0];
          const account = next[1];
          if (account) {
            this.name.setValue(account.contactName);
            this.phone.setValue(MyEstesFormatters.formatPhone(account.phone));
          }
          if (profile) {
            this.email.setValue(profile.email);
          }

          this.phoneExt.setValue("");
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
      // new
    }
  }

  openPickupOptionsModal(html: string) {
    this.dialogService.prompt(PickupOptionsModalComponent, html);
  }

  openInfoModal(title: string, html: string) {
    this.dialogService.info(title, html);
  }

  get pickupRequest() {
    return this.formGroup.controls["pickupRequest"];
  }
  get accountCode() {
    return this.formGroup.controls["accountCode"];
  }
  get name() {
    return this.formGroup.controls["name"];
  }
  get email() {
    return this.formGroup.controls["email"];
  }
  get phone() {
    return this.formGroup.controls["phone"];
  }
  get phoneExt() {
    return this.formGroup.controls["phoneExt"];
  }
  get role() {
    return this.formGroup.controls["role"];
  }
  get pickupDate() {
    return this.formGroup.controls["pickupDate"];
  }
  // get startTime() {
  //   return this.formGroup.controls['startTime'];
  // }
  // get endTime() {
  //   return this.formGroup.controls['endTime'];
  // }
  get specialInstruction() {
    return this.formGroup.controls["specialInstruction"];
  }
  get pickupAccessorials(): FormArray {
    return this.formGroup.controls["pickupAccessorials"] as FormArray;
  }
  get serviceOptions(): FormArray {
    return this.formGroup.controls["serviceOptions"] as FormArray;
  }
  get availableByHour() {
    return this.formGroup.controls["availableByHour"];
  }
  get availableByMinutes() {
    return this.formGroup.controls["availableByMinutes"];
  }
  get availableByAmPm() {
    return this.formGroup.controls["availableByAmPm"];
  }
  get closesByHour() {
    return this.formGroup.controls["closesByHour"];
  }
  get closesByMinutes() {
    return this.formGroup.controls["closesByMinutes"];
  }
  get closesByAmPm() {
    return this.formGroup.controls["closesByAmPm"];
  }
  get freeze() {
    return this.formGroup.controls["freeze"];
  }
  get liftgateRequired() {
    return this.formGroup.controls["liftgateRequired"];
  }
  get hookOrDrop() {
    return this.formGroup.controls["hookOrDrop"];
  }
  get oversize() {
    return this.formGroup.controls["oversize"];
  }
  get noStackPallet() {
    return this.formGroup.controls["noStackPallet"];
  }
  get food() {
    return this.formGroup.controls["food"];
  }
  get poison() {
    return this.formGroup.controls["poison"];
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
