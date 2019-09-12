import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EdiImplementationRequestComponent } from './edi-implementation-request.component';

describe('EdiImplementationRequestComponent', () => {
  let component: EdiImplementationRequestComponent;
  let fixture: ComponentFixture<EdiImplementationRequestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EdiImplementationRequestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EdiImplementationRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
