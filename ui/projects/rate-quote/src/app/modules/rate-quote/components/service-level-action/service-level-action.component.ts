import {
  Component,
  OnInit,
  Input,
  OnDestroy,
  Output,
  EventEmitter
} from "@angular/core";
import { RateQuote, EmailRequest } from "../../models";
import { DialogService, SnackbarService } from "common";
import { LtlContactMeModalComponent } from "../ltl-contact-me-modal/ltl-contact-me-modal.component";
import { RateQuoteService } from "../../services/rate-quote.service";
import { Subscription } from "rxjs";
import { HttpErrorResponse } from "@angular/common/http";

@Component({
  selector: "app-service-level-action",
  templateUrl: "./service-level-action.component.html",
  styleUrls: ["./service-level-action.component.scss"]
})
export class ServiceLevelActionComponent implements OnInit, OnDestroy {
  @Input() quote: RateQuote;

  selected = false;
  loadingContactMe = false;
  loadingSelected = false;
  contacted = false;
  contactMeSub: Subscription;
  selectSub: Subscription;

  constructor(
    private dialog: DialogService,
    private rateQuoteService: RateQuoteService,
    private snackbar: SnackbarService
  ) {}

  ngOnInit() {}

  ngOnDestroy() {
    this.safelyStopContactMeSub();
  }

  getActionText() {
    switch (this.quote.pricing.display.toUpperCase()) {
      case "S":
        return `Show Quote`;
      case "C":
        return "Contact Me";
      default:
        return ``;
    }
  }

  showDropdownArrows(): boolean {
    return this.quote.pricing.display === "S";
  }

  contactMe(e: MouseEvent) {
    // Prevent panel from opening.
    e.stopPropagation();
    /**
     * Select Quote & push to GM
     */
    this.selectQuoteAndPushGM(this.quote.quoteID);

    switch (this.quote.app.toUpperCase()) {
      case "LTL":
        this.dialog
          .prompt(LtlContactMeModalComponent, this.quote, null, "50rem")
          .subscribe(res => {
            if (res === true) {
              this.contacted = true;
            }
          });
        break;
      default:
        this.nonLtlContactMe();
        break;
    }
  }

  private selectQuoteAndPushGM(quoteID: string) {
    this.rateQuoteService.selectQuote(quoteID).subscribe(
      next => {
        console.log(
          this.quote.quoteID + "Quote has been selected & pushed to GM"
        );
      },
      err => {
        console.log(
          this.quote.quoteID + " Quote did not push to GM due to errors"
        );
      }
    );
  }

  private safelyStopContactMeSub() {
    if (this.contactMeSub) {
      this.contactMeSub.unsubscribe();
    }
  }

  private nonLtlContactMe() {
    const payload = new EmailRequest();

    payload.action = "C";
    payload.addresses = [this.quote.contactEmail];
    payload.app = this.quote.app;
    payload.level = this.quote.service.id;
    payload.quoteId = this.quote.quoteID;
    payload.quoteRefNum = +this.quote.quoteRefNum;

    this.safelyStopContactMeSub();
    this.loadingContactMe = true;

    this.rateQuoteService.email(payload).subscribe(
      next => {
        this.contacted = true;
        this.snackbar.success(next.message);
      },
      err => {
        this.loadingContactMe = false;
        let msg = `An unexpected error occurred.  Please try again.`;
        if (err instanceof HttpErrorResponse) {
          if (err.error.message) {
            msg = err.error.message;
          }
        }
        this.snackbar.error(msg);
      },
      () => (this.loadingContactMe = false)
    );
  }
}
