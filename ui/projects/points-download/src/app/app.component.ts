import {
  Component,
  ComponentRef,
  ComponentFactoryResolver,
  ApplicationRef,
  Injector,
  OnInit
} from '@angular/core';
import { ComponentPortal, DomPortalOutlet } from '@angular/cdk/portal';
import { AlertComponent } from 'common';
import { AlertService } from 'common';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'Points Download';
  portalHost: DomPortalOutlet;
  alertPortal: ComponentPortal<any>;
  alertPortalComponentRef: ComponentRef<AlertComponent>;
  alerts: Array<string> = [];

  constructor(
    private _componentFactoryResolver: ComponentFactoryResolver,
    private _appRef: ApplicationRef,
    private _injector: Injector,
    private _alertService: AlertService
  ) {}

  ngOnInit() {
    this.portalHost = new DomPortalOutlet(
      document.querySelector('#portalOutlet'),
      this._componentFactoryResolver,
      this._appRef,
      this._injector
    );

    this.alertPortal = new ComponentPortal(AlertComponent);

    this._alertService.alert$.subscribe(next => {
      // if (!this.portalHost.hasAttached()) {
      //   this.alertPortalComponentRef = this.portalHost.attach(this.alertPortal);
      // }

      this.alerts.push(new Date().toString());
    });
  }

  onCloseAlert(alert: string) {
    const index = this.alerts.indexOf(alert);
    this.alerts.splice(index, 1);
  }
}
