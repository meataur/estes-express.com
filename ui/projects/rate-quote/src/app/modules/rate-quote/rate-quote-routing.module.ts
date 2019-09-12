import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthGuard, AuthCanLoadGuard, ScopeGuard, ScopeCanLoadGuard } from 'common';
import { CreateRateQuoteComponent } from './components/create-rate-quote/create-rate-quote.component';
import { RateQuoteComponent } from './components/rate-quote/rate-quote.component';
import { RateQuoteScopeGuard } from './guards/rate-quote-scope.guard';
import { QuoteHistoryComponent } from './components/quote-history/quote-history.component';
import { RateQuoteActionEnum } from './models';
import { CreateRateEstimateComponent } from './components/create-rate-estimate/create-rate-estimate.component';
import { RateEstimateGuard } from './guards/rate-estimate.guard';
import { CanDeactivateGuard } from './guards/rate-quote-deactivate.guard';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'estimate',
    pathMatch: 'full'
  },
  {
    path: 'estimate',
    component: CreateRateEstimateComponent,
    runGuardsAndResolvers: 'always',
    canActivate: [RateEstimateGuard]
  },
  {
    path: 'rate-quote',
    component: RateQuoteComponent,
    runGuardsAndResolvers: 'always',
    canActivate: [AuthGuard],
    children: [
      { path: '', redirectTo: 'create' },
      {
        path: 'create',
        component: CreateRateQuoteComponent,
        canActivate: [RateQuoteScopeGuard],
        canDeactivate: [CanDeactivateGuard],
        runGuardsAndResolvers: 'always'
      },
      {
        path: 'create/quote/:quoteId',
        component: CreateRateQuoteComponent,
        canActivate: [RateQuoteScopeGuard],
        canDeactivate: [CanDeactivateGuard],
        runGuardsAndResolvers: 'always',
        data: { action: RateQuoteActionEnum.CreateFromQuote }
      },
      {
        path: 'history',
        component: QuoteHistoryComponent,
        canActivate: [ScopeGuard],
        runGuardsAndResolvers: 'always',
        data: { scope: 'QUOTEHIST' }
      }
    ]
  }
];

@NgModule({
  imports: [CommonModule, RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [RateQuoteScopeGuard, RateEstimateGuard, CanDeactivateGuard]
})
export class RateQuoteRoutingModule {}
