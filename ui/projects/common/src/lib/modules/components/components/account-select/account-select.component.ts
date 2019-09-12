import { Component, OnInit, Input } from '@angular/core';
import { FormControl } from '@angular/forms';
import { FormService } from '../../../../util/services/form.service';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { UtilService } from '../../../../util/services/util.service';
import { SubAccount } from '../../../../models/sub-account.interface';
import { DialogService } from '../../../dialog/services/dialog.service';
import { AccountSelectorDialogService } from '../../../dialog/services/account-selector-dialog.service';
import { AuthService } from '../../../auth/public_api';

@Component({
  selector: 'lib-account-select',
  templateUrl: './account-select.component.html',
  styleUrls: ['./account-select.component.scss']
})
export class AccountSelectComponent implements OnInit {
  @Input() label: string;
  @Input() fc: FormControl;
  filteredAccounts: Observable<SubAccount[]>;
  showDropdown = false;

  constructor(
    private utilService: UtilService,
    private dialog: AccountSelectorDialogService,
    public formService: FormService,
    private auth: AuthService
  ) {}

  openAccountDialog() {
    this.dialog.openAccountDialog().subscribe(next => {
      if (next) {
        this.fc.setValue(next);
      }
    });
  }

  ngOnInit() {
    if (this.auth.isLocalAccount) {
      this.showDropdown = false;
      this.prepopulateAccountCode();
    } else if (this.auth.is91Account || this.auth.isGroupAccount || this.auth.isNationalAccount) {
      this.fc.valueChanges.subscribe(filterVal => this.getFilteredAccounts(filterVal));
      this.showDropdown = true;
    }
  }

  private prepopulateAccountCode() {
    const token = this.auth.getAuthToken();
    if (token) {
      this.fc.setValue(token.accountCode, { emitEvent: false });
      this.fc.disable({ emitEvent: false });
    }
  }

  private getFilteredAccounts(value: string) {
    if (value.length > 2) {
      this.filteredAccounts = this.utilService
        .getSubAccountsByAccountNumber(value)
        .pipe(map(data => data.content));
    } else {
      this.filteredAccounts = null;
    }
  }
}
