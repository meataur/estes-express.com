import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EdiRequestComponent } from './edi-request.component';

describe('EdiRequestComponent', () => {
  let component: EdiRequestComponent;
  let fixture: ComponentFixture<EdiRequestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EdiRequestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EdiRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
