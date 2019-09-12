import { FormControl, Validators } from '@angular/forms';
import { DocumentTypeEnum } from './document-type.enum';
import { MyEstesValidators } from 'common';

export class BolSourceForm {
  documentType = new FormControl('E');
  // templateName = new FormControl('');

  constructor(type: DocumentTypeEnum, templateName?: string) {
    this.documentType.setValidators(MyEstesValidators.required);

    if (type) {
      this.documentType.setValue(type);
    }
    // if (templateName) {
    //   this.templateName.setValue(templateName);
    // }
  }
}
