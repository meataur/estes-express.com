import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Component({
  selector: 'app-pickup-options-modal',
  templateUrl: './pickup-options-modal.component.html',
  styleUrls: ['./pickup-options-modal.component.scss']
})
export class PickupOptionsModalComponent implements OnInit {
  constructor(
    private _sanitizer: DomSanitizer,
    public dialogRef: MatDialogRef<PickupOptionsModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: string
  ) {}

  public get htmlProperty(): SafeHtml {
    return this._sanitizer.bypassSecurityTrustHtml(this.data);
  }

  ngOnInit() {}
}
