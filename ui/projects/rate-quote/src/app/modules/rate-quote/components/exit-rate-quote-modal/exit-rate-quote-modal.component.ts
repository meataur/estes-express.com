import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-exit-rate-quote-modal',
  templateUrl: './exit-rate-quote-modal.component.html',
  styleUrls: ['./exit-rate-quote-modal.component.scss']
})
export class ExitRateQuoteModalComponent implements OnInit {
  constructor(
    private dialogRef: MatDialogRef<ExitRateQuoteModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngOnInit() {}

  confirm() {
    this.dialogRef.close(true);
  }

  onNoClick() {
    this.dialogRef.close(false);
  }
}
