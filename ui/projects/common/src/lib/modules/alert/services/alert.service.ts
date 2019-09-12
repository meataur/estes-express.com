import { Injectable } from '@angular/core';
import { Subject, Observable } from 'rxjs';

export enum AlertTypeEnum {
  Success = 1,
  Error,
  Info
}

@Injectable({
  providedIn: 'root'
})
export class AlertService {
  private alertSource = new Subject<[AlertTypeEnum, string]>();

  alert$ = this.alertSource.asObservable();

  constructor() {}

  success(message: string) {
    // console.log(`success requested. emitting...`);
    this.alertSource.next([AlertTypeEnum.Success, message]);
  }

  error(message: string) {
    this.alertSource.next([AlertTypeEnum.Error, message]);
  }

  info(message: string) {
    this.alertSource.next([AlertTypeEnum.Info, message]);
  }
}
