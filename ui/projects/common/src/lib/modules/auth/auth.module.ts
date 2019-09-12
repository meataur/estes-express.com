import { NgModule, ModuleWithProviders, InjectionToken } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from './services/auth.service';
import { AuthGuard } from './guards/auth.guard';
import { AuthCanLoadGuard } from './guards/auth-can-load.guard';
import { ScopeGuard } from './guards/scope.guard';

@NgModule({
  imports: [CommonModule],
  declarations: []
})
export class AuthModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: AuthModule,
      providers: [AuthService, AuthGuard, AuthCanLoadGuard, ScopeGuard]
    };
  }
}
