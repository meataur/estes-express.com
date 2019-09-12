import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageQuickLinksComponent } from './manage-quick-links.component';

describe('ManageQuickLinksComponent', () => {
  let component: ManageQuickLinksComponent;
  let fixture: ComponentFixture<ManageQuickLinksComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManageQuickLinksComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageQuickLinksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
