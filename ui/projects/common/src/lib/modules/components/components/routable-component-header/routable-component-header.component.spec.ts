import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RoutableComponentHeaderComponent } from './routable-component-header.component';

describe('RoutableComponentHeaderComponent', () => {
  let component: RoutableComponentHeaderComponent;
  let fixture: ComponentFixture<RoutableComponentHeaderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RoutableComponentHeaderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RoutableComponentHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
