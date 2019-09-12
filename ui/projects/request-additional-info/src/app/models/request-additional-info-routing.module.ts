import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { RequestAdditionalInfoComponent } from '../request-additional-info.component';
const routes: Routes = [
  {path: 'request-additional-info', component: RequestAdditionalInfoComponent}
];


@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class RequestAdditionalInfoRoutingModule { }
