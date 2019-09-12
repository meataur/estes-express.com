import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { Routes, RouterModule } from '@angular/router';
import { AuthGuard, AuthCanLoadGuard, ScopeGuard, ScopeCanLoadGuard } from 'common';
// import { RateQuoteModule } from '../modules/rate-quote/rate-quote.module';

const routes: Routes = [
  {
    path: '',
    loadChildren: '../modules/rate-quote/rate-quote.module#RateQuoteModule',
    runGuardsAndResolvers: 'always'
  },
  {
    path: 'myestes',
    loadChildren: './lazy-my-estes.module#LazyMyEstesModule',
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
  imports: [
    CommonModule,
    RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload', enableTracing: false })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
