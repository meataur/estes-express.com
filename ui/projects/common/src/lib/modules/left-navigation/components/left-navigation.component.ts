import { Component, OnInit, AfterViewInit } from '@angular/core';
import { Observable } from 'rxjs';
import { LeftNavigationService } from '../services/left-navigation.service';
import { map, startWith, delay, tap } from 'rxjs/operators';

@Component({
  selector: 'lib-left-navigation',
  templateUrl: './left-navigation.component.html'
})
export class LeftNavigationComponent implements OnInit {
  leftNav$: Observable<[string, Array<any>]>;

  constructor(private leftNavigationService: LeftNavigationService) {}

  ngOnInit() {
    this.leftNav$ = this.leftNavigationService.leftNav$;
  }
}
