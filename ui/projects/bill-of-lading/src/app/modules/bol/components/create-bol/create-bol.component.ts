import {
  Component,
  OnInit,
  ViewChild,
  ElementRef,
  OnDestroy
} from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import {
  FormService,
  SnackbarService,
  DialogService,
  FeedbackTypes,
  UtilService,
  AuthService,
  validateFormFields
} from "common";
import { mustBeTrueValidator } from "common";
import { take, tap, skip, map, switchMap, catchError } from "rxjs/operators";
import { QuoteDetailsComponent } from "../quote-details/quote-details.component";
import { BolSourceComponent } from "../bol-source/bol-source.component";
import { BolDetailsComponent } from "../bol-details/bol-details.component";
import { PickupRequestComponent } from "../pickup-request/pickup-request.component";
import {
  AddressSourceEnum,
  AddressInformation,
  BillOfLading,
  BolSectionService,
  BolServiceResponse,
  CreateBillOfLadingResponse,
  BolActionEnum,
  Template,
  Commodity,
  CountryEnum,
  RoleEnum
} from "../../models";
import { ShipperConsigneeDetailsComponent } from "../shipper-consignee-details/shipper-consignee-details.component";
import { BolSourceFormService } from "../../services/bol-source-form.service";
import { BolDetailsFormService } from "../../services/bol-details-form.service";
import { AccessorialsFormService } from "../../services/accessorials-form.service";
import { PickupRequestFormService } from "../../services/pickup-request-form.service";
import { CommodityFormService } from "../../services/commodity-form.service";
import { ReferenceNumbersFormService } from "../../services/reference-numbers.service";
import { ShippingLabelsFormService } from "../../services/shipping-labels-form.service";
import { EmailAndFaxNotificationFormService } from "../../services/email-fax-notification-form.service";
import { ShipperConsigneeFormService } from "../../services/shipper-consignee-form.service";
import { BillingInformationFormService } from "../../services/billing-information-form.service";
import { QuoteDetailsFormService } from "../../services/quote-details.service";
import { CanComponentDeactivate } from "../../guards/can-component-deactivate.guard";
import {
  Observable,
  of,
  forkJoin,
  empty,
  Subscription,
  merge,
  zip,
  EMPTY
} from "rxjs";
import { CloseBolModalComponent } from "../close-bol-modal/close-bol-modal.component";
import { Route, ActivatedRoute, Router } from "@angular/router";
import { BolService } from "../../services/bol.service";
import { HttpErrorResponse } from "@angular/common/http";
import { TemplateSectionFormService } from "../../services/template-section-form.service";
import { RateQuote } from "../../models/rate-quote.interface";

