import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { ShipmentTrackingService } from '../../shipment-tracking.service';
import { Subject } from 'rxjs';
import { takeUntil, switchMap } from 'rxjs/operators';
import { SnackbarService } from 'common';

@Component({
  selector: 'app-image-fetch-weight-and-research',
  templateUrl: './image-fetch-weight-and-research.component.html',
  styleUrls: ['./image-fetch-weight-and-research.component.scss']
})
export class ImageFetchWeightAndResearchComponent implements OnInit, OnDestroy {
  private stop$ = new Subject<boolean>();
  loading = false;
  @Input() proNumber: string;

  constructor(private shipmentTrackingService: ShipmentTrackingService, private snackbar: SnackbarService) {}

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

    this.shipmentTrackingService
      .getDocumentDetailsWeightAndResearch([this.proNumber])
      .pipe(
        takeUntil(this.stop$),
        switchMap(res => {
          if (res && res.length) {
            const details = res.shift();
            return this.shipmentTrackingService.downloadDocumentWeightAndResearch(details.documentLink);
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

        },
        err => {
          this.handleError();
          this.loading = false;
        },
        () => (this.loading = false)
      );
  }

  private handleError() {
    this.snackbar.error(`An error occurred while retrieving the image.  Please try again.`);
  }
}
