import { Injectable } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { Observable } from 'rxjs';
import { ConfirmationDialogData } from '../models';
import { ConfirmationComponent } from '../components/confirmation/confirmation.component';
import { InfoDialogComponent } from '../components/info-dialog/info-dialog.component';
import { CommodityLibraryModalComponent } from '../components/commodity-library-modal/commodity-library-modal.component';
import { Commodity } from '../models';

@Injectable({
  providedIn: 'root'
})
export class DialogService {
  constructor(public dialog: MatDialog) {}

  openCommodityLibrary(): Observable<Commodity> {
    return this.prompt(CommodityLibraryModalComponent, null, 'estes-app');
  }

  prompt(component: any, data: any | null, panelClass?: string, width?: string): Observable<any> {
    const dialogRef = this.openDialog(component, data, panelClass, width);

    return dialogRef.afterClosed();
  }

  confirm(title: string, message: string): Observable<boolean> {
    const data: ConfirmationDialogData = {
      title: title,
      message: message
    };

    const dialogRef = this.openDialog(ConfirmationComponent, data, 'estes-app');

    return dialogRef.afterClosed();
  }

  info(title: string, template: any, panelClass?: string) {
    const data = {
      title: title,
      template: template
    };
    const dialogRef = this.openDialog(InfoDialogComponent, data, panelClass);

    return dialogRef.afterClosed();
  }

  private openDialog(component: any, data: any, panelClass?: string, width?: string) {
    const config: MatDialogConfig = {
      data: data,
      disableClose: false,
      panelClass: ['estes-modal', panelClass]
    };

    if (width) {
      config.width = width;
    }

    return this.dialog.open(component, config);
  }
}
