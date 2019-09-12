import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { ManageQuickLinksComponent } from './components/manage-quick-links/manage-quick-links.component';
import { AuthGuard } from '../auth/public_api';

const routes: Routes = [
  {
    path: 'links',
    component: ManageQuickLinksComponent,
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [CommonModule, RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class QuickLinksRoutingModule {}