@Component({
  selector: "app-create-bol",
  templateUrl: "./create-bol.component.html",
  styleUrls: ["./create-bol.component.scss"],
  providers: [
    BolSourceFormService,
    BolDetailsFormService,
    QuoteDetailsFormService,
    AccessorialsFormService,
    PickupRequestFormService,
    CommodityFormService,
    ReferenceNumbersFormService,
    ShippingLabelsFormService,
    EmailAndFaxNotificationFormService,
    ShipperConsigneeFormService,
    BillingInformationFormService
  ]
})
export class CreateBolComponent extends CanComponentDeactivate
  implements OnInit, OnDestroy {
  private pickupReqSub: Subscription;
  private templateSub: Subscription;
  private routeSub: Subscription;
  template: Template;
  @ViewChild("bolSource") bolSource: ElementRef;
  @ViewChild("bolDetails") bolDetails: ElementRef;
  @ViewChild("quoteDetails") quoteDetails: ElementRef;
  @ViewChild("pickupRequest") pickupRequest: ElementRef;
  @ViewChild("accesorials") accesorials: ElementRef;
  @ViewChild("shipperConsigneeDetails") shipperConsigneeDetails: ElementRef;
  @ViewChild("billingInfo") billingInfo: ElementRef;
  @ViewChild("commodityInfo") commodityInfo: ElementRef;
  @ViewChild("referenceNumbers") referenceNumbers: ElementRef;
  @ViewChild("shippingLabels") shippingLabels: ElementRef;
  @ViewChild("templateSection") templateSection: ElementRef;
  @ViewChild("notifications") notifications: ElementRef;
  @ViewChild(BolDetailsComponent) bolDetailsComponent: BolDetailsComponent;
  scrollTarget: ElementRef;
  submitButtonText: string;
  bolDraft: BillOfLading;
  feedback: [FeedbackTypes, string[] | string];
  formSub: Subscription;
  templateSelectedSub: Subscription;
  loading = false;
  formSubmitted = false;
  action: BolActionEnum;
  templateName: string;
  showSaveAsTemplateOnlyButton = false;
  loadingFormSubmit: boolean;
  loadingSaveTemplateOnly: boolean;
  loadingSaveDraft: boolean;
  readonly hasPickupRequestAccess = this.auth.isInScope("PKN200");

  constructor(
    private bolSourceFormService: BolSourceFormService,
    private bolDetailsFormService: BolDetailsFormService,
    private quoteDetailsFormService: QuoteDetailsFormService,
    private pickupRequestFormService: PickupRequestFormService,
    private accessorialsFormService: AccessorialsFormService,
    private shipperConsigneeFormService: ShipperConsigneeFormService,
    private billingInformationFormService: BillingInformationFormService,
    private commodityFormService: CommodityFormService,
    private referenceNumbersFormService: ReferenceNumbersFormService,
    private shippingLabelsFormService: ShippingLabelsFormService,
    private templateSectionFormService: TemplateSectionFormService,
    private emailAndFaxNotificationFormService: EmailAndFaxNotificationFormService,
    private snackbar: SnackbarService,
    private dialog: DialogService,
    private route: ActivatedRoute,
    private utilService: UtilService,
    private router: Router,
    private auth: AuthService,
    bolService: BolService
  ) {
    super(bolService);
  }

  ngOnInit() {
    window.scrollTo({ top: 0, behavior: "smooth" });
    this.bolService.resetFormState();
    // this.route.data
    this.routeSub = zip(this.route.data, this.route.paramMap).subscribe(
      next => {
        this.submitButtonText = this.getSubmitButtonText();
        const data = next[0];
        const paramMap = next[1];

        // Default the action to be 'Create'
        const action =
          (data["action"] as BolActionEnum) || BolActionEnum.Create;
        this.action = action;
        let identifier = null;

        switch (action) {
          case BolActionEnum.CreateFromExistingBol:
            identifier = paramMap.get("bolId");
            this.populateFormWithExistingBol(identifier);
            break;
          case BolActionEnum.EditTemplate:
            identifier = paramMap.get("templateId");
            this.templateName = identifier;
            this.populateFormWithTemplate(identifier);
            break;
          case BolActionEnum.CreateFromTemplate:
            identifier = paramMap.get("templateId");
            this.populateFormWithTemplate(identifier);
            this.setTemplate(identifier);
            break;
          case BolActionEnum.CreateFromDraft:
            identifier = paramMap.get("draftId");
            this.populateFormWithDraft(identifier);
            break;
          case BolActionEnum.CreateFromQuote:
            identifier = paramMap.get("quoteId");
            this.populateFormWithQuoteId(identifier);
            break;
        }
      }
    );

    this.templateSub = this.templateSectionFormService.saveTemplate$.subscribe(
      next => {
        if (next === true) {
          switch (this.action) {
            case BolActionEnum.Create:
            case BolActionEnum.CreateFromDraft:
            case BolActionEnum.CreateFromTemplate:
            case BolActionEnum.CreateFromExistingBol:
            case BolActionEnum.CreateFromQuote:
              this.showSaveAsTemplateOnlyButton = true;
              break;
            case BolActionEnum.EditTemplate:
              this.showSaveAsTemplateOnlyButton = false;
              break;
          }
        } else {
          this.showSaveAsTemplateOnlyButton = false;
        }
      }
    );

    this.templateSelectedSub = this.bolSourceFormService.templateSelection$.subscribe(
      next => {
        this.populateFormWithTemplate(next.templateName);
      }
    );

    if (this.hasPickupRequestAccess) {
      this.pickupReqSub = this.pickupRequestFormService.generatePickupRequest$.subscribe(
        next => {
          const submitText = this.getSubmitButtonText();
          switch (this.action) {
            case BolActionEnum.Create:
            case BolActionEnum.CreateFromDraft:
            case BolActionEnum.CreateFromTemplate:
            case BolActionEnum.CreateFromExistingBol:
            case BolActionEnum.CreateFromQuote:
              if (next === true) {
                this.submitButtonText = `${submitText} + Generate Pickup Request`;
              } else if (next === false) {
                this.submitButtonText = submitText;
              }
              break;
            case BolActionEnum.EditTemplate:
              this.submitButtonText = submitText;
          }
        }
      );
    }
  }
  private setTemplate(templateName: string) {
    this.bolService
      .getTemplates(
        { page: 1, size: 1, sort: "templateName", direction: "asc" },
        { templateName: templateName, filterBy: "TEMPLATE_NAME" }
      )
      .subscribe(t => {
        if (t.content.length) {
          this.template = t.content[0];
        } else {
          this.snackbar.error(`No template found.`);
        }
      });
  }

  canDeactivate(): Observable<boolean> {
    if (
      this.bolService.modified &&
      !this.formSubmitted &&
      this.auth.isLoggedIn
    ) {
      return this.dialog.prompt(
        CloseBolModalComponent,
        this.generateBillOfLadingFromForm()
      );
    } else {
      return of(true);
    }
  }

  private getSubmitButtonText() {
    switch (this.action) {
      case BolActionEnum.EditTemplate:
        return "Save Template";
      default:
        return "Submit BOL";
    }
  }

  private populateFormWithQuoteId(quoteId: string) {
    this.loading = true;

    this.bolService.getQuoteById(quoteId).subscribe(
      next => {
        this.snackbar.success(`Rate quote retrieved successfully.`);
        this.shipperConsigneeFormService.rateQuote = next;
        this.bolDraft = this.generateBolFromQuote(next);
        // if (!this.utilService.isDateExpired(next.expireDate, next.expireTime)) {
        // } else {
        //   this.snackbar.error(`Rate quote expired.`);
        // }
      },
      err => {
        this.loading = false;
        let msg = `An error occurred retrieving quote details.  Please reload the page and try again.`;
        if (err instanceof HttpErrorResponse) {
          if (err.error.message) {
            msg = err.error.message;
          }
        }
        this.snackbar.error(msg);
      },
      () => (this.loading = false)
    );
  }

  private generateBolFromQuote(q: RateQuote): BillOfLading {
    const b = new BillOfLading();

    b.generalInfo.quote = q.quoteID;
    b.generalInfo.proNumber = '';
    b.generalInfo.assignProNumber= false;
    b.accessorials = q.accessorials.reduce((accum, curr) => {
      accum.push(curr.accessorial);
      return accum;
    }, []);

    b.commodityInfo.commodityList = q.commodities.reduce((accum, curr) => {
      const c = new Commodity();

      c.goodsType = { code: curr.commodity.pieceType, description: null };
      c.description = curr.commodity.description;
      c.goodsWeight = curr.commodity.weight.toString();
      c.goodsUnit = curr.commodity.pieces.toString();
      c.shipmentClass = {
        code: curr.commodity.shipClass.toString(),
        description: null
      };
      accum.push(c);
      return accum;
    }, []);

    if (q.guaranteed) {
      b.generalInfo.guranteed = true;
    }

    b.shipperInfo.country = q.origin.country as CountryEnum;
    b.shipperInfo.city = q.origin.city;
    b.shipperInfo.state = q.origin.state;
    b.shipperInfo.zip = q.origin.zip;

    b.consigneeInfo.country = q.dest.country as CountryEnum;
    b.consigneeInfo.city = q.dest.city;
    b.consigneeInfo.state = q.dest.state;
    b.consigneeInfo.zip = q.dest.zip;

    return b;
  }

  private populateFormWithDraft(draftBolNumber: string) {
    this.loading = true;

    this.bolService.getDraft(draftBolNumber).subscribe(
      next => {
        next.generalInfo.quote = null;
        this.bolDraft = next;
      },
      err => {
        this.snackbar.error(
          "An error occurred requesting the Bill of Lading draft.  Please try again."
        );
        this.loading = false;
      },
      () => (this.loading = false)
    );
  }

  private populateFormWithExistingBol(bolId: string) {
    this.loading = true;

    this.bolService.getBolById(bolId).subscribe(
      next => {
        next.generalInfo.quote = null;
        this.bolDraft = next;
      },
      err => {
        this.snackbar.error(
          "An error occurred retrieving the Bill of Lading.  Please try again."
        );
        this.loading = false;
      },
      () => (this.loading = false)
    );
  }

  private populateFormWithTemplate(templateName: string) {
    this.loading = true;
    this.bolService.getTemplateByName(templateName).subscribe(
      next => {
        next.generalInfo.quote = null;
        this.bolDraft = next;
      },
      err => {
        this.snackbar.error(
          "An error occurred retrieving the Bill of Lading template.  Please try again."
        );
        this.loading = false;
      },
      () => (this.loading = false)
    );
  }

  private validateBolSection(
    service: BolSectionService,
    e: ElementRef
  ): boolean {
    if (!service.valid()) {
      if (!this.scrollTarget && !!e) {
        this.scrollTarget = e;
      }
      return false;
    }
    return true;
  }

  save() {
    this.scrollTarget = null;
    this.feedback = null;
    if (this.bolDetailsComponent.bolReferenceNumber.valid) {
      this.loadingSaveDraft = true;
      this.bolService
        .saveBillOfLading(this.generateBillOfLadingFromForm())
        .subscribe(
          next => {
            this.formSubmitted = true;
            this.loadingSaveDraft = false;
            this.snackbar.success("Bill of Lading saved successfully.");
            this.router.navigate(["/bol/drafts"], { relativeTo: this.route });
          },
          err => {
            this.loadingSaveDraft = false;
            this.snackbar.error(
              `An error occurrd while saving the draft.  Please try again.`
            );
          }
        );
    } else {
      this.bolDetailsComponent.bolReferenceNumber.markAsTouched({
        onlySelf: false
      });

      this.scrollTarget = this.bolDetails;

      (this.scrollTarget.nativeElement as HTMLElement).scrollIntoView({
        behavior: "smooth",
        block: "start"
      });
      this.snackbar.error(
        `In order to save a draft, please provide a BOL Reference Number.`
      );
    }
  }

  onSubmit() {
    this.feedback = null;
    this.scrollTarget = null;

    switch (this.action) {
      case BolActionEnum.Create:
      case BolActionEnum.CreateFromDraft:
      case BolActionEnum.CreateFromTemplate:
      case BolActionEnum.CreateFromExistingBol:
      case BolActionEnum.CreateFromQuote:
        this.createBol();
        break;
      case BolActionEnum.EditTemplate:
        this.saveTemplate();
        break;
    }
  }

  onSaveAsTemplateOnly() {
    this.scrollTarget = null;
    if (this.templateSectionFormService.valid()) {
      this.loadingSaveTemplateOnly = true;
      this.bolService
        .saveTemplate(
          this.generateBillOfLadingFromForm(),
          this.templateSectionFormService.getTemplateName()
        )
        .subscribe(
          next => {
            this.formSubmitted = true;
            this.loadingSaveTemplateOnly = false;
            this.snackbar.success(`Template saved successfully.`);
            this.router.navigate(["/bol/templates"], {
              relativeTo: this.route
            });
          },
          err => {
            this.snackbar.error(
              `An error occurrd while saving the template.  Please try again.`
            );
            this.loadingSaveTemplateOnly = false;
          }
        );
    } else {
      this.scrollTarget = this.templateSection;
      this.snackbar.error(
        `Please specify a valid template name in the Save Template section.`
      );
      (this.scrollTarget.nativeElement as HTMLElement).scrollIntoView({
        behavior: "smooth",
        block: "start"
      });
    }
  }

  private createBol() {
    const validBolSource = this.validateBolSection(
      this.bolSourceFormService,
      this.bolSource
    );
    const validBolDetails = this.validateBolSection(
      this.bolDetailsFormService,
      this.bolDetails
    );
    const validQuoteDetails = this.validateBolSection(
      this.quoteDetailsFormService,
      this.quoteDetails
    );
    const validPickupRequest = this.validateBolSection(
      this.pickupRequestFormService,
      this.pickupRequest
    );
    const validAccessorialsFormService = this.validateBolSection(
      this.accessorialsFormService,
      this.accesorials
    );
    const validAhipperConsigneeFormService = this.validateBolSection(
      this.shipperConsigneeFormService,
      this.shipperConsigneeDetails
    );
    const validAillingInformationFormService = this.validateBolSection(
      this.billingInformationFormService,
      this.billingInfo
    );
    const validAommodityFormService = this.validateBolSection(
      this.commodityFormService,
      this.commodityInfo
    );
    const validAeferenceNumbersFormService = this.validateBolSection(
      this.referenceNumbersFormService,
      this.referenceNumbers
    );
    const validAhippingLabelsFormService = this.validateBolSection(
      this.shippingLabelsFormService,
      this.shippingLabels
    );
    const validAmailAndFaxNotificationFormService = this.validateBolSection(
      this.emailAndFaxNotificationFormService,
      this.notifications
    );
    const validTemplateSection = this.validateBolSection(
      this.templateSectionFormService,
      this.templateSection
    );

    if (
      !validBolSource ||
      !validBolDetails ||
      !validQuoteDetails ||
      !validPickupRequest ||
      !validAccessorialsFormService ||
      !validAhipperConsigneeFormService ||
      !validAillingInformationFormService ||
      !validAommodityFormService ||
      !validAeferenceNumbersFormService ||
      !validAhippingLabelsFormService ||
      !validAmailAndFaxNotificationFormService ||
      !validTemplateSection
    ) {
      if (this.scrollTarget) {
        (this.scrollTarget.nativeElement as HTMLElement).scrollIntoView({
          behavior: "smooth",
          block: "start"
        });
        this.snackbar.error(
          "There are validation errors in the form.  Please review the form and submit again."
        );
      }
    } else {
      const billOfLading = this.generateBillOfLadingFromForm();

      this.loadingFormSubmit = true;
      this.formSub = this.validateFieldsServerSide(billOfLading)
        .pipe(switchMap(valid => this.trySaveTemplate(valid)))
        .pipe(switchMap(valid => this.submitBillOfLading(valid)))
        .subscribe(
          next => {
            this.formSubmitted = true;
            this.snackbar.success("Bill of Lading created successfully.");
            this.bolService.pickupErrors = next.pickupErrors;
            if (next.pickupRequest != null) {
              this.bolService.pickupRequestNumber =
                next.pickupRequest.shipmentInfo[0].requestNumber;
            }

            this.router.navigate(["/bol/confirmation", next.bol.bolId]);
          },
          err => {
            this.loadingFormSubmit = false;
            if (err instanceof HttpErrorResponse) {
              let errors: Array<string> = [
                "An unknown error has occurred.  Please try again."
              ];
              if (err.error.length) {
                errors = (err.error as Array<BolServiceResponse>).reduce(
                  (prev, next) => {
                    prev.push(next.message);
                    return prev;
                  },
                  []
                );
                this.feedback = ["error", errors];
              }
            }
          },
          () => (this.loadingFormSubmit = false)
        );
    }
  }

  private saveTemplate() {
    if (this.action === BolActionEnum.EditTemplate && this.templateName) {
      this.loadingFormSubmit = true;
      this.bolService
        .saveTemplate(this.generateBillOfLadingFromForm(), this.templateName)
        .subscribe(
          next => {
            this.loadingFormSubmit = false;
            this.formSubmitted = true;
            this.snackbar.success("Template saved successfully.");
            this.router.navigate(["/bol/templates"], {
              relativeTo: this.route
            });
          },
          err => {
            this.snackbar.error(
              `An error occurrd while saving the template.  Please try again.`
            );
            this.loadingFormSubmit = false;
          }
        );
    }
  }

  private submitBillOfLading(
    validServerSideValidation: boolean
  ): Observable<CreateBillOfLadingResponse> {
    if (validServerSideValidation) {
      return this.bolService.createBillOfLading(
        this.generateBillOfLadingFromForm()
      );
    } else {
      return EMPTY;
    }
  }

  private trySaveTemplate(
    validServerSideValidation: boolean
  ): Observable<boolean> {
    if (
      validServerSideValidation &&
      this.templateSectionFormService.saveTemplate
    ) {
      return this.bolService
        .saveTemplate(
          this.generateBillOfLadingFromForm(),
          this.templateSectionFormService.getTemplateName()
        )
        .pipe(map(res => validServerSideValidation));
    } else {
      return of(validServerSideValidation);
    }
  }

  private validateFieldsServerSide(
    billOfLading: BillOfLading
  ): Observable<boolean> {
    return forkJoin(
      this.validateAddresses(billOfLading),
      this.validateProNumber(billOfLading)
    ).pipe(
      map(next => {
        let valid = true;
        let errors: Array<string> = [];
        const validateAddress = next[0];
        const validateProNumber = next[1];

        if (validateAddress[0] === false) {
          valid = false;
          if (validateAddress[1].length) {
            errors = errors.concat(validateAddress[1]);
          }
        }

        if (validateProNumber[0] === false) {
          valid = false;
          if (validateProNumber[1].length) {
            errors = errors.concat(validateProNumber[1]);
          }
        }

        if (!valid) {
          this.feedback = ["error", errors];
          return false;
        } else {
          return true;
        }
      })
    );
  }

  private validateProNumber(
    billOfLading: BillOfLading
  ): Observable<[boolean, string[]]> {
    let t: [boolean, string[]];

    if (
      !billOfLading.generalInfo.assignProNumber &&
      billOfLading.generalInfo.proNumber
    ) {
      return this.utilService
        .validateProNumber(billOfLading.generalInfo.proNumber)
        .pipe(
          map(next => {
            t = [true, null];
            return t;
          }),
          catchError(val => {
            let msg: Array<string> = [
              "An error occurred while validating the PRO number.  Please try again."
            ];
            if (val instanceof HttpErrorResponse) {
              if (val.error.length) {
                msg = (val.error as Array<BolServiceResponse>).reduce(
                  (accum, curr) => {
                    if (curr.message) {
                      accum.push(curr.message);
                    }
                    return accum;
                  },
                  []
                );
              }
            }
            t = [false, msg];
            return of(t);
          })
        );
    } else {
      t = [true, null];
      return of(t);
    }
  }

 /**
  * This validation is only for Pickup Request Shipper
  * @param billOfLading
  */
  private validateAddresses(
    billOfLading: BillOfLading
  ): Observable<[boolean, string[]]> {
    let t: [boolean, string[]];
    /**
     * If Pickup request is null return success response.
     */
    if ( billOfLading.pickupDetailInfo == null ) {
      t = [true, null];
      return of(t);
    }

    return this.utilService.validateCityStateZip(
      billOfLading.shipperInfo.city,
      billOfLading.shipperInfo.state,
      billOfLading.shipperInfo.zip
    ).pipe(
      map(next => {
        const errors: Array<string> = [];
        let hasError = false;

        if (!next) {
          hasError = true;
          errors.push(
            `The shipper city, state and ZIP code are currently outside of our pickup service area.`
          );
          t = [false, errors];
        } else {
          t = [true, null];
        }

        return t;
      })
    );


  }

  private generateBillOfLadingFromForm(bol?: BillOfLading): BillOfLading {
    if (!bol) {
      bol = new BillOfLading();
    }

    this.bolSourceFormService.populateModel(bol);
    this.bolDetailsFormService.populateModel(bol);
    this.quoteDetailsFormService.populateModel(bol);

    if (!this.hasPickupRequestAccess) {
      bol.generalInfo.pickupRequest = false;
      bol.pickupDetailInfo = null;
    } else {
      this.pickupRequestFormService.populateModel(bol);
    }

    this.accessorialsFormService.populateModel(bol);
    this.shipperConsigneeFormService.populateModel(bol);
    this.billingInformationFormService.populateModel(bol);
    this.commodityFormService.populateModel(bol);
    this.referenceNumbersFormService.populateModel(bol);
    this.shippingLabelsFormService.populateModel(bol);
    this.emailAndFaxNotificationFormService.populateModel(bol);

    return bol;
  }

  ngOnDestroy() {
    if (this.formSub) {
      this.formSub.unsubscribe();
    }
    if (this.pickupReqSub) {
      this.pickupReqSub.unsubscribe();
    }
    if (this.templateSub) {
      this.templateSub.unsubscribe();
    }
    if (this.routeSub) {
      this.routeSub.unsubscribe();
    }
  }
}
