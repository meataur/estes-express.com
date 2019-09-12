import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TemplateAdvancedSearchComponent } from './template-advanced-search.component';

describe('TemplateAdvancedSearchComponent', () => {
  let component: TemplateAdvancedSearchComponent;
  let fixture: ComponentFixture<TemplateAdvancedSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TemplateAdvancedSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TemplateAdvancedSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
