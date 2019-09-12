import { Component, OnInit, Inject, SecurityContext } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { RateQuote } from '../../models';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Component({
  selector: 'app-disclosures-modal',
  templateUrl: './disclosures-modal.component.html',
  styleUrls: ['./disclosures-modal.component.scss']
})
export class DisclosuresModalComponent implements OnInit {
  constructor(
    private dialogRef: MatDialogRef<DisclosuresModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: RateQuote,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit() {}

  getHtmlDisclaimers(): SafeHtml {
    return this.sanitizer.bypassSecurityTrustHtml(
      this.data.disclaimers.reduce((accum, curr) => {
        return (accum += curr);
      }, '')
    );
  }
}
