import {
  Component,
  OnInit,
  Input,
  Output,
  EventEmitter,
  OnDestroy,
  AfterViewInit
} from '@angular/core';
import { FormGroup, Validators } from '@angular/forms';
import {
  FormService,
  MessageConstants,
  RegEx,
  UtilService,
  PackageType,
  MyEstesValidators
} from 'common';
import { goodsTypes } from '../commodity-information/goods-types';
import { classTypes } from '../commodity-information/class-types';
import { Subscription, Observable, BehaviorSubject, combineLatest } from 'rxjs';
import { patternValidator } from 'common';
import { DocumentTypeEnum, ShipmentClass } from '../../models';
import { BolSourceFormService } from '../../services/bol-source-form.service';
import { share, map, reduce, tap } from 'rxjs/operators';

@Component({
  selector: 'app-commodity',
  templateUrl: './commodity.component.html',
  styleUrls: ['./commodity.component.scss']
})
export class CommodityComponent implements OnInit, OnDestroy {
  @Output() deleteCommodity: EventEmitter<number> = new EventEmitter<number>();
  @Output() hazmatChanged: EventEmitter<boolean> = new EventEmitter<boolean>();
  @Input() index: number;
  @Input() commodityForm: FormGroup;
  @Input() canDelete: boolean;

  packageTypes$: Observable<Array<PackageType>>;
  filteredPackageTypes$: Observable<Array<PackageType>>;
  filter$: BehaviorSubject<boolean>;

  classes$: Observable<Array<ShipmentClass>>;

  hazmatSub: Subscription;
  bolSourceFormSub: Subscription;
  vicsBol: boolean;

  constructor(
    public formService: FormService,
    private bolSourceFormService: BolSourceFormService,
    private utilService: UtilService
  ) {}

  compareFn(item1, item2): boolean {
    return item1 && item2 ? item1.code === item2.code : item1 === item2;
  }

  ngOnInit() {
    this.packageTypes$ = this.utilService.getPackageTypes().pipe(share());
    this.filter$ = new BehaviorSubject(this.hazmat.value);
    this.filteredPackageTypes$ = this.createFilterPackageTypes(this.filter$, this.packageTypes$);
    this.classes$ = this.utilService.getClasses().pipe(
      map(next =>
        next.reduce((accum, val) => {
          accum.push({ code: val, description: null });
          return accum;
        }, [])
      )
    );

    this.hazmatSub = this.commodityForm.controls['hazmat'].valueChanges.subscribe(
      (next: boolean) => {
        if (next) {
          this.description.setValidators([MyEstesValidators.required, Validators.maxLength(512)]);
          this.goodsType.setValue('');
        } else {
          this.description.clearValidators();
        }
        this.description.updateValueAndValidity();
        this.hazmatChanged.emit(next);
        this.filterChange(next);
      }
    );

    this.bolSourceFormSub = this.bolSourceFormService.documentTypeChanged$.subscribe(next => {
      if (next === DocumentTypeEnum.V) {
        this.vicsBol = true;
        this.numberOfPackage.setValidators([MyEstesValidators.required, Validators.maxLength(5)]);
        this.packageType.setValidators([MyEstesValidators.required]);
      } else {
        this.vicsBol = false;
        this.numberOfPackage.reset();
        this.packageType.reset();
        this.numberOfPackage.clearValidators();
        this.packageType.clearValidators();
      }
    });
  }

  public createFilterPackageTypes(
    filter$: Observable<boolean>,
    characters$: Observable<Array<PackageType>>
  ) {
    return combineLatest(characters$, filter$, (types: Array<PackageType>, filter: boolean) => {
      if (!filter) return types;
      return types.filter((type: PackageType) => type.code !== 'PT');
    });
  }

  filterChange(value: boolean) {
    this.filter$.next(value);
  }

  ngOnDestroy() {}

  delete() {
    if (this.canDelete) {
      this.deleteCommodity.emit(this.index);
    }
  }

  get commodityId() {
    return this.commodityForm.controls['commodityId'];
  }
  get hazmat() {
    return this.commodityForm.controls['hazmat'];
  }
  get goodsUnit() {
    return this.commodityForm.controls['goodsUnit'];
  }
  get goodsType() {
    return this.commodityForm.controls['goodsType'];
  }
  get goodsWeight() {
    return this.commodityForm.controls['goodsWeight'];
  }
  get shipmentClass() {
    return this.commodityForm.controls['shipmentClass'];
  }
  get numberOfPackage() {
    return this.commodityForm.controls['numberOfPackage'];
  }
  get packageType() {
    return this.commodityForm.controls['packageType'];
  }
  get nmfc() {
    return this.commodityForm.controls['nmfc'];
  }
  get nmfcExt() {
    return this.commodityForm.controls['nmfcExt'];
  }
  get description() {
    return this.commodityForm.controls['description'];
  }
}
