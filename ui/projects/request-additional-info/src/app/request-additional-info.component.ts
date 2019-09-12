import { AfterViewInit, Component, OnInit, OnDestroy } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
// import { PointsDownloadService } from '../services/points-download.service';
import { Subscription, merge } from "rxjs";
import {
  FormService,
  patternValidator,
  validateFormFields,
  RegEx,
  MessageConstants,
  Masks,
  AuthService,
  SnackbarService,
  UtilService,
  MyEstesFormatters
} from "common";

import { startWith, switchMap } from "rxjs/operators";

import { RequestAdditionalInfoService } from "./request-additional-info.service";
import { ActivatedRoute, Router } from "@angular/router";
import { FeedbackTypes } from "../../../common/src/public_api";

@Component({
  selector: "app-request-additional-info",
  templateUrl: "./request-additional-info.component.html",
  styleUrls: ["./request-additional-info.component.scss"]
})
export class RequestAdditionalInfoComponent implements OnInit, OnDestroy {
  formGroup: FormGroup;
  username: string;

  proMask = Masks.pronumber;
  phoneMask = Masks.phone;
  problemTypes: string[];
  problemTypeMappings: Object;
  proNumberSub: Subscription;
  problemTypeSub: Subscription;
  paramSub: Subscription;
  infoSub: Subscription;
  imageTypeSub: Subscription;

  loadingProblemTypes: boolean;
  showImageTypes: boolean;
  imageTypeValid: boolean = true;
  paramSet: boolean;
  errorMessages: [string, any];
  successMessage: [FeedbackTypes, string];
  loading: boolean;
  isAuthenticated: boolean = false;

  constructor(
    private fb: FormBuilder,
    private requestAdditionalInfoService: RequestAdditionalInfoService,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private utilService: UtilService,
    private snackbarService: SnackbarService,
    public formService: FormService
  ) {}

  ngOnInit() {
    this.problemTypeMappings = {
      trackingHelp: "Tracking Help",
      imageNotAvailable: "Image Not Available",
      ratingQuestion: "Rating Question",
      other: "Other"
    };

    this.formGroup = this.fb.group({
      name: ["", [Validators.required, Validators.maxLength(50)]],
      phoneNumber: ["", Validators.required],
      phoneNumberExt: ["", Validators.maxLength(5)],
      faxNumber: [""],
      emailAddress: [
        "",
        [
          Validators.required,
          patternValidator(RegEx.email, MessageConstants.invalidEmail)
        ]
      ],
      proNumber: [
        "",
        [patternValidator(RegEx.proNumber, MessageConstants.invalidProNumber)]
      ],
      problem: ["", Validators.required],
      bolSelected: [false],
      drSelected: [false],
      wrSelected: [false],
      description: ["", Validators.maxLength(250)]
    });

    this.proNumberSub = this.proNumber.valueChanges
      .pipe(
        startWith(""),
        switchMap(pro => {
          this.loadingProblemTypes = true;
          this.problem.disable();
          return this.requestAdditionalInfoService.getProblemTypes(pro);
        })
      )
      .subscribe(
        data => {
          this.problemTypes = data;
          this.loadingProblemTypes = false;
          this.problem.enable();
        },
        err => {
          this.errorMessages = ["err", err];
        }
      );

    this.problemTypeSub = this.problem.valueChanges.subscribe(val => {
      switch (val) {
        case "imageNotAvailable":
          this.showImageTypes = true;
          this.description.setValidators([Validators.maxLength(250)]);
          break;
        case "other":
        case "trackingHelp":
        case "ratingQuestion":
          this.showImageTypes = false;
          this.description.setValidators([
            Validators.maxLength(250),
            Validators.required
          ]);
          break;
      }
    });

    this.paramSub = this.route.queryParams.subscribe(params => {
      if (params && params["pro"]) {
        this.paramSet = true;
        this.proNumber.setValue(params["pro"]);
      }
    });

    this.imageTypeSub = merge(
      this.bolSelected.valueChanges,
      this.wrSelected.valueChanges,
      this.drSelected.valueChanges
    ).subscribe(() => {
      this.imageTypeValid = this.validateImageTypes();
    });

    this.isAuthenticated = this.authService.isLoggedIn;

    if (this.isAuthenticated) {
      //get profile and account info
      this.infoSub = this.utilService
        .getProfileInfo()
        .pipe(
          switchMap(data => {
            //set profile info
            if (data) {
              this.emailAddress.setValue(data.email || "");
              this.name.setValue(data.firstName + " " + data.lastName);
              this.username = data.username || "";
            }
            //Use account code from profile info to fetch account information. We use the account information to prepopulate
            if (data.accountCode) {
              return this.utilService.getAccountInfo(data.accountCode);
            }
          })
        )
        .subscribe(
          data => {
            if (data) {
              let phone = data.phone || "";
              this.phoneNumber.setValue(MyEstesFormatters.formatPhone(phone));
            }
          },
          err => {
            this.errorMessages = ["err", err];
          }
        );
    }

    this.authService.authStateSet.subscribe((authState: string) => {
      if (this.isAuthenticated && authState === 'unauthenticated') {
        this.router.navigate(['login']);
      }
      this.isAuthenticated = 'authenticated' === authState ? true : false;
    });
  }

  ngOnDestroy() {
    if (this.paramSub) {
      this.paramSub.unsubscribe();
    }
    if (this.problemTypeSub) {
      this.problemTypeSub.unsubscribe();
    }
    if (this.infoSub) {
      this.infoSub.unsubscribe();
    }
    if (this.proNumberSub) {
      this.proNumberSub.unsubscribe();
    }
    if (this.imageTypeSub) {
      this.imageTypeSub.unsubscribe();
    }
  }

  validateImageTypes() {
    if (this.problem.value === "imageNotAvailable") {
      if (
        this.wrSelected.value ||
        this.drSelected.value ||
        this.bolSelected.value
      ) {
        return true;
      } else {
        return false;
      }
    } else {
      return true;
    }
  }

  get name() {
    return this.formGroup.controls["name"];
  }
  get phoneNumber() {
    return this.formGroup.controls["phoneNumber"];
  }
  get phoneNumberExt() {
    return this.formGroup.controls["phoneNumberExt"];
  }
  get faxNumber() {
    return this.formGroup.controls["faxNumber"];
  }
  get emailAddress() {
    return this.formGroup.controls["emailAddress"];
  }
  get proNumber() {
    return this.formGroup.controls["proNumber"];
  }
  get problem() {
    return this.formGroup.controls["problem"];
  }
  get description() {
    return this.formGroup.controls["description"];
  }
  get bolSelected() {
    return this.formGroup.controls["bolSelected"];
  }
  get wrSelected() {
    return this.formGroup.controls["wrSelected"];
  }
  get drSelected() {
    return this.formGroup.controls["drSelected"];
  }

  onSubmit() {
    this.imageTypeValid = this.validateImageTypes();
    if (this.formGroup.valid && this.imageTypeValid) {
      this.loading = true;
      this.requestAdditionalInfoService
        .submitRequest(this.formGroup.getRawValue())
        .subscribe(
          res => {
            this.successMessage = [
              "success",
              "Your message has been sent. A customer care representative will respond as soon as possible."
            ];
            this.loading = false;
          },
          err => {
            this.errorMessages = ["error", err];
            this.loading = false;
          }
        );
    } else {
      validateFormFields(this.formGroup);
      this.snackbarService.validationError();
    }
  }
}
