import { Component, OnInit, Inject, OnDestroy } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import {
  FormBuilder,
  FormGroup
} from '@angular/forms';
import { FeedbackTypes, SnackbarService, FormService, MyEstesValidators, RegEx, MessageConstants, textAreaValidator, validateFormFields } from 'common';
import { ImageViewingService } from '../../services/image-viewing.service';
import { EmailImagesRequest, ImageResult, Image } from '../../models';
import { Subscription } from 'rxjs';

@Component({
  selector: 'lib-email-dialog',
  templateUrl: './email-dialog.component.html',
  styleUrls: []
})
export class EmailDialogComponent implements OnInit, OnDestroy {
  loading = false;
  formGroup: FormGroup;
  errorMessage: [FeedbackTypes, string];
  selections: ImageResult[];
  emailSub: Subscription;

  constructor(
    private fb: FormBuilder,
    private snackbarService: SnackbarService,
    private imageViewingService: ImageViewingService,
    public formService: FormService,
    public dialogRef: MatDialogRef<EmailDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngOnInit() {
    this.selections = this.data.selections;

    this.formGroup = this.fb.group({
      recipientEmails: [
        '',
        [
          MyEstesValidators.required,
          textAreaValidator(RegEx.email, MessageConstants.invalidEmailTextArea, 5)
        ]
      ]
    });

  }

  ngOnDestroy() {
    if (this.emailSub) {
      this.emailSub.unsubscribe();
    }
  }

  get recipientEmails() {
    return this.formGroup.controls['recipientEmails'];
  }

  onNoClick() {
    this.dialogRef.close(false);
  }

  onSubmit() {
    this.errorMessage = null;
    if (this.formGroup.valid) {
      this.loading = true;
      //set the email request
      const request = new EmailImagesRequest();
      //set the request images from the selections
      request.imageUrl = [];
      this.selections.forEach(s => {
        const imageDetails: Image[] = s.imageDetails || [];
        imageDetails.forEach(detail => {
          request.imageUrl.push(detail.imageLocation);
        });
      });
      request.userEmail = '';
      request.recipientEmails = this.recipientEmails.value.split('\n').filter(Boolean);
      
      this.emailSub = this.imageViewingService.emailImages(request).subscribe(res => {
        this.snackbarService.success(res.message || 'Email sent.');
        this.dialogRef.close();
      }, err => {
        this.errorMessage = ['error', err];
      });
      
    } else {
      validateFormFields(this.formGroup);
      this.snackbarService.validationError();
    }
  }
}
