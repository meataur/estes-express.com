import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NeedServiceHelpComponent } from './need-service-help.component';

describe('NeedServiceHelpComponent', () => {
  let component: NeedServiceHelpComponent;
  let fixture: ComponentFixture<NeedServiceHelpComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NeedServiceHelpComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NeedServiceHelpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
