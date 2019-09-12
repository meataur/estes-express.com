import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HistoryAdvancedSearchComponent } from './history-advanced-search.component';

describe('HistoryAdvancedSearchComponent', () => {
  let component: HistoryAdvancedSearchComponent;
  let fixture: ComponentFixture<HistoryAdvancedSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HistoryAdvancedSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HistoryAdvancedSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
