import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HistoryBolComponent } from './history-bol.component';

describe('HistoryBolComponent', () => {
  let component: HistoryBolComponent;
  let fixture: ComponentFixture<HistoryBolComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HistoryBolComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HistoryBolComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
