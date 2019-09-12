import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ImageFetchWeightAndResearchComponent } from './image-fetch-weight-and-research.component';

describe('ImageFetchComponent', () => {
  let component: ImageFetchWeightAndResearchComponent;
  let fixture: ComponentFixture<ImageFetchWeightAndResearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ImageFetchWeightAndResearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ImageFetchWeightAndResearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
