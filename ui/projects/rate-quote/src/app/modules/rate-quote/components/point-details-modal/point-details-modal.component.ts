import { Component, OnInit, Input, Inject } from '@angular/core';
import { Point } from '../../models';
import { PointLookupService, Terminal, SnackbarService } from 'common';
import { Observable, empty, EMPTY } from 'rxjs';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { catchError } from 'rxjs/operators';

@Component({
  selector: 'app-point-details-modal',
  templateUrl: './point-details-modal.component.html',
  styleUrls: ['./point-details-modal.component.scss']
})
export class PointDetailsModalComponent implements OnInit {
  point$: Observable<Terminal>;

  constructor(
    private dialogRef: MatDialogRef<PointDetailsModalComponent>,
    @Inject(MAT_DIALOG_DATA) public point: Point,
    private pointLookup: PointLookupService,
    private snackbar: SnackbarService
  ) {}

  ngOnInit() {
    this.point$ = this.pointLookup.getTerminalByPoint(this.point).pipe(
      catchError(err => {
        this.snackbar.error('An error occurred retrieving point information.  Please try again.');
        return EMPTY;
      })
    );
  }
}
