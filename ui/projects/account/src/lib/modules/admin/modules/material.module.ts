import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  MatCardModule,
  MatDialogModule,
  MatInputModule,
  MatCheckboxModule,
  MatProgressSpinnerModule,
  MatRadioModule,
  MatTooltipModule,
  MatSortModule,
  MatSelectModule,
  MatTableModule,
  MatPaginatorModule
} from '@angular/material';


@NgModule({
  imports: [
    CommonModule,
    MatCardModule,
    MatDialogModule,
    MatInputModule,
    MatProgressSpinnerModule,
    MatSelectModule,
    MatSortModule,
    // MatCheckboxModule,
    MatRadioModule,
    MatTableModule,
    MatPaginatorModule
    // MatTooltipModule
  ],
  exports: [
    MatCardModule,
    MatInputModule,
    MatProgressSpinnerModule,
    MatSortModule,
    MatSelectModule,
    MatTableModule,
    MatPaginatorModule,
    // MatCheckboxModule,
    MatRadioModule
    // MatTooltipModule
  ]
})
export class MaterialModule {}
