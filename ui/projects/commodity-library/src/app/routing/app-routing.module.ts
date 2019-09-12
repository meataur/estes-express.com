import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { Routes, RouterModule } from '@angular/router';
import { AuthGuard, AuthCanLoadGuard, ScopeGuard, ScopeCanLoadGuard } from 'common';
import { CommodityListComponent } from '../components/commodity-list/commodity-list.component';
import { CommodityComponent } from '../components/commodity/commodity.component';
import { CommodityActionEnum } from '../models';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: CommodityListComponent,
    canActivate: [AuthGuard, ScopeGuard],
    canLoad: [AuthCanLoadGuard, ScopeCanLoadGuard],
    data: { scope: 'EBG10O120' },
    runGuardsAndResolvers: 'always',
  },
  {
    path: 'create',
    component: CommodityComponent,
    canActivate: [AuthGuard, ScopeGuard],
    canLoad: [AuthCanLoadGuard, ScopeCanLoadGuard],
    data: { scope: 'EBG10O120', action: CommodityActionEnum.Create },
    runGuardsAndResolvers: 'always',
  },
  {
    path: 'edit/:id',
    component: CommodityComponent,
    canActivate: [AuthGuard, ScopeGuard],
    canLoad: [AuthCanLoadGuard, ScopeCanLoadGuard],
    data: { scope: 'EBG10O120', action: CommodityActionEnum.Edit },
    runGuardsAndResolvers: 'always',
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
