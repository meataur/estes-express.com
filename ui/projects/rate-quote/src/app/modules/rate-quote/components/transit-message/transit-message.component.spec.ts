import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TransitMessageComponent } from './transit-message.component';

describe('TransitMessageComponent', () => {
  let component: TransitMessageComponent;
  let fixture: ComponentFixture<TransitMessageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TransitMessageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransitMessageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
