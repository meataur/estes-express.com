import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { EdiRequestComponent } from '../edi-request.component';
import { EdiImplementationRequestComponent } from '../edi-implementation-request.component';
import { AuthGuard, AuthCanLoadGuard, ScopeGuard, ScopeCanLoadGuard } from 'common';

const routes: Routes = [
  { path: '', component: EdiRequestComponent, pathMatch: 'full', runGuardsAndResolvers: 'always' },
  { path: 'implementation-request', component: EdiImplementationRequestComponent, pathMatch: 'full', runGuardsAndResolvers: 'always' },
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
