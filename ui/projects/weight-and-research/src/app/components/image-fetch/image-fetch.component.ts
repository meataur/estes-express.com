import { Component, OnInit, Input, OnDestroy } from "@angular/core";
import { WeightAndResearchService } from "../../services/weight-and-research.service";
import { Subject } from "rxjs";
import { takeUntil, switchMap } from "rxjs/operators";
import { SnackbarService } from "common";

@Component({
  selector: "app-image-fetch",
  templateUrl: "./image-fetch.component.html",
  styleUrls: ["./image-fetch.component.scss"]
})
export class ImageFetchComponent implements OnInit, OnDestroy {
  private stop$ = new Subject<boolean>();
  loading = false;
  @Input() proNumber: string;

  constructor(
    private wrService: WeightAndResearchService,
    private snackbar: SnackbarService
  ) {}

  ngOnInit() {}

  ngOnDestroy() {
    this.stop$.next(true);
    this.stop$.unsubscribe();
  }

  onClick(event: Event) {
    event.stopPropagation();

    this.fetchImage();
  }

  private fetchImage() {
    this.stop$.next(true);
    this.loading = true;

    this.wrService
      .getDocumentDetails([this.proNumber])
      .pipe(
        takeUntil(this.stop$),
        switchMap(res => {
          if (res && res.length) {
            const details = res.shift();
            return this.wrService.downloadDocument(details.documentLink);
          }
        })
      )
      .subscribe(
        next => {
          const file = new Blob([next], { type: "application/pdf" });
          let filename = `ESTES-Weight-Research-${this.proNumber}.pdf`;
          if (window.navigator && window.navigator.msSaveOrOpenBlob) {
            window.navigator.msSaveOrOpenBlob(file, filename);
            return;
          }

          const data = window.URL.createObjectURL(file);
          let link = document.createElement("a");
          link.href = data;
          link.download = filename;
          //link.click();
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

          // if (next && next.length) {
          //   const details = next.shift();
          //   this.openUrl(
          //     `${environment.serviceBaseUrl}/myestes/ImageViewing/v1.0/wr/download?fileName=${
          //       details.documentLink
          //     }`
          //   );
          // } else {
          //   this.handleError();
          // }
        },
        err => {
          this.handleError();
          this.loading = false;
        },
        () => (this.loading = false)
      );
  }

  private openUrl(url: string) {
    if (url) {
      const win = window.open(url, "_blank");
      win.focus();
    }
  }

  private handleError() {
    this.snackbar.error(
      `An error occurred while retrieving the image.  Please try again.`
    );
  }
}
