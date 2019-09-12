import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateBolComponent } from './create-bol.component';

describe('CreateBolComponent', () => {
  let component: CreateBolComponent;
  let fixture: ComponentFixture<CreateBolComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateBolComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateBolComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
