import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PcRaterDownloadComponent } from './pc-rater-download.component';

describe('PcRaterDownloadComponent', () => {
  let component: PcRaterDownloadComponent;
  let fixture: ComponentFixture<PcRaterDownloadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PcRaterDownloadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PcRaterDownloadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
