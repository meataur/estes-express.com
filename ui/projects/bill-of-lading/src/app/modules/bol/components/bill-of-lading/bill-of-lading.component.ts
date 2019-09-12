import { Component, OnInit } from '@angular/core';
import { AuthService, PromoService, LeftNavigationService } from 'common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-bill-of-lading',
  templateUrl: './bill-of-lading.component.html',
  styleUrls: ['./bill-of-lading.component.scss']
})
export class BillOfLadingComponent implements OnInit {
  navLinks = [
    {
      label: 'Create BOL',
      path: 'create'
    },
    {
      label: 'BOL History',
      path: 'history'
    },
    {
      label: 'BOL Templates',
      path: 'templates'
    },
    {
      label: 'BOL Drafts',
      path: 'drafts'
    }
    // {
    //   label: 'BOL Help',
    //   path: 'help'
    // }
  ];

  title = 'Bill of Lading';

  constructor(
    private router: Router,
    private authService: AuthService,
    private promoService: PromoService,
  ) {}

  ngOnInit() {
    this.promoService.setAppId('bill-of-lading');
    this.promoService.setAppState('authenticated');
    
    this.authService.authStateSet.subscribe((authState: string) => {
      if ('unauthenticated' === authState) {
        this.router.navigate(['login']);
      }
    });
  }
}
