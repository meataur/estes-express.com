import { Component, OnInit, ViewChild, OnDestroy, Inject } from '@angular/core';
import { MatPaginator, MatTableDataSource, MatSort } from '@angular/material';
import { merge, of, Subscription } from 'rxjs';
import { startWith, switchMap, map, catchError } from 'rxjs/operators';


import { FeedbackTypes, DialogService } from 'common';

import { environment } from '../environments/environment';
import { PickupRequestService } from './pickup-request.service';
import { PickupResponse } from './models/pickup-response.model';
import { Pickup } from './models/pickup.model';


@Component({
  selector: 'app-pickup-history',
  templateUrl: './pickup-history.component.html',
  styleUrls: ['./pickup-history.component.scss']
})
export class PickupHistoryComponent implements OnInit {

  displayedColumns: string[];
  pageSize: number;
  results: Pickup[];
  dataSource: MatTableDataSource<Pickup>;
  feedback: [FeedbackTypes, string];
  loading: boolean;
  tableSub: Subscription;
  resultsLength: number;


  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;


  constructor(private pickupRequestService: PickupRequestService, private dialogService: DialogService) {
    this.displayedColumns = ['requestNumber', 'proNumber', 'shipperCompany', 'shipperCity', 'shipperState', 'submittedDate', 'pickupDate', 'status', 'mobileView'];
    this.resultsLength = 0
  }

  ngOnInit() {
    this.paginator.pageSize = 25;
    this.dataSource = new MatTableDataSource();
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.tableSub = merge(this.sort.sortChange, this.paginator.page)
    .pipe(
      startWith({}),
      switchMap(() => {
        this.loading = true;
        return this.pickupRequestService.getPickupHistory(
          this.paginator.pageIndex + 1,
          this.paginator.pageSize,
          this.sort.active || 'requestNumber',
          this.sort.direction || 'desc'
        );
      }),
      map(data => {
        this.resultsLength = data.totalElements;
        return data.content;
      }))
      .subscribe(data => {
        this.results = data;
        this.dataSource = new MatTableDataSource(data);
        this.loading = false;
      }, err => {
        this.loading = false;
      });
  }

  getShipmentTrackingUrl(proNumber: string) {
    return `${environment.appBaseUrl}/shipment-tracking/?type=PRO&query=${proNumber}`;
  }

  openPickupModal(pickup: Pickup) {
    this.dialogService.info('Pickup Request Details', `<div>
                                                       <div class="row"><label class="col-4 font-weight-bold">Request Number:</label><span class="col-8">${pickup.requestNumber || ''}</span></div>
                                                       <div class="row"><label class="col-4 font-weight-bold">Pieces:</label><span class="col-8">${pickup.totalPieces || ''}</span></div>
                                                       <div class="row"><label class="col-4 font-weight-bold">Weight:</label><span class="col-8">${pickup.totalWeight || ''}</span></div>
                                                       <div class="row mt-2 mb-2"><h5 class="col-12 font-weight-bold">Terminal Information</h5></div>
                                                       <div class="row"><label class="col-4 font-weight-bold">Name:</label><span class="col-8">${pickup.terminalName || ''}</span></div>
                                                       <div class="row"><label class="col-4 font-weight-bold">Phone:</label><span class="col-8">${pickup.terminalPhone || ''}</span></div>
                                                       <div class="row"><label class="col-4 font-weight-bold">Fax:</label><span class="col-8">${pickup.terminalFax || ''}</span></div>`, 'estes-pickup-modal');
  }

}
