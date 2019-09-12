import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdditionalCargoLiabilityModalComponent } from './additional-cargo-liability-modal.component';

describe('AdditionalCargoLiabilityModalComponent', () => {
  let component: AdditionalCargoLiabilityModalComponent;
  let fixture: ComponentFixture<AdditionalCargoLiabilityModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdditionalCargoLiabilityModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdditionalCargoLiabilityModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
