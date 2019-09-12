import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AccessorialsComponent } from './accessorials.component';

describe('AccessorialsComponent', () => {
  let component: AccessorialsComponent;
  let fixture: ComponentFixture<AccessorialsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AccessorialsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccessorialsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
