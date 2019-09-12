import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BolRoutingModule } from './bol-routing.module';
import { BillOfLadingComponent } from './components/bill-of-lading/bill-of-lading.component';
import { CreateBolComponent } from './components/create-bol/create-bol.component';
import { DraftsBolComponent } from './components/drafts-bol/drafts-bol.component';
import { SharedModule } from 'common';
import { MaterialModule } from './modules/material.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { BolService } from './services/bol.service';
import { QuoteDetailsComponent } from './components/quote-details/quote-details.component';
import { BolDetailsComponent } from './components/bol-details/bol-details.component';
import { BolSourceComponent } from './components/bol-source/bol-source.component';
import { PickupRequestComponent } from './components/pickup-request/pickup-request.component';
import { PickupOptionsModalComponent } from './components/pickup-options-modal/pickup-options-modal.component';
import { AccessorialsComponent } from './components/accessorials/accessorials.component';
import { AccessorialsModalComponent } from './components/accessorials-modal/accessorials-modal.component';
import { ShipperConsigneeDetailsComponent } from './components/shipper-consignee-details/shipper-consignee-details.component';
import { AddressDetailsComponent } from './components/address-details/address-details.component';
import { AddAddressModalComponent } from './components/add-address-modal/add-address-modal.component';
import { BillingInformationComponent } from './components/billing-information/billing-information.component';
import { CommodityInformationComponent } from './components/commodity-information/commodity-information.component';
import { CommodityComponent } from './components/commodity/commodity.component';
import { CommodityFormService } from './services/commodity-form.service';
import { ReferenceNumbersListComponent } from './components/reference-numbers-list/reference-numbers-list.component';
import { ReferenceNumberComponent } from './components/reference-number/reference-number.component';
import { ReferenceNumbersFormService } from './services/reference-numbers.service';
import { ShippingLabelsComponent } from './components/shipping-labels/shipping-labels.component';
import { ShippingLabelsFormService } from './services/shipping-labels-form.service';
import { NotificationsComponent } from './components/notifications/notifications.component';
import { EmailAndFaxNotificationFormService } from './services/email-fax-notification-form.service';
import { PickupRequestFormService } from './services/pickup-request-form.service';
import { BolSourceFormService } from './services/bol-source-form.service';
import { TosModalComponent } from './components/tos-modal/tos-modal.component';
import { ProNumberModalComponent } from './components/pro-number-modal/pro-number-modal.component';
import { CanDeactivateGuard } from './guards/can-component-deactivate.guard';
import { CloseBolModalComponent } from './components/close-bol-modal/close-bol-modal.component';
import { TemplatesBolComponent } from './components/templates-bol/templates-bol.component';
import { HistoryBolComponent } from './components/history-bol/history-bol.component';
import { DraftAdvancedSearchComponent } from './components/draft-advanced-search/draft-advanced-search.component';
import { DisableControlDirective } from './directives/disable-control.directive';
import { ConfirmationComponent } from './components/confirmation/confirmation.component';
import { TemplateAdvancedSearchComponent } from './components/template-advanced-search/template-advanced-search.component';
import { TemplateSectionComponent } from './components/template-section/template-section.component';
import { DraftsModalComponent } from './components/drafts-modal/drafts-modal.component';
import { TemplatesModalComponent } from './components/templates-modal/templates-modal.component';
import { HistoryModalComponent } from './components/history-modal/history-modal.component';
import { CreateShippingLabelModalComponent } from './components/create-shipping-label-modal/create-shipping-label-modal.component';
import { CommodityLibraryModalComponent } from './components/commodity-library-modal/commodity-library-modal.component';
import 'hammerjs';
import { environment } from '../../../environments/environment';

@NgModule({
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  imports: [
    CommonModule,
    BolRoutingModule,
    SharedModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule
  ],
  declarations: [
    BillOfLadingComponent,
    CreateBolComponent,
    DraftsBolComponent,
    QuoteDetailsComponent,
    BolDetailsComponent,
    BolSourceComponent,
    PickupRequestComponent,
    PickupOptionsModalComponent,
    AccessorialsComponent,
    AccessorialsModalComponent,
    ShipperConsigneeDetailsComponent,
    AddressDetailsComponent,
    AddAddressModalComponent,
    BillingInformationComponent,
    CommodityInformationComponent,
    CommodityComponent,
    ReferenceNumbersListComponent,
    ReferenceNumberComponent,
    ShippingLabelsComponent,
    NotificationsComponent,
    TosModalComponent,
    ProNumberModalComponent,
    CloseBolModalComponent,
    TemplatesBolComponent,
    HistoryBolComponent,
    DraftAdvancedSearchComponent,
    DisableControlDirective,
    ConfirmationComponent,
    TemplateAdvancedSearchComponent,
    TemplateSectionComponent,
    DraftsModalComponent,
    TemplatesModalComponent,
    HistoryModalComponent,
    CreateShippingLabelModalComponent,
    CommodityLibraryModalComponent
  ],
  entryComponents: [
    PickupOptionsModalComponent,
    AccessorialsModalComponent,
    AddAddressModalComponent,
    TosModalComponent,
    ProNumberModalComponent,
    CloseBolModalComponent,
    DraftsModalComponent,
    TemplatesModalComponent,
    HistoryModalComponent,
    CreateShippingLabelModalComponent,
    CommodityLibraryModalComponent
  ],
  providers: [BolService, CanDeactivateGuard]
})
export class BolModule {}
