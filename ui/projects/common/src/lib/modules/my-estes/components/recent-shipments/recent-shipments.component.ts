import { Component, OnInit, ViewChild, OnDestroy, Inject } from '@angular/core';
import { MatPaginator, MatTableDataSource, MatSort } from '@angular/material';
import { MyestesService } from '../../services/myestes.service';
import { ActivatedRoute, Router } from '@angular/router';
import { formatDate } from '@angular/common';
import { FormGroup, FormBuilder, AbstractControl, Validators } from '@angular/forms';
import { merge, SubscriptionLike, Subscription, Observable } from 'rxjs';
import { startWith, map, take } from 'rxjs/operators';
import { RecentShipment, PreferenceData } from '../../models';
import { DialogService } from '../../../dialog/services/dialog.service';
import { PreferenceDialogComponent } from '../preference-dialog/preference-dialog.component';
import { LeftNavigationService } from '../../../left-navigation/services/left-navigation.service';
import { FeedbackTypes } from '../../../components/models';
import { FormService } from '../../../../util/services/form.service';
import { validateFormFields } from '../../../../util/validate-form-fields';
import { APP_CONFIG } from '../../../../app.config';
import { AuthService } from '../../../auth/services/auth.service';
import { AppConfig } from '../../../../models/app-config.interface';

@Component({
  selector: 'lib-recent-shipments',
  templateUrl: './recent-shipments.component.html',
  styleUrls: ['./recent-shipments.component.scss']
})
export class RecentShipmentsComponent implements OnInit, OnDestroy {
  loading = false;
  displayedColumns = ['pro', 'pickupDate', 'deliveryDate', 'bol', 'status', 'consigneeInfo'];
  pageSize = 25;
  results: Array<RecentShipment> = [];
  resultsLength = 0;
  accountNumber: string;
  formGroup: FormGroup;
  tableSub: Subscription;
  dataSource = new MatTableDataSource<RecentShipment>();
  noData = this.dataSource.connect().pipe(map(data => data.length === 0));
  originalShipmentType: string;
  shipmentSub: Subscription;
  prefSub: Subscription;
  shipmentType: 'T' | 'S' | 'C';

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    private myestesService: MyestesService,
    private route: ActivatedRoute,
    private router: Router,
    private dialogService: DialogService,
    private authService: AuthService,
    public leftNavigationService: LeftNavigationService,
    @Inject(APP_CONFIG) public appConfig: AppConfig
  ) {}

  ngOnInit() {
    this.sort.active = 'pro';
    this.sort.direction = 'asc';
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.accountNumber = this.route.snapshot.paramMap.get('accountNumber');
    this.prefSub = this.myestesService
      .getDefaultParty()
      .pipe(take(1))
      .subscribe(next => {
        if (next) {
          this.shipmentType = next;
          this.originalShipmentType = next;
        }

        this.populateTable();
      });
    this.leftNavigationService.setNavigation(`Manage`, `/manage`);
    this.authService.authStateSet.subscribe((authState: string) => {
      if ('unauthenticated' === authState) {
        this.router.navigate(['login']);
	    }
    });
  }

  getShipmentTrackingUrl(proNumber: string) {
    return `${this.appConfig.appBaseUrl}/shipment-tracking/?type=PRO&query=${proNumber}`;
  }

  openPreferenceModal() {
    const data: PreferenceData = {
      shipmentType: this.originalShipmentType
    };

    this.dialogService
      .prompt(PreferenceDialogComponent, data)
      .subscribe((next: 'C' | 'T' | 'S') => {
        if (next) {
          this.originalShipmentType = next;
          this.onShipmentTypeChanged(next);
        }
      });
  }

  get accountLookup(): AbstractControl {
    return this.formGroup.controls['accountLookup'];
  }

  applyFilter(filterValue: string) {
    let filterVal = filterValue.trim().toLowerCase();
    filterVal = filterVal.replace('-', '');
    this.dataSource.filter = filterVal;

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  onShipmentTypeChanged(shipmentType: 'S' | 'C' | 'T') {
    this.shipmentType = shipmentType;
    this.populateTable();
  }

  private populateTable() {
    this.loading = true;
    if (this.tableSub) {
      this.tableSub.unsubscribe();
    }
    this.tableSub = this.myestesService
      .getRecentShipments(this.accountNumber, this.shipmentType)
      .subscribe(
        next => {
          this.dataSource.data = next;
        },
        err => {
          this.loading = false;
        },
        () => (this.loading = false)
      );
  }

  ngOnDestroy() {
    if (this.tableSub) {
      this.tableSub.unsubscribe();
    }
    if (this.prefSub) {
      this.prefSub.unsubscribe();
    }
    if (this.shipmentSub) {
      this.shipmentSub.unsubscribe();
    }
  }
}
