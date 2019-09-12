import { Component, OnInit } from '@angular/core';
import { ClaimsRequest } from './models/claims-request.model';
import { ClaimsService } from './claims.service';
import { LeftNavigationService, PromoService } from 'common';
import { AuthService } from 'common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-claims',
  templateUrl: './claims.component.html',
  styleUrls: ['./claims.component.scss']
})
export class ClaimsComponent implements OnInit {
  claimRequest: ClaimsRequest;
  accountCode: string;

  constructor(
    private router: Router,
    public authenticationService: AuthService,
    public leftNavigationService: LeftNavigationService,
    public promoService: PromoService,
  ) {}
  public tabSelectedIndex: number;

  ngOnInit() {
    if (this.authenticationService.getAuthToken() != null) {
      this.accountCode = this.authenticationService.getAuthToken().accountCode;
    }

    this.leftNavigationService.setNavigation(`Manage`, `/manage`);
    this.promoService.setAppId('address-book');
    this.promoService.setAppState('any');

    this.authenticationService.authStateSet.subscribe((authState: string) => {
      if ('unauthenticated' === authState) {
        this.router.navigate(['login']);
	    }
    });
  }

  get hasClaimsFileAccess() {
    return this.authenticationService.isInScope('CLAIMSFILE');
  }
  get hasClaimsInquiryAccess() {
    return this.authenticationService.isInScope('CLAIMIN');
  }
  updateSelectedTab(tab: string) {
    if (this.hasClaimsFileAccess && this.hasClaimsInquiryAccess) {
      this.tabSelectedIndex = tab === 'file-claim' ? 1 : 0;
    }
  }
}
