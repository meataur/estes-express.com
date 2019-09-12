import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TemplatesBolComponent } from './templates-bol.component';

describe('TemplatesBolComponent', () => {
  let component: TemplatesBolComponent;
  let fixture: ComponentFixture<TemplatesBolComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TemplatesBolComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TemplatesBolComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
