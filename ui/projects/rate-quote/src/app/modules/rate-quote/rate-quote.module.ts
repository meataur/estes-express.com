import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule, QuickLinksModule } from 'common';
import { MaterialModule } from '../material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { RateQuoteRoutingModule } from './rate-quote-routing.module';
import { RateQuoteComponent } from './components/rate-quote/rate-quote.component';
import { CreateRateQuoteComponent } from './components/create-rate-quote/create-rate-quote.component';
import { RateQuoteTypeComponent } from './components/rate-quote-type/rate-quote-type.component';
import { RequestorComponent } from './components/requestor/requestor.component';
import { PickupDetailsComponent } from './components/pickup-details/pickup-details.component';
import { RoutingInformationComponent } from './components/routing-information/routing-information.component';
import { CommodityListComponent } from './components/commodity-list/commodity-list.component';
import { FreightInformationComponent } from './components/freight-information/freight-information.component';
import { AccessorialsComponent } from './components/accessorials/accessorials.component';
import { CommodityComponent } from './components/commodity/commodity.component';
import { AccessorialComponent } from './components/accessorial/accessorial.component';
import { RateRequestResultsComponent } from './components/rate-request-results/rate-request-results.component';
import { QuoteDetailsComponent } from './components/quote-details/quote-details.component';
import { ServiceLevelActionComponent } from './components/service-level-action/service-level-action.component';
import { QuoteDrawerComponent } from './components/quote-drawer/quote-drawer.component';
import { QuoteSidePanelComponent } from './components/quote-side-panel/quote-side-panel.component';
import { QuoteNextStepsComponent } from './components/quote-next-steps/quote-next-steps.component';
import { QuoteOptionsComponent } from './components/quote-options/quote-options.component';
import { QuoteChargeItemsComponent } from './components/quote-charge-items/quote-charge-items.component';
import { QuoteCommoditiesComponent } from './components/quote-commodities/quote-commodities.component';
import { EmailQuoteComponent } from './components/email-quote/email-quote.component';
import { VtlQuoteDetailsComponent } from './components/quote-details/components/vtl-quote-details/vtl-quote-details.component';
import { QuoteDetailsHostDirective } from './components/quote-details/directives/quote-details-host.directive';
import { TcQuoteDetailsComponent } from './components/quote-details/components/tc-quote-details/tc-quote-details.component';
import { LtlQuoteDetailsComponent } from './components/quote-details/components/ltl-quote-details/ltl-quote-details.component';
import { NeedHelpComponent } from './components/need-help/need-help.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { GuaranteedNextStepsComponent } from './components/quote-next-steps/components/guaranteed-next-steps/guaranteed-next-steps.component';
import { NonGuaranteedNextStepsComponent } from './components/quote-next-steps/components/non-guaranteed-next-steps/non-guaranteed-next-steps.component';
import { NextStepsHostDirective } from './components/quote-next-steps/directives/next-steps-host.directive';
import { GetEmailModalComponent } from './components/get-email-modal/get-email-modal.component';
import { LtlContactMeModalComponent } from './components/ltl-contact-me-modal/ltl-contact-me-modal.component';
import { EmailAddressPromptModalComponent } from './components/email-address-prompt-modal/email-address-prompt-modal.component';
import { QuoteHistoryComponent } from './components/quote-history/quote-history.component';
import { HistoryAdvancedSearchComponent } from './components/history-advanced-search/history-advanced-search.component';
import { QuoteReferenceListComponent } from './components/quote-reference-list/quote-reference-list.component';
import { LtlContactMeCommodityComponent } from './components/ltl-contact-me-commodity/ltl-contact-me-commodity.component';
import { QuoteHistoryDrawerComponent } from './components/quote-history-drawer/quote-history-drawer.component';
import { QuoteContactInformationComponent } from './components/quote-contact-information/quote-contact-information.component';
import { DisclosuresModalComponent } from './components/disclosures-modal/disclosures-modal.component';
import { PointDetailsModalComponent } from './components/point-details-modal/point-details-modal.component';
import { CreateRateEstimateComponent } from './components/create-rate-estimate/create-rate-estimate.component';
import { QuoteCommentsComponent } from './components/quote-comments/quote-comments.component';
import { QuoteDrawerDisclaimersComponent } from './components/quote-drawer-disclaimers/quote-drawer-disclaimers.component';
import { QuoteEstimateSidePanelComponent } from './components/quote-estimate-side-panel/quote-estimate-side-panel.component';
import { QuoteEstimateDrawerComponent } from './components/quote-estimate-drawer/quote-estimate-drawer.component';
import { QuoteEstimateDetailsComponent } from './components/quote-details/components/quote-estimate-details/quote-estimate-details.component';
import { ExitRateQuoteModalComponent } from './components/exit-rate-quote-modal/exit-rate-quote-modal.component';
import { RouteReuseStrategy } from '@angular/router';
import { RateQuoteRouteReuseStrategy } from './rate-quote-route-reuse-strategy';
import { RateQuoteIntroComponent } from './components/rate-quote-intro/rate-quote-intro.component';
import { TimePipe } from './pipes/display-time.pipe';
import { GuaranteedOptionMessageComponent } from './components/guaranteed-option-message/guaranteed-option-message.component';
import { QuoteHistorySidePanelComponent } from './components/quote-history-side-panel/quote-history-side-panel.component';
import { PointControlComponent } from './components/point-control/point-control.component';
import { RateQuoteActionHeaderComponent } from './components/rate-quote-action-header/rate-quote-action-header.component';
import { TransitMessageComponent } from './components/transit-message/transit-message.component';
import { NeedItFasterComponent } from './components/need-it-faster/need-it-faster.component';
import { LtlGuaranteedReserveShipmentComponent } from './components/ltl-guaranteed-reserve-shipment/ltl-guaranteed-reserve-shipment.component';
import { AdditionalCargoLiabilityModalComponent } from './components/additional-cargo-liability-modal/additional-cargo-liability-modal.component';
import { RateEstimateIntroComponent } from './components/rate-estimate-intro/rate-estimate-intro.component';
import { NeedServiceHelpComponent } from './components/need-service-help/need-service-help.component';
import { NeedItFasterEstimateComponent } from './components/need-it-faster-estimate/need-it-faster-estimate.component';

