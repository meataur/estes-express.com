import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommodityInformationComponent } from './commodity-information.component';

describe('CommodityInformationComponent', () => {
  let component: CommodityInformationComponent;
  let fixture: ComponentFixture<CommodityInformationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommodityInformationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommodityInformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
