import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ImageFetchComponent } from './image-fetch.component';

describe('ImageFetchComponent', () => {
  let component: ImageFetchComponent;
  let fixture: ComponentFixture<ImageFetchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ImageFetchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ImageFetchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
