import { Directive, ViewContainerRef } from '@angular/core';

@Directive({
  selector: '[appNextStepsHost]'
})
export class NextStepsHostDirective {
  constructor(public viewContainerRef: ViewContainerRef) {}
}
