import { Component, OnInit } from "@angular/core";
import { TerminalLookupService } from "../terminal-lookup.service";
import {
  LeftNavigationService,
  SnackbarService,
  EmailDialogData,
  EmailDialogService,
  UtilService,
  PointLookupService,
  PromoService,
  FeedbackTypes
} from "common";
import { TerminalPointRequest } from "../terminal-point-request.model";
import { ActivatedRoute, Router } from "@angular/router";
import {
  FormControl,
  FormGroup,
  FormBuilder,
  FormArray,
  FormGroupDirective,
  Validators,
  ValidationErrors
} from "@angular/forms";
import {
  FormService,
  Point,
  Terminal,
  MyEstesValidators,
  RegEx,
  MessageConstants,
  validateFormFields,
  patternValidator
} from "common";
import { TerminalListResponse } from "../terminal-list-response.model";
import { EmailTerminalDialogComponent } from "../email-dialogs/email-terminal-dialog.component";
import { EmailCoverageDialogComponent } from "../email-dialogs/email-coverage-dialog.component";
import { ViewChild } from "@angular/core";
import {
  animate,
  state,
  style,
  transition,
  trigger
} from "@angular/animations";
import { Observable, of, forkJoin } from "rxjs";
import { debounceTime } from "rxjs/operators";
import { MatDialog } from "@angular/material";
import { environment } from "../../environments/environment";
declare var MarkerClusterer: any;

@Component({
  selector: "app-terminal-lookup",
  templateUrl: "./terminal-lookup.component.html",
  styleUrls: [],
  animations: [
    trigger("detailExpand", [
      state(
        "collapsed",
        style({
          height: "0px",
          minHeight: "0px",
          display: "none",
          border: "none"
        })
      ),
      state("expanded", style({ height: "*" })),
      transition(
        "expanded <=> collapsed",
        animate("225ms cubic-bezier(0.4, 0.0, 0.2, 1)")
      )
    ])
  ]
})
export class TerminalLookupComponent implements OnInit {
  isLookUpByZip: boolean = false;

  @ViewChild("gmap") gmapElement: any;
  @ViewChild(FormGroupDirective) terminalLookupForm;
  baseURL = environment.serviceBaseUrl;
  formGroup: FormGroup;
  public map: google.maps.Map;
  public mapProp: any;
  public markersArray: google.maps.Marker[];
  public markerCluster: any;
  public defaultLatLng: google.maps.LatLng;

  public errorMessages: [FeedbackTypes, string];
  public loading: boolean;
  public submitted: boolean;
  public lastSubmittedRequest: any;

  public countries: any[];
  public states: String[];
  public unitedStatesList: String[];
  public provincesList: String[];

  public terminalListResponse: TerminalListResponse;
  public terminalCoords: any;
  public lookupByOptions: String[];
  public lookupByStrings: {};
  public searchAreaName: String;
  public showEmailTerminalButton: boolean;

  public displayedColumns: String[];

  public expandedElement: Terminal;

  public pointSuggestions: Observable<any[]>;

  constructor(
    public terminalLookupService: TerminalLookupService,
    public leftNavigationService: LeftNavigationService,
    public dialog: MatDialog,
    public utilService: UtilService,
    public pointLookupService: PointLookupService,
    public router: Router,
    public promoService: PromoService,
    private snackbar: SnackbarService,
    public formService: FormService,
    private fb: FormBuilder
  ) {
    // Create list of countries for country dropdown
    this.countries = [
      { viewValue: "United States", value: "US" },
      { viewValue: "Canada", value: "CN" },
      { viewValue: "Mexico", value: "MX" }
    ];
    this.defaultLatLng = new google.maps.LatLng(39.8283, -98.5795);
  }

  get country() {
    return this.formGroup.controls["country"];
  }

  get lookupBy() {
    return this.formGroup.controls["lookupBy"];
  }

  get state() {
    return this.formGroup.controls["state"];
  }

  get stateSelect() {
    return this.formGroup.controls["stateSelect"];
  }

