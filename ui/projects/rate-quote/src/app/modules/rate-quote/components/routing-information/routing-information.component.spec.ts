import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RoutingInformationComponent } from './routing-information.component';

describe('RoutingInformationComponent', () => {
  let component: RoutingInformationComponent;
  let fixture: ComponentFixture<RoutingInformationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RoutingInformationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RoutingInformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
