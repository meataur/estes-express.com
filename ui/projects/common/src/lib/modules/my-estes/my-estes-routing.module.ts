import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { WelcomePageComponent } from './components/welcome-page/welcome-page.component';
import { RecentShipmentsComponent } from './components/recent-shipments/recent-shipments.component';
import { RecentShipmentsGuard } from './guards/recent-shipments.guard';
import { SubAccountsListGuard } from './guards/sub-accounts-list.guard';
import { AuthGuard } from '../auth/guards/auth.guard';
import { ScopeGuard } from '../auth/guards/scope.guard';

const routes: Routes = [
  {
    path: '',
    component: WelcomePageComponent,
    pathMatch: 'full',
    canActivate: [SubAccountsListGuard, AuthGuard, ScopeGuard],
    data: { scope: 'RSG10O100' }
  },
  {
    path: 'recent-shipments/:accountNumber',
    component: RecentShipmentsComponent,
    canActivate: [RecentShipmentsGuard, AuthGuard, ScopeGuard],
    data: { scope: 'RSG10O100' }
  }
];

@NgModule({
  imports: [CommonModule, RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [RecentShipmentsGuard, SubAccountsListGuard]
})
export class MyEstesRoutingModule {}
