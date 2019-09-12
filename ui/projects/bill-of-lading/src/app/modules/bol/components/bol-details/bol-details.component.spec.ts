import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BolDetailsComponent } from './bol-details.component';

describe('BolDetailsComponent', () => {
  let component: BolDetailsComponent;
  let fixture: ComponentFixture<BolDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BolDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BolDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
