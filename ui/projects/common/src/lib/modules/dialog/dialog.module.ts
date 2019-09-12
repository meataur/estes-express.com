import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfirmationComponent } from './components/confirmation/confirmation.component';
import { InfoDialogComponent } from './components/info-dialog/info-dialog.component';
import {
  MatDialogModule,
  MatCardModule,
  MatInputModule,
  MatSelectModule,
  MatRadioModule,
  MatTableModule,
  MatPaginatorModule,
  MatProgressSpinnerModule,
  MatTabsModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatSortModule
} from '@angular/material';
import { DialogService } from './services/dialog.service';
import { EmailDialogComponent } from './components/email-dialog/email-dialog.component';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { EmailService } from './services/email.service';
import { EmailDialogService } from './services/email-dialog.service';
import { AddressSelectorDialogService } from './services/address-selector-dialog.service';
import { AccountSelectorDialogService } from './services/account-selector-dialog.service';
import { AddressBookService } from './services/address-book.service';
import { ComponentsModule } from '../components/components.module';
import { SnackbarModule } from '../snackbar/snackbar.module';
import { AddressSelectorDialogComponent } from './components/address-selector/address-selector-dialog.component';
import { UtilModule } from '../../util/util.module';
import { AccountSelectorDialogComponent } from './components/account-selector/account-selector-dialog.component';
import { CommodityLibraryModalComponent } from './components/commodity-library-modal/commodity-library-modal.component';

@NgModule({
  imports: [
    HttpClientModule,
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatInputModule,
    MatSelectModule,
    MatRadioModule,
    MatTableModule,
    MatPaginatorModule,
    MatTabsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatDialogModule,
    MatProgressSpinnerModule,
    MatSortModule,
    ComponentsModule,
    SnackbarModule,
    UtilModule
  ],
  declarations: [
    ConfirmationComponent,
    InfoDialogComponent,
    EmailDialogComponent,
    AddressSelectorDialogComponent,
    AccountSelectorDialogComponent,
    CommodityLibraryModalComponent
  ],
  entryComponents: [
    ConfirmationComponent,
    InfoDialogComponent,
    EmailDialogComponent,
    AddressSelectorDialogComponent,
    AccountSelectorDialogComponent,
    CommodityLibraryModalComponent
  ]
})
export class DialogModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: DialogModule,
      providers: [
        DialogService,
        EmailDialogService,
        AddressSelectorDialogService,
        AddressBookService,
        AccountSelectorDialogService
      ]
    };
  }
}
