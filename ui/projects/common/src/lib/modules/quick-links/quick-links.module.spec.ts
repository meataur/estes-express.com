import { QuickLinksModule } from './quick-links.module';

describe('QuickLinksModule', () => {
  let quickLinksModule: QuickLinksModule;

  beforeEach(() => {
    quickLinksModule = new QuickLinksModule();
  });

  it('should create an instance', () => {
    expect(quickLinksModule).toBeTruthy();
  });
});
