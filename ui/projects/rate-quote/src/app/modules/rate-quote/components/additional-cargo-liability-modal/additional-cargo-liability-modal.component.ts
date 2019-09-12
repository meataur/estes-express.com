import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { environment } from '../../../../../environments/environment';

@Component({
  selector: 'app-additional-cargo-liability-modal',
  templateUrl: './additional-cargo-liability-modal.component.html',
  styleUrls: ['./additional-cargo-liability-modal.component.scss']
})
export class AdditionalCargoLiabilityModalComponent implements OnInit {
  readonly rulesTariffUrl = `${environment.cmsUrl}/resources/fees-and-surcharges/rules-tariff`;
  constructor(dialogRef: MatDialogRef<AdditionalCargoLiabilityModalComponent>) {}

  ngOnInit() {}
}
