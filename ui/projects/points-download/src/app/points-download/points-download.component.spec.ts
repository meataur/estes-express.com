import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PointsDownloadComponent } from './points-download.component';

describe('PointsDownloadComponent', () => {
  let component: PointsDownloadComponent;
  let fixture: ComponentFixture<PointsDownloadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PointsDownloadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PointsDownloadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
