import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateShippingLabelModalComponent } from './create-shipping-label-modal.component';

describe('CreateShippingLabelModalComponent', () => {
  let component: CreateShippingLabelModalComponent;
  let fixture: ComponentFixture<CreateShippingLabelModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateShippingLabelModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateShippingLabelModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
