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
  MatPaginatorModule,
  MatSortModule
} from '@angular/material';


@NgModule({
  imports: [
    CommonModule,
    MatCardModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatRadioModule,
    MatInputModule,
    // MatSelectModule,
    // MatCheckboxModule,
    // MatRadioModule,
    // MatTableModule,
    // MatPaginatorModule
    // MatTooltipModule
  ],
  exports: [
    MatCardModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatRadioModule,
    MatInputModule,
    // MatSelectModule,
    // MatTableModule,
    // MatPaginatorModule,
    // MatCheckboxModule,
    // MatRadioModule
    // MatTooltipModule
  ]
})
export class MaterialModule {}
