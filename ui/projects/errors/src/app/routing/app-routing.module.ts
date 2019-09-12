import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ErrorsComponent } from '../errors.component';
import { AuthGuard, AuthCanLoadGuard, ScopeGuard, ScopeCanLoadGuard } from 'common';
import { LoginComponent, ForgotPasswordComponent, SignupComponent, RequestAccountNumberComponent} from 'account';

const routes: Routes = [
	{
		path: '',
		pathMatch: 'full',
		redirectTo: '/not-found'
	},
	{
		path: 'bad-request',
		component: ErrorsComponent,
		pathMatch: 'full',
		data: {
			code: '400'
		}
	},
	{
		path: 'unauthorized',
		component: ErrorsComponent,
		pathMatch: 'full',
		data: {
			code: '401'
		}
	},
	{
		path: 'forbidden',
		component: ErrorsComponent,
		pathMatch: 'full',
		data: {
			code: '403'
		}
	},
	{
		path: 'not-found',
		component: ErrorsComponent,
		pathMatch: 'full',
		data: {
			code: '404'
		}
	},
	{
		path: 'internal-server-error',
		component: ErrorsComponent,
		pathMatch: 'full',
		data: {
			code: '500'
		}
	},
	{
		path: 'login',
		component: LoginComponent,
		pathMatch: 'prefix'
	},
	{
		path: 'forgot-password',
		component: ForgotPasswordComponent,
		pathMatch: 'prefix'
	},
	{
		path: 'sign-up',
		component: SignupComponent,
		pathMatch: 'prefix'
	},
	{
		path: 'request-account-number',
		component: RequestAccountNumberComponent,
		pathMatch: 'prefix'
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
    data: { scope: 'ADMINSUBS' },
    runGuardsAndResolvers: 'always'
  },
	{
		path: '**',
		redirectTo: '/not-found'
	}
];

@NgModule({
	imports: [
		CommonModule,
		RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload', enableTracing: true })
	],
	exports: [RouterModule]
})
export class AppRoutingModule {}
