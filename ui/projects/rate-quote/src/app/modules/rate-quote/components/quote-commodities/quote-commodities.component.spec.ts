import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuoteCommoditiesComponent } from './quote-commodities.component';

describe('QuoteCommoditiesComponent', () => {
  let component: QuoteCommoditiesComponent;
  let fixture: ComponentFixture<QuoteCommoditiesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuoteCommoditiesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuoteCommoditiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
