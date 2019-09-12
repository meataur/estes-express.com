import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatTableDataSource, MatSort } from '@angular/material';
import { Accessorial } from '../../models';
import { SelectionModel } from '@angular/cdk/collections';

@Component({
  selector: 'app-accessorials-modal',
  templateUrl: './accessorials-modal.component.html',
  styleUrls: ['./accessorials-modal.component.scss']
})
export class AccessorialsModalComponent implements OnInit {
  @ViewChild(MatSort) sort: MatSort;
  dataSource = new MatTableDataSource<Accessorial>();
  displayedColumns = ['select', 'description'];
  selection: SelectionModel<Accessorial>;

  constructor(
    public dialogRef: MatDialogRef<AccessorialsModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: SelectionModel<Accessorial>
  ) {}

  ngOnInit() {
    this.selection = new SelectionModel<Accessorial>(true, [...this.data.selected]);
    this.dataSource.sort = this.sort;
    this.dataSource.data = []; // AccessorialList;
  }

  onSubmit() {
    this.dialogRef.close(this.selection);
  }

  onNoClick() {
    this.dialogRef.close();
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    this.isAllSelected()
      ? this.selection.clear()
      : this.dataSource.data.forEach(row => this.selection.select(row));
  }
}
