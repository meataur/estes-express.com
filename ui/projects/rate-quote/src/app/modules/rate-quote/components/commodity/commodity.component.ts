import {
  Component,
  OnInit,
  EventEmitter,
  Input,
  Output,
  OnDestroy,
  AfterViewInit,
  ChangeDetectorRef
} from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Observable, Subject, of } from 'rxjs';
import { PackageType, UtilService, FormService, AuthService } from 'common';
import { map, share, takeUntil } from 'rxjs/operators';
import { RateQuoteType, CommodityItemForm } from '../../models';
import { RateQuoteTypeService } from '../../services/rate-quote-type.service';
import { RateQuoteService } from '../../services/rate-quote.service';
import { ChangeDetectionStrategy } from '@angular/core';
import { CommoditiesService } from '../../services/commodities.service';

@Component({
  selector: 'app-commodity',
  templateUrl: './commodity.component.html',
  styleUrls: ['./commodity.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CommodityComponent implements OnInit, OnDestroy {
  private stop$ = new Subject<boolean>();
  @Output() deleteCommodity: EventEmitter<number> = new EventEmitter<number>();
  @Input() index: number;
  @Input() commodityForm: FormGroup;
  @Input() canDelete: boolean;
  @Input() rateQuoteType$: Observable<RateQuoteType>;

  packageTypes$: Observable<Array<PackageType>>;
  classes$: Observable<Array<string>>;
  ltlOnly: boolean;
  vtlOnly: boolean;

  constructor(
    private utilService: UtilService,
    public formService: FormService,
    private auth: AuthService,
    private commodityService: CommoditiesService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.commodityService.changeDetect$.pipe(takeUntil(this.stop$)).subscribe(_ => {
      this.cdr.markForCheck();
    });
    this.packageTypes$ = this.utilService.getPackageTypes();
    this.classes$ = this.utilService.getClasses();

    this.rateQuoteType$.pipe(takeUntil(this.stop$)).subscribe(next => {
      this.ltlOnly = next.isLTLOnly;
      this.vtlOnly = next.isVTLOnly;
      CommodityItemForm.setFormValidators(
        this.commodityForm,
        next.isVTLOnly,
        next.isLTLOnly,
        this.isRateEstimate
      );
    });
  }

  ngOnDestroy() {
    this.stop$.next(true);
    this.stop$.unsubscribe();
  }

  delete() {
    this.deleteCommodity.next(this.index);
  }

  get isRateEstimate() {
    return !this.auth.isLoggedIn;
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
