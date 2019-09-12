import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserListComponent } from './components/user-list/user-list.component';
import { CreateUserComponent } from './components/create-user/create-user.component';
import { AdminRoutingModule } from './admin-routing.module';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from './modules/material.module';
import { SharedModule } from 'common';
import { AdminService } from './services/admin.service';
import { ResetPasswordModalComponent } from './components/reset-password-modal/reset-password-modal.component';
import { UserAccessModalComponent } from './components/user-access-modal/user-access-modal.component';

@NgModule({
  imports: [
    CommonModule,
    AdminRoutingModule,
    ReactiveFormsModule,
    MaterialModule,
    SharedModule
  ],
  declarations: [UserListComponent, CreateUserComponent, ResetPasswordModalComponent, UserAccessModalComponent],
  providers: [AdminService],
  entryComponents: [ResetPasswordModalComponent, UserAccessModalComponent]
})
export class AdminModule { }
