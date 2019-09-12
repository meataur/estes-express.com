import { FormControl } from '@angular/forms';

export class RateQuoteTypeForm {
  ltl = new FormControl(true);
  volumeAndTruckload = new FormControl(false);
  timeCritical = new FormControl(false);

  constructor(selectedApps?: Array<'LTL' | 'VTL' | 'TC'>) {
    if (selectedApps && Array.isArray(selectedApps)) {
      this.ltl.setValue(false);
      this.volumeAndTruckload.setValue(false);
      this.timeCritical.setValue(false);

      for (const app of selectedApps) {
        switch (app.toUpperCase()) {
          case 'LTL':
            this.ltl.setValue(true);
            break;
          case 'VTL':
            this.volumeAndTruckload.setValue(true);
            break;
          case 'TC':
            this.timeCritical.setValue(true);
            break;
        }
      }
    }
  }
}

export class RateQuoteType {
  ltl = true;
  volumeAndTruckload = false;
  timeCritical = false;

  get isLTLOnly(): boolean {
    if (this.ltl && (!this.timeCritical && !this.volumeAndTruckload)) {
      return true;
    }

    return false;
  }

  get isVTLOnly(): boolean {
    if (this.volumeAndTruckload && (!this.timeCritical && !this.ltl)) {
      return true;
    }

    return false;
  }

  get isTCOnly(): boolean {
    if (this.timeCritical && (!this.volumeAndTruckload && !this.ltl)) {
      return true;
    }

    return false;
  }

  get isLTLandTCOnly(): boolean {
    if (this.timeCritical && this.ltl && !this.volumeAndTruckload) {
      return true;
    }

    return false;
  }

  constructor() {}
}
