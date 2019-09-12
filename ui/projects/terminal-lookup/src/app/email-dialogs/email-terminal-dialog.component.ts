import { Component, OnInit, Inject } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";
import {
  FormBuilder,
  FormGroup,
  Validators,
  FormControl
} from "@angular/forms";
import { TerminalLookupService } from "../terminal-lookup.service";
import {
  FormService,
  RegEx,
  MessageConstants,
  textAreaValidator,
  validateFormFields,
  SnackbarService,
  FeedbackTypes,
  EmailDialogData,
  MyEstesValidators
} from "common";

@Component({
  templateUrl: "./email-terminal-dialog.component.html"
})
export class EmailTerminalDialogComponent implements OnInit {
  loading = false;
  formGroup: FormGroup;
  errorMessage: [FeedbackTypes, string];

  constructor(
    private fb: FormBuilder,
    private snackbarService: SnackbarService,
    public formService: FormService,
    public dialogRef: MatDialogRef<EmailTerminalDialogComponent>,
    public terminalLookupService: TerminalLookupService,
    @Inject(MAT_DIALOG_DATA) public data: EmailDialogData
  ) {}

  ngOnInit() {
    this.formGroup = this.fb.group({
      fileType: [".xls", MyEstesValidators.required],
      emails: [
        "",
        [
          MyEstesValidators.required,
          textAreaValidator(RegEx.email, MessageConstants.invalidEmailTextArea)
        ]
      ],
      searchAllTerminals: [false]
    });
  }

  get fileType(): FormControl {
    return this.formGroup.controls["fileType"] as FormControl;
  }
  get emails(): FormControl {
    return this.formGroup.controls["emails"] as FormControl;
  }
  get searchAllTerminals(): FormControl {
    return this.formGroup.controls["searchAllTerminals"] as FormControl;
  }

  onNoClick() {
    this.dialogRef.close(false);
  }

  onSubmit() {
    this.errorMessage = null;
    if (this.formGroup.valid) {
      this.loading = true;
      if (this.searchAllTerminals.value) {
        this.data.requestPayload["state"] = "";
        this.data.requestPayload["country"] = "**";
      } else {
        this.data.requestPayload[
          "state"
        ] = this.terminalLookupService.searchState;
        this.data.requestPayload[
          "country"
        ] = this.terminalLookupService.searchCountry;
      }
      this.data.requestPayload["emails"] = this.emails.value;
      this.data.requestPayload["fileType"] = this.fileType.value;
      this.terminalLookupService
        .emailTerminalsByCountryState(this.data.requestPayload)
        .subscribe(
          next => {
            if (next.errorCode && next.errorCode.toUpperCase() === "ERROR") {
              this.errorMessage = ["error", next.message];
            } else {
              this.snackbarService.success(`${next.message}`);
              this.dialogRef.close(next);
            }
          },
          err => {
            this.loading = false;
            this.snackbarService.error(`Error: ${err}`);
          }
        );
    } else {
      validateFormFields(this.formGroup);
    }
  }
}
