import { Component, OnInit, OnChanges, OnDestroy, SimpleChanges, Input } from '@angular/core';
import { Subscription } from 'rxjs';
import { BolSection } from '../../models';
import { FormGroup } from '@angular/forms';
import { TemplateSectionFormService } from '../../services/template-section-form.service';
import { FormService } from 'common';

@Component({
  selector: 'app-template-section',
  templateUrl: './template-section.component.html',
  styleUrls: ['./template-section.component.scss']
})
export class TemplateSectionComponent extends BolSection implements OnInit, OnChanges, OnDestroy {
  @Input() editTemplateName: string;
  formSub: Subscription;
  formGroup: FormGroup;

  constructor(
    private templateSectionFormService: TemplateSectionFormService,
    public formService: FormService
  ) {
    super();
  }

  ngOnInit() {
    this.formSub = this.templateSectionFormService.templateSectionForm$.subscribe(fg => {
      this.formGroup = fg;
    });
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.editTemplateName) {
      const isSet = !!changes.editTemplateName.currentValue;
      if (!isSet) {
        this.templateSectionFormService.resetForm();
      } else {
        this.templateSectionFormService.resetForm(
          true,
          changes.editTemplateName.currentValue,
          true
        );
      }
    }
  }

  ngOnDestroy() {}

  get saveTemplate() {
    return this.formGroup.controls['saveTemplate'];
  }
  get templateName() {
    return this.formGroup.controls['templateName'];
  }
}
