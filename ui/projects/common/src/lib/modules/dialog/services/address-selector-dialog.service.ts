import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BookAddress } from '../models/book-address.model';
import { MatDialog } from '@angular/material';
import { AddressSelectorDialogComponent } from '../components/address-selector/address-selector-dialog.component';

@Injectable({
  providedIn: 'root'
})
export class AddressSelectorDialogService {

  constructor( public dialog: MatDialog ) {
    };

   openAddressDialog(): Observable<BookAddress> {
    const addressDialogRef = this.dialog.open(AddressSelectorDialogComponent, {
      width: '50rem',
      panelClass: ['estes-app', 'estes-modal']
    });

    return addressDialogRef.afterClosed();
   }
}
