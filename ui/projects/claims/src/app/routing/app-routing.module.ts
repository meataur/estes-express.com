import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ClaimsComponent } from '../claims.component';
import { ClaimsInquiryComponent } from '../claims-inquiry/claims-inquiry.component';
import { FileClaimComponent } from '../file-claim/file-claim.component';
import { AuthGuard, AuthCanLoadGuard, ScopeCanLoadGuard, ScopeGuard, MyEstesModule } from 'common';
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
  {
    path: '',
    component: ClaimsComponent,
    pathMatch: 'full',
    canActivate: [AuthGuard, ScopeGuard],
    runGuardsAndResolvers: 'always',
    data: {scope: ['CLAIMIN', 'CLAIMSFILE']}
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
  imports: [
    CommonModule,
    RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload', enableTracing: false })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
