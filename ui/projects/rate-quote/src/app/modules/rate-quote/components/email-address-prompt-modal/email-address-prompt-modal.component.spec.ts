import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmailAddressPromptModalComponent } from './email-address-prompt-modal.component';

describe('EmailAddressPromptModalComponent', () => {
  let component: EmailAddressPromptModalComponent;
  let fixture: ComponentFixture<EmailAddressPromptModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmailAddressPromptModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmailAddressPromptModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
