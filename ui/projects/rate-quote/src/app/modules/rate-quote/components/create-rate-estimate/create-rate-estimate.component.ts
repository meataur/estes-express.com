import { Component, OnInit, ElementRef, ViewChild, OnDestroy } from '@angular/core';
import { RateQuoteTypeService } from '../../services/rate-quote-type.service';
import { RequestorService } from '../../services/requestor.service';
import { PickupDetailsService } from '../../services/pickup-details.service';
import { RoutingInformationService } from '../../services/routing-information.service';
import { CommoditiesService } from '../../services/commodities.service';
import { FreightInformationService } from '../../services/freight-information.service';
import { AccessorialsService } from '../../services/accessorials.service';
import { FeedbackTypes, SnackbarService, DialogService } from 'common';
import { RateQuoteService } from '../../services/rate-quote.service';
import {
  RateQuoteFormSectionService,
  RatingRequest,
  RateQuote,
  RateQuoteActionEnum,
  ServiceResponse,
  RateQuoteStepEnum,
  RateQuoteType
} from '../../models';
import { ActivatedRoute, UrlHandlingStrategy, Router } from '@angular/router';
import { Subscription, zip, Observable, of, Subject } from 'rxjs';
import { LtlContactMeModalComponent } from '../ltl-contact-me-modal/ltl-contact-me-modal.component';
import { HttpErrorResponse } from '@angular/common/http';
import { RateEstimateService } from '../../services/rate-estimate.service';
import { takeUntil } from 'rxjs/operators';
import { environment } from '../../../../../environments/environment';

@Component({
  selector: 'app-create-rate-estimate',
  templateUrl: './create-rate-estimate.component.html',
  styleUrls: ['./create-rate-estimate.component.scss']
})
export class CreateRateEstimateComponent implements OnInit, OnDestroy {
  private routeSub: Subscription;
  private stop$ = new Subject<boolean>();
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
  terminalLookupUrl = `${environment.appBaseUrl}/terminal-lookup`;

  @ViewChild('requestor') requestor: ElementRef;
  @ViewChild('routingInfo') routingInfo: ElementRef;
  @ViewChild('commmodityList') commmodityList: ElementRef;
  @ViewChild('accessorials') accessorials: ElementRef;

  constructor(
    private rateQuoteService: RateQuoteService,
    private rateQuoteTypeService: RateQuoteTypeService,
    private requestorService: RequestorService,
    private routingInformationService: RoutingInformationService,
    private commoditiesService: CommoditiesService,
    private accessorialService: AccessorialsService,
    private snackbar: SnackbarService,
    private route: ActivatedRoute,
    private dialog: DialogService,
    private router: Router,
    private estimateService: RateEstimateService
  ) {}

  ngOnInit() {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.resetForm();
    this.initReviseEstimateWatcher();
  }

  ngOnDestroy() {
    if (this.routeSub) {
      this.routeSub.unsubscribe();
    }

    this.stop$.next(true);
    this.stop$.unsubscribe();
  }

  canDeactivate(): Observable<boolean> {
    return of(true);
    // TODO: Populate with relevant logic to check if form was submitted
    // if (this.bolService.modified && !this.formSubmitted && this.auth.isLoggedIn) {
    //   return this.dialog.prompt(CloseBolModalComponent, this.generateBillOfLadingFromForm());
    // } else {
    //   return of(true);
    // }
  }

  private initReviseEstimateWatcher() {
    this.estimateService.reviseQuote$.pipe(takeUntil(this.stop$)).subscribe(next => {
      this.step = RateQuoteStepEnum.Step1;
      this.ratesQuotes = [];
    });
  }

  private initRouteWatcher() {
    this.routeSub = zip(this.route.data, this.route.paramMap).subscribe(next => {
      const data = next[0];
      const paramMap = next[1];
      const action = (data['action'] as RateQuoteActionEnum) || RateQuoteActionEnum.Create;

      switch (action) {
        case RateQuoteActionEnum.CreateFromQuote:
          const quoteId = paramMap.get('quoteId');
          this.initFormWithQuote(quoteId);
          break;
      }
    });
  }

  private initFormWithQuote(quoteId: string) {
    this.rateQuoteService.getQuoteById(quoteId).subscribe(next => {
      this.rateQuote = next;
    });
  }

  onSubmit() {
    this.feedback = null;
    this.scrollTarget = null;

    const validRequestor = this.validateSection(this.requestorService, this.requestor);
    const validRoutingInfo = this.validateSection(this.routingInformationService, this.routingInfo);
    const validCommmodityList = this.validateSection(this.commoditiesService, this.commmodityList);
    const validAccessorials = this.validateSection(this.accessorialService, this.accessorials);

    if (!validRequestor || !validRoutingInfo || !validCommmodityList || !validAccessorials) {
      if (this.scrollTarget) {
        this.snackbar.error(
          'There are validation errors in the form.  Please review the form and submit again.'
        );
        (this.scrollTarget.nativeElement as HTMLElement).scrollIntoView({
          behavior: 'smooth',
          block: 'start'
        });
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
          let msg: string | string[] =
            'An error occurred while sending the rate request.  Please try again.';
          if (err instanceof HttpErrorResponse) {
            if (err.error && Array.isArray(err.error)) {
              msg = (err.error as Array<ServiceResponse>).reduce((accum, curr) => {
                if (curr.message) {
                  accum.push(curr.message);
                }

                return accum;
              }, []);
            }
            this.feedback = ['error', msg];
          } else {
            this.feedback = ['error', msg];
          }
        },
        () => (this.loadingStep1 = false)
      );
    }
  }

  private resetForm() {
    this.requestorService.resetForm();
    this.routingInformationService.resetForm();
    this.commoditiesService.resetForm();
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
    this.router.navigate(['/estimate']);
  }

  private createRateRequestPayload() {
    const payload = new RatingRequest();
    payload.apps = ['LTL'];
    payload.terms = '';
    payload.role = '';

    this.requestorService.populateModel(payload, null, true);
    this.routingInformationService.populateModel(payload);
    this.commoditiesService.populateModel(payload, true);
    this.accessorialService.populateModel(payload);

    return payload;
  }

  private validateSection(service: RateQuoteFormSectionService, e: ElementRef): boolean {
    if (!service.valid()) {
      if (!this.scrollTarget && !!e) {
        this.scrollTarget = e;
      }
      return false;
    }
    return true;
  }
}