  get zip() {
    return this.formGroup.controls["zip"];
  }

  get city() {
    return this.formGroup.controls["city"];
  }

  reloadSuggestions() {
    if (this.city.value || this.state.value || this.zip) {
      const point = new Point();
      point.city = this.city.value;
      point.state = this.state.value;
      point.country = this.country.value;
      point.zip = this.zip.value;
      this.pointSuggestions = this.pointLookupService
        .lookupPoints(point)
        .pipe(debounceTime(100));
    } else {
      this.pointSuggestions = of([]).pipe(debounceTime(100));
    }
  }

  /**
   * autofillPoint sets the request based on the point selected from the autoselector
   * @param point is the selected point from the autoselect list
   */
  autofillPoint(point) {
    this.zip.setValue(point.zip);
    this.city.setValue(point.city);
    this.state.setValue(point.state);
  }

  /**
   * resets the lookup form and empties the results list
   * @param terminalLookupForm is the terminal lookup form, we use it to reset the validation
   */
  reset() {
    this.terminalLookupForm.resetForm({
      country: "US",
      lookupBy: "ZIP Code",
      stateSelect: "",
      zip: "",
      city: "",
      state: ""
    });
    this.terminalListResponse = null;
    this.resetMap();
    this.countrySelected();
  }

  resetMap() {
    this.clearMarkers();
    this.map.setCenter(this.defaultLatLng);
    this.map.setZoom(3);
  }

  navigate(url: string) {
    window.open(url, "_blank");
  }

  /**
   * lookup submits a lookup request and returns a list of terminals based on the input form data
   * @param isValid boolean form validation indicator
   */
  lookup() {
    this.isLookUpByZip = false;
    const ctrl = this;
    ctrl.showEmailTerminalButton = false;
    // console.error('Form valid: ', this.formGroup.valid);
    this.terminalListResponse = null;
    this.resetMap();

    if (this.formGroup.valid) {
      ctrl.errorMessages = null;
      ctrl.loading = true;
      if (
        ctrl.lookupBy.value === "State" ||
        ctrl.lookupBy.value === "Province"
      ) {
        const request = new TerminalPointRequest(
          ctrl.country.value,
          ctrl.stateSelect.value
        );
        ctrl.terminalLookupService
          .getTerminalsByCountryState(request)
          .subscribe(
            res => {
              ctrl.terminalListResponse = res;
              // expand terminal if only one is returned in list
              if (
                ctrl.terminalListResponse.terminalLists.length == 1 &&
                ctrl.terminalListResponse.terminalLists[0].terminals.length ===
                  1
              ) {
                this.expandTerminalRow(
                  ctrl.terminalListResponse.terminalLists[0].terminals[0]
                );
              }
              ctrl.lastSubmittedRequest = request;
              ctrl.loading = false;
              ctrl.showEmailTerminalButton = true;
              ctrl.setMapPoints();
              this.snackbar.success(
                `See the search results in the table below.`
              );
            },
            err => {
              ctrl.errorMessages = ["error", err];
              this.loading = false;
              let errorMessagesStr = "";
              if (Array.isArray(err)) {
                errorMessagesStr = err.join("\n");
              } else {
                errorMessagesStr = err;
              }
              this.snackbar.error(errorMessagesStr);
            }
          );
      } else {
        const point = new Point();
        point.city = ctrl.city.value;
        point.state = ctrl.state.value;
        point.country = ctrl.country.value;
        point.zip = ctrl.zip.value;
        ctrl.terminalLookupService.getTerminalsByPoint(point).subscribe(
          res => {
            ctrl.terminalListResponse = res;
            // Expand terminal if only one is returned in list
            if (
              ctrl.terminalListResponse.terminalLists.length == 1 &&
              ctrl.terminalListResponse.terminalLists[0].terminals.length === 1
            ) {
              ctrl.terminalListResponse.terminalLists[0].terminals[0].expanded = true;
            }
            ctrl.lastSubmittedRequest = point;
            this.loading = false;
            ctrl.setMapPoints();
            this.snackbar.success(`See the search results in the table below.`);
            this.isLookUpByZip = true;
          },
          err => {
            ctrl.errorMessages = ["error", err];
            this.loading = false;
            let errorMessagesStr = "";
            if (Array.isArray(err)) {
              errorMessagesStr = err.join("\n");
            } else {
              errorMessagesStr = err;
            }
            this.snackbar.error(errorMessagesStr);
          }
        );
      }
    } else {
      this.snackbar.error(
        `Invalid search criteria. Check the search form for errors.`
      );
      // this.getFormValidationErrors();
    }
  }

