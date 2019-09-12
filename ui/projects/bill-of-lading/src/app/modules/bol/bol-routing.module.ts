import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthGuard, AuthCanLoadGuard, ScopeGuard, ScopeCanLoadGuard } from 'common';
import { CreateBolComponent } from './components/create-bol/create-bol.component';
import { DraftsBolComponent } from './components/drafts-bol/drafts-bol.component';
import { BillOfLadingComponent } from './components/bill-of-lading/bill-of-lading.component';
import { CanDeactivateGuard } from './guards/can-component-deactivate.guard';
import { TemplatesBolComponent } from './components/templates-bol/templates-bol.component';
import { HistoryBolComponent } from './components/history-bol/history-bol.component';
import { ConfirmationComponent } from './components/confirmation/confirmation.component';
import { BolActionEnum } from './models';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'bol',
    pathMatch: 'full'
  },
  {
    path: 'bol',
    canActivate: [AuthGuard],
    component: BillOfLadingComponent,
    runGuardsAndResolvers: 'always',
    children: [
      { path: '', redirectTo: 'create' },
      {
        path: 'create',
        component: CreateBolComponent,
        canDeactivate: [CanDeactivateGuard]
      },
      {
        path: 'templates/edit/:templateId',
        component: CreateBolComponent,
        canDeactivate: [CanDeactivateGuard],
        data: { action: BolActionEnum.EditTemplate }
      },
      {
        path: 'create/draft/:draftId',
        component: CreateBolComponent,
        canDeactivate: [CanDeactivateGuard],
        data: { action: BolActionEnum.CreateFromDraft }
      },
      {
        path: 'create/existing/:bolId',
        component: CreateBolComponent,
        canDeactivate: [CanDeactivateGuard],
        data: { action: BolActionEnum.CreateFromExistingBol }
      },
      {
        path: 'create/template/:templateId',
        component: CreateBolComponent,
        canDeactivate: [CanDeactivateGuard],
        data: { action: BolActionEnum.CreateFromTemplate }
      },
      {
        path: 'create/quote/:quoteId',
        component: CreateBolComponent,
        canDeactivate: [CanDeactivateGuard],
        data: { action: BolActionEnum.CreateFromQuote }
      },
      {
        path: 'drafts',
        component: DraftsBolComponent
      },
      {
        path: 'templates',
        component: TemplatesBolComponent
      },
      {
        path: 'history',
        component: HistoryBolComponent
      },
      {
        path: 'confirmation/:id',
        component: ConfirmationComponent
      }
    ]
  }
];

@NgModule({
  imports: [CommonModule, RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: []
})
export class BolRoutingModule {}
