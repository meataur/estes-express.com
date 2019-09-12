import {
  Component,
  OnInit,
  ViewChild,
  Input,
  EventEmitter,
  Output
} from "@angular/core";
import { MatPaginator, MatSort, MatTableDataSource } from "@angular/material";
import {
  FeedbackTypes,
  extractDateFromString,
  SnackbarService,
  AuthService,
  UtilService
} from "common";
import { map, shareReplay } from "rxjs/operators";
import { RateQuote, ServiceLevelEnum } from "../../models";
import {
  animate,
  transition,
  state,
  style,
  trigger
} from "@angular/animations";
import { RateQuoteService } from "../../services/rate-quote.service";
import { Observable, of } from "rxjs";
import { TimePipe } from "../../pipes/display-time.pipe";

@Component({
  selector: "app-rate-request-results",
  templateUrl: "./rate-request-results.component.html",
  styleUrls: ["./rate-request-results.component.scss"],
  animations: [
    trigger("detailExpand", [
      state(
        "collapsed",
        style({
          height: "0px",
          minHeight: "0",
          display: "none",
          overflow: "hidden"
        })
      ),
      state("expanded", style({ height: "*", overflow: "hidden" })),
      transition(
        "expanded <=> collapsed",
        animate("225ms cubic-bezier(0.4, 0.0, 0.2, 1)")
      )
    ])
  ]
})
export class RateRequestResultsComponent implements OnInit {
  pageSize = 25;
  displayedColumns = this.auth.isLoggedIn
    ? ["serviceLevel", "deliveryDate", "deliveryTime", "charges", "actions"]
    : ["serviceLevel", "charges", "actions"];
  dataSource = new MatTableDataSource<RateQuote>();
  noData = this.dataSource.connect().pipe(map(data => data.length === 0));
  loading: boolean;
  feedback: [FeedbackTypes, string | string[]];
  expandedElement: any | null;
  aggregatedInfo: Array<string> = [];

  formattedAccount$: Observable<string>;

  @Input() quotes: Array<RateQuote>;
  @Input() accountCode: string;

  @Output() startNewQuote: EventEmitter<boolean> = new EventEmitter();
  @Output() reviseQuote: EventEmitter<boolean> = new EventEmitter();

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    public auth: AuthService,
    private rateQuoteService: RateQuoteService,
    private snackbar: SnackbarService,
    private utilService: UtilService,
    private timePipe: TimePipe
  ) {}

  ngOnInit() {
    const firstQuote = this.quotes[0];
    if (firstQuote) {
      this.formattedAccount$ = this.utilService
        .getAccountInfo(firstQuote.accountCode)
        .pipe(
          map(res => `${res.name} - ${res.accountNumber}`),
          shareReplay(1)
        );
    }

    this.aggregatedInfo = this.quotes.reduce((accum, curr) => {
      if (curr.info.length) {
        for (const info of curr.info) {
          if (!accum.includes(info)) {
            accum.push(info);
          }
        }
      }
      return accum;
    }, []);
    this.dataSource.data = this.quotes;
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.dataSource.sortingDataAccessor = (item, property) => {
      switch (property) {
        case "serviceLevel":
          return item.service.text;
        case "deliveryDate":
          return extractDateFromString(item.delivery.date);
        case "deliveryTime":
          try {
            const splitTime = item.delivery.time.split(":");
            const hours = +splitTime[0];
            const minutes = +splitTime[1];
            const seconds = +splitTime[2];
            const d = new Date();

            d.setHours(hours, minutes, seconds);
            return d;
          } catch (e) {
            console.warn(`Failed to parse delivery time for sorting.`);
            return item.delivery.time;
          }
        case "charges":
          return item.pricing.totalPrice;
        default:
          return item[property];
      }
    };
  }

  showVolumeAndTruckloadDeliveryTooltip(q: RateQuote) {
    return q.service.id === ServiceLevelEnum.VolumeAndTruckloadBasic;
  }

  getDeliveryDate(q: RateQuote) {
    if (q.service.id === ServiceLevelEnum.VolumeAndTruckloadBasic) {
      return `TBD (Based on standard transit and capacity)`;
    }

    return q.delivery.date;
  }

  getDeliveryTime(q: RateQuote) {
    if (q.service.id === ServiceLevelEnum.VolumeAndTruckloadBasic) {
      return `TBD`;
    }

    return this.timePipe.transform(q.delivery.time);
  }

  onQuoteSelected(element: RateQuote) {
    if (this.expandedElement === element) {
      this.expandedElement = null;
    } else {
      if (element.pricing.display === "S") {
        if (element.selected === true) {
          this.expandedElement = element;
        } else {
          /**
           * Commented out the code as we need to push the quote on quote selection each time.
           * It does not matter whether user is logged in or not
           */
          /*
          if (!this.auth.isLoggedIn) {
            this.expandedElement = element;
            element.selected = true;
            return;
          }
          */

          element.isSelecting = true;
          this.rateQuoteService.selectQuote(element.quoteID).subscribe(
            next => {
              this.expandedElement = element;
              element.selected = true;
            },
            err => {
              this.snackbar.error(
                `An error occurred getting the quote details.  Please try again.`
              );
              element.isSelecting = false;
            },
            () => (element.isSelecting = false)
          );
        }
      }
    }
  }
}
