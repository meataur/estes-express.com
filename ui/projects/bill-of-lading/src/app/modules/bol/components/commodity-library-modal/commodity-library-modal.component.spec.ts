import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommodityLibraryModalComponent } from './commodity-library-modal.component';

describe('CommodityLibraryModalComponent', () => {
  let component: CommodityLibraryModalComponent;
  let fixture: ComponentFixture<CommodityLibraryModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommodityLibraryModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommodityLibraryModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
