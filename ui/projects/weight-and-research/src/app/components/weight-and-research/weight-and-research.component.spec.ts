import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WeightAndResearchComponent } from './weight-and-research.component';

describe('WeightAndResearchComponent', () => {
  let component: WeightAndResearchComponent;
  let fixture: ComponentFixture<WeightAndResearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WeightAndResearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WeightAndResearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
