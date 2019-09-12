import { NgModule } from '@angular/core';
import { MyEstesModule } from 'common';

/**
 * Had to create a wrapper module to support lazy loading.
 * See https://github.com/angular/angular-cli/issues/6373#issuecomment-319116889
 */
@NgModule({
	imports: [MyEstesModule]
})
export class LazyMyEstesModule {}
