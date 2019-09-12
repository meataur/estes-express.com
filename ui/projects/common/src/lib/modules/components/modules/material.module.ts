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
  MatSortModule,
  MatAutocompleteModule,
  MatDialogModule,
  MatTabsModule
} from '@angular/material';

@NgModule({
  imports: [
    CommonModule,
    MatAutocompleteModule,
    MatTabsModule,
    // MatCardModule,
    MatTableModule,
    // MatPaginatorModule,
    // MatSortModule
    MatInputModule,
    // MatSelectModule,
    // MatCheckboxModule,
    // MatRadioModule,
    // MatTableModule,
    // MatPaginatorModule
    MatTooltipModule,
    MatDialogModule
  ],
  exports: [
    // MatCardModule,
    MatTableModule,
    MatAutocompleteModule,
    // MatPaginatorModule,
    // MatSortModule
    MatInputModule,
    // MatSelectModule,
    // MatTableModule,
    // MatPaginatorModule,
    // MatCheckboxModule,
    // MatRadioModule
    MatTooltipModule,
    MatTabsModule,
    MatDialogModule
  ]
})
export class MaterialModule {}
