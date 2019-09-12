import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FreightInformationComponent } from './freight-information.component';

describe('FreightInformationComponent', () => {
  let component: FreightInformationComponent;
  let fixture: ComponentFixture<FreightInformationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FreightInformationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FreightInformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
