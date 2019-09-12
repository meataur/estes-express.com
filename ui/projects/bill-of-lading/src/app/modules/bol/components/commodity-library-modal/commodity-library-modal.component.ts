import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { Subscription, empty } from 'rxjs';
import { MatTableDataSource, MatSort, MatPaginator, MatDialogRef } from '@angular/material';
import { switchMap, map } from 'rxjs/operators';
import { DialogService, SnackbarService } from 'common';
import { CommodityLibraryViewModel } from '../../models';
import { BolService } from '../../services/bol.service';

@Component({
  selector: 'app-commodity-library-modal',
  templateUrl: './commodity-library-modal.component.html',
  styleUrls: ['./commodity-library-modal.component.scss']
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
  dataSource = new MatTableDataSource<CommodityLibraryViewModel>();
  noData = this.dataSource.connect().pipe(map(data => data.length === 0));
  loading: boolean;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    public dialogRef: MatDialogRef<CommodityLibraryModalComponent>,
    private bolService: BolService,
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

  onCommoditySelected(c: CommodityLibraryViewModel) {
    this.dialogRef.close(c);
  }

  getFormattedDescription(description: string): string {
    return description.length > 30 ? `${description.substr(0, 30)}...` : description;
  }

  private initTable() {
    this.loading = true;
    this.tableSub = this.bolService.getCommodities().subscribe(
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
