import {
  Component,
  OnInit,
  ElementRef,
  ViewChild,
  OnDestroy,
  HostListener,
  ChangeDetectorRef
} from "@angular/core";
import { RateQuoteTypeService } from "../../services/rate-quote-type.service";
import { RequestorService } from "../../services/requestor.service";
import { PickupDetailsService } from "../../services/pickup-details.service";
import { RoutingInformationService } from "../../services/routing-information.service";
import { CommoditiesService } from "../../services/commodities.service";
import { FreightInformationService } from "../../services/freight-information.service";
import { AccessorialsService } from "../../services/accessorials.service";
import {
  FeedbackTypes,
  SnackbarService,
  DialogService,
  UtilService,
  AuthService
} from "common";
import { RateQuoteService } from "../../services/rate-quote.service";
import {
  RateQuoteFormSectionService,
  RatingRequest,
  RateQuote,
  RateQuoteActionEnum,
  ServiceResponse,
  RateQuoteStepEnum
} from "../../models";
import { ActivatedRoute, UrlHandlingStrategy, Router } from "@angular/router";
import { Subscription, zip, of, Observable } from "rxjs";
import { LtlContactMeModalComponent } from "../ltl-contact-me-modal/ltl-contact-me-modal.component";
import { HttpErrorResponse } from "@angular/common/http";
import { ExitRateQuoteModalComponent } from "../exit-rate-quote-modal/exit-rate-quote-modal.component";
import { CanComponentDeactivate } from "../../guards/rate-quote-deactivate.guard";
import { map, take } from "rxjs/operators";
import { CommodityListComponent } from "../commodity-list/commodity-list.component";

