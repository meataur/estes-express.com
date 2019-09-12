import { Directive, ViewContainerRef } from '@angular/core';

@Directive({
  selector: '[appQuoteDetailsHost]'
})
export class QuoteDetailsHostDirective {
  constructor(public viewContainerRef: ViewContainerRef) {}
}
