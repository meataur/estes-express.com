import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { BolService } from '../../services/bol.service';
import { DialogService, SnackbarService } from 'common';
import { Subject, Subscription, merge, of } from 'rxjs';
import { map, startWith, switchMap, catchError } from 'rxjs/operators';
import { Template, TemplateFilterEnum } from '../../models';
import { TemplatesModalComponent } from '../templates-modal/templates-modal.component';

@Component({
  selector: 'app-templates-bol',
  templateUrl: './templates-bol.component.html',
  styleUrls: ['./templates-bol.component.scss']
})
export class TemplatesBolComponent implements OnInit, OnDestroy {
  private refreshData = new Subject<boolean>();
  activeAdvancedSearch = false;
  loading = false;
  displayedColumns = ['templateName', 'bolNumber', 'bolDate', 'proNumber', 'shipper', 'consignee', 'actions'];
  pageSize = 25;
  results: Array<Template>;
  dataSource = new MatTableDataSource<Template>();
  noData = this.dataSource.connect().pipe(map(data => data.length === 0));
  tableSub: Subscription;
  resultsLength: number;
  expandSearch = false;
  filterOptions: {
    templateName: string;
    bolEndDate: string;
    bolNumber: string;
    bolStartDate: string;
    consignee: string;
    filterBy: string;
    proNumber: string;
    shipper: string;
  } = {
    templateName: '',
    bolEndDate: '',
    bolNumber: '',
    bolStartDate: '',
    consignee: '',
    filterBy: '',
    proNumber: '',
    shipper: ''
  };

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    private bolService: BolService,
    private dialog: DialogService,
    private snackbar: SnackbarService
  ) {}

  ngOnInit() {
    this.paginator.pageSize = this.pageSize;
    this.sort.active = 'bolDate';
    this.sort.direction = 'desc';

    // this.dataSource.sortingDataAccessor = (item, property) => {
    //   console.log(item, property);
    //   switch (property) {
    //     case 'bolDate':
    //       console.log(`sorting bolDate`);
    //       return new Date(item.bolDate);
    //     default:
    //       return item[property];
    //   }
    // };

    this.tableSub = merge(this.sort.sortChange, this.paginator.page, this.refreshData)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.loading = true;
          return this.bolService.getTemplates(
            {
              page: this.paginator.pageIndex + 1,
              size: this.paginator.pageSize,
              sort: this.sort.active || 'bolDate',
              direction: this.sort.direction || 'desc'
            },
            this.filterOptions
          );
        }),
        map(data => {
          this.resultsLength = data.totalElements;

          return data.content;
        }),
        catchError(() => {
          return of([]);
        })
      )
      .subscribe(data => {
        this.loading = false;
        this.dataSource.data = data;
      });
  }

  openHelpModal() {
    this.dialog.prompt(TemplatesModalComponent, null);
  }

  onFilterChange(e: [TemplateFilterEnum, string | string[] | null]) {
    const filter: TemplateFilterEnum = e[0];

    this.filterOptions.filterBy = filter;

    switch (TemplateFilterEnum[filter]) {
      case TemplateFilterEnum.SHOW_ALL:
        this.activeAdvancedSearch = false;
        // No contains filter needed.
        break;
      case TemplateFilterEnum.BOL_DATE_RANGE:
        this.filterOptions.bolStartDate = e[1][0];
        this.filterOptions.bolEndDate = e[1][1];
        this.activeAdvancedSearch = true;
        break;
      case TemplateFilterEnum.TEMPLATE_NAME:
        this.filterOptions.templateName = e[1] as string;
        this.activeAdvancedSearch = true;
        break;
      case TemplateFilterEnum.BOL_NUMBER:
        this.filterOptions.bolNumber = e[1] as string;
        this.activeAdvancedSearch = true;
        break;
      case TemplateFilterEnum.CONSIGNEE:
        this.filterOptions.consignee = e[1] as string;
        this.activeAdvancedSearch = true;
        break;
      case TemplateFilterEnum.SHIPPER:
        this.filterOptions.shipper = e[1] as string;
        this.activeAdvancedSearch = true;
        break;
      case TemplateFilterEnum.PRO_NUMBER:
        this.filterOptions.proNumber = e[1] as string;
        this.activeAdvancedSearch = true;
        break;
    }

    this.paginator.firstPage();
    this.refreshData.next(true);
  }

  delete(t: Template) {
    this.dialog
      .confirm(
        `Confirm Delete`,
        `Are you sure you want to delete the template named: ${t.templateName}?`
      )
      .subscribe(next => {
        if (next) {
          this.bolService.deleteTemplate(t.templateName).subscribe(
            () => {
              this.snackbar.success(`Template deleted successfully.`);
              this.refreshData.next(true);
            },
            err => {
              this.snackbar.error(
                `An unexpected error occurred while deleting the template.  Please try again.`
              );
            }
          );
        }
      });
  }

  ngOnDestroy() {
    if (this.tableSub) {
      this.tableSub.unsubscribe();
    }
  }
}
