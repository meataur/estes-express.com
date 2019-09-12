import { FormControl } from '@angular/forms';
import { MyEstesValidators, RegEx, textAreaValidator, MessageConstants } from 'common';

export class EmailForm {
  emailAddresses = new FormControl('');

  constructor() {
    this.emailAddresses.setValidators([
      MyEstesValidators.required,
      textAreaValidator(RegEx.email, MessageConstants.invalidEmailTextArea, 5)
    ]);
  }
}
