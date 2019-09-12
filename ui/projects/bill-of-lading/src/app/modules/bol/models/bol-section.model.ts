import { Input } from '@angular/core';
import { BillOfLading } from './bill-of-lading.model';

export abstract class BolSection {
  @Input() draft: BillOfLading;
}
