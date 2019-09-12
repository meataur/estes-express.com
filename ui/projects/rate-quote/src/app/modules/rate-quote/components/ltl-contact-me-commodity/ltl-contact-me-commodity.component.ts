import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { Subject, Observable } from 'rxjs';
import { FormGroup } from '@angular/forms';
import { UtilService, FormService, PackageType } from 'common';

@Component({
  selector: 'app-ltl-contact-me-commodity',
  templateUrl: './ltl-contact-me-commodity.component.html',
  styleUrls: ['./ltl-contact-me-commodity.component.scss']
})
export class LtlContactMeCommodityComponent implements OnInit, OnDestroy {
  private stop$ = new Subject<boolean>();
  @Input() index;
  @Input() commodityForm: FormGroup;

  packageTypes$: Observable<Array<PackageType>>;
  classes$: Observable<Array<string>>;

  constructor(
    private utilService: UtilService,
    public formService: FormService,
  ) {}

  ngOnInit() {
    this.packageTypes$ = this.utilService.getPackageTypes();
    this.classes$ = this.utilService.getClasses();
  }

  ngOnDestroy() {
    this.stop$.next(true);
    this.stop$.unsubscribe();
  }

  get description() {
    return this.commodityForm.controls['description'];
  }
  get length() {
    return this.commodityForm.controls['length'];
  }
  get width() {
    return this.commodityForm.controls['width'];
  }
  get height() {
    return this.commodityForm.controls['height'];
  }
  get pieces() {
    return this.commodityForm.controls['pieces'];
  }
  get pieceType() {
    return this.commodityForm.controls['pieceType'];
  }
  get shipClass() {
    return this.commodityForm.controls['shipClass'];
  }
  get weight() {
    return this.commodityForm.controls['weight'];
  }
}
