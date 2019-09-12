import { Component, OnInit, OnDestroy, ViewChild, ViewChildren, QueryList, AfterViewInit } from '@angular/core';
import { forkJoin, Subscription } from 'rxjs';
import { MatTableDataSource, MatDialog, MatSort } from '@angular/material';
import { Report } from './report.model';
import { OnlineReportingService } from './online-reporting.service';
import { FeedbackTypes } from 'common';
import { SnackbarService, DialogService, AuthService, PromoService, LeftNavigationService } from 'common';
import { Router } from '@angular/router';
import { CreateReportComponent } from './create-report.component';
import { isObject } from 'util';


@Component({
  selector: 'app-online-reporting',
  templateUrl: './online-reporting.component.html',
  styleUrls: ['./online-reporting.component.scss']
})
export class OnlineReportingComponent implements OnInit, AfterViewInit, OnDestroy {
  @ViewChildren(MatSort) sorts: QueryList<MatSort>;
  // @ViewChildren(CreateReportComponent) createReportComponents: QueryList<CreateReportComponent>;

  activeReports: MatTableDataSource<Report>;
  expiredReports: MatTableDataSource<Report>;
  editReport: Report;

  loading: boolean;
  errorMessages: [FeedbackTypes, string];
  displayedColumns: string[];
  tabSelectedIndex: number;
  frequencyStrings: any;
  reportTables: MatTableDataSource<Report>[];
  reportTableTitles: string[];
  reportSub: Subscription;
  openReportSub: Subscription;
  deleteReportSub: Subscription;


  constructor(
    private onlineReportingService: OnlineReportingService,
    public dialog: MatDialog,
    private snackbarService: SnackbarService,
    private dialogService: DialogService,
    public leftNavigationService: LeftNavigationService,
    public promoService: PromoService,
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit() {

    this.loading = true;
    this.displayedColumns = ['reportName', 'frequency', 'format', 'startDate', 'expirationDate', 'actions'];
    this.activeReports = new MatTableDataSource();
    this.expiredReports = new MatTableDataSource();
    this.reportTables = [this.activeReports, this.expiredReports];
    this.tabSelectedIndex = 0;
    this.reportTableTitles = ['Active Reports', 'Expired Reports'];
    this.frequencyStrings = {
      'D': 'Daily',
      'M': 'Monthly',
      'W': 'Weekly',
      '': ''
    };

    // this.availableReportsHelpText = 'The application’s “Active Reports” section displays a history of the reports that have been scheduled to run. Each list includes details such as Report Name, Frequency, Format, Start Date, and Expiration Date, as well as the option of modifying or canceling the report.';
    // this.expiredReportsHelpText = 'The application’s “Expired Reports” section displays a history of the reports that have expired. Each list contains the same detail as “My Reports,” with the option of modifying or canceling the report.';

    this.editReport = null;
    this.loadReports();

    this.leftNavigationService.setNavigation(`Manage`, `/manage`);
    this.promoService.setAppId('online-reporting');
    this.promoService.setAppState('authenticated');
    

 	this.authService.authStateSet.subscribe((authState: string) => {
      // console.log('OnlineReportingComponent.authStateSet:', authState);
      if ('unauthenticated' === authState) {
        this.router.navigate(['login']);
	  }
    });
  }

  ngAfterViewInit() {
    let i = 0;
    this.sorts.toArray().forEach(sort => {
      this.reportTables[i].sort = sort;
      i++;
    });
  }

  ngOnDestroy() {
    if (this.reportSub) {
      this.reportSub.unsubscribe();
    }
    if (this.openReportSub) {
      this.openReportSub.unsubscribe();
    }
    if (this.deleteReportSub) {
      this.deleteReportSub.unsubscribe();
    }
  }

  loadReports() {
    let ctrl = this;
    this.reportSub = ctrl.onlineReportingService.getReports()
    .subscribe(res => {
      let active = [];
      let expired = [];
      res.forEach(report => {
        if (report.expired) {
          expired.push(report);
        } else {
          active.push(report);
        }
      });
      ctrl.activeReports = new MatTableDataSource(active);
      ctrl.expiredReports = new MatTableDataSource(expired);
      ctrl.reportTables = [ctrl.activeReports, ctrl.expiredReports];
      // ctrl.reportTables[0].sort = ctrl.sort;
      // ctrl.reportTables[1].sort = ctrl.sort;
      ctrl.loading = false;
    },
    err => {
      ctrl.activeReports = new MatTableDataSource([]);
      ctrl.expiredReports = new MatTableDataSource([]);
      ctrl.reportTables = [ctrl.activeReports, ctrl.expiredReports];
      ctrl.errorMessages = ['error', err];
      ctrl.loading = false;
    });
  }

  openEditReportModal(report) {
    if (this.editReport) {
      this.openReportSub = this.dialogService.confirm('', 'Editing a new report will close the existing edit tab. Any unsaved changes will be lost. Would you like to proceed?').subscribe(confirm => {
        if (confirm) {
          this.editReport = report;
          this.tabSelectedIndex = 2;
        }
      })
    } else {
      this.editReport = report;
      this.tabSelectedIndex = 2;
    }
  }

  deleteReport(scheduleId: string) {
    this.deleteReportSub = this.dialogService.confirm('', 'Are you sure you want to delete?').subscribe(confirm => {
      if (confirm) {
        this.onlineReportingService.deleteReport(scheduleId)
        .subscribe(data => {
          this.snackbarService.success(data.message);
          this.loadReports();
        },
        err => {
          this.snackbarService.error(err);
        });
      }
    });

  }
}
