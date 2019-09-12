import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  MatCardModule,
  MatInputModule,
  MatCheckboxModule,
  MatRadioModule,
  MatTooltipModule,
  MatSelectModule,
  MatTableModule,
  MatPaginatorModule
} from '@angular/material';


@NgModule({
  imports: [
    CommonModule,
    MatCardModule,
    MatInputModule,
    MatSelectModule,
    // MatCheckboxModule,
    MatRadioModule,
    MatTableModule,
    MatPaginatorModule
    // MatTooltipModule
  ],
  exports: [
    MatCardModule,
    MatInputModule,
    MatSelectModule,
    MatTableModule,
    MatPaginatorModule,
    // MatCheckboxModule,
    MatRadioModule
    // MatTooltipModule
  ]
})
export class MaterialModule {}
