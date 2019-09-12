import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BolSourceComponent } from './bol-source.component';

describe('BolSourceComponent', () => {
  let component: BolSourceComponent;
  let fixture: ComponentFixture<BolSourceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BolSourceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BolSourceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
