import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { OnlineReportingComponent } from '../online-reporting.component';
import { CreateReportComponent } from '../create-report.component';
import { AuthGuard, AuthCanLoadGuard, ScopeGuard, ScopeCanLoadGuard } from 'common';

const routes: Routes = [
  {
    path: '',
    component: OnlineReportingComponent,
    pathMatch: 'full',
    canActivate: [AuthGuard, ScopeGuard],
    runGuardsAndResolvers: 'always',
    data: { scope: 'ONL100' }
  },
  {
    path: 'create-report/:type',
    component: CreateReportComponent,
    pathMatch: 'full',
    canActivate: [AuthGuard, ScopeGuard],
    runGuardsAndResolvers: 'always',
    data: { scope: 'ONL100' }
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
  imports: [CommonModule, RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload', enableTracing: false })],
  exports: [RouterModule]
})
export class AppRoutingModule {}
