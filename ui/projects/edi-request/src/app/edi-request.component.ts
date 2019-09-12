import { Component, OnInit } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort } from '@angular/material';
import { AuthService, PromoService, LeftNavigationService } from 'common';
import { environment } from '../environments/environment';

@Component({
  selector: 'app-edi-request',
  templateUrl: './edi-request.component.html',
  styleUrls: ['./edi-request.component.scss']
})
export class EdiRequestComponent implements OnInit {
  ediSets: MatTableDataSource<any>;
  displayedColumns: string[];

  constructor(
    private authService: AuthService,
    private promoService: PromoService,
	private leftNavigationService: LeftNavigationService
	) {}

  ngOnInit() {
    this.displayedColumns = ['name', 'description'];
    const ediSetData = [{
      name: '204 Motor Carrier Shipment Information',
      version: '4010',
      description: 'The customer-provided information is translated, and the freight bill is generated from the 204/211 data. If all of the information is provided by the shipper, the billing clerk simply verifies the information against the paperwork provided at pickup. Auto-creating these freight bills benefits the shipper by preventing keying errors and accelerating freight processing.',
      url: `${environment.serviceBaseUrl}/downloads/Estes204v4010.pdf`
    },
    {
      name: '210 Freight Details and Invoice',
      version: '4010',
      description: 'The 210 EDI set is electronic invoicing. Billing electronically eliminates paper flow and handling for both Estes and our customers. It cuts down on the amount of administrative effort involved in billing (for Estes) and paying (for our customers) of invoices. When combined with customers sending 820s and remittance advice, the process becomes much more streamlined and efficient.',
      url: `${environment.serviceBaseUrl}/downloads/Estes210v4010.pdf`
    },
    {
      name: '211 Motor Carrier Bill of Lading',
      version: '4010',
      description: 'The customer-provided information is translated, and the freight bill is generated from the 204/211 data. If all of the information is provided by the shipper, the billing clerk simply verifies the information against the paperwork provided at pickup. Auto-creating these freight bills benefits the shipper by preventing keying errors and accelerating freight processing.',
      url: `${environment.serviceBaseUrl}/downloads/Estes211v4010.pdf`
    },
    {
      name: '214 Shipment Status Message',
      version: '4010',
      description: 'The 214 EDI set provides our customers with information about the status of their shipments. Many different statuses can be reported, which eliminates the need for the customer to call about their shipment\'s disposition.',
      url: `${environment.serviceBaseUrl}/downloads/Estes214v4010.pdf`
    },
    {
      name: '820 Payment Order/Remittance Advice',
      version: '4010',
      description: 'Estes encourages the use of the 820 for remittance purposes. Although a transfer of funds is desirable, an 820 Remittance and a mailed check are acceptable. Estes automatically applies payment based on its 10-digit freight bill number provided in the 820, which prevents payment application errors.',
      url: `${environment.serviceBaseUrl}/downloads/Estes820v4010.pdf`
    }];
    this.ediSets = new MatTableDataSource(ediSetData);
    this.leftNavigationService.setNavigation(`Resources`, `/resources`);
    this.promoService.setAppId('edi-request');
    this.promoService.setAppState('any');
    
 	this.authService.authStateSet.subscribe((authState: string) => {
      this.promoService.setAppId('edi-request');
      this.promoService.setAppState(authState);
    });
  }

}
