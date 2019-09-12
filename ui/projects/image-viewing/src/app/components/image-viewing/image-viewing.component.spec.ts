import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ImageViewingComponent } from './image-viewing.component';

describe('ImageViewingComponent', () => {
  let component: ImageViewingComponent;
  let fixture: ComponentFixture<ImageViewingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ImageViewingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ImageViewingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
