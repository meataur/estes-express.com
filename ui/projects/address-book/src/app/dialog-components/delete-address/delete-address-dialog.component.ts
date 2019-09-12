import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { BookAddress } from '../../book-address.model';

@Component({
  selector: 'delete-address-dialog',
  templateUrl: './delete-address-dialog.component.html',
  styleUrls: []
})
export class DeleteAddressDialogComponent implements OnInit {

  public bookAddress: any;

  constructor( public dialogRef: MatDialogRef<DeleteAddressDialogComponent>,
               @Inject(MAT_DIALOG_DATA) public data: any) {
    this.bookAddress = new BookAddress(data.bookAddress);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit() {

  }
}
