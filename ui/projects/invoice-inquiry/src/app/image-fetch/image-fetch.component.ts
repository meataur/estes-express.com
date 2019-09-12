import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { InvoiceInquiryService } from '../invoice-inquiry.service';
import { takeWhile, expand, delay, retryWhen, tap, take } from 'rxjs/operators';
import { SnackbarService } from 'common';
import { Image } from '../models/image.model';

@Component({
  selector: 'invoice-image-fetch',
  templateUrl: './image-fetch.component.html',
  styleUrls: ['./image-fetch.component.scss']
})
export class ImageFetchComponent implements OnDestroy {
  loading = false;
  response: Image;
  @Input() proNumber: string;

  constructor(
    private invoiceInquiryService: InvoiceInquiryService,
    private snackbar: SnackbarService
  ) {}

  open() {
    this.fetchImage();
  }

  private fetchImage() {
    this.loading = true;

    return this.invoiceInquiryService
      .fetchImage(this.proNumber)
      .pipe(delay(1000))
      .pipe(
        expand(val => {
          return this.invoiceInquiryService.fetchImage(this.proNumber).pipe(delay(1000));
        }),
        tap(val => (this.response = val)),
        take(20),
        takeWhile(val => val.status === 'W'),
        retryWhen(errors =>
          errors.pipe(
            delay(1000),
            take(20)
          )
        )
      )
      .subscribe(
        next => {},
        err => {
          this.handleError();
          this.loading = false;
        },
        () => {
          this.loading = false;

          if (this.response && this.response.location) {
            this.openUrl(this.response.location);
          } else {
            this.handleError();
          }
        }
      );
  }

  private openUrl(url: string) {
    if (url) {
      window.open(url, '_blank');
    }
  }

  private handleError() {
    this.snackbar.error(`An error occurred while retrieving the image.  Please try again.`);
  }

  ngOnDestroy() {}
}
