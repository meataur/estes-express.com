import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { InvoiceInquiryComponent } from '../invoice-inquiry.component';
import { StatementDetailsComponent } from '../statement-details/statement-details.component';
import { PayInvoicesComponent } from '../pay-invoices/pay-invoices.component';
import { SpecialInstructionsComponent } from '../special-instructions/special-instructions.component';
import { AuthGuard, AuthCanLoadGuard, ScopeGuard, ScopeCanLoadGuard, MyEstesModule } from 'common';
import { ProfileModule, AdminModule } from 'account';

export function AdminModuleFn() {
  return AdminModule;
}
export function MyEstesModuleFn() {
  return MyEstesModule;
}
export function ProfileModuleFn() {
  return ProfileModule;
}

const routes: Routes = [
  { path: '',
    component: InvoiceInquiryComponent,
    pathMatch: 'full',
    canActivate: [AuthGuard, ScopeGuard],
    runGuardsAndResolvers: 'always',
    data: { scope: 'INVINQUIRY' }
  },
  { path: 'statement-details/:statement',
    component: StatementDetailsComponent,
    pathMatch: 'full',
    canActivate: [AuthGuard, ScopeGuard],
    runGuardsAndResolvers: 'always',
    data: { scope: 'INVINQUIRY' }
  },
  { path: 'payments',
    component: PayInvoicesComponent,
    pathMatch: 'full',
    canActivate: [AuthGuard, ScopeGuard],
    runGuardsAndResolvers: 'always',
    data: { scope: 'INVINQUIRY' }
  },
  { path: 'special-instructions',
    component: SpecialInstructionsComponent,
    pathMatch: 'full',
    canActivate: [AuthGuard],
    runGuardsAndResolvers: 'always'
  },
  {
    path: 'myestes',
    loadChildren: MyEstesModuleFn,
    canActivate: [AuthGuard],
    canLoad: [AuthCanLoadGuard],
    runGuardsAndResolvers: 'always'
  },
  {
    path: 'profile',
    loadChildren: './lazy-profile.module#LazyProfileModule',
    canActivate: [AuthGuard, ScopeGuard],
    canLoad: [AuthCanLoadGuard, ScopeCanLoadGuard],
    runGuardsAndResolvers: 'always',
    data: { scope: 'EDITPROF' }
  },
  {
    path: 'admin',
    loadChildren: './lazy-admin.module#LazyAdminModule',
    canActivate: [AuthGuard, ScopeGuard],
    canLoad: [AuthCanLoadGuard, ScopeCanLoadGuard],
    data: { scope: ['ADMINSUBS', 'ADMIN'] },
    runGuardsAndResolvers: 'always'
  }
];

@NgModule({
  imports: [CommonModule, RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload', enableTracing: false })],
  exports: [RouterModule]
})
export class AppRoutingModule {}
