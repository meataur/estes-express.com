import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';


@Component({
  selector: 'lib-info-dialog',
  templateUrl: './info-dialog.component.html',
  styleUrls: []
})
export class InfoDialogComponent implements OnInit {
  constructor(
    private _sanitizer: DomSanitizer,
    public dialogRef: MatDialogRef<InfoDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngOnInit() {}

  public get htmlProperty(): SafeHtml {
    return this._sanitizer.bypassSecurityTrustHtml(this.data.template);
  }

  onNoClick() {
    this.dialogRef.close(false);
  }
}
