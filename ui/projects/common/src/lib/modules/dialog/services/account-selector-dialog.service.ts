import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MatDialog } from '@angular/material';
import { SubAccount } from '../../../models/sub-account.interface';
import { UtilService } from '../../../util/services/util.service';
import { AccountSelectorDialogComponent } from '../components/account-selector/account-selector-dialog.component';

@Injectable({
  providedIn: 'root'
})
export class AccountSelectorDialogService {

  constructor(public dialog: MatDialog, public utilService: UtilService) {
  }

  openAccountDialog(): Observable<SubAccount> {
    const accountDialogRef = this.dialog.open(AccountSelectorDialogComponent, {
      width: '50rem',
      panelClass: ['estes-app', 'estes-modal'],
    });

    return accountDialogRef.afterClosed();
  }
}
