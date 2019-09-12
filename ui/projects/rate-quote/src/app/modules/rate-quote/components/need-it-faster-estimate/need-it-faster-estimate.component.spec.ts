import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NeedItFasterEstimateComponent } from './need-it-faster-estimate.component';

describe('NeedItFasterEstimateComponent', () => {
  let component: NeedItFasterEstimateComponent;
  let fixture: ComponentFixture<NeedItFasterEstimateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NeedItFasterEstimateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NeedItFasterEstimateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