@NgModule({
  imports: [
    CommonModule,
    RateQuoteRoutingModule,
    SharedModule,
    MaterialModule,
    ReactiveFormsModule,
    NgbModule
  ],
  declarations: [
    RateQuoteComponent,
    CreateRateQuoteComponent,
    RateQuoteTypeComponent,
    RequestorComponent,
    PickupDetailsComponent,
    RoutingInformationComponent,
    CommodityListComponent,
    FreightInformationComponent,
    AccessorialsComponent,
    CommodityComponent,
    AccessorialComponent,
    RateRequestResultsComponent,
    QuoteDetailsComponent,
    ServiceLevelActionComponent,
    QuoteDrawerComponent,
    QuoteSidePanelComponent,
    QuoteNextStepsComponent,
    QuoteOptionsComponent,
    QuoteChargeItemsComponent,
    QuoteCommoditiesComponent,
    EmailQuoteComponent,
    VtlQuoteDetailsComponent,
    QuoteDetailsHostDirective,
    TcQuoteDetailsComponent,
    LtlQuoteDetailsComponent,
    NeedHelpComponent,
    GuaranteedNextStepsComponent,
    NonGuaranteedNextStepsComponent,
    NextStepsHostDirective,
    GetEmailModalComponent,
    LtlContactMeModalComponent,
    EmailAddressPromptModalComponent,
    QuoteHistoryComponent,
    HistoryAdvancedSearchComponent,
    QuoteReferenceListComponent,
    LtlContactMeCommodityComponent,
    QuoteHistoryDrawerComponent,
    QuoteContactInformationComponent,
    DisclosuresModalComponent,
    PointDetailsModalComponent,
    CreateRateEstimateComponent,
    QuoteCommentsComponent,
    QuoteDrawerDisclaimersComponent,
    QuoteEstimateSidePanelComponent,
    QuoteEstimateDrawerComponent,
    QuoteEstimateDetailsComponent,
    ExitRateQuoteModalComponent,
    RateQuoteIntroComponent,
    TimePipe,
    GuaranteedOptionMessageComponent,
    QuoteHistorySidePanelComponent,
    PointControlComponent,
    RateQuoteActionHeaderComponent,
    TransitMessageComponent,
    NeedItFasterComponent,
    LtlGuaranteedReserveShipmentComponent,
    AdditionalCargoLiabilityModalComponent,
    RateEstimateIntroComponent,
    NeedServiceHelpComponent,
    NeedItFasterEstimateComponent
  ],
  providers: [
    TimePipe
  ],
  entryComponents: [
    VtlQuoteDetailsComponent,
    TcQuoteDetailsComponent,
    LtlQuoteDetailsComponent,
    GuaranteedNextStepsComponent,
    NonGuaranteedNextStepsComponent,
    GetEmailModalComponent,
    LtlContactMeModalComponent,
    EmailAddressPromptModalComponent,
    PointDetailsModalComponent,
    DisclosuresModalComponent,
    QuoteEstimateDetailsComponent,
    ExitRateQuoteModalComponent,
    LtlGuaranteedReserveShipmentComponent,
    AdditionalCargoLiabilityModalComponent
  ]
})
export class RateQuoteModule {}
