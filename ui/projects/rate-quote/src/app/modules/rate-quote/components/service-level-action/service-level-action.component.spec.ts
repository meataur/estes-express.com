import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceLevelActionComponent } from './service-level-action.component';

describe('ServiceLevelActionComponent', () => {
  let component: ServiceLevelActionComponent;
  let fixture: ComponentFixture<ServiceLevelActionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ServiceLevelActionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServiceLevelActionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
