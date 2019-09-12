import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material';


@Component({
  selector: 'app-history-modal',
  templateUrl: './history-modal.component.html',
  styleUrls: ['./history-modal.component.scss']
})
export class HistoryModalComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<HistoryModalComponent>) { }

  ngOnInit() {
  }

  onNoClick() {
    this.dialogRef.close(false);
  }
}
