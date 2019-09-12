import { Component, OnInit, Input } from "@angular/core";
import {
  ImageResult,
  ImageDetailsRequest,
  ImageStatusEnum
} from "../../models";
import { UtilService} from 'common';
import { ImageViewingService } from "../../services/image-viewing.service";
import { Subscription, Observable, timer } from "rxjs";
import { expand, takeWhile, tap, delay, takeUntil } from "rxjs/operators";
import { HttpErrorResponse } from "@angular/common/http";

@Component({
  selector: "app-image-fetch",
  templateUrl: "./image-fetch.component.html",
  styleUrls: ["./image-fetch.component.scss"],
  providers: [ImageViewingService]
})
export class ImageFetchComponent implements OnInit {
  message: string;
  statusSub: Subscription;
  feedback: string;
  @Input() image: ImageResult;

  constructor(private imageService: ImageViewingService, private utilService: UtilService) {}

  get imageAvailable() {
    return this.image.status === ImageStatusEnum.AVAILABLE;
  }

  get imageError() {
    return this.image.status === ImageStatusEnum.ERROR;
  }

  get imageErrorMessage(): string {
    return this.imageError ? this.image.errorMessage : null;
  }

  get statusText() {
    if (this.feedback) {
      return this.feedback;
    }

    switch (this.image.status) {
      case "WORKING":
        return `Working...`;
      case "NOT_AVAILABLE":
        return `Image not available`;
      case "FAX_SENT":
        return `Fax has been sent`;
      case "FAX_ERROR":
        return `Fax request unsuccessful`;
      case "ERROR":
        return this.imageErrorMessage;
      default:
        return ``;
    }
  }

  ngOnInit() {
    if (!this.imageAvailable) {
      if (!this.imageError) {
        const timer$ = timer(20000);
        this.statusSub = this.imageService
          .getImageStatus(
            this.image.docType,
            this.image.requestNumber,
            this.image.searchData
          )
          .pipe(
            expand(val =>
              this.imageService
                .getImageStatus(
                  this.image.docType,
                  this.image.requestNumber,
                  this.image.searchData
                )
                .pipe(delay(2000))
            ),
            tap(v => {
              this.image.status = v.status;
              if (this.imageAvailable) {
                this.getImageDetails();
              }
            }),
            takeWhile(val => val.status === ImageStatusEnum.WORKING),
            takeUntil(timer$)
          )
          .subscribe(
            val => {
              if (this.imageAvailable) {
                this.getImageDetails();
              }
            },
            err => {
              const genericErrorMessage = `An unexpected error occurred.  Please try again.`;
              if (err instanceof HttpErrorResponse) {
                if (err.error && err.error.length && err.error[0].message) {
                  this.feedback = err.error[0].message;
                } else {
                  this.feedback = genericErrorMessage;
                }
              } else {
                this.feedback = genericErrorMessage;
              }
            }
          );
      }
    } else {
      this.getImageDetails();
    }
  }

  getImageDetails() {
    const payload = new ImageDetailsRequest();
    payload.docType = this.image.docType;
    payload.key1 = this.image.key1;
    payload.key2 = this.image.key2;
    payload.key3 = this.image.key3;
    payload.key4 = this.image.key4;
    payload.key5 = this.image.key5;

    this.imageService.getImageDetails(payload).subscribe(next => {
      this.image.imageDetails = next;
    });
  }

  openHtml() {
    const imageLocations = [];
    this.image.imageDetails.forEach(i => {
      imageLocations.push(i.imageLocation);
    });
    this.utilService.openImagesInNewTab(imageLocations);
  }
}
