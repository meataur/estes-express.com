import { Validators, FormControl } from '@angular/forms';
import { GeneralInformation } from './general-information.model';
import { MyEstesValidators } from 'common';

export class QuoteDetailsForm {
  quoteNumber = new FormControl('');
  guaranteed = new FormControl(false);
  tosChecked = new FormControl(false);

  constructor(g: GeneralInformation) {
    this.quoteNumber.setValidators([Validators.maxLength(7), Validators.minLength(7)]);
    this.guaranteed.setValidators([MyEstesValidators.required]);

    /**
     * The quote field should only be set at this point if the user is entering the create quote
     * page with the CreateFromQuote action (see BolActionEnum and CreateBolComponent for more information).
     */
    if (g.quote) {
      this.quoteNumber.setValue(g.quote);
    }
    if (g.guranteed) {
      this.guaranteed.setValue(g.guranteed);
    }
    if (g.tosChecked) {
      this.tosChecked.setValue(g.tosChecked);
    }
  }
}
