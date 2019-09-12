import { Component, OnInit, Input, ViewChild, ComponentFactoryResolver } from '@angular/core';
import { RateQuote } from '../../models';
import { NextStepsHostDirective } from './directives/next-steps-host.directive';
import { NextStepsChildComponent } from './models/quote-details-child-component.interface';
import { GuaranteedNextStepsComponent } from './components/guaranteed-next-steps/guaranteed-next-steps.component';
import { NonGuaranteedNextStepsComponent } from './components/non-guaranteed-next-steps/non-guaranteed-next-steps.component';

@Component({
  selector: 'app-quote-next-steps',
  templateUrl: './quote-next-steps.component.html',
  styleUrls: ['./quote-next-steps.component.scss']
})
export class QuoteNextStepsComponent implements OnInit {
  @Input() quote: RateQuote;
  @ViewChild(NextStepsHostDirective) quoteDetailsHost: NextStepsHostDirective;

  constructor(private componentFactoryResolver: ComponentFactoryResolver) {}

  ngOnInit() {
    this.loadComponent();
  }

  loadComponent() {
    let component: any;

    switch (this.quote.guaranteed) {
      case true:
        component = GuaranteedNextStepsComponent;
        break;
      case false:
        component = NonGuaranteedNextStepsComponent;
        break;
      default:
        throw new Error(`No compatible NextStepsChildComponent found for rate quote.`);
    }

    const componentFactory = this.componentFactoryResolver.resolveComponentFactory(component);

    const viewContainerRef = this.quoteDetailsHost.viewContainerRef;
    viewContainerRef.clear();

    const componentRef = viewContainerRef.createComponent(componentFactory);
    (<NextStepsChildComponent>componentRef.instance).quote = this.quote;
  }
}