  /**
   * submits a lookup request for all US terminals
   */
  lookupAllTerminals() {
    this.isLookUpByZip = false;
    const ctrl = this;
    ctrl.showEmailTerminalButton = false;
    this.terminalListResponse = null;
    this.resetMap();
    ctrl.lookupBy.setValue("State");
    ctrl.stateSelect.setValue("");
    const request = new TerminalPointRequest();
    request.country = "**";
    ctrl.loading = true;
    ctrl.terminalLookupService.getTerminalsByCountryState(request).subscribe(
      res => {
        ctrl.terminalListResponse = res;
        // Expand terminal if only one is returned in list
        if (
          ctrl.terminalListResponse.terminalLists.length == 1 &&
          ctrl.terminalListResponse.terminalLists[0].terminals.length === 1
        ) {
          ctrl.terminalListResponse.terminalLists[0].terminals[0].expanded = true;
        }
        ctrl.lastSubmittedRequest = request;
        ctrl.loading = false;
        ctrl.showEmailTerminalButton = true;
        ctrl.setMapPoints();
      },
      err => {
        ctrl.errorMessages = ["error", err];
        this.loading = false;
        let errorMessagesStr = "";
        if (Array.isArray(err)) {
          errorMessagesStr = err.join("\n");
        } else {
          errorMessagesStr = err;
        }
        this.snackbar.error(errorMessagesStr);
      }
    );
  }

  getFormValidationErrors() {
    // console.log('getFormValidationErrors');
    Object.keys(this.formGroup.controls).forEach(key => {
      const controlErrors: ValidationErrors = this.formGroup.get(key).errors;
      if (controlErrors != null) {
        Object.keys(controlErrors).forEach(keyError => {
          // console.error('Key control: ' + key + ', keyError: ' + keyError + ', err value: ', controlErrors[keyError]);
        });
      }
    });
  }

  /**
   * countrySelected sets state list and string variables that are specific to the country selected in the lookup form
   * the function is triggered when a new country is selected from the dropdown list
   */
  countrySelected() {
    const ctrl = this;
    if (ctrl.country.value) {
      if (ctrl.country.value === "CN") {
        ctrl.lookupByOptions = ["Province", "Postal Code"];
        ctrl.lookupBy.setValue("Postal Code");
        ctrl.lookupByStrings = {
          state: "Province",
          zip: "Postal Code"
        };
        ctrl.states = ctrl.provincesList;
      } else if (ctrl.country.value === "MX") {
        ctrl.lookupByOptions = ["State", "Postal Code"];
        ctrl.lookupBy.setValue("Postal Code");
        ctrl.lookupByStrings = {
          state: "State",
          zip: "Postal Code"
        };
        ctrl.states = ctrl.unitedStatesList;
      } else {
        ctrl.lookupByOptions = ["State", "ZIP Code"];
        ctrl.lookupBy.setValue("ZIP Code");
        ctrl.lookupByStrings = {
          state: "State",
          zip: "ZIP Code"
        };
        ctrl.states = ctrl.unitedStatesList;
      }
    }
  }

