import { Component, OnInit } from '@angular/core';
import { LeftNavigationService } from 'common';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  constructor(public leftNavigationService: LeftNavigationService) {}

  ngOnInit() {
    this.leftNavigationService.setNavigation(`Manage`, `/manage`);
  }
}
