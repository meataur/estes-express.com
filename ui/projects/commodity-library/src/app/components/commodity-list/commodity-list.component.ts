import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { CommodityService } from '../../services/commodity.service';
import { Observable, Subscription, empty } from 'rxjs';
import { Commodity } from '../../models';
import { MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { map, switchMap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { SnackbarService, DialogService, AuthService, PromoService, LeftNavigationService } from 'common';

@Component({
  selector: 'app-commodity-list',
  templateUrl: './commodity-list.component.html',
  styleUrls: ['./commodity-list.component.scss']
})
export class CommodityListComponent implements OnInit, OnDestroy {
  // commodities$: Observable<Array<Commodity>>;
  pageSize = 25;
  displayedColumns = [
    'id',
    'description',
    'hazmat',
    'goodsQuantity',
    'weight',
    'nmfc',
    'shipClass',
    'action'
  ];
  tableSub: Subscription;
  deleteSub: Subscription;
  dataSource = new MatTableDataSource<Commodity>();
  noData = this.dataSource.connect().pipe(map(data => data.length === 0));
  loading: boolean;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    private router: Router,
    private commodityService: CommodityService,
    private snackbar: SnackbarService,
    private dialog: DialogService,
    private authService: AuthService,
    private promoService: PromoService,
    private leftNavigationService: LeftNavigationService
  ) {}

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.initTable();
    this.leftNavigationService.setNavigation(`My Estes`, `/menus/account`);
    this.promoService.setAppId('commodity-library');
    this.promoService.setAppState('authenticated');
    
 	this.authService.authStateSet.subscribe((authState: string) => {
      if ('unauthenticated' === authState) {
        this.router.navigate(['login']);
	  }
    });
  }

  ngOnDestroy() {
    if (this.tableSub) {
      this.tableSub.unsubscribe();
    }
    if (this.deleteSub) {
      this.deleteSub.unsubscribe();
    }
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  delete(c: Commodity) {
    this.deleteSub = this.dialog
      .confirm(
        `Confirm Delete`,
        `Are you sure you want delete this commodity with Product ID ${c.id}?`
      )
      .pipe(
        switchMap(resp => {
          if (resp === true) {
            return this.commodityService.deleteCommodity(c.id);
          } else {
            return empty();
          }
        })
      )
      .subscribe(next => {
        this.snackbar.success(`Commodity deleted successfully.`);
        this.dataSource.paginator.firstPage();
        this.initTable();
      }, err => {
        this.snackbar.error(`An error occurred while deleting the Commodity.  Please try again.`);
      });
  }

  getFormattedDescription(description: string): string {
    return description.length > 30
      ? `${description.substr(0, 30)}...`
      : description;
  }

  private initTable() {
    this.loading = true;
    this.tableSub = this.commodityService.getCommodities().subscribe(
      next => {
        this.dataSource.data = next || [];
      },
      err => {
        this.loading = false;
        this.snackbar.error(`An error occurred while loading Commodities.  Please try again.`);
      },
      () => (this.loading = false)
    );
  }
}
