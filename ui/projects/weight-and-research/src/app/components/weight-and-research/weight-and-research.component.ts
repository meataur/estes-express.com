import { Component, OnInit, ViewChild, OnDestroy } from "@angular/core";
import {
  FormGroup,
  FormBuilder,
  Validators,
  FormGroupDirective
} from "@angular/forms";
import { WeightAndResearchService } from "../../services/weight-and-research.service";
import {
  FormService,
  SnackbarService,
  validateFormFields,
  extractDateFromString,
  AuthService,
  UserRoleEnum,
  UtilService,
  SubAccount,
  FeedbackTypes,
  MyEstesValidators,
  DialogService,
  LeftNavigationService,
  PromoService,
  RegEx,
  textAreaValidator,
  dateValidator
} from "common";
import {
  WeightAndResearchForm,
  WRSearchRequest,
  WRCertificate
} from "../../models";
import { Observable, Subscription, Subject } from "rxjs";
import { MatTableDataSource, MatPaginator, MatSort } from "@angular/material";
import { map, takeUntil } from "rxjs/operators";
import { formatDate } from "@angular/common";
import { SelectionModel } from "@angular/cdk/collections";
import { GetEmailModalComponent } from "../get-email-modal/get-email-modal.component";
import { ActivatedRoute, Router } from "@angular/router";

@Component({
  selector: "app-weight-and-research",
  templateUrl: "./weight-and-research.component.html",
  styleUrls: ["./weight-and-research.component.scss"]
})
export class WeightAndResearchComponent implements OnInit, OnDestroy {
  private stop$ = new Subject<boolean>();
  private emailSub: Subscription;
  formGroup: FormGroup;
  results: Observable<Array<WRCertificate>>;
  pageSize = 25;
  displayedColumns = [
    "select",
    "proNumber",
    "bolNumber",
    "poNumber",
    "correctionDate",
    "correctionType",
    "action"
  ];
  tableSub: Subscription;
  dataSource = new MatTableDataSource<WRCertificate>();
  noData = this.dataSource.connect().pipe(map(data => data.length === 0));
  loading: boolean;
  feedback: [FeedbackTypes, string | string[]];
  selection: SelectionModel<WRCertificate>;
  paramSub: Subscription;
  showTable: boolean;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(FormGroupDirective) myForm: FormGroupDirective;

  constructor(
    private fb: FormBuilder,
    private wrService: WeightAndResearchService,
    private snackbar: SnackbarService,
    private auth: AuthService,
    private utilService: UtilService,
    public formService: FormService,
    private dialog: DialogService,
    public leftNavigationService: LeftNavigationService,
    public promoService: PromoService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.formGroup = this.fb.group(new WeightAndResearchForm());
    this.initAccountCode();
    this.initFormValueChanges();
  }

  ngOnInit() {
    this.leftNavigationService.setNavigation(`Manage`, `/manage`);
    this.promoService.setAppId("weight-and-research");
    this.promoService.setAppState("authenticated");

    this.showTable = false;
    this.selection = new SelectionModel<WRCertificate>(true, []);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;

    this.paramSub = this.route.queryParams.subscribe(params => {
      let searchType = params["searchType"];
      let criteria = params["criteria"];
      if (searchType === "F") {
        this.searchBy.setValue("PRO");
      } else {
        this.searchBy.setValue("Date Range");
      }
      this.searchTerm.setValue(criteria);
      if (searchType || criteria) {
        this.onSubmit();
      }
    });

    this.dataSource.sortingDataAccessor = (item, property) => {
      switch (property) {
        case "correctionDate":
          return extractDateFromString(item.correctionDate);
        default:
          return item[property];
      }
    };

    this.auth.authStateSet.subscribe((authState: string) => {
      if (authState === 'unauthenticated') {
        this.router.navigate(['login']);
      }
    });
  }

  ngOnDestroy() {
    this.stop$.next(true);
    this.stop$.unsubscribe();
    if (this.tableSub) {
      this.tableSub.unsubscribe();
    }
    if (this.emailSub) {
      this.emailSub.unsubscribe();
    }
  }

  reset() {
    this.myForm.resetForm(
      this.fb.group(new WeightAndResearchForm()).getRawValue()
    );
    this.initAccountCode();
    this.dataSource.data = [];
    this.showTable = false;
  }

