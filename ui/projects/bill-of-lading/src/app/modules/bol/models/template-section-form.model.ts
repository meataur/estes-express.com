import { FormControl, Validators } from '@angular/forms';
import { MyEstesValidators } from 'common';

export class TemplateSectionForm {
  saveTemplate = new FormControl(false);
  templateName = new FormControl('');

  constructor(saveTemplate = false, templateName = '', disabled = false) {
    this.templateName.setValidators(Validators.maxLength(255));
    if (saveTemplate) {
      this.saveTemplate.setValue(saveTemplate);
      this.templateName.setValidators([MyEstesValidators.required, Validators.maxLength(255)]);
      if (templateName) {
        this.templateName.setValue(templateName);
      }
    }
    if (disabled) {
      this.saveTemplate.disable();
      this.templateName.disable();
    }
  }
}
