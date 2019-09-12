import { Injectable } from '@angular/core';
import { EmailDialogData } from '../models';
import { EmailDialogComponent } from '../components/email-dialog/email-dialog.component';
import { Observable } from 'rxjs';
import { MatDialog } from '@angular/material';

@Injectable({
  providedIn: 'root'
})
export class EmailDialogService {

  constructor(public dialog: MatDialog) { }

  openEmailDialog(data: EmailDialogData): Observable<boolean> {
    const dialogRef = this.openDialog(EmailDialogComponent, data);

    return dialogRef.afterClosed();
  }

  private openDialog(component: any, data: any) {
    return this.dialog.open(component, {
      width: '550px',
      data: data,
      disableClose: false,
      panelClass: ['estes-modal']
    });
  }
}
