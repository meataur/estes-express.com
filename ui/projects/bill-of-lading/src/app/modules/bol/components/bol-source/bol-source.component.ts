import {
  Component,
  OnInit,
  SimpleChanges,
  OnChanges,
  OnDestroy,
  Input,
  ViewChild
} from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { FormService } from 'common';
import { Subscription, Observable, Subject, zip } from 'rxjs';
import { BolSourceFormService } from '../../services/bol-source-form.service';
import { BillOfLading, BolSection, Template, BolActionEnum } from '../../models';
import { debounceTime, distinctUntilChanged, switchMap, map, startWith } from 'rxjs/operators';
import { BolService } from '../../services/bol.service';
import { MatAutocompleteSelectedEvent, MatAutocomplete, MatOption } from '@angular/material';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-bol-source',
  templateUrl: './bol-source.component.html',
  styleUrls: ['./bol-source.component.scss']
})
export class BolSourceComponent extends BolSection implements OnInit, OnChanges, OnDestroy {
  /**
   * @description The templateName property of the template.
   */
  // @Input() template: Template;
  stop$ = new Subject<boolean>();
  formGroup: FormGroup;
  formSub: Subscription;
  filterOptions: Observable<Array<Template>>;
  routeSub: Subscription;
  action: BolActionEnum;
  @ViewChild(MatAutocomplete) autocomplete: MatAutocomplete;

  constructor(
    private fb: FormBuilder,
    public formService: FormService,
    private bolSourceFormService: BolSourceFormService,
    private bolService: BolService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    super();
  }

  ngOnInit() {
    this.routeSub = zip(this.route.data, this.route.paramMap).subscribe(next => {
      const data = next[0];
      const paramMap = next[1];

      // Default the action to be 'Create'
      const action = (data['action'] as BolActionEnum) || BolActionEnum.Create;
      this.action = action;
      // let identifier = null;
      // console.log(BolActionEnum[action]);

      // switch (action) {
      //   case BolActionEnum.EditTemplate:

      //     identifier = paramMap.get('templateId');
      //     this.templateName = identifier;
      //     this.submitButtonText = this.getSubmitButtonText();
      //     this.populateFormWithTemplate(identifier);
      //     break;
      //   case BolActionEnum.CreateFromTemplate:
      //     identifier = paramMap.get('templateId');
      //     this.submitButtonText = this.getSubmitButtonText();
      //     this.populateFormWithTemplate(identifier);
      //     break;
      //   case BolActionEnum.CreateFromDraft:
      //     identifier = paramMap.get('draftId');
      //     this.submitButtonText = this.getSubmitButtonText();
      //     this.populateFormWithDraft(identifier);
      //     break;
      // }
    });

    this.formSub = this.bolSourceFormService.bolSourceForm$.subscribe(fg => {
      this.stop$.next(true);
      this.formGroup = fg;

      // this.filterOptions = this.templateName.valueChanges.pipe(
      //   startWith(''),
      //   debounceTime(200),
      //   distinctUntilChanged(),
      //   switchMap(s => {
      //     return this.bolService.getTemplates(
      //       { page: 1, size: 1000, sort: 'templateName', direction: 'asc' },
      //       { templateName: s, filterBy: 'TEMPLATE_NAME' }
      //     );
      //   }),
      //   map(res => res.content)
      // );
    });
  }

  // get showTemplateDropdown(): boolean {
  //   return this.action !== BolActionEnum.EditTemplate;
  // }

  // get introductionText(): string {
  //   switch (this.action) {
  //     case BolActionEnum.EditTemplate:
  //       return `Create a BOL from a blank form:`;
  //     default:
  //       return `Create a BOL from a blank form or select a saved BOL Template:`;
  //   }
  // }

  displayFn(user?: Template): string | undefined {
    return user ? user.templateName : undefined;
  }

  onOptionSelected(e: MatAutocompleteSelectedEvent) {
    this.navigateToCreateFromTemplate(e.option.value.templateName);
  }

  private navigateToCreateFromTemplate(templateName: string) {
    this.router.navigate(['/bol/create/template', templateName]);
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.draft && changes.draft.currentValue) {
      this.bolSourceFormService.resetForm(
        (changes.draft.currentValue as BillOfLading).documentType
      );
    }
    // if (changes.template && changes.template.currentValue) {
    //   this.templateName.setValue(changes.template.currentValue);
    // }
  }

  // private selectTemplate(templateName: string) {
  //   console.log(`selecting template...`);
  //   this.bolService
  //     .getTemplates(
  //       { page: 1, size: 1, sort: 'templateName', direction: 'asc' },
  //       { templateName: templateName, filterBy: 'TEMPLATE_NAME' }
  //     )
  //     .subscribe(t => {
  //       this.templateName.setValue(t.content[0]);
  //       this.navigateToCreateFromTemplate((this.templateName.value as Template).templateName);
  //     });
  // }

  ngOnDestroy() {
    this.stop$.next(true);
    this.stop$.unsubscribe();
    this.formSub.unsubscribe();
  }

  get documentType() {
    return this.formGroup.controls['documentType'];
  }
  // get templateName() {
  //   return this.formGroup.controls['templateName'];
  // }
}
