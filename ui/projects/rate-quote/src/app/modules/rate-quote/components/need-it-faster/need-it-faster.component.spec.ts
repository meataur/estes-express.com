import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NeedItFasterComponent } from './need-it-faster.component';

describe('NeedItFasterComponent', () => {
  let component: NeedItFasterComponent;
  let fixture: ComponentFixture<NeedItFasterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NeedItFasterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NeedItFasterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
