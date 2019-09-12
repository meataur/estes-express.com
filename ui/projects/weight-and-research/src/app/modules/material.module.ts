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
  MatTabsModule,
  MatDatepickerModule,
  MatSlideToggleModule,
  MatDialogModule,
  MatSortModule,
  MatIconModule,
  MatButtonModule,
  MatAutocompleteModule
} from '@angular/material';


@NgModule({
  imports: [
    CommonModule,
    MatCardModule,
    MatInputModule,
    MatSelectModule,
    MatCheckboxModule,
    MatRadioModule,
    MatTableModule,
    MatPaginatorModule,
    MatTabsModule,
    MatDatepickerModule,
    MatSlideToggleModule,
    MatDialogModule,
    MatSortModule,
    MatIconModule,
    MatButtonModule,
    MatAutocompleteModule
  ],
  exports: [
    MatTabsModule,
    MatCardModule,
    MatInputModule,
    MatSelectModule,
    MatTableModule,
    MatPaginatorModule,
    MatCheckboxModule,
    MatRadioModule,
    MatDatepickerModule,
    MatSlideToggleModule,
    MatDialogModule,
    MatSortModule,
    MatIconModule,
    MatButtonModule,
    MatAutocompleteModule
  ]
})
export class MaterialModule {}
