import { Component, OnInit, Input } from "@angular/core";
import { environment } from "../../../../../environments/environment";
import { RateQuote } from "../../models";
import { DialogService } from "common";
import { GetEmailModalComponent } from "../get-email-modal/get-email-modal.component";
import { RateQuoteService } from "../../services/rate-quote.service";

@Component({
  selector: "app-rate-quote-action-header",
  templateUrl: "./rate-quote-action-header.component.html",
  styleUrls: ["./rate-quote-action-header.component.scss"]
})
export class RateQuoteActionHeaderComponent implements OnInit {
  @Input() quote: RateQuote;

  constructor(private dialog: DialogService, private rateQuoteService: RateQuoteService) {}

  ngOnInit() {}

  emailQuote() {
    this.dialog.prompt(GetEmailModalComponent, this.quote);
  }

  viewQuote() {
    /*
    const url = `${
      environment.serviceBaseUrl
    }/WebApp/SolutionCenter/print?quoteId=${this.quote.quoteID}&frmfile=`;
    const win = window.open(url, "_blank");
    win.focus();
    */

    this.rateQuoteService.getQuotePdf(this.quote.quoteID).subscribe(
      next => {
        // It is necessary to create a new blob object with mime-type explicitly set
        // otherwise only Chrome works like it should
        let newBlob = new Blob([next], { type: "application/pdf" });

        let filename = `ESTES_Rate_Quote_${this.quote.quoteDate}_${
          this.quote.quoteID
        }.pdf`;
        // IE doesn't allow using a blob object directly as link href
        // instead it is necessary to use msSaveOrOpenBlob
        if (window.navigator && window.navigator.msSaveOrOpenBlob) {
          window.navigator.msSaveOrOpenBlob(newBlob, filename);
          return;
        }

        // For other browsers:
        // Create a link pointing to the ObjectURL containing the blob.
        const data = window.URL.createObjectURL(newBlob);
        let link = document.createElement("a");
        link.href = data;
        link.download = filename;

        link.dispatchEvent(
          new MouseEvent("click", {
            bubbles: true,
            cancelable: true,
            view: window
          })
        );
        setTimeout(function() {
          // For Firefox it is necessary to delay revoking the ObjectURL
          window.URL.revokeObjectURL(data);
          link.remove();
        }, 100);
      },
      error => {
        //console.log(error);
      },
      () => {
      }
    );
  }
}
