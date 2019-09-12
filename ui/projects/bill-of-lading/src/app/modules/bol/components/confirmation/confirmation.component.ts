import { Component, OnInit, OnDestroy } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { BolService } from "../../services/bol.service";
import { BillOfLading } from "../../models";
import { Observable, Subscription } from "rxjs";
import { share, map } from "rxjs/operators";
import { UtilService, Address, SnackbarService } from "common";
import { AuthService } from "common";
import { environment } from "../../../../../environments/environment";
import { CreateBillOfLadingResponse } from "../../models";
import printJS from "print-js";

@Component({
  selector: "app-confirmation",
  templateUrl: "./confirmation.component.html",
  styleUrls: ["./confirmation.component.scss"]
})
export class ConfirmationComponent implements OnInit, OnDestroy {
  loading = false;
  hasShippingLabel = false;
  bolId: string;
  bol$: Observable<BillOfLading>;
  bolSub: Subscription;
  accountSub: Subscription;
  profileSub: Subscription;
  formattedAccount: string;
  profileName: string;
  shipperAddress: Address;
  consigneeAddress: Address;
  totalCommodities: number;
  totalShipmentWeight: number | string;
  loadingPdf: boolean;
  pdfDownloadLink: string;
  downloadPdfSub: Subscription;
  bol: BillOfLading;
  loadingShippingLabel: boolean;
  printingPdf: boolean;
  pickupErrors: any[];
  pickupRequestNumber: string;
  paramSub: Subscription;

  constructor(
    private route: ActivatedRoute,
    private bolService: BolService,
    private utilService: UtilService,
    private auth: AuthService,
    private snackbar: SnackbarService,
    private router: Router
  ) {}

  ngOnInit() {
    this.bolId = this.route.snapshot.paramMap.get("id");

    this.loading = true;
    const bolObs = this.bolService.getBolById(this.bolId).pipe(share());
    this.bol$ = bolObs;
    this.bolSub = bolObs.subscribe(
      next => {
        this.bol = next;
        const shipper = next.shipperInfo;
        this.shipperAddress = {
          city: shipper.city,
          state: shipper.state,
          zip: shipper.zip,
          streetAddress: shipper.address1,
          streetAddress2: shipper.address2,
          country: shipper.country
        };
        const consignee = next.consigneeInfo;
        this.consigneeAddress = {
          city: consignee.city,
          state: consignee.state,
          zip: consignee.zip,
          streetAddress: consignee.address1,
          streetAddress2: consignee.address2,
          country: consignee.country
        };

        this.hasShippingLabel = !!(
          next.shippingLabel.labelType &&
          next.shippingLabel.numberOfLabel &&
          next.shippingLabel.startLabel
        );

        this.totalCommodities = next.commodityInfo.commodityList.length;
        if (this.totalCommodities > 0) {
          this.totalShipmentWeight = next.commodityInfo.commodityList.reduce(
            (accum, curr) => {
              const weight = curr.goodsWeight;
              if (!isNaN(+weight)) {
                accum += +weight;
              }
              return accum;
            },
            0
          );
        } else {
          this.totalShipmentWeight = "N/A";
        }
      },
      () => (this.loading = false),
      () => (this.loading = false)
    );
    this.accountSub = this.utilService
      .getAccountInfo(this.auth.accountCode)
      .subscribe(next => {
        if (next) {
          this.formattedAccount = `${next.accountNumber} - ${next.name}`;
        }
      });
    this.profileSub = this.utilService.getProfileInfo().subscribe(next => {
      this.profileName = `${next.firstName} ${next.lastName}`;
    });

    const pickupErrs = [];
    if (
      this.bolService.pickupErrors != null &&
      this.bolService.pickupErrors.length > 0
    ) {
      this.bolService.pickupErrors.forEach(err => {
        if (err.message) {
          pickupErrs.push(err.message);
        }
      });
    }

    this.pickupErrors = pickupErrs.length > 0 ? pickupErrs : null;
    console.log(this.bolService.pickupRequestNumber);
    this.pickupRequestNumber = this.bolService.pickupRequestNumber;
    // this.loadingPdf = true;
    // this.downloadPdfSub = this.bolService.viewBolPdf(this.bolId).subscribe(
    //   next => {
    //     // this.loadingPdf = false;
    //     // console.log(next);
    //     this.pdfDownloadLink = `${environment.serviceBaseUrl}${next.documentUrl}`;
    //     // const win = window.open(url, '_blank');
    //     // win.focus();
    //   },
    //   err => {
    //     this.loadingPdf = false;
    //     this.snackbar.error(
    //       `An error occurred while retrieving the Bill of Lading.  Please try again.`
    //     );
    //   },
    //   () => (this.loadingPdf = false)
    // );
  }