  /**
   * Expands a terminal row and loads additional terminal detail info if not included in the terminal data model
   * @param element is the terminal element to expand
   */
  expandTerminalRow(element) {
    // If element already expanded, close. Otherwise expand
    if (!element.expanded) {
      element.loading = true;
      // We can assume the terminal list already has details if the searchType is not state
      if (this.terminalListResponse.searchType === "state") {
        const point = new Point();
        point.city = element.address.city;
        point.state = element.address.state;
        point.country = element.address.country;
        point.zip = element.address.zip;

        this.terminalLookupService.getTerminalsByPoint(point).subscribe(
          res => {
            if (
              res &&
              res.terminalLists &&
              res.terminalLists.length > 0 &&
              res.terminalLists[0].terminals
            ) {
              const term = res.terminalLists[0].terminals[0];
              element.address = term.address || {};
              element.displayOptions = term.displayOptions || "";
              element.email = term.email || "";
              element.expanded = term.expanded || false;
              element.fax = term.fax || "";
              element.id = term.id || "";
              element.loading = term.loading || false;
              element.manager = term.manager || "";
              element.managerEmail = term.managerEmail || "";
              element.name = term.name || "";
              element.nationalMap = term.nationalMap || "";
              element.phone = term.phone || "";
              element.serviceArea = term.serviceArea || "";
              element.serviceMap = term.serviceMap || "";
              element.loading = false;
              element.expanded = true;
            }
          },
          err => {
            this.snackbar.error(err);
            element.loading = false;
          }
        );
      } else {
        element.expanded = true;
        element.loading = false;
      }
    } else {
      element.expanded = false;
    }
  }

  openTerminalEmailDialog() {
    const data = <EmailDialogData>{
      title: `Email Terminal Results`,
      url: "",
      message: "",
      requestPayload: {
        country: this.country.value,
        state: this.state.value
      }
    };

    this.dialog.open(EmailTerminalDialogComponent, {
      data: data,
      width: "50rem",
      disableClose: false,
      panelClass: ["estes-modal"]
    });
  }

  openCoverageEmailDialog(
    country: string,
    state: string,
    city: string,
    zip: string
  ) {
    const data = <EmailDialogData>{
      title: `Email Next Day Points`,
      url: "",
      message: "",
      requestPayload: {
        city: city,
        state: state,
        zip: zip,
        country: country
      }
    };

    this.dialog.open(EmailCoverageDialogComponent, {
      data: data,
      width: "50rem",
      disableClose: false,
      panelClass: ["estes-modal"]
    });
  }

  clearMarkers() {
    if (this.markerCluster) {
      this.markersArray.forEach(marker => {
        this.markerCluster.removeMarker(marker);
        marker.setMap(null);
      });
      this.markersArray = [];
      this.markerCluster = new MarkerClusterer(this.map, this.markersArray);
      if (this.markerCluster) {
        this.markerCluster.clearMarkers();
      }
    }
  }

  setMapPoints() {
    if (
      this.map &&
      this.terminalListResponse.terminalLists.length > 0 &&
      this.terminalCoords &&
      this.terminalCoords.length > 0
    ) {
      const bounds = new google.maps.LatLngBounds();
      const infowindow = new google.maps.InfoWindow();
      this.clearMarkers();
      this.terminalListResponse.terminalLists.forEach(terminalList => {
        terminalList.terminals.forEach(terminal => {
          this.terminalCoords.forEach(coord => {
            if (terminal.id == coord.terminalCode) {
              if (coord.latitude && coord.longitude) {
                terminal["lat"] = coord.latitude;
                terminal["lng"] = coord.longitude;
                const myLatlng = new google.maps.LatLng(
                  terminal.lat,
                  terminal.lng
                );
                const marker: any = new google.maps.Marker({
                  draggable: true,
                  animation: google.maps.Animation.DROP,
                  position: myLatlng,
                  icon: "assets/estes-powerball-icon.png"
                });
                this.markersArray.push(marker);
                bounds.extend(marker.position);
                google.maps.event.addListener(
                  marker,
                  "click",
                  (function(marker, terminal) {
                    return function() {
                      infowindow.setContent(
                        `<a href="https://www.google.com/maps/search/?api=1&query=${
                          terminal.address.streetAddress
                        },${terminal.address.streetAddress2},${
                          terminal.address.city
                        },${terminal.address.state},${
                          terminal.address.zip
                        }" target="_blank"><strong>${terminal.name} (${
                          terminal.id
                        })</strong></a></br><span>${
                          terminal.address.streetAddress
                        }, ${
                          terminal.address.streetAddress2
                        }</span></br><span>${terminal.address.city}, ${
                          terminal.address.state
                        } ${terminal.address.zip}</span>`
                      );
                      infowindow.open(this.map, marker);
                    };
                  })(marker, terminal)
                );
              }
            }
          });
        });
      });
      this.markerCluster = new MarkerClusterer(this.map, this.markersArray, {
        imagePath: "assets/estes-google-maps-cluster-icon",
        maxZoom: 6,
        gridSize: 35
      });

      if (!bounds.isEmpty()) {
        this.map.fitBounds(bounds);
        const zoomLevel = this.map.getZoom();
        if (zoomLevel > 14) {
          this.map.setZoom(14);
        }
      }
    }
  }

