import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DisclosuresModalComponent } from './disclosures-modal.component';

describe('DisclosuresModalComponent', () => {
  let component: DisclosuresModalComponent;
  let fixture: ComponentFixture<DisclosuresModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DisclosuresModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DisclosuresModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
