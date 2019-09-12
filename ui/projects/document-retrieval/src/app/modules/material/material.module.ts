import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  MatCardModule,
  MatInputModule,
  MatCheckboxModule,
  MatRadioModule,
  MatTooltipModule
} from '@angular/material';

@NgModule({
  imports: [
    CommonModule,
    MatCardModule,
    MatInputModule,
    MatCheckboxModule,
    MatRadioModule,
    MatTooltipModule
  ],
  exports: [
    MatCardModule,
    MatInputModule,
    MatCheckboxModule,
    MatRadioModule,
    MatTooltipModule
  ]
})
export class MaterialModule {}
