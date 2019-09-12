import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { Subscription, empty } from 'rxjs';
import { MatTableDataSource, MatSort, MatPaginator, MatDialogRef } from '@angular/material';
import { switchMap, map } from 'rxjs/operators';
import { CommodityService } from '../../services/commodity.service';
import { Commodity } from '../../models';
import { SnackbarService } from '../../../snackbar/public_api';
import { Observable } from 'rxjs';

@Component({
  selector: 'lib-commodity-library-modal',
  templateUrl: './commodity-library-modal.component.html',
  styleUrls: ['./commodity-library-modal.component.scss'],
  providers: [CommodityService]
})
export class CommodityLibraryModalComponent implements OnInit, OnDestroy {
  pageSize = 25;
  displayedColumns = [
    'id',
    'description',
    'hazmat',
    'goodsQuantity',
    'weight',
    'nmfc',
    'shipClass'
  ];
  tableSub: Subscription;
  deleteSub: Subscription;
  dataSource = new MatTableDataSource<Commodity>();
  noData = this.dataSource.connect().pipe(map(data => data.length === 0));
  loading: boolean;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    public dialogRef: MatDialogRef<CommodityLibraryModalComponent>,
    private commodityService: CommodityService,
    private snackbar: SnackbarService
  ) {}

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.initTable();
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

  onCommoditySelected(c: Commodity) {
    this.dialogRef.close(c);
  }

  getFormattedDescription(description: string): string {
    return description.length > 30 ? `${description.substr(0, 30)}...` : description;
  }

  private initTable() {
    this.loading = true;
    this.tableSub = this.commodityService.getCommodities().subscribe(
      next => {
        this.dataSource.data = next;
      },
      err => {
        this.loading = false;
        this.snackbar.error(`An error occurred while loading Commodities.  Please try again.`);
      },
      () => (this.loading = false)
    );
  }
}
