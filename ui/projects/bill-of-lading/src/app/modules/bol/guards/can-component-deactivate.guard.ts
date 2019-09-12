import { Injectable, HostListener } from '@angular/core';
import { CanDeactivate } from '@angular/router';
import { Observable, of } from 'rxjs';
import { BolService } from '../services/bol.service';
import { tap } from 'rxjs/operators';

export abstract class CanComponentDeactivate {
  protected bolService: BolService;
  abstract canDeactivate(): Observable<boolean>;

  // See https://developer.mozilla.org/en-US/docs/Web/API/WindowEventHandlers/onbeforeunload
  @HostListener('window:beforeunload', ['$event'])
  unloadNotification($event: any) {
    if (this.bolService.modified) {
      // Cancel the event
      $event.preventDefault();
      // Chrome requires returnValue to be set
      $event.returnValue = '';
    }
  }

  constructor(bolService: BolService) {
    this.bolService = bolService;
  }
}

@Injectable({
  providedIn: 'root'
})
export class CanDeactivateGuard implements CanDeactivate<CanComponentDeactivate> {
  canDeactivate(component: CanComponentDeactivate): Observable<boolean> {
    return component.canDeactivate
      ? component.canDeactivate()
      : of(true);
  }
}
