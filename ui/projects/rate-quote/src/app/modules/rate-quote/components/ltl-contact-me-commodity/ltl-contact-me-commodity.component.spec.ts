import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LtlContactMeCommodityComponent } from './ltl-contact-me-commodity.component';

describe('LtlContactMeCommodityComponent', () => {
  let component: LtlContactMeCommodityComponent;
  let fixture: ComponentFixture<LtlContactMeCommodityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LtlContactMeCommodityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LtlContactMeCommodityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