  getFormattedCommodityPiecesType(code: string): Observable<string> {
    return this.utilService.getPackageTypes().pipe(
      map(res => {
        const match = res.find(
          c => c.code.toUpperCase() === code.toUpperCase()
        );
        return match.description;
      })
    );
  }

  ngOnDestroy() {
    if (this.profileSub) {
      this.profileSub.unsubscribe();
    }
    if (this.bolSub) {
      this.bolSub.unsubscribe();
    }
    this.bolService.pickupErrors = null;
  }

  openBol() {
    this.loadingPdf = true;
    this.bolService.viewBolPdf(this.bolId).subscribe(
      next => {
        // this.loadingPdf = false;
        // console.log(next);
        this.open(`${environment.serviceBaseUrl}${next.documentUrl}`);
        // const win = window.open(url, '_blank');
        // win.focus();
      },
      err => {
        this.loadingPdf = false;
        this.snackbar.error(
          `An error occurred while retrieving the Bill of Lading.  Please try again.`
        );
      },
      () => (this.loadingPdf = false)
    );
  }

  printBol() {
    this.printingPdf = true;
    this.bolService.viewBolPdf(this.bolId).subscribe(
      next => {
        // this.loadingPdf = false;
        this.print(`${environment.serviceBaseUrl}${next.documentUrl}`);
        // printJS(`${environment.serviceBaseUrl}${next.documentUrl}`);
        // console.log(next);
        // const w = window.open('about:blank');
        // w.document.write();
        // if (navigator.appName === 'Microsoft Internet Explorer') {
        //   window.print();
        // } else {
        //   w.print();
        // }
        // window.print(`${environment.serviceBaseUrl}${next.documentUrl}`);
        // this.open(`${environment.serviceBaseUrl}${next.documentUrl}`);
        // const win = window.open(url, '_blank');
        // win.focus();
      },
      err => {
        this.printingPdf = false;
        this.snackbar.error(
          `An error occurred while retrieving the Bill of Lading.  Please try again.`
        );
      },
      () => (this.printingPdf = false)
    );
  }

  openShippingLabels() {
    this.loadingShippingLabel = true;
    this.bolService.getShippingLabel(this.bolId).subscribe(
      next => {
        // this.loadingShippingLabel = false;
        // console.log(next);
        this.open(`${environment.serviceBaseUrl}${next.documentUrl}`);
        // const win = window.open(url, '_blank');
        // win.focus();
      },
      err => {
        this.loadingShippingLabel = false;
        this.snackbar.error(
          `An error occurred while retrieving the Shipping Labels.  Please try again.`
        );
      },
      () => (this.loadingShippingLabel = false)
    );
  }

  open(url: string) {
    if (url) {
      const win = window.open(url, "_blank");
      win.focus();
    }
  }

  print(pdfurl: string) {
    if (pdfurl) {
      // const xhr = new XMLHttpRequest();
      // xhr.open('GET', pdfurl, true);
      // xhr.responseType = 'blob';
      // xhr.onload = function(e) {
      //   if (this.status === 200) {
      //     // Note: .response instead of .responseText
      //     const blob = new Blob([this.response], { type: 'application/pdf' });
      //     const url = URL.createObjectURL(blob);
      //     console.log(url);
      //     //   _iFrame = document.createElement('iframe');
      //     // _iFrame.setAttribute('src', url);
      //     // _iFrame.setAttribute('style', 'visibility:hidden;');
      //     // $('#someDiv').append(_iFrame);
      //   }
      // };
      // xhr.send();
      // const win = window.open(url, '_blank');
      // win.document.close();
      // win.focus();
      // win.print();
      // win.close();
    }
  }

  // createPickupRequest() {}

  // download() {
  //   this.loadingPdf = true;
  //   this.bolService.viewBolPdf(this.bolId).subscribe(
  //     next => {
  //       // this.loadingPdf = false;
  //       // console.log(next);
  //       const url = `${environment.serviceBaseUrl}${next.documentUrl}`;
  //       const win = window.open(url, '_blank');
  //       win.focus();
  //     },
  //     err => {
  //       this.loadingPdf = false;
  //       this.snackbar.error(
  //         `An error occurred while retrieving the Bill of Lading.  Please try again.`
  //       );
  //     },
  //     () => (this.loadingPdf = false)
  //   );

  // }
}