@Component({
  selector: "app-create-rate-quote",
  templateUrl: "./create-rate-quote.component.html",
  styleUrls: ["./create-rate-quote.component.scss"],
  providers: [RequestorService]
})
export class CreateRateQuoteComponent
  implements OnInit, OnDestroy, CanComponentDeactivate {
  private routeSub: Subscription;
  /**
   * @description If a quoteId parameter is set for this route, the application will
   * attmept to pull down and set the returned quote to this property.  This is passed
   * to all child components for pre-population.
   */
  rateQuote: RateQuote;
  step: RateQuoteStepEnum = RateQuoteStepEnum.Step1;
  feedback: [FeedbackTypes, string | string[]];
  ratesQuotes: Array<RateQuote>;
  scrollTarget: any;
  loadingStep1 = false;

  @ViewChild("rateQuoteType") rateQuoteType: ElementRef;
  @ViewChild("requestor") requestor: ElementRef;
  @ViewChild("pickupDetails") pickupDetails: ElementRef;
  @ViewChild("routingInfo") routingInfo: ElementRef;
  @ViewChild("commmodityList") commmodityList: ElementRef;
  @ViewChild("freightInfo") freightInfo: ElementRef;
  @ViewChild("accessorials") accessorials: ElementRef;

  @ViewChild(CommodityListComponent) comComp: CommodityListComponent;

  @HostListener("window:beforeunload", ["$event"])
  showNotification($event) {
    if (this.showDeactivateNotification()) {
      if (!this.rateQuoteService.mailToInProgress) {
        $event.preventDefault();
        $event.returnValue = "";
      }
      this.rateQuoteService.mailToInProgress = false;
    }
  }

  constructor(
    private rateQuoteService: RateQuoteService,
    private rateQuoteTypeService: RateQuoteTypeService,
    private requestorService: RequestorService,
    private pickupDetailsService: PickupDetailsService,
    private routingInformationService: RoutingInformationService,
    private commoditiesService: CommoditiesService,
    private freightInformationService: FreightInformationService,
    private accessorialService: AccessorialsService,
    private snackbar: SnackbarService,
    private route: ActivatedRoute,
    private router: Router,
    private dialog: DialogService,
    private utilService: UtilService,
    private auth: AuthService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.resetForm();
    this.initRouteWatcher();
  }

  ngOnDestroy() {
    if (this.routeSub) {
      this.routeSub.unsubscribe();
    }
  }

  canDeactivate(): Observable<boolean> {
    if (
      this.step === RateQuoteStepEnum.Step2 &&
      this.rateQuoteService.quoteSelected === false
    ) {
      return this.dialog.prompt(ExitRateQuoteModalComponent, null);
    } else {
      return of(true);
    }
  }

  private showDeactivateNotification(): boolean {
    return (
      this.step === RateQuoteStepEnum.Step2 &&
      this.rateQuoteService.quoteSelected === false
    );
  }

  private initRouteWatcher() {
    this.routeSub = zip(this.route.data, this.route.paramMap).subscribe(
      next => {
        const data = next[0];
        const paramMap = next[1];
        const action =
          (data["action"] as RateQuoteActionEnum) || RateQuoteActionEnum.Create;

        switch (action) {
          case RateQuoteActionEnum.CreateFromQuote:
            const quoteId = paramMap.get("quoteId");
            this.initFormWithQuote(quoteId);
            break;
        }
      }
    );
  }

  private initFormWithQuote(quoteId: string) {
    this.rateQuoteService
      .getQuoteById(quoteId)
      .pipe(
        map(res => {
          res.commodities = res.commodities.filter(
            c => c.deficitRate === false
          );
          return res;
        })
      )
      .subscribe(next => {
        this.rateQuote = next;
      });
  }

  private resetForm() {
    this.rateQuote = null;
    this.rateQuoteTypeService.resetForm();
    this.requestorService.resetForm();
    this.pickupDetailsService.resetForm();
    this.routingInformationService.resetForm();
    this.commoditiesService.resetForm();
    this.freightInformationService.resetForm();
    this.accessorialService.resetForm();
  }

  reviseQuote() {
    this.step = RateQuoteStepEnum.Step1;
    this.loadingStep1 = false;
    this.scrollTarget = null;
    this.ratesQuotes = [];
    this.feedback = null;
    this.rateQuoteService.quoteSelected = false;
  }

  startNewQuote() {
    this.router.navigate(["/rate-quote/create"]);
  }

  get today() {
    return new Date();
  }

  onSubmit() {
    this.feedback = null;
    this.scrollTarget = null;

    const validRateQuoteType = this.validateSection(
      this.rateQuoteTypeService,
      this.rateQuoteType
    );
    const validRequestor = this.validateSection(
      this.requestorService,
      this.requestor
    );
    const validPickupDetails = this.validateSection(
      this.pickupDetailsService,
      this.pickupDetails
    );
    const validRoutingInfo = this.validateSection(
      this.routingInformationService,
      this.routingInfo
    );
    const validCommmodityList = this.validateSection(
      this.commoditiesService,
      this.commmodityList
    );
    const validFreightInfo = this.validateSection(
      this.freightInformationService,
      this.freightInfo
    );
    const validAccessorials = this.validateSection(
      this.accessorialService,
      this.accessorials
    );

    // this.cdr.markForCheck();

    if (
      !validRateQuoteType ||
      !validRequestor ||
      !validPickupDetails ||
      !validRoutingInfo ||
      !validCommmodityList ||
      !validFreightInfo ||
      !validAccessorials
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
      const payload = this.createRateRequestPayload();
      this.loadingStep1 = true;
      this.rateQuoteService.requestQuote(payload).subscribe(
        next => {
          this.ratesQuotes = next;
          this.step = RateQuoteStepEnum.Step2;
          // Scroll to the top of the page.
          window.scrollTo(0, 0);
        },
        err => {
          this.loadingStep1 = false;
          if (err instanceof HttpErrorResponse) {
            if (err.error && err.error.length) {
              const msgs: Array<string> = (err.error as Array<
                ServiceResponse
              >).reduce((accum, curr) => {
                if (curr.message) {
                  accum.push(curr.message);
                }

                return accum;
              }, []);
              this.feedback = ["error", msgs];
            }
          } else {
            this.feedback = [
              "error",
              "An error occurred while sending the rate request.  Please try again."
            ];
          }
        },
        () => (this.loadingStep1 = false)
      );
    }
  }

  private createRateRequestPayload() {
    const payload = new RatingRequest();
    const rateQuoteType = this.rateQuoteTypeService.getRateQuoteType();

    this.rateQuoteTypeService.populateModel(payload);
    this.requestorService.populateModel(payload, rateQuoteType);
    this.pickupDetailsService.populateModel(payload, rateQuoteType);
    this.routingInformationService.populateModel(payload);
    this.commoditiesService.populateModel(payload);
    this.freightInformationService.populateModel(payload, rateQuoteType);
    this.accessorialService.populateModel(payload);

    return payload;
  }

  private validateSection(
    service: RateQuoteFormSectionService,
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
}
