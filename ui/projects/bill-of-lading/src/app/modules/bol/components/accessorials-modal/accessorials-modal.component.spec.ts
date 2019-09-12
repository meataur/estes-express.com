import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AccessorialsModalComponent } from './accessorials-modal.component';

describe('AccessorialsModalComponent', () => {
  let component: AccessorialsModalComponent;
  let fixture: ComponentFixture<AccessorialsModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AccessorialsModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccessorialsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
