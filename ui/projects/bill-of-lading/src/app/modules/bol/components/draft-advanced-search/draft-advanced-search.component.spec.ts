import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DraftAdvancedSearchComponent } from './draft-advanced-search.component';

describe('DraftAdvancedSearchComponent', () => {
  let component: DraftAdvancedSearchComponent;
  let fixture: ComponentFixture<DraftAdvancedSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DraftAdvancedSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DraftAdvancedSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
