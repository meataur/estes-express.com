import { FormControl, Validators } from '@angular/forms';
import { textAreaValidator, RegEx, dateValidator } from 'common';

export class WeightAndResearchForm {
  accountNumber = new FormControl('', Validators.required);
  endDate = new FormControl('', dateValidator());
  searchBy = new FormControl('Other');
  searchTerm = new FormControl('', { updateOn: 'blur' });
  startDate = new FormControl('', dateValidator());

  constructor() {
    this.searchTerm.setValidators(
      textAreaValidator(RegEx.anythingButWhitespace, `Please enter a valid value per line.`)
    );
  }
}
