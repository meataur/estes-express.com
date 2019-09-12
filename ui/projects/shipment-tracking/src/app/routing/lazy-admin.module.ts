import { NgModule } from '@angular/core';
import { AdminModule } from 'account';

/**
 * Had to create a wrapper module to support lazy loading.
 * See https://github.com/angular/angular-cli/issues/6373#issuecomment-319116889
 */
@NgModule({
	imports: [AdminModule]
})
export class LazyAdminModule {}
