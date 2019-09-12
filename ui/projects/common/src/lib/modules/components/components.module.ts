import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddressComponent } from './components/address/address.component';
import { FeedbackComponent } from './components/feedback/feedback.component';
import { TooltipComponent } from './components/tooltip/tooltip.component';
import { MatTooltipModule } from '@angular/material';
// import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormHeaderComponent } from './components/form-header/form-header.component';
import { RoutableComponentHeaderComponent } from './components/routable-component-header/routable-component-header.component';
// import { TableComponent } from './components/table/table.component';
import { MaterialModule } from './modules/material.module';
import { AccountSelectComponent } from './components/account-select/account-select.component';
import { ReactiveFormsModule } from '@angular/forms';
import { ButtonLinkComponent } from './components/button-link/button-link.component';
import { InlineButtonComponent } from './components/inline-button/inline-button.component';
import {
  RouterTabComponent,
  RouterTabItem,
  RouterTab
} from './components/router-tab/router-tab.component';
import { RouterModule } from '@angular/router';
import { HorizontalOverflowComponent } from './components/horizontal-overflow/horizontal-overflow.component';

@NgModule({
  imports: [CommonModule, RouterModule, MaterialModule, ReactiveFormsModule],
  declarations: [
    AddressComponent,
    FeedbackComponent,
    TooltipComponent,
    FormHeaderComponent,
    RoutableComponentHeaderComponent,
    AccountSelectComponent,
    ButtonLinkComponent,
    InlineButtonComponent,
    RouterTabComponent,
    RouterTabItem,
    RouterTab,
    HorizontalOverflowComponent
    // TableComponent
  ],
  exports: [
    AddressComponent,
    FeedbackComponent,
    TooltipComponent,
    FormHeaderComponent,
    RoutableComponentHeaderComponent,
    AccountSelectComponent,
    ButtonLinkComponent,
    InlineButtonComponent,
    RouterTabComponent,
    RouterTabItem,
    RouterTab,
    HorizontalOverflowComponent
    // TableComponent
  ]
})
export class ComponentsModule {}