  docDetails(proNumber) {
    this.results = this.wrService.getDocumentDetails([proNumber]);
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  onSubmit() {
    if (this.formGroup.valid) {
      const searchRequest = this.populateModel();

      this.initTable(searchRequest);
    } else {
      validateFormFields(this.formGroup);
      this.snackbar.validationError();
    }
  }

  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    this.isAllSelected()
      ? this.selection.clear()
      : this.dataSource.data.forEach(row => this.selection.select(row));
  }

  get accountNumber() {
    return this.formGroup.controls["accountNumber"];
  }
  get endDate() {
    return this.formGroup.controls["endDate"];
  }
  get searchBy() {
    return this.formGroup.controls["searchBy"];
  }
  get searchTerm() {
    return this.formGroup.controls["searchTerm"];
  }
  get startDate() {
    return this.formGroup.controls["startDate"];
  }
  get today() {
    return new Date();
  }
  get threeYearsPrior() {
    const today = this.today;
    const threeYearsPrior = today;
    threeYearsPrior.setFullYear(threeYearsPrior.getFullYear() - 3);

    return threeYearsPrior;
  }

  emailSelected() {
    if (this.searchTerm.valid && this.selection.selected.length) {
      const proNumbers = this.selection.selected.reduce((accum, curr) => {
        accum.push(curr.proNumber);
        return accum;
      }, []);
      this.emailSub = this.dialog
        .prompt(GetEmailModalComponent, proNumbers)
        .pipe(takeUntil(this.stop$))
        .subscribe();
    }
  }

  private getParsedTextArea() {
    if (this.searchTerm.valid && this.searchTerm.value) {
      return this.utilService.extractTextAreaLineItems(this.searchTerm
        .value as string);
    }
  }

  private populateModel(): WRSearchRequest {
    const payload = new WRSearchRequest();
    payload.accountNumber = this.accountNumber.value;
    payload.searchBy = this.searchBy.value;
    payload.searchTerm = this.getParsedTextArea();
    payload.endDate = this.endDate.value
      ? formatDate(this.endDate.value, "yyyyMMdd", "en-US")
      : "";
    payload.startDate = this.startDate.value
      ? formatDate(this.startDate.value, "yyyyMMdd", "en-US")
      : "";

    switch (payload.searchBy) {
      case "Date Range":
        delete payload.searchTerm;
        break;
      case "Other":
      case "PRO":
        delete payload.startDate;
        delete payload.endDate;
        break;
    }

    return payload;
  }

  private initFormValueChanges() {
    this.searchBy.valueChanges.pipe(takeUntil(this.stop$)).subscribe(next => {
      switch (next) {
        case "Date Range":
          this.searchTerm.reset("");
          this.searchTerm.clearValidators();
          this.startDate.setValidators([
            MyEstesValidators.required,
            dateValidator()
          ]);
          this.endDate.setValidators([
            MyEstesValidators.required,
            dateValidator()
          ]);
          break;
        case "PRO":
        case "Other":
          this.startDate.reset("");
          this.endDate.reset("");
          this.startDate.clearValidators();
          this.endDate.clearValidators();
          this.searchTerm.setValidators([
            MyEstesValidators.required,
            textAreaValidator(
              RegEx.anythingButWhitespace,
              `Please enter a valid value per line.`
            )
          ]);
          break;
      }
      this.startDate.updateValueAndValidity();
      this.endDate.updateValueAndValidity();
      this.searchTerm.updateValueAndValidity();
    });
    //this.searchBy.updateValueAndValidity();
  }

  private initTable(searchRequest: WRSearchRequest) {
    this.loading = true;
    this.showTable = true;
    this.tableSub = this.wrService.getImageList(searchRequest).subscribe(
      next => {
        this.dataSource.data = next;
        // console.log('searchRequest:', next);
        if (next && Array.isArray(next) && next.length > 0) {
          this.snackbar.success(`See the search results in the table below.`);
        } else {
          this.snackbar.error(`No results meet your search criteria.`);
        }
      },
      err => {
        this.loading = false;

        if (err.status === 400) {
          if (err.error instanceof Array) {
            let messages: string[] = [];
            err.error.forEach(e => messages.push(e.message));
            this.searchTerm.setErrors({ serverError: messages });
            this.snackbar.validationError();
          } else {
            this.snackbar.error(err.error.message);
          }
        } else {
          this.snackbar.error(`An error occurred.  Please try again.`);
        }
      },
      () => (this.loading = false)
    );
  }

  private initAccountCode() {
    switch (this.auth.getUserRole()) {
      case UserRoleEnum.Local:
        const token = this.auth.getAuthToken();
        this.accountNumber.setValue(token.accountCode);
        break;
    }
  }
}
