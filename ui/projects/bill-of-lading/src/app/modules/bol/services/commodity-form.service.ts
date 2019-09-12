import { Injectable, OnDestroy } from '@angular/core';
import { FormGroup, FormBuilder, FormArray } from '@angular/forms';
import { BehaviorSubject, Observable, merge, Subject } from 'rxjs';
import {
  CommodityInformationForm,
  CommodityInformation,
  Commodity,
  CommodityForm,
  BolSection,
  BolSectionService,
  BillOfLading
} from '../models';
import { BolService } from './bol.service';
import { filter, take, takeUntil, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CommodityFormService extends BolSectionService implements OnDestroy {
  private stop$ = new Subject<boolean>();
  private commodityForm: BehaviorSubject<FormGroup | undefined> = new BehaviorSubject(
    this.fb.group(new CommodityInformationForm(new CommodityInformation()))
  );

  commodityForm$: Observable<FormGroup> = this.commodityForm.asObservable().pipe(
    tap(fg => {
      this.stop$.next(true);

      const commodityList = fg.controls['commodityList'] as FormArray;
      if (commodityList.controls.length === 0) {
        this.addCommodity();
      }

      if (this.bolService.modified !== true) {
        fg.valueChanges
          .pipe(
            filter(() => fg.dirty === true),
            take(1),
            takeUntil(merge(this.stop$, this.bolService.stopModified$))
          )
          .subscribe(() => {
            console.log(`Form modified: Commodities`);
            this.bolService.setModified();
          });
      }
    })
  );

  constructor(private fb: FormBuilder, private bolService: BolService) {
    super();
  }

  addCommodity(c: Commodity = new Commodity()) {
    const currentForm = this.commodityForm.getValue();
    const currentCommodities = currentForm.get('commodityList') as FormArray;

    currentCommodities.push(this.fb.group(new CommodityForm(c)));
    this.commodityForm.next(currentForm);
  }

  deleteCommodity(index: number) {
    const currentTeam = this.commodityForm.getValue();
    const currentCommodities = currentTeam.get('commodityList') as FormArray;

    if (currentCommodities.length > 1) {
      currentCommodities.removeAt(index);
      this.commodityForm.next(currentTeam);
    }
  }

  valid(): boolean {
    return this.isValid(this.commodityForm.getValue());
  }

  ngOnDestroy() {
    this.stop$.next(true);
    this.stop$.unsubscribe();
  }

  resetForm(c: CommodityInformation = new CommodityInformation()) {
    const newForm = this.fb.group(new CommodityInformationForm(c));

    if (c.commodityList.length) {
      const commodityList = newForm.get('commodityList') as FormArray;

      for (const commodity of c.commodityList) {
        // console.log(`creating commodity from`, commodity);
        const commodityForm = this.fb.group(new CommodityForm(commodity));
        // console.log(`adding commodity form...`, commodityForm.getRawValue());
        commodityList.push(commodityForm);
      }
    }

    this.commodityForm.next(newForm);
  }

  populateModel(bol: BillOfLading) {
    const form = this.commodityForm.getValue();

    //   commodityList = new FormArray([]);
    // contactName = new FormControl('');
    // phone = new FormControl('');
    // phoneExt = new FormControl('');
    // totalCube = new FormControl('');
    // specialIns = new FormControl('');

    bol.commodityInfo.contactName = form.controls['contactName'].value;
    bol.commodityInfo.phone = form.controls['phone'].value;
    bol.commodityInfo.phoneExt = form.controls['phoneExt'].value;
    bol.commodityInfo.totalCube = form.controls['totalCube'].value;
    bol.commodityInfo.specialIns = form.controls['specialIns'].value;
    bol.commodityInfo.commodityList = [];

    for (const c of (form.controls['commodityList'] as FormArray).controls) {
      const fg = c as FormGroup;

      // commodityId = new FormControl();
      // hazmat = new FormControl(false);
      // goodsUnit = new FormControl();
      // goodsType = new FormControl();
      // goodsWeight = new FormControl();
      // shipmentClass = new FormControl();
      // numberOfPackage = new FormControl('');
      // packageType = new FormControl('');
      // nmfc = new FormControl();
      // nmfcExt = new FormControl();
      // description = new FormControl();

      const commodity = new Commodity();
      commodity.commodityId = fg.controls['commodityId'].value;
      commodity.hazmat = fg.controls['hazmat'].value;
      commodity.goodsType = fg.controls['goodsType'].value;
      commodity.goodsUnit = fg.controls['goodsUnit'].value;
      commodity.goodsWeight = fg.controls['goodsWeight'].value;
      commodity.shipmentClass = fg.controls['shipmentClass'].value;
      commodity.numberOfPackage = fg.controls['numberOfPackage'].value;
      commodity.packageType = fg.controls['packageType'].value;
      commodity.nmfc = fg.controls['nmfc'].value;
      commodity.nmfcExt = fg.controls['nmfcExt'].value;
      commodity.description = fg.controls['description'].value;

      bol.commodityInfo.commodityList.push(commodity);
    }
  }
}
