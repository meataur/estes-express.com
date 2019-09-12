import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReferenceNumbersListComponent } from './reference-numbers-list.component';

describe('ReferenceNumbersListComponent', () => {
  let component: ReferenceNumbersListComponent;
  let fixture: ComponentFixture<ReferenceNumbersListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReferenceNumbersListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReferenceNumbersListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
