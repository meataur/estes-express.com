import {
  Component,
  OnInit,
  Input,
  ViewChild,
  ComponentFactoryResolver,
  OnDestroy
} from '@angular/core';
import { RateQuote, Point, RoleEnum, TermsEnum, validPoint } from '../../models';
import { Address, AuthService } from 'common';
import { Observable, Subject } from 'rxjs';
import { RateQuoteService } from '../../services/rate-quote.service';
import { QuoteDetailsHostDirective } from './directives/quote-details-host.directive';
import { QuoteDetailsChildComponent } from './models/quote-details-child-component.interface';
import { LtlQuoteDetailsComponent } from './components/ltl-quote-details/ltl-quote-details.component';
import { TcQuoteDetailsComponent } from './components/tc-quote-details/tc-quote-details.component';
import { VtlQuoteDetailsComponent } from './components/vtl-quote-details/vtl-quote-details.component';
import { QuoteEstimateDetailsComponent } from './components/quote-estimate-details/quote-estimate-details.component';
import { Router, NavigationEnd, ActivatedRoute } from '@angular/router';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-quote-details',
  templateUrl: './quote-details.component.html',
  styleUrls: ['./quote-details.component.scss']
})
export class QuoteDetailsComponent implements OnInit, OnDestroy {
  stop$ = new Subject<boolean>();
  showGuaranteedOptionMessage: boolean;
  @Input() quote: RateQuote;
  @Input() formattedAccount$: Observable<string>;
  @ViewChild(QuoteDetailsHostDirective) quoteDetailsHost: QuoteDetailsHostDirective;

  constructor(
    private componentFactoryResolver: ComponentFactoryResolver,
    private auth: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    this.loadComponent();

    this.showGuaranteedOptionMessage = !this.router.url.endsWith('history');
  }

  ngOnDestroy() {
    this.stop$.next(true);
    this.stop$.unsubscribe();
  }

  loadComponent() {
    const component: any = this.getDetailsComponent();

    const componentFactory = this.componentFactoryResolver.resolveComponentFactory(component);

    const viewContainerRef = this.quoteDetailsHost.viewContainerRef;
    viewContainerRef.clear();

    const componentRef = viewContainerRef.createComponent(componentFactory);
    (<QuoteDetailsChildComponent>componentRef.instance).quote = this.quote;

    if (
      componentRef.instance instanceof VtlQuoteDetailsComponent ||
      componentRef.instance instanceof TcQuoteDetailsComponent ||
      componentRef.instance instanceof LtlQuoteDetailsComponent
    ) {
      componentRef.instance.formattedAccount$ = this.formattedAccount$;
    }
  }

  private getDetailsComponent() {
    if (!this.auth.isLoggedIn) {
      return QuoteEstimateDetailsComponent;
    }

    switch (this.quote.app.trim().toUpperCase()) {
      case 'LTL':
        return LtlQuoteDetailsComponent;
      case 'TC':
        return TcQuoteDetailsComponent;
      case 'VTL':
        return VtlQuoteDetailsComponent;
      default:
        throw new Error(`No compatible quote details component found for rate quote.`);
    }
  }
}