  ngOnInit() {
    this.formGroup = this.fb.group({
      country: ["US", MyEstesValidators.required],
      lookupBy: ["", MyEstesValidators.required],
      stateSelect: ["", false],
      zip: [
        "",
        [
          MyEstesValidators.required,
          patternValidator(
            RegEx.USorCNPostalCode,
            MessageConstants.invalidUSorCNPostalCode
          )
        ]
      ],
      city: ["", MyEstesValidators.required],
      state: ["", MyEstesValidators.required]
    });
    this.lookupBy.valueChanges.subscribe(next => {
      // console.log('lookupBy changed to: ', next);
      if (next === "State" || next === "Province") {
        this.formGroup.get("city").clearValidators();
        this.formGroup.get("city").setValidators([]);
        this.formGroup.get("city").updateValueAndValidity();
        this.formGroup.get("state").clearValidators();
        this.formGroup.get("state").setValidators([]);
        this.formGroup.get("state").updateValueAndValidity();
        this.formGroup.get("zip").clearValidators();
        this.formGroup.get("zip").setValidators([]);
        this.formGroup.get("zip").updateValueAndValidity();
      } else {
        this.formGroup.get("city").clearValidators();
        this.formGroup.get("city").setValidators(MyEstesValidators.required);
        this.formGroup.get("city").updateValueAndValidity();
        this.formGroup.get("state").clearValidators();
        this.formGroup.get("state").setValidators(MyEstesValidators.required);
        this.formGroup.get("state").updateValueAndValidity();
        this.formGroup.get("zip").clearValidators();
        this.formGroup
          .get("zip")
          .setValidators([
            MyEstesValidators.required,
            patternValidator(
              RegEx.USorCNPostalCode,
              MessageConstants.invalidUSorCNPostalCode
            )
          ]);
        this.formGroup.get("zip").updateValueAndValidity();
      }
    });
    // initialize google map component
    this.mapProp = {
      center: this.defaultLatLng,
      zoom: 3,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    this.map = new google.maps.Map(
      this.gmapElement.nativeElement,
      this.mapProp
    );
    this.markersArray = [];

    // Set our default request to lookup by state, with US preselected as country

    this.terminalListResponse = null;

    this.countrySelected();
    this.errorMessages = null;

    // Order of displayed columns in terminal list table
    this.displayedColumns = ["terminal", "address", "phone", "fax", "expand"];
    this.pointSuggestions = of([]);

    this.leftNavigationService.setNavigation(`Ship`, `/ship`);
    this.promoService.setAppId("terminal-lookup");
    this.promoService.setAppState("any");

    // preload states and provinces to allow for more performant form input process
    forkJoin(
      this.utilService.getStates("US"),
      this.utilService.getStates("CN")
    ).subscribe(
      ([res1, res2]) => {
        this.unitedStatesList = res1;
        this.provincesList = res2;
        this.states = this.unitedStatesList;
      },
      err => {}
    );

    this.terminalLookupService.getTerminalCoordinates().subscribe(data => {
      this.terminalCoords = data.contentlets || [];
    });
  }
}
