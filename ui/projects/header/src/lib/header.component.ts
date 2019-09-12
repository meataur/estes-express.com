import { Component, OnInit, HostListener, Inject } from '@angular/core';
import { trigger, state, style, animate, transition } from '@angular/animations';
import { AuthService, DialogService } from 'common';
import { HeaderService } from './header.service';
import { APP_CONFIG } from './models/app.config';
import { AppConfig } from './models/app-config.interface';
import { Observable } from 'rxjs';

@Component({
  selector: 'lib-header',
  templateUrl: './header.component.html',
  animations: [
    trigger('collapse', [
      state(
        'open',
        style({
          transform: 'translate3d(0, 0, 0)'
        })
      ),
      state(
        'closed',
        style({
          transform: 'translate3d(-100%, 0, 0)'
        })
      ),
      transition('closed => open', animate('250ms ease-in')),
      transition('open => closed', animate('250ms ease-out'))
    ])
  ]
})
export class HeaderComponent implements OnInit {
  public isLoggedIn: boolean;
  public title = 'Estes Express Lines';
  public mainNavIsCollapsed: boolean;
  public myNavIsCollapsed: boolean;
  public showSearch: boolean;
  public collapse: string;
  public navigation: Array<any>;
  public accountNumber: string;
  public companyName$: Observable<string>;
  @HostListener('window:resize') onResize() {
    // console.log('Header resize collapse: ' +  this.collapse);
    if ('open' === this.collapse) {
      this.toggleMainNav();
    }
    this.myNavIsCollapsed = true;
    this.showSearch = false;
  }
  constructor(
    private authService: AuthService,
    private dialogService: DialogService,
    private headerService: HeaderService,
    @Inject(APP_CONFIG) private appConfig: AppConfig
  ) {
    this.mainNavIsCollapsed = true;
    this.myNavIsCollapsed = true;
    this.showSearch = false;
    this.collapse = 'closed';
    this.navigation = [];
  }

  ngOnInit() {
    const self = this;
    this.isLoggedIn = this.authService.isLoggedIn;
    this.authService.authAction$.subscribe(next => {
      this.isLoggedIn = next === 1 ? true : false;
    });
    this.headerService.getNavigation('/').subscribe(
      data => {
        // console.log('Header.getNavigation.Array.isArray(data):', Array.isArray(data));
        self.navigation = (Array.isArray(data)) ? data : [];
        // console.log('self.navigation:', self.navigation);
      },
      error => {
        // console.warn('Get navigation failed');
      }
    );
    this.authService.authStateSet.subscribe((authState: string) => {
        // console.log('HeaderComponent.AuthService.authStateSet: ' + authState);
        this.isLoggedIn = ('authenticated' === authState) ? true : false;
        this.accountNumber = this.authService.accountCode;
        this.companyName$ = this.authService.getCompanyName();
    });
  }

  toggleMainNav() {
    if ('open' === this.collapse) {
      this.collapse = 'closed';
      this.mainNavIsCollapsed = true;
      document.getElementsByTagName('body')[0].style.overflowY = 'auto';
    } else {
      this.collapse = 'open';
      this.mainNavIsCollapsed = false;
      document.getElementsByTagName('body')[0].style.overflowY = 'hidden';
    }
  }

  toggleMyNav() {
    if (this.myNavIsCollapsed) {
      document.getElementsByTagName('body')[0].style.overflowY = 'hidden';
    } else {
      document.getElementsByTagName('body')[0].style.overflowY = 'auto';
    }
    this.myNavIsCollapsed = !this.myNavIsCollapsed;
  }

  toggleSearch() {
    if (this.showSearch) {
      document.getElementsByTagName('body')[0].style.overflowY = 'auto';
    } else {
      document.getElementsByTagName('body')[0].style.overflowY = 'hidden';
    }
    this.showSearch = !this.showSearch;
  }

  toggleDropdownHover(open, elem) {
        // console.log('toggleDropdown.elem', elem);
        // console.log('toggleDropdown.open:', open);
        var navLink = elem.querySelector(':scope > .nav-link');
        if (open) {
            navLink.className += ' active';
        } else {
            navLink.className = navLink.className.replace(/\s*active/, '');
        }
  }

  toggleNav(event) {
      // console.log('toggleNav:', event);
      if ('main-nav' == event.target.id) {
          this.toggleMainNav();
      }
  }

  toggleSearchBar(event) {
      if ('search' == event.target.id) {
          this.toggleSearch();
      }
  }

  toggleMyNavBar(event) {
      if ('my-estes-nav' == event.target.id) {
          this.toggleMyNav();
      }
  }

  get commodityLibraryUrl() {
    return `${this.appConfig.appBaseUrl}/commodity-library`;
  }

  logout() {
    if (this.isLoggedIn) {
      this.dialogService.confirm('Logout', 'Are you sure you wish to log out?').subscribe(next => {
        if (next) {
          this.authService.logout().subscribe();
        }
      });
    }
  }
}
