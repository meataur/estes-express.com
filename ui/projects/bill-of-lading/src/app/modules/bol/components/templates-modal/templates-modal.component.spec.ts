import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TemplatesModalComponent } from './templates-modal.component';

describe('TemplatesModalComponent', () => {
  let component: TemplatesModalComponent;
  let fixture: ComponentFixture<TemplatesModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TemplatesModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TemplatesModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
