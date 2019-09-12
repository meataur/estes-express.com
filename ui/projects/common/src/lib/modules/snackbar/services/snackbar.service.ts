import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material';
import { SnackbarMessageComponent } from '../components/snackbar-message/snackbar-message.component';

@Injectable({
  providedIn: 'root'
})
export class SnackbarService {
  constructor(private snackBar: MatSnackBar) {}

  success(message: string) {
    this.snackBar.openFromComponent(SnackbarMessageComponent, {
      duration: 3800,
      data: { message: message, type: 'success' },
      panelClass: ['snackbar', 'snackbar--success']
    });
  }
  info(message: string) {
    this.snackBar.openFromComponent(SnackbarMessageComponent, {
      duration: 3800,
      data: { message: message, type: 'info' },
      panelClass: ['snackbar', 'snackbar--info']
    });
  }
  error(message: string) {
    this.snackBar.openFromComponent(SnackbarMessageComponent, {
      duration: 3800,
      data: { message: message, type: 'error' },
      panelClass: ['snackbar', 'snackbar--error']
    });
  }
  validationError() {
    this.snackBar.openFromComponent(SnackbarMessageComponent, {
      duration: 3800,
      data: { message: 'There are validation errors in the form.  Please review the form and submit again.', type: 'error'},
      panelClass: ['snackbar', 'snackbar--error']
    });
  }
}
